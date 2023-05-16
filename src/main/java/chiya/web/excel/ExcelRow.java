package chiya.web.excel;

/**
 * excel导入处理接口
 * 
 * @author brain
 *
 */
@FunctionalInterface
public interface ExcelRow<T> {

	/**
	 * 读取一行数据并返回对象
	 * 
	 * @param row 行内单元格内容
	 * @return 对象
	 */
	T nextRow(ChiyaRow row);

}
