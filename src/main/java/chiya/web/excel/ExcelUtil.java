package chiya.web.excel;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import chiya.core.base.exception.Assert;
import chiya.core.base.loop.Loop;
import chiya.core.base.pack.IntegerPack;
import chiya.core.base.string.StringUtil;
import chiya.web.excel.rendering.RenderingExcel;

/**
 * Excel工具库
 * 
 * @author brain
 *
 */
public class ExcelUtil {
	/**
	 * 获取一个工作簿
	 * 
	 * @param inputStream 输入流
	 * @return Workbook 工作簿
	 */
	public static Workbook getWorkbook(InputStream inputStream) {
		Workbook workbook = null;
		try {
			workbook = WorkbookFactory.create(inputStream);
		} catch (IOException e) {
			Assert.fail("文件有误");
		}
		return workbook;
	}

	/**
	 * 关闭工作簿
	 * 
	 * @param workbook 工作簿
	 */
	public static void workbookClose(Workbook workbook) {
		try {
			workbook.close();
		} catch (IOException e) {}
	}

	/**
	 * 导出
	 * 
	 * @param <T>       List的类型
	 * @param list      要导出的列表
	 * @param name      导出的工作簿名称
	 * @param mapConfig 字段配置文件
	 */
	public static <T> void exportExecl(List<T> list, String name, BaseFormatter<T> baseFormatter, OutputStream outputStream) {
		Workbook workbook = new XSSFWorkbook();
		workbook.createSheet(name);
		Sheet sheet = workbook.getSheetAt(0);
		createTableHead(sheet, baseFormatter.fieldConfig);
		createTableBody(sheet, list, baseFormatter);
		try {
			workbook.write(outputStream);
		} catch (IOException e) {
			Assert.fail("写入错误");
		} finally {
			workbookClose(workbook);
		}

	}

	/**
	 * 创建表头
	 * 
	 * @param sheet    工作页
	 * @param listHead 表头字段
	 */
	public static void createTableHead(Sheet sheet, List<String> listHead) {
		Row row = sheet.createRow(0);
		for (int i = 0; i < listHead.size(); i++) {
			// 设置表头
			row.createCell(i).setCellValue(listHead.get(i));
		}
	}

	/**
	 * 创建表头
	 * 
	 * @param sheet    工作页
	 * @param listHead 表头字段
	 */
	public static void createTableHead(Sheet sheet, String... listHead) {
		Row row = sheet.createRow(0);
		for (int i = 0; i < listHead.length; i++) {
			// 设置表头
			row.createCell(i).setCellValue(listHead[i]);
		}
	}

	/**
	 * 创建表头
	 * 
	 * @param sheet 工作页
	 * @param map   表头字段
	 */
	public static <T> void createTableHead(Sheet sheet, LinkedHashMap<String, Function<T, Object>> map) {
		Row row = sheet.createRow(0);
		IntegerPack integerPack = new IntegerPack(0);
		map.forEach((key, value) -> {
			row.createCell(integerPack.getAndIncrement()).setCellValue(key);
		});
	}

	/**
	 * 装配List列表
	 * 
	 * @param <T>   List的类型
	 * @param sheet 工作页
	 * @param list  装配的内容
	 * @param map   映射位置关系
	 */
	public static <T> void createTableBody(Sheet sheet, List<T> list, BaseFormatter<T> baseFormatter) {
		HashMap<Integer, Integer> count = new HashMap<Integer, Integer>();

		IntegerPack integerPack = new IntegerPack(0);
		list.forEach(obj -> {
			Row row = sheet.createRow(integerPack.incrementAndGet());
			IntegerPack cellIndex = new IntegerPack();
			baseFormatter.fieldConfig.forEach((key, value) -> {
				// 单元默认格宽度
				if (!count.containsKey(cellIndex.getData())) { count.put(cellIndex.getData(), 10); }
				Object data = value.apply(obj);
				String v = baseFormatter.formatter(key, data);
				// 设置字符长度
				if (v != null && count.get(cellIndex.getData()) < v.length()) { count.put(cellIndex.getData(), v.length()); }
				row.createCell(cellIndex.getAndIncrement()).setCellValue(v);
			});
		});
		for (int i = 0; i < baseFormatter.fieldConfig.size(); i++) {
			sheet.setColumnWidth(i, count.get(i) * 350);
		}
	}

	/**
	 * 导入
	 * 
	 * @param <T>         导入的对象类型
	 * @param inputStream 输入流
	 * @param excelRow    读取一行后返回对象的方法
	 * @return 读取后生成的列表
	 */
	public static <T> List<T> importExecl(InputStream inputStream, ExcelRow<T> excelRow) {
		Workbook workbook = ExcelUtil.getWorkbook(inputStream);
		Sheet sheet = workbook.getSheetAt(0);
		List<T> list = importExecl(sheet, excelRow, 1);
		ExcelUtil.workbookClose(workbook);
		return list;
	}

	/**
	 * 导入
	 * 
	 * @param <T>         导入的对象类型
	 * @param inputStream 输入流
	 * @param excelRow    读取一行后返回对象的方法
	 * @param startRow    开始的行
	 * @return 读取后生成的列表
	 */
	public static <T> List<T> importExecl(Sheet sheet, ExcelRow<T> excelRow, int startRow) {
		int last = sheet.getLastRowNum();
		List<T> list = new ArrayList<T>();
		ChiyaRow chiyaRow = new ChiyaRow();
		for (int i = startRow; i < last + 1; i++) {
			chiyaRow.setRow(sheet.getRow(i));
			// 默认全部允许添加
			chiyaRow.setAllowAdd(true);
			T object = excelRow.nextRow(chiyaRow);
			if (chiyaRow.isAllowAdd()) { list.add(object); }

		}
		return list;
	}

	/**
	 * 导入
	 * 
	 * @param <T>         导入的对象类型
	 * @param inputStream 输入流
	 * @param sheetName   工作页名称
	 * @param excelRow    读取一行后返回对象的方法
	 * @param startRow    开始的行
	 * @return 读取后生成的列表
	 */
	public static <T> List<T> importExecl(InputStream inputStream, String sheetName, ExcelRow<T> excelRow, int startRow) {
		Workbook workbook = ExcelUtil.getWorkbook(inputStream);
		Sheet sheet = workbook.getSheet(sheetName);
		List<T> list = importExecl(sheet, excelRow, startRow);
		ExcelUtil.workbookClose(workbook);
		return list;
	}

	/**
	 * 获取或创建一个sheet页
	 * 
	 * @param workbook 工作簿
	 * @param name     sheet名称
	 * @return sheet对象
	 */
	public static Sheet getOrCreateSheet(Workbook workbook, String name) {
		Sheet sheet = workbook.getSheet(name);
		if (sheet == null) { sheet = workbook.createSheet(name); }
		return sheet;
	}

	/**
	 * 向指定单元格中写入数据
	 * 
	 * @param sheet     sheet页
	 * @param rowIndex  row坐标，从0计算
	 * @param CellIndex cell坐标，从0计算
	 * @param value     写入的对象
	 */
	public static void writeValue(Sheet sheet, int rowIndex, int CellIndex, Object value) {
		Row row = sheet.getRow(rowIndex);
		if (row == null) { row = sheet.createRow(rowIndex); }
		Cell cell = row.getCell(CellIndex);
		if (cell == null) { cell = row.createCell(CellIndex); }

		if (value != null) { cell.setCellValue(value.toString()); }

	}

	/**
	 * 向指定单元格中写入数据
	 * 
	 * @param sheet          sheet页
	 * @param rowIndex       row坐标，从0计算
	 * @param cellIndex      cell坐标，从0计算
	 * @param value          写入的对象
	 * @param formatFunction 格式化方法
	 */
	public static void writeValue(Sheet sheet, int rowIndex, int cellIndex, List<Object> value, Function<Object, String> formatFunction) {
		if (value != null) {
			Loop.forEach(value, (obj, index) -> {
				if (obj != null) {
					if (formatFunction != null) {
						writeValue(sheet, rowIndex + index, cellIndex, formatFunction.apply(obj));
					} else {
						writeValue(sheet, rowIndex + index, cellIndex, obj.toString());
					}
				}
			});
		}
	}

	/**
	 * 向指定单元格中写入数据
	 * 
	 * @param sheet     sheet页
	 * @param rowIndex  row坐标，从0计算
	 * @param cellIndex cell坐标，从0计算
	 * @param value     写入的对象
	 */
	public static void writeValue(Sheet sheet, int rowIndex, int cellIndex, List<Object> value) {
		writeValue(sheet, rowIndex, cellIndex, value, null);
	}

	/**
	 * 获取行的样式
	 * 
	 * @param row 行信息
	 * @return 行每个单元格的样式
	 */
	public static HashMap<Integer, CellStyle> getRowStyle(Row row) {
		HashMap<Integer, CellStyle> hashMap = new HashMap<>();
		if (row != null) { row.forEach(cell -> hashMap.put(cell.getColumnIndex(), cell.getCellStyle())); }
		return hashMap;
	}

	/**
	 * 获取一行中所有数据
	 * 
	 * @param row 一行中所有数据
	 * @return 当前行的列表
	 */
	public static HashMap<Integer, String> getRowValue(Row row) {
		HashMap<Integer, String> listData = new HashMap<>();
		if (row != null) { row.forEach(cell -> listData.put(cell.getColumnIndex(), ChiyaRow.DATA_FORMATTER.formatCellValue(cell))); }
		return listData;
	}

	/**
	 * 设置改行的样式
	 * 
	 * @param row     样式
	 * @param hashMap 每个单元格样式配置
	 */
	public static void setRowStyle(Row row, HashMap<Integer, CellStyle> hashMap) {
		hashMap.forEach((index, cellStyle) -> {
			if (cellStyle != null) {
				Cell cell = row.getCell(index);
				if (cell == null) { cell = row.createCell(index); }
				cell.setCellStyle(cellStyle);
			}
		});
	}

	/**
	 * 设置单元格的值
	 * 
	 * @param row     样式
	 * @param hashMap 每个单元格的值
	 */
	public static void setRowValue(Row row, HashMap<Integer, String> hashMap) {
		hashMap.forEach((index, data) -> {
			if (data != null) {
				Cell cell = row.getCell(index);
				if (cell == null) { cell = row.createCell(index); }
				cell.setCellValue(data.toString());
			}
		});
	}

	/**
	 * 在指定行数前插入指定数量的空行，保留插入行的样式
	 * 
	 * @param start 参考行数
	 * @param count 插入多少行
	 * @param sheet 当前操作的sheet页
	 */
	public static void insertRow(int start, int count, Sheet sheet) {
		HashMap<Integer, CellStyle> hashMap = getRowStyle(sheet.getRow(start));
		if (sheet.getRow(start) == null || count == 0) { return; }
		int temp = start == sheet.getLastRowNum() ? sheet.getLastRowNum() + 1 : sheet.getLastRowNum();
		sheet.shiftRows(start + 1, temp, count);

		for (int i = start; i < start + count + 1; i++) {
			Row row = sheet.getRow(i);
			if (row == null) { row = sheet.createRow(i); }
			setRowStyle(row, hashMap);
		}
	}

	/**
	 * 复制行
	 * 
	 * @param startRow 起始行
	 * @param count    复制行数
	 * @param sheet    当前操作的sheet页
	 */
	public static void copyRow(int startRow, int count, Sheet sheet) {
		List<HashMap<Integer, CellStyle>> listStyle = new ArrayList<>();
		List<HashMap<Integer, String>> listData = new ArrayList<>();
		Loop.step(count, index -> {
			Row row = sheet.getRow(startRow + index);
			listStyle.add(getRowStyle(row));
			listData.add(getRowValue(row));
		});
		if (sheet.getRow(startRow) == null || count == 0) { return; }

		sheet.shiftRows(startRow, sheet.getLastRowNum(), count);

		for (int i = startRow; i < startRow + count; i++) {
			Row row = sheet.getRow(i);
			if (row == null) { row = sheet.createRow(i); }
			setRowStyle(row, listStyle.get(i - startRow));
			setRowValue(row, listData.get(i - startRow));
			copyMergedRegions(sheet, i + count, i);
		}
	}

	/**
	 * 复制合并信息
	 * 
	 * @param sourceSheet    当前数据源
	 * @param sourceRowIndex 原始目标行
	 * @param targetRowIndex 目标行
	 */
	private static void copyMergedRegions(Sheet sourceSheet, int sourceRowIndex, int targetRowIndex) {
		for (int i = 0; i < sourceSheet.getNumMergedRegions(); i++) {
			CellRangeAddress range = sourceSheet.getMergedRegion(i);
			if (range.getFirstRow() == sourceRowIndex) {
				// 创建新的合并区域
				CellRangeAddress newRange = new CellRangeAddress(
					targetRowIndex,
					targetRowIndex + (range.getLastRow() - range.getFirstRow()),
					range.getFirstColumn(),
					range.getLastColumn()
				);
				sourceSheet.addMergedRegion(newRange);
			}
		}
	}

	/**
	 * sheet页写入数据
	 * 
	 * @param listConfit        坐标配置
	 * @param dataMap           数据
	 * @param sheet             所在sheet页
	 * @param formatFunctionMap 格式化方法配置
	 */
	public static void writeData(List<ExcelCoordinateConfig> listConfit, HashMap<String, List<Object>> dataMap, Sheet sheet, Map<String, Function<Object, String>> formatFunctionMap) {
		// 方便操作进行实例化
		if (formatFunctionMap == null) { formatFunctionMap = new HashMap<>(); }
		Map<String, Function<Object, String>> formatFunction = formatFunctionMap;
		// 对配置进行排序，按照区块标记的行号排序
		listConfit.sort((a, b) -> RenderingExcel.getBlockRow(a) - RenderingExcel.getBlockRow(b));
		IntegerPack insertCount = new IntegerPack();
		listConfit.forEach(config -> RenderingExcel.getRendering(config).rendering(config, dataMap, sheet, formatFunction, insertCount));
	}

	/**
	 * sheet页写入数据
	 * 
	 * @param listConfit 坐标配置
	 * @param dataMap    数据
	 * @param sheet      所在sheet页
	 */
	public static void writeData(List<ExcelCoordinateConfig> listConfit, HashMap<String, List<Object>> dataMap, Sheet sheet) {
		writeData(listConfit, dataMap, sheet, null);
	}

	/**
	 * sheet页写入数据
	 * 
	 * @param listConfit 坐标配置
	 * @param dataMap    数据
	 * @param sheet      所在sheet页
	 */
	public static void writeData(List<ExcelCoordinateConfig> listConfit, HashMap<String, List<Object>> dataMap, Workbook workbook) {
		writeData(listConfit, dataMap, workbook, null);

	}

	/**
	 * sheet页写入数据
	 * 
	 * @param listConfit        坐标配置
	 * @param dataMap           数据
	 * @param sheet             所在sheet页
	 * @param formatFunctionMap 格式化方法配置
	 */
	public static void writeData(List<ExcelCoordinateConfig> listConfit, HashMap<String, List<Object>> dataMap, Workbook workbook, Map<String, Function<Object, String>> formatFunctionMap) {
		HashMap<String, List<ExcelCoordinateConfig>> hashMap = new HashMap<>();
		listConfit.forEach(config -> {
			if (!hashMap.containsKey(config.getSheetName())) { hashMap.put(config.getSheetName(), new ArrayList<>()); }
			hashMap.get(config.getSheetName()).addAll(config.getListExcelCoordinateConfig());
		});
		hashMap.forEach((sheetName, list) -> {
			Sheet sheet = getOrCreateSheet(workbook, sheetName);
			writeData(list, dataMap, sheet, formatFunctionMap);
		});

	}

	/**
	 * 合并单元格
	 * 
	 * @param sheet     合并的sheet业
	 * @param rowIndex  行坐标
	 * @param cellIndex 列坐标
	 * @param dataSize  数据大小
	 * @param dataRow   数据是按照行排布
	 * @param mergeSize 合并大小
	 */
	public static void mergeCell(Sheet sheet, int rowIndex, int cellIndex, int dataSize, boolean dataRow, int mergeSize) {
		CellStyle style = sheet.getWorkbook().createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER); // 水平居中
		style.setVerticalAlignment(VerticalAlignment.CENTER); // 垂直居中
		if (dataRow) {
			Loop.step(dataSize, i -> {
				Row row = sheet.getRow(rowIndex + i);
				if (row == null) { row = sheet.createRow(rowIndex + i); }
				Cell cell = row.getCell(cellIndex);
				if (cell == null) { cell = row.createCell(cellIndex); }
				if (!StringUtil.isEmpty(ChiyaRow.DATA_FORMATTER.formatCellValue(cell))) {
					// 按照行合并
					sheet.addMergedRegion(new CellRangeAddress(rowIndex + i, rowIndex + i + mergeSize, cellIndex, cellIndex));
					// 设置样式
					cell.setCellStyle(style);
				}
			});
		} else {
			Loop.step(dataSize, i -> {
				Row row = sheet.getRow(rowIndex);
				if (row == null) { row = sheet.createRow(rowIndex); }
				Cell cell = row.getCell(cellIndex + i);
				if (cell == null) { cell = row.createCell(cellIndex + i); }
				if (!StringUtil.isEmpty(ChiyaRow.DATA_FORMATTER.formatCellValue(cell))) {
					// 按照列合并
					sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, cellIndex + i, cellIndex + i + mergeSize));
					// 设置样式
					cell.setCellStyle(style);
				}
			});
		}
	}
}
