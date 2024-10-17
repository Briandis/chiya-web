package chiya.web.excel.rendering;

import java.util.concurrent.ConcurrentHashMap;

import chiya.web.excel.ExcelCoordinateConfig;

/**
 * 渲染容器
 * 
 * @author chiya
 *
 */
public class RenderingExcel {

	/** 单例唯一 */
	private static final ConcurrentHashMap<String, BaseRenderingExcel> CACHE = new ConcurrentHashMap<>();
	/** 默认通用解析 */
	private static BaseRenderingExcel normal = new NormalRendering();

	/**
	 * 注册渲染实例
	 * 
	 * @param baseRenderingExcel 渲染实例
	 */
	public static void register(BaseRenderingExcel baseRenderingExcel) {
		CACHE.put(baseRenderingExcel.getType(), baseRenderingExcel);
	}

	static {
		register(new FlowRendering());
		register(normal);
		register(new DynamicBlockRendering());
		register(new PackBlockRendering());
	}

	/**
	 * 获取渲染方式
	 * 
	 * @param name 渲染方式
	 * @return 渲染方式
	 */
	public static BaseRenderingExcel getRendering(String name) {
		return name != null && CACHE.containsKey(name) ? CACHE.get(name) : normal;
	}

	/**
	 * 获取渲染方式
	 * 
	 * @param config 配置
	 * @return 渲染方式
	 */
	public static BaseRenderingExcel getRendering(ExcelCoordinateConfig config) {
		return getRendering(config.getType());
	}

	/**
	 * 更换普通的渲染逻辑
	 * 
	 * @param baseRenderingExcel
	 */
	public static void changeNormal(BaseRenderingExcel baseRenderingExcel) {
		normal = baseRenderingExcel;
	}

	/**
	 * 获取对应区块类型的起始位置
	 * 
	 * @param config 配置
	 * @return 起始位置
	 */
	public static int getBlockRow(ExcelCoordinateConfig config) {
		return getRendering(config.getType()).getRowIndex(config);
	}

}
