package chiya.web.excel.rendering;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.apache.poi.ss.usermodel.Sheet;

import chiya.core.base.exception.Assert;
import chiya.core.base.loop.Loop;
import chiya.core.base.pack.IntegerPack;
import chiya.web.excel.ExcelCoordinateConfig;
import chiya.web.excel.ExcelUtil;

/**
 * 动态区块渲染模式
 * 
 * @author chiya
 *
 */
public class DynamicBlockRendering implements BaseRenderingExcel {

	/**
	 * 计算动态区块数量
	 * 
	 * @param config    配置
	 * @param dataMap   数据集
	 * @param maxCount  最大数量
	 * @param blockSize 区块数量
	 */
	private void countDynamicBlock(ExcelCoordinateConfig config, HashMap<String, List<Object>> dataMap, IntegerPack maxCount, IntegerPack blockSize) {
		// 检查区块内的引用字段产生的区块数量
		config.getListExcelCoordinateConfig().forEach(reference -> {
			if (reference.getReference() != null) {
				int count = 0;
				while (reference.getReference() != null && dataMap.containsKey(reference.getReferenceGroup(count))) {
					count++;
				}
				if (maxCount.getData() < count) { maxCount.setData(count); }
			}
			// 如果配置的引用超出了区块，则以引用到区块的行数作为复制行数
			if (blockSize.getData() < reference.getRowIndex() - config.getRowIndex()) {
				// 计算区块位置与实际内部字段的位置差，如果设置的size小于实际的，则遵循内部声明的位置计算差值
				blockSize.setData(reference.getRowIndex() - config.getRowIndex());
			}
		});
	}

	@Override
	public String getType() {
		return "DynamicBlocks";
	}

	@Override
	public void rendering(ExcelCoordinateConfig config, HashMap<String, List<Object>> dataMap, Sheet sheet, Map<String, Function<Object, String>> formatFunction, IntegerPack insertCount) {
		// 计算插入行数，需要找到起始的列并计算差值
		IntegerPack insertSize = new IntegerPack();
		IntegerPack start = new IntegerPack();
		IntegerPack maxCount = new IntegerPack();
		IntegerPack blockSize = new IntegerPack(config.getBlockSize());

		// 检查区块内的引用字段产生的区块数量
		countDynamicBlock(config, dataMap, maxCount, blockSize);

		// 复制区块
		Loop.step(maxCount.getData() - 1, index -> {
			int countLine = index == 0 ? 0 : index * blockSize.getData();
			ExcelUtil.copyRow(insertCount.getData() + config.getRowIndex() + countLine, blockSize.getData(), sheet);
		});
		// 按照动态区块进行循环
		Loop.step(maxCount.getData(), index -> {
			if (index != 0) {
				// 计算由于动态区块引发的坐标漂移
				insertCount.addAndGet(blockSize.getData());
			}
			// 计算要插入的行数
			config.getListExcelCoordinateConfig().forEach(reference -> {
				if (reference.getReference() != null) {
					if (start.getData() < reference.getRowIndex()) { start.setData(reference.getRowIndex()); }
					List<Object> data = dataMap.get(reference.getReferenceGroup(index));
					if (data != null && data.size() + reference.getRowIndex() > insertSize.getData()) { insertSize.setData(data.size() + reference.getRowIndex()); }
				}
			});
			insertSize.setData(insertSize.getData() - start.getData() - 1);
			// 先插入行数
			if (insertSize.getData() < 0) { insertSize.setData(0); }
			ExcelUtil.insertRow(start.getData() + insertCount.getData(), insertSize.getData(), sheet);

			config.getListExcelCoordinateConfig().forEach(reference -> {
				if (reference.getReference() != null) {
					List<Object> data = dataMap.get(reference.getReferenceGroup(index));
					Assert.isTrue(data == null || data.size() == 0, reference.getReferenceGroup(index) + "的值不存在");
					ExcelUtil.writeValue(sheet, reference.getRowIndex() + insertCount.getData(), reference.getCellIndex(), data, formatFunction.get(reference.getFormat()));

					if (reference.getMergeDown() > 0) {
						// 后置处理合并单元格
						ExcelUtil.mergeCell(
							sheet,
							reference.getRowIndex() + insertCount.getData(),
							reference.getCellIndex(),
							data.size(),
							true,
							reference.getMergeDown()
						);
					}
				} else if (reference.getDefalutValue() != null) {
					// 根据当前配置写入，并重新计算位置
					ExcelUtil.writeValue(sheet, reference.getRowIndex() + insertCount.getData(), reference.getCellIndex(), reference.getDefalutValue());
				}

			});

			insertCount.addAndGet(insertSize.getData());
		});

	}

	/**
	 * 获取区块行位置
	 * 
	 * @param block 区块
	 * @return 位置
	 */
	@Override
	public int getRowIndex(ExcelCoordinateConfig block) {
		return block.getRowIndex();
	}

}
