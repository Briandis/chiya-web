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

import chiya.core.base.collection.ContainerUtil;
import chiya.core.base.exception.Assert;
import chiya.core.base.loop.Loop;
import chiya.core.base.number.NumberUtil;
import chiya.core.base.pack.IntegerPack;
import chiya.core.base.string.StringUtil;

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
		sheet.shiftRows(start, sheet.getLastRowNum(), count);
		for (int i = start; i < start + count; i++) {
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
		listConfit = ContainerUtil.listSort(listConfit, (o1, o2) -> {
			IntegerPack a = new IntegerPack(-1);
			IntegerPack b = new IntegerPack(-1);
			if (!StringUtil.eqString(o1.getType(), "flow")) {
				o1.getListExcelCoordinateConfig().forEach(obj -> {
					if (a.getData() == -1) { a.setData(obj.getRowIndex()); }
					if (obj.getRowIndex() < a.getData()) { a.setData(obj.getRowIndex()); }
				});
			} else {
				a.setData(o1.getRowIndex());
			}
			if (!StringUtil.eqString(o2.getType(), "flow")) {
				o2.getListExcelCoordinateConfig().forEach(obj -> {
					if (b.getData() == -1) { b.setData(obj.getRowIndex()); }
					if (obj.getRowIndex() < b.getData()) { b.setData(obj.getRowIndex()); }
				});
			} else {
				b.setData(o2.getRowIndex());
			}

			return NumberUtil.compareSize(a.getData(), b.getData());
		});
		IntegerPack insertCount = new IntegerPack();
		listConfit.forEach(config -> {
			// 流式布局处理
			if (StringUtil.eqString(config.getType(), "flow")) {
				// 计算插入行数
				int size = config.getListExcelCoordinateConfig().size();
				int insertSize = size % config.getLineCount() != 0 ? size / config.getLineCount() : size / config.getLineCount() + 1;

				// 先插入行数
				insertRow(config.getRowIndex(), insertSize, sheet);

				IntegerPack x = new IntegerPack(config.getRowIndex() + insertCount.getData());
				IntegerPack y = new IntegerPack(config.getCellIndex());

				Loop.forEach(config.getListExcelCoordinateConfig(), (block, count) -> {
					// 每行数量达到设置上限后换行
					if (count != 0 && count % config.getLineCount() == 0) {
						x.incrementAndGet();
						y.setData(config.getCellIndex());
					}
					block.getListExcelCoordinateConfig().forEach(excelConfig -> {
						if (excelConfig.getReference() != null) {
							List<Object> data = dataMap.get(excelConfig.getReference());
							Assert.isTrue(data == null || data.size() == 0, excelConfig.getReference() + "的值不存在");
							writeValue(sheet, x.getData(), y.getData(), data, formatFunction.get(excelConfig.getFormat()));

						} else if (excelConfig.getDefalutValue() != null) {
							// 如果不存在引用 则写入默认值
							writeValue(sheet, x.getData(), y.getData(), excelConfig.getDefalutValue());
						}
						y.incrementAndGet();
					});
					y.addAndGet(config.getSpacing());
				});
				insertCount.addAndGet(insertSize);
			} else if (StringUtil.eqString(config.getType(), "DynamicBlocks")) {
				// 计算插入行数，需要找到起始的列并计算差值
				IntegerPack insertSize = new IntegerPack();
				IntegerPack start = new IntegerPack();
				HashMap<String, Integer> groupCount = new HashMap<>();
				IntegerPack maxCount = new IntegerPack();
				IntegerPack blockSize = new IntegerPack(config.getBlockSize());

				// 检查区块内的引用字段产生的区块数量
				config.getListExcelCoordinateConfig().forEach(block -> {
					if (block.getReference() != null) {
						int count = 0;
						while (block.getReference() != null && dataMap.containsKey(block.getReferenceGroup(count))) {
							count++;
						}
						groupCount.put(block.getReference(), count);
						if (maxCount.getData() < count) { maxCount.setData(count); }
					}
					if (blockSize.getData() < block.getRowIndex() - config.getRowIndex()) {
						// 计算区块位置与实际内部字段的位置差，如果设置的size小于实际的，则遵循内部声明的位置计算差值
						blockSize.setData(block.getRowIndex() - config.getRowIndex());
					}
				});
				// 复制区块
				Loop.step(maxCount.getData() - 1, index -> {
					int countLine = index == 0 ? 0 : index * blockSize.getData();
					copyRow(insertCount.getData() + config.getRowIndex() + countLine, blockSize.getData(), sheet);
				});
				// 按照动态区块进行循环
				Loop.step(maxCount.getData(), index -> {
					if (index != 0) {
						// 计算由于动态区块引发的坐标漂移
						insertCount.addAndGet(blockSize.getData());
					}
					// 计算要插入的行数
					config.getListExcelCoordinateConfig().forEach(block -> {
						if (block.getReference() != null) {
							if (start.getData() < block.getRowIndex()) { start.setData(block.getRowIndex()); }
							List<Object> data = dataMap.get(block.getReferenceGroup(index));
							if (data != null && data.size() + block.getRowIndex() > insertSize.getData()) { insertSize.setData(data.size() + block.getRowIndex()); }
						}
					});
					insertSize.setData(insertSize.getData() - start.getData() - 1);
					// 先插入行数
					if (insertSize.getData() < 0) { insertSize.setData(0); }
					insertRow(start.getData() + insertCount.getData(), insertSize.getData(), sheet);

					Loop.forEach(config.getListExcelCoordinateConfig(), (block, count) -> {
						if (block.getReference() != null) {
							List<Object> data = dataMap.get(block.getReferenceGroup(index));
							Assert.isTrue(data == null || data.size() == 0, block.getReferenceGroup(index) + "的值不存在");
							writeValue(sheet, block.getRowIndex() + insertCount.getData(), block.getCellIndex(), data, formatFunction.get(block.getFormat()));

							if (block.getMergeDown() > 0) {
								// 后置处理合并单元格
								mergeCell(
									sheet,
									block.getRowIndex() + insertCount.getData(),
									block.getCellIndex(),
									data.size(),
									true,
									block.getMergeDown()
								);
							}
						} else if (block.getDefalutValue() != null) {
							// 根据当前配置写入，并重新计算位置
							writeValue(sheet, block.getRowIndex() + insertCount.getData(), block.getCellIndex(), block.getDefalutValue());
						}

					});

					insertCount.addAndGet(insertSize.getData());
				});

			} else {
				// 普通情况
				// 计算插入行数，需要找到起始的列并计算差值
				IntegerPack insertSize = new IntegerPack();
				IntegerPack start = new IntegerPack();
				config.getListExcelCoordinateConfig().forEach(block -> {
					if (block.getReference() != null) {
						if (start.getData() < block.getRowIndex()) { start.setData(block.getRowIndex()); }
						List<Object> data = dataMap.get(block.getReference());
						if (data != null && data.size() + block.getRowIndex() > insertSize.getData()) { insertSize.setData(data.size() + block.getRowIndex()); }
					}
				});
				insertSize.setData(insertSize.getData() - start.getData() - 1);
				// 先插入行数
				if (insertSize.getData() < 0) { insertSize.setData(0); }
				insertRow(start.getData() + insertCount.getData(), insertSize.getData(), sheet);

				Loop.forEach(config.getListExcelCoordinateConfig(), (block, count) -> {
					if (block.getReference() != null) {
						List<Object> data = dataMap.get(block.getReference());
						Assert.isTrue(data == null || data.size() == 0, block.getReference() + "的值不存在");
						writeValue(sheet, block.getRowIndex() + insertCount.getData(), block.getCellIndex(), data, formatFunction.get(block.getFormat()));

						if (block.getMergeDown() > 0) {
							// 后置处理合并单元格
							mergeCell(
								sheet,
								block.getRowIndex() + insertCount.getData(),
								block.getCellIndex(),
								data.size(),
								true,
								block.getMergeDown()
							);
						}
					} else if (block.getDefalutValue() != null) {
						// 根据当前配置写入，并重新计算位置
						writeValue(sheet, block.getRowIndex() + insertCount.getData(), block.getCellIndex(), block.getDefalutValue());
					}

				});

				insertCount.addAndGet(insertSize.getData());
			}
		});
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
