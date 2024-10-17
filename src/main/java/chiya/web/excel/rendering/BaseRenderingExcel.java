package chiya.web.excel.rendering;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.apache.poi.ss.usermodel.Sheet;

import chiya.core.base.pack.IntegerPack;
import chiya.web.excel.ExcelCoordinateConfig;

/**
 * 基础抽象渲染EXCEL
 * 
 * @author chiya
 *
 */
public interface BaseRenderingExcel {

	/**
	 * 获取类型
	 * 
	 * @return 类型
	 */
	String getType();

	/**
	 * 
	 * @param block          当前区块配置
	 * @param dataMap        当前数据
	 * @param sheet          当前渲染的sheet页
	 * @param formatFunction 格式化方法
	 * @param insertCount    插入行数
	 */
	void rendering(ExcelCoordinateConfig block, HashMap<String, List<Object>> dataMap, Sheet sheet, Map<String, Function<Object, String>> formatFunction, IntegerPack insertCount);

	/**
	 * 获取区块行位置
	 * 
	 * @param block 区块
	 * @return 位置
	 */
	default int getRowIndex(ExcelCoordinateConfig block) {
		sort(block);
		List<ExcelCoordinateConfig> list = block.getListExcelCoordinateConfig();
		return list != null && list.size() != 0 ? list.get(0).getRowIndex() : 0;
	}

	/**
	 * 排序
	 * 
	 * @param block
	 */
	default void sort(ExcelCoordinateConfig block) {
		block.getListExcelCoordinateConfig().sort((a, b) -> a.getRowIndex() - b.getRowIndex());
	}

}
