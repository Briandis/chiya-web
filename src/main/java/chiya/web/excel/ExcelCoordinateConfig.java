package chiya.web.excel;

import java.util.ArrayList;
import java.util.List;

/**
 * Excel坐标配置
 * 
 * @author chiya
 *
 */
public class ExcelCoordinateConfig {

	/** 行坐标 */
	private Integer rowIndex = 0;
	/** 列坐标 */
	private Integer cellIndex = 0;
	/** 引用的值 */
	private String reference;
	/** 默认单元格填充的值 */
	private String defalutValue;
	/** 对引用的值的格式化的方法 */
	private String format;
	/** 标注类型 */
	private String type;

	/** 特殊规则下，一行几个 */
	private Integer lineCount;
	/** 特殊规则下，间距 */
	private Integer spacing;
	/** 所在sheet名称 */
	private String sheetName;

	// 特殊规则
	/** 尝试向下合并 */
	private Integer mergeDown = 0;
	/** 尝试向右合并 */
	private Integer mergeRight = 0;

	/** 深层的相对坐标 */
	private List<ExcelCoordinateConfig> listExcelCoordinateConfig;

	/**
	 * 添加坐标配置
	 * 
	 * @param excelCoordinateConfig 坐标配置
	 * @return 自身
	 */
	public ExcelCoordinateConfig chainConfig(ExcelCoordinateConfig excelCoordinateConfig) {
		if (listExcelCoordinateConfig == null) { listExcelCoordinateConfig = new ArrayList<>(); }
		listExcelCoordinateConfig.add(excelCoordinateConfig);
		return this;
	}

	/**
	 * 获取行坐标
	 * 
	 * @return 行坐标
	 */
	public Integer getRowIndex() {
		return rowIndex;
	}

	/**
	 * 设置行坐标
	 * 
	 * @param rowIndex 行坐标
	 */
	public void setRowIndex(Integer rowIndex) {
		this.rowIndex = rowIndex;
	}

	/**
	 * 链式添加行坐标
	 * 
	 * @param rowIndex 行坐标
	 * @return 对象本身
	 */
	public ExcelCoordinateConfig chainRowIndex(Integer rowIndex) {
		setRowIndex(rowIndex);
		return this;
	}

	/**
	 * 获取列坐标
	 * 
	 * @return 列坐标
	 */
	public Integer getCellIndex() {
		return cellIndex;
	}

	/**
	 * 设置列坐标
	 * 
	 * @param cellIndex 列坐标
	 */
	public void setCellIndex(Integer cellIndex) {
		this.cellIndex = cellIndex;
	}

	/**
	 * 链式添加列坐标
	 * 
	 * @param cellIndex 列坐标
	 * @return 对象本身
	 */
	public ExcelCoordinateConfig chainCellIndex(Integer cellIndex) {
		setCellIndex(cellIndex);
		return this;
	}

	/**
	 * 获取引用的值
	 * 
	 * @return 引用的值
	 */
	public String getReference() {
		return reference;
	}

	/**
	 * 设置引用的值
	 * 
	 * @param reference 引用的值
	 */
	public void setReference(String reference) {
		this.reference = reference;
	}

	/**
	 * 链式添加引用的值
	 * 
	 * @param reference 引用的值
	 * @return 对象本身
	 */
	public ExcelCoordinateConfig chainReference(String reference) {
		setReference(reference);
		return this;
	}

	/**
	 * 获取默认单元格填充的值
	 * 
	 * @return 默认单元格填充的值
	 */
	public String getDefalutValue() {
		return defalutValue;
	}

	/**
	 * 设置默认单元格填充的值
	 * 
	 * @param defalutValue 默认单元格填充的值
	 */
	public void setDefalutValue(String defalutValue) {
		this.defalutValue = defalutValue;
	}

	/**
	 * 链式添加默认单元格填充的值
	 * 
	 * @param defalutValue 默认单元格填充的值
	 * @return 对象本身
	 */
	public ExcelCoordinateConfig chainDefalutValue(String defalutValue) {
		setDefalutValue(defalutValue);
		return this;
	}

	/**
	 * 获取对引用的值的格式化的方法
	 * 
	 * @return 对引用的值的格式化的方法
	 */
	public String getFormat() {
		return format;
	}

	/**
	 * 设置对引用的值的格式化的方法
	 * 
	 * @param format 对引用的值的格式化的方法
	 */
	public void setFormat(String format) {
		this.format = format;
	}

	/**
	 * 链式添加对引用的值的格式化的方法
	 * 
	 * @param format 对引用的值的格式化的方法
	 * @return 对象本身
	 */
	public ExcelCoordinateConfig chainFormat(String format) {
		setFormat(format);
		return this;
	}

	/**
	 * 获取深层的相对坐标
	 * 
	 * @return 深层的相对坐标
	 */
	public List<ExcelCoordinateConfig> getListExcelCoordinateConfig() {
		return listExcelCoordinateConfig;
	}

	/**
	 * 设置深层的相对坐标
	 * 
	 * @param listExcelCoordinateConfig 深层的相对坐标
	 */
	public void setListExcelCoordinateConfig(List<ExcelCoordinateConfig> listExcelCoordinateConfig) {
		this.listExcelCoordinateConfig = listExcelCoordinateConfig;
	}

	/**
	 * 链式添加深层的相对坐标
	 * 
	 * @param listExcelCoordinateConfig 深层的相对坐标
	 * @return 对象本身
	 */
	public ExcelCoordinateConfig chainListExcelCoordinateConfig(List<ExcelCoordinateConfig> listExcelCoordinateConfig) {
		setListExcelCoordinateConfig(listExcelCoordinateConfig);
		return this;
	}

	/**
	 * 获取标注类型
	 * 
	 * @return 标注类型
	 */
	public String getType() {
		return type;
	}

	/**
	 * 设置标注类型
	 * 
	 * @param type 标注类型
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 链式添加标注类型
	 * 
	 * @param type 标注类型
	 * @return 对象本身
	 */
	public ExcelCoordinateConfig chainType(String type) {
		setType(type);
		return this;
	}

	/**
	 * 获取特殊规则下，一行几个
	 * 
	 * @return 特殊规则下，一行几个
	 */
	public Integer getLineCount() {
		return lineCount;
	}

	/**
	 * 设置特殊规则下，一行几个
	 * 
	 * @param lineCount 特殊规则下，一行几个
	 */
	public void setLineCount(Integer lineCount) {
		this.lineCount = lineCount;
	}

	/**
	 * 链式添加特殊规则下，一行几个
	 * 
	 * @param lineCount 特殊规则下，一行几个
	 * @return 对象本身
	 */
	public ExcelCoordinateConfig chainLineCount(Integer lineCount) {
		setLineCount(lineCount);
		return this;
	}

	/**
	 * 获取特殊规则下，间距
	 * 
	 * @return 特殊规则下，间距
	 */
	public Integer getSpacing() {
		return spacing;
	}

	/**
	 * 设置特殊规则下，间距
	 * 
	 * @param spacing 特殊规则下，间距
	 */
	public void setSpacing(Integer spacing) {
		this.spacing = spacing;
	}

	/**
	 * 链式添加特殊规则下，间距
	 * 
	 * @param spacing 特殊规则下，间距
	 * @return 对象本身
	 */
	public ExcelCoordinateConfig chainSpacing(Integer spacing) {
		setSpacing(spacing);
		return this;
	}

	/**
	 * 获取所在sheet名称
	 * 
	 * @return 所在sheet名称
	 */
	public String getSheetName() {
		return sheetName;
	}

	/**
	 * 设置所在sheet名称
	 * 
	 * @param sheetName 所在sheet名称
	 */
	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	/**
	 * 链式添加所在sheet名称
	 * 
	 * @param sheetName 所在sheet名称
	 * @return 对象本身
	 */
	public ExcelCoordinateConfig chainSheetName(String sheetName) {
		setSheetName(sheetName);
		return this;
	}

	/**
	 * 获取尝试向下合并
	 * 
	 * @return 尝试向下合并
	 */
	public Integer getMergeDown() {
		return mergeDown;
	}

	/**
	 * 设置尝试向下合并
	 * 
	 * @param mergeDown 尝试向下合并
	 */
	public void setMergeDown(Integer mergeDown) {
		this.mergeDown = mergeDown;
	}

	/**
	 * 链式添加尝试向下合并
	 * 
	 * @param mergeDown 尝试向下合并
	 * @return 对象本身
	 */
	public ExcelCoordinateConfig chainMergeDown(Integer mergeDown) {
		setMergeDown(mergeDown);
		return this;
	}

	/**
	 * 获取尝试向右合并
	 * 
	 * @return 尝试向右合并
	 */
	public Integer getMergeRight() {
		return mergeRight;
	}

	/**
	 * 设置尝试向右合并
	 * 
	 * @param mergeRight 尝试向右合并
	 */
	public void setMergeRight(Integer mergeRight) {
		this.mergeRight = mergeRight;
	}

	/**
	 * 链式添加尝试向右合并
	 * 
	 * @param mergeRight 尝试向右合并
	 * @return 对象本身
	 */
	public ExcelCoordinateConfig chainMergeRight(Integer mergeRight) {
		setMergeRight(mergeRight);
		return this;
	}
}
