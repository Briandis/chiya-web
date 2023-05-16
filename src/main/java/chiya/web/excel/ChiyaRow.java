package chiya.web.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;

import chiya.core.base.number.NumberUtil;

/**
 * 封装的row方法
 * 
 * @author chiya
 *
 */
public class ChiyaRow {

	/** 表格的行 */
	private Row row;
	/** 单元格格式化工具 */
	private DataFormatter dataFormatter = new DataFormatter();
	/** 允许添加 */
	private boolean allowAdd;

	/**
	 * 是否允许添加
	 * 
	 * @return true:允许/false:不允许
	 */
	public boolean isAllowAdd() {
		return allowAdd;
	}

	/**
	 * 设置允许添加
	 * 
	 * @param allowAdd 允许添加的状态
	 */
	public void setAllowAdd(boolean allowAdd) {
		this.allowAdd = allowAdd;
	}

	/**
	 * 获取原始的row对象
	 * 
	 * @return 原始row对象
	 */
	public Row getRow() {
		return row;
	}

	/**
	 * 设置要生成的行
	 * 
	 * @param row 行
	 */
	public void setRow(Row row) {
		this.row = row;
	}

	/**
	 * 获取单元格为字符串
	 * 
	 * @param index 下标
	 * @return 字符串
	 */
	public String formatString(int index) {
		Cell cell = row.getCell(index);
		return cell != null ? dataFormatter.formatCellValue(cell) : null;
	}

	/**
	 * 获取单元格为数值
	 * 
	 * @param index 下标
	 * @return 字符串
	 */
	public Integer formatInteger(int index) {
		Cell cell = row.getCell(index);
		return cell != null ? NumberUtil.parseInt(dataFormatter.formatCellValue(cell)) : null;
	}

	/**
	 * 获取单元格为浮点数
	 * 
	 * @param index 下标
	 * @return 字符串
	 */
	public Double formatDouble(int index) {
		Cell cell = row.getCell(index);
		return cell != null ? NumberUtil.parseDouble(dataFormatter.formatCellValue(cell)) : null;
	}
}
