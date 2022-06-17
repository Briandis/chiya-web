package chiya.web.security.entity;

import java.util.List;

/**
 * 权限的角色
 * 
 * @author brain
 *
 */
public class SecurityRole {
	/** 唯一标识 */
	private Integer id;
	/** 角色名称 */
	private String name;
	/** 描述信息 */
	private String info;

	/** 持有的接口 */
	private List<SecurityInterface> listInterface;

	public SecurityRole(Integer id, String name, String info) {
		this.id = id;
		this.name = name;
		this.info = info;
	}

	/**
	 * @return id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id 要设置的 id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name 要设置的 name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return info
	 */
	public String getInfo() {
		return info;
	}

	/**
	 * @param info 要设置的 info
	 */
	public void setInfo(String info) {
		this.info = info;
	}

	/**
	 * @return listInterface
	 */
	public List<SecurityInterface> getListInterface() {
		return listInterface;
	}

	/**
	 * @param listInterface 要设置的 listInterface
	 */
	public void setListInterface(List<SecurityInterface> listInterface) {
		this.listInterface = listInterface;
	}

}
