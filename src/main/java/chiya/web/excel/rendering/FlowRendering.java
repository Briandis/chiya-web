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
 * 流式布局
 * 
 * @author chiya
 *
 */
public class FlowRendering implements BaseRenderingExcel {

	@Override
	public String getType() {
		return "flow";
	}

	@Override
	public void rendering(ExcelCoordinateConfig config, HashMap<String, List<Object>> dataMap, Sheet sheet, Map<String, Function<Object, String>> formatFunction, IntegerPack insertCount) {

		// 计算插入行数
		int size = config.getListExcelCoordinateConfig().size();
		int insertSize = size % config.getLineCount() != 0 ? size / config.getLineCount() : size / config.getLineCount() + 1;

		// 先插入行数
		ExcelUtil.insertRow(config.getRowIndex(), insertSize, sheet);

		IntegerPack x = new IntegerPack(config.getRowIndex() + insertCount.getData());
		IntegerPack y = new IntegerPack(config.getCellIndex());

		Loop.forEach(config.getListExcelCoordinateConfig(), (reference, count) -> {
			// 每行数量达到设置上限后换行
			if (count != 0 && count % config.getLineCount() == 0) {
				x.incrementAndGet();
				y.setData(config.getCellIndex());
			}
			reference.getListExcelCoordinateConfig().forEach(excelConfig -> {
				if (excelConfig.getReference() != null) {
					List<Object> data = dataMap.get(excelConfig.getReference());
					Assert.isTrue(data == null || data.size() == 0, excelConfig.getReference() + "的值不存在");
					ExcelUtil.writeValue(sheet, x.getData(), y.getData(), data, formatFunction.get(excelConfig.getFormat()));

				} else if (excelConfig.getDefalutValue() != null) {
					// 如果不存在引用 则写入默认值
					ExcelUtil.writeValue(sheet, x.getData(), y.getData(), excelConfig.getDefalutValue());
				}
				y.incrementAndGet();
			});
			y.addAndGet(config.getSpacing());
		});
		insertCount.addAndGet(insertSize);
	}

}
