package com.zero.base.util.excelanalysis.model;

import java.util.HashMap;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * @ClassName: CellRule
 * @Description: 校验规则
 * @author: 
 * @date: 2018-9-4 下午3:36:35
 *
 */
public abstract class CellRule {
	/**
	 * 不允许为空
	 */
	private boolean notNull;
	/**
	 * 映射保存在key
	 */
	private String mapColumn;
	/**
	 * 映射的bean字段
	 */
	private String beanFiled;
	/**
	 * 单元格名称
	 */
	private String cellName;
	/**
	 * 根据配置的字典名称转换对应的值
	 */
	private String transformDicName;
	private boolean changeData;
	private HashMap<String, HashMap<String, String>> changeDataMaps;

	public boolean match(String text, String reg) {
		if (StringUtils.isBlank(text) || StringUtils.isBlank(reg))
			return false;
		return Pattern.compile(reg).matcher(text).matches();
	}

	public abstract CheckCellInfo checkData(String value);

	public boolean isNotNull() {
		return notNull;
	}

	public void setNotNull(boolean notNull) {
		this.notNull = notNull;
	}

	public String getMapColumn() {
		return mapColumn;
	}

	public void setMapColumn(String mapColumn) {
		this.mapColumn = mapColumn;
	}

	public String getBeanFiled() {
		return beanFiled;
	}

	public void setBeanFiled(String beanFiled) {
		this.beanFiled = beanFiled;
	}

	public String getCellName() {
		return cellName;
	}

	public void setCellName(String cellName) {
		this.cellName = cellName;
	}

	public String getTransformDicName() {
		return transformDicName;
	}

	public void setTransformDicName(String transformDicName) {
		this.transformDicName = transformDicName;
	}

	public boolean isChangeData() {
		return changeData;
	}

	public void setChangeData(boolean changeData) {
		this.changeData = changeData;
	}

	public HashMap<String, HashMap<String, String>> getChangeDataMaps() {
		return changeDataMaps;
	}

	public void setChangeDataMaps(HashMap<String, HashMap<String, String>> changeDataMaps) {
		this.changeDataMaps = changeDataMaps;
	}

}
