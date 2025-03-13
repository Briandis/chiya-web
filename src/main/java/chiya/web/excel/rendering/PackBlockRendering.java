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
 * 
 * @author chiya
 *
 */
public class PackBlockRendering implements BaseRenderingExcel {

	@Override
	public String getType() {
		return "PackBlock";
	}

	/**
	 * 包装块，包装块会复制自身，内层不是单元格引用，而是区块
	 */
	@Override
	public void rendering(ExcelCoordinateConfig packConfig, HashMap<String, List<Object>> dataMap, Sheet sheet, Map<String, Function<Object, String>> formatFunction, IntegerPack insertCount) {
		// 计算插入行数，需要找到起始的列并计算差值
		IntegerPack insertSize = new IntegerPack();
		IntegerPack start = new IntegerPack();
		HashMap<String, Integer> groupCount = new HashMap<>();
		IntegerPack maxCount = new IntegerPack();
		IntegerPack blockSize = new IntegerPack(packConfig.getBlockSize());
		IntegerPack blockWidth = new IntegerPack(packConfig.getBlockWidth());
		IntegerPack blockCellCount = new IntegerPack(0);
		IntegerPack tempInsertCount = new IntegerPack(0);
		IntegerPack allTempInserMaxCount = new IntegerPack(0);

		boolean leftRightFlag = packConfig.getDirection() == ChiyaConstant.Direction.LEFT || packConfig.getDirection() == ChiyaConstant.Direction.RIGHT;
		boolean upDownFlag = packConfig.getDirection() == ChiyaConstant.Direction.UP || packConfig.getDirection() == ChiyaConstant.Direction.DOWN;

		// 内部可能包含多个子区块，所以需要统计内部所有的
		packConfig.getListExcelCoordinateConfig().forEach(config -> {
			// 检查区块内的引用字段产生的区块数量
			config.getListExcelCoordinateConfig().forEach(reference -> {
				if (reference.getReference() != null) {
					int count = 0;
					while (reference.getReference() != null && dataMap.containsKey(reference.getReferenceGroup(count))) {
						count++;
					}
					groupCount.put(reference.getReference(), count);
					if (maxCount.getData() < count) { maxCount.setData(count); }
				}
				if (blockSize.getData() < reference.getRowIndex() - packConfig.getRowIndex()) {
					// 计算区块位置与实际内部字段的位置差，如果设置的size小于实际的，则遵循内部声明的位置计算差值
					blockSize.setData(reference.getRowIndex() - packConfig.getRowIndex());
				}
			});
		});

		// 检查渲染方向
		if (upDownFlag) {
			// 复制区块，纵向复制
			// 先复制未渲染区块行，该阶段不需要考虑数据行数
			Loop.step(maxCount.getData() - 1, index -> {
				int countLine = index == 0 ? 0 : index * blockSize.getData();
				ExcelUtil.copyRow(insertCount.getData() + packConfig.getRowIndex() + countLine, blockSize.getData(), sheet);
			});
		} else if (leftRightFlag) {
			// 横向复制
			Loop.step(maxCount.getData() - 1, index -> {
				int countLine = (index + 1) * blockWidth.getData();
				// 复制的时候应当考虑到之前新增的行数导致的偏移
				ExcelUtil.copy(
					packConfig.getRowIndex() + insertCount.getData(),
					packConfig.getCellIndex(),
					packConfig.getBlockSize(),
					packConfig.getBlockWidth(),
					packConfig.getRowIndex() + insertCount.getData(),
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
					insertCount.addAndGet(blockSize.getData());
				}
				if (leftRightFlag) {
					// 计算由于动态区块引发的坐标漂移
					blockCellCount.addAndGet(packConfig.getBlockWidth());
					tempInsertCount.setData(0);
				}
			}

			// 进入深层坐标
			packConfig.getListExcelCoordinateConfig().forEach(block -> {
				// 起始位置在区块中重复执行需要清零处理，不然会出现计算错误
				start.setData(0);
				insertSize.setData(0);

				// 计算要插入的行数
				block.getListExcelCoordinateConfig().forEach(reference -> {
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

				if (upDownFlag) {
					// 只有纵向渲染的时候才进行行复制
					ExcelUtil.insertRow(start.getData() + insertCount.getData() + tempInsertCount.getData(), insertSize.getData(), sheet);
				}
				if (leftRightFlag) {
					// 左右排布下插入需要忽略某些行
					ExcelUtil.insertRow(start.getData() + insertCount.getData() + tempInsertCount.getData(), insertSize.getData(), sheet);
					ExcelUtil.moveUpColumn(
						sheet,
						packConfig.getCellIndex() + (index * packConfig.getBlockWidth()),
						packConfig.getCellIndex() + packConfig.getBlockWidth() + (index * packConfig.getBlockWidth()),
						start.getData() + insertCount.getData() + 1 + tempInsertCount.getData(),
						insertSize.getData()
					);
				}

				block.getListExcelCoordinateConfig().forEach(reference -> {
					if (reference.getReference() != null) {
						List<Object> data = dataMap.get(reference.getReferenceGroup(index));
						Assert.isTrue(data == null || data.size() == 0, reference.getReferenceGroup(index) + "的值不存在");
						ExcelUtil.writeValue(
							sheet,
							reference.getRowIndex() + insertCount.getData() + tempInsertCount.getData(),
							reference.getCellIndex() + blockCellCount.getData(),
							data,
							formatFunction.get(reference.getFormat()),
							reference.getDirection()
						);
						if (reference.getMergeDown() > 0) {
							// 后置处理合并单元格
							ExcelUtil.mergeCell(
								sheet,
								reference.getRowIndex() + insertCount.getData() + tempInsertCount.getData(),
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
							reference.getRowIndex() + insertCount.getData() + tempInsertCount.getData(),
							reference.getCellIndex() + blockCellCount.getData(),
							reference.getDefalutValue()
						);
					}

				});

				if (upDownFlag) {
					// 只有这两个方向的时候才做行增加
					// 包装去看内的区块方向先不考虑，默认为普通区块
					insertCount.addAndGet(insertSize.getData());
				}
				if (leftRightFlag) {
					// 左右复制使用临时
					tempInsertCount.addAndGet(insertSize.getData());
				}

			});
			if (tempInsertCount.getData() > allTempInserMaxCount.getData()) {
				// 存储插入的最大行数
				allTempInserMaxCount.setData(tempInsertCount.getData());
			}
		});
		if (leftRightFlag) {
			// 在渲染完该区块后再累加插入行数，因为横向，所以只记录最大插入
			insertCount.addAndGet(allTempInserMaxCount.getData());
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

	/**
	 * 排序
	 * 
	 * @param block
	 */
	@Override
	public void sort(ExcelCoordinateConfig block) {
		block.getListExcelCoordinateConfig().forEach(blockLine -> blockSort(blockLine));
		block.getListExcelCoordinateConfig().sort((a, b) -> getBolckRow(a) - getBolckRow(b));
	}

	/**
	 * 区块内排序
	 * 
	 * @param block
	 */
	private void blockSort(ExcelCoordinateConfig block) {
		block.getListExcelCoordinateConfig().sort((a, b) -> a.getRowIndex() - b.getRowIndex());
	}

	/**
	 * 获取内行区块起始位置
	 * 
	 * @param block 区块
	 * @return 起始位置
	 */
	private int getBolckRow(ExcelCoordinateConfig block) {
		List<ExcelCoordinateConfig> list = block.getListExcelCoordinateConfig();
		return list != null && list.size() != 0 ? list.get(0).getRowIndex() : 0;
	}
}
