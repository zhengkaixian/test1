
package com.zero.base.pojo.database;

import java.util.Map;

/** 
 * @ClassName: SampleDataDto 
 * @Description: TODO (这里用一句话描述这个类的作用)
 * @author: 
 * @date: 2019年2月18日 下午7:00:31
 *
 */
public class SampleDataDto {
	private String tableName;
	private String tableCName;
	private Map<String, Object> datas;
	/**
	 * @return the tableName
	 */
	public String getTableName() {
		return tableName;
	}
	/**
	 * @param tableName 要设置的 tableName 
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	/**
	 * @return the tableCName
	 */
	public String getTableCName() {
		return tableCName;
	}
	/**
	 * @param tableCName 要设置的 tableCName 
	 */
	public void setTableCName(String tableCName) {
		this.tableCName = tableCName;
	}
	/**
	 * @return the datas
	 */
	public Map<String, Object> getDatas() {
		return datas;
	}
	/**
	 * @param datas 要设置的 datas 
	 */
	public void setDatas(Map<String, Object> datas) {
		this.datas = datas;
	}
}
