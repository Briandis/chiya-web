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
 * 普通渲染模式
 * 
 * @author chiya
 *
 */
public class NormalRendering implements BaseRenderingExcel {

	@Override
	public String getType() {
		return "Noramal";
	}

	@Override
	public void rendering(ExcelCoordinateConfig config, HashMap<String, List<Object>> dataMap, Sheet sheet, Map<String, Function<Object, String>> formatFunction, IntegerPack insertCount) {
		// 普通情况
		// 计算插入行数，需要找到起始的列并计算差值
		IntegerPack insertSize = new IntegerPack();
		IntegerPack start = new IntegerPack();

		// 获取类型
		// 向上渲染和向下需要计算行高，左右不需要
		// 按照区块设计原则，区块内坐标是绝对的，所以允许多方向渲染，所以插入行数只需要计算向上向下渲染的即可
		config.getListExcelCoordinateConfig().forEach(reference -> {
			if (reference.getReference() != null) {
				if (start.getData() < reference.getRowIndex()) { start.setData(reference.getRowIndex()); }
				List<Object> data = dataMap.get(reference.getReference());
				if (data != null && data.size() + reference.getRowIndex() > insertSize.getData()
					&& (reference.getDirection() == ChiyaConstant.Direction.UP || reference.getDirection() == ChiyaConstant.Direction.DOWN)) {
					insertSize.setData(data.size() + reference.getRowIndex());
				}
			}
		});
		insertSize.setData(insertSize.getData() - start.getData() - 1);
		// 先插入行数
		if (insertSize.getData() < 0) { insertSize.setData(0); }
		ExcelUtil.insertRow(start.getData() + insertCount.getData(), insertSize.getData(), sheet);

		Loop.forEach(config.getListExcelCoordinateConfig(), (reference, count) -> {
			// 根据引用写入数据
			if (reference.getReference() != null) {
				List<Object> data = dataMap.get(reference.getReference());
				Assert.isTrue(data == null || data.size() == 0, reference.getReference() + "的值不存在");
				// 写入
				ExcelUtil.writeValue(
					sheet,
					reference.getRowIndex() + insertCount.getData(),
					reference.getCellIndex(),
					data,
					formatFunction.get(reference.getFormat()),
					// 按照区块配置的渲染方向写入，虽然也可以按照
					reference.getDirection()
				);

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

	}

}
