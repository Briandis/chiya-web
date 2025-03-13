package chiya.web.excel.rendering;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.apache.poi.ss.usermodel.Sheet;

import chiya.core.base.constant.ChiyaConstant;
import chiya.core.base.exception.Assert;
import chiya.core.base.loop.Loop;
import chiya.core.base.pack.IntegerPack;
import chiya.web.excel.ExcelCoordinateConfig;
import chiya.web.excel.ExcelUtil;

/**
 * 动态区块渲染模式，即复制自身
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
		IntegerPack blockWidth = new IntegerPack(config.getBlockWidth());
		IntegerPack blockCellCount = new IntegerPack(0);
		IntegerPack tempInsertCount = new IntegerPack(0);

		boolean leftRightFlag = config.getDirection() == ChiyaConstant.Direction.LEFT || config.getDirection() == ChiyaConstant.Direction.RIGHT;
		boolean upDownFlag = config.getDirection() == ChiyaConstant.Direction.UP || config.getDirection() == ChiyaConstant.Direction.DOWN;

		// 检查区块内的引用字段产生的区块数量
		countDynamicBlock(config, dataMap, maxCount, blockSize);

		// 检查渲染方向
		if (upDownFlag) {
			// 复制区块，纵向复制
			// 先复制未渲染区块行，该阶段不需要考虑数据行数
			Loop.step(maxCount.getData() - 1, index -> {
				int countLine = index == 0 ? 0 : index * blockSize.getData();
				ExcelUtil.copyRow(insertCount.getData() + config.getRowIndex() + countLine, blockSize.getData(), sheet);
			});
		} else if (leftRightFlag) {
			// 横向复制
			Loop.step(maxCount.getData() - 1, index -> {
				int countLine = (index + 1) * blockWidth.getData();
				// 复制的时候应当考虑到之前新增的行数导致的偏移
				ExcelUtil.copy(
					config.getRowIndex() + insertCount.getData(),
					config.getCellIndex(),
					config.getBlockSize(),
					config.getBlockWidth(),
					config.getRowIndex() + insertCount.getData(),
					countLine,
					sheet,
					sheet
				);
			});
		}

		// 按照动态区块进行循环
		Loop.step(maxCount.getData(), index -> {
			if (index != 0) {
				if (upDownFlag) {
					// 计算由于动态区块引发的坐标漂移
					insertCount.addAndGet(insertSize.getData());
				}
				if (leftRightFlag) {
					// 计算由于动态区块引发的坐标漂移
					blockCellCount.addAndGet(config.getBlockWidth());
				}
			}
			// 计算要插入的行数，并考虑该字段复制方向，只有上下才需要计入新增行数
			config.getListExcelCoordinateConfig().forEach(reference -> {
				if (reference.getReference() != null) {
					if (start.getData() < reference.getRowIndex()) { start.setData(reference.getRowIndex()); }
					List<Object> data = dataMap.get(reference.getReferenceGroup(index));
					if (data != null && data.size() + reference.getRowIndex() > insertSize.getData()
						&& (reference.getDirection() == ChiyaConstant.Direction.UP || reference.getDirection() == ChiyaConstant.Direction.DOWN)) {
						insertSize.setData(data.size() + reference.getRowIndex());
					}
				}
			});
			insertSize.setData(insertSize.getData() - start.getData() - 1);
			// 先插入行数
			if (insertSize.getData() < 0) { insertSize.setData(0); }
			// 需要区分插入方式
			if (upDownFlag) {
				// 上下排布默认插入
				ExcelUtil.insertRow(start.getData() + insertCount.getData(), insertSize.getData(), sheet);
			}
			if (leftRightFlag) {
				// 左右排布下插入需要忽略某些行
				ExcelUtil.insertRow(start.getData() + insertCount.getData(), insertSize.getData(), sheet);
				ExcelUtil.moveUpColumn(
					sheet,
					config.getCellIndex() + (index * config.getBlockWidth()),
					config.getCellIndex() + config.getBlockWidth() + (index * config.getBlockWidth()),
					start.getData() + insertCount.getData() + 1,
					insertSize.getData()
				);
				if (insertSize.getData() > tempInsertCount.getData()) {
					// 存储插入的最大行数
					tempInsertCount.setData(insertSize.getData());
				}
			}
			config.getListExcelCoordinateConfig().forEach(reference -> {
				if (reference.getReference() != null) {
					List<Object> data = dataMap.get(reference.getReferenceGroup(index));
					Assert.isTrue(data == null || data.size() == 0, reference.getReferenceGroup(index) + "的值不存在");
					// 如果是横向复制的时候，无法考虑因为插入带来的偏移问题，比如区块向右，字段也向右
					ExcelUtil.writeValue(
						sheet,
						reference.getRowIndex() + insertCount.getData(),
						reference.getCellIndex() + blockCellCount.getData(),
						data,
						formatFunction.get(reference.getFormat()),
						reference.getDirection()
					);

					if (reference.getMergeDown() > 0) {
						// 后置处理合并单元格
						ExcelUtil.mergeCell(
							sheet,
							reference.getRowIndex() + insertCount.getData(),
							reference.getCellIndex() + blockCellCount.getData(),
							data.size(),
							true,
							reference.getMergeDown()
						);
					}
				} else if (reference.getDefalutValue() != null) {
					// 根据当前配置写入，并重新计算位置
					ExcelUtil.writeValue(
						sheet,
						reference.getRowIndex() + insertCount.getData(),
						reference.getCellIndex() + blockCellCount.getData(),
						reference.getDefalutValue()
					);
				}

			});
			if (upDownFlag) {
				// 只有这两个方向的时候才做行增加
				insertCount.addAndGet(insertSize.getData());
			}
		});
		if (leftRightFlag) {
			// 在渲染完该区块后再累加插入行数，因为横向，所以只记录最大插入
			insertCount.addAndGet(tempInsertCount.getData());
		}
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
