package chiya.web.excel;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import chiya.core.base.exception.Assert;

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
			workbook = new XSSFWorkbook(inputStream);
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
		AtomicInteger atomicInteger = new AtomicInteger(0);
		map.forEach((key, value) -> {
			row.createCell(atomicInteger.getAndIncrement()).setCellValue(key);
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

		AtomicInteger atomicInteger = new AtomicInteger(0);
		list.forEach(obj -> {
			Row row = sheet.createRow(atomicInteger.incrementAndGet());
			AtomicInteger cellIndex = new AtomicInteger();
			baseFormatter.fieldConfig.forEach((key, value) -> {
				// 单元默认格宽度
				if (!count.containsKey(cellIndex.intValue())) { count.put(cellIndex.intValue(), 10); }
				Object data = value.apply(obj);
				String v = baseFormatter.formatter(key, data);
				// 设置字符长度
				if (v != null && count.get(cellIndex.intValue()) < v.length()) { count.put(cellIndex.intValue(), v.length()); }
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
}
