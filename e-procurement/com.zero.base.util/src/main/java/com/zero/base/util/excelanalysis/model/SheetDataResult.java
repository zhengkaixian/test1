package com.zero.base.util.excelanalysis.model;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: SheetDataResult
 * @Description: 解析sheet页的结果数据
 * @author: 
 * @date: 2018-8-30 上午11:38:08
 * 
 */

public class SheetDataResult {
	/**
	 * sheet页标示
	 */
	int sheetIndex;
	/**
	 * 行数
	 */
	int rowNum;
	/**
	 * sheet名称
	 */
	String sheetName;
	/**
	 * sheet数据（map保存）
	 */
	List<Map<String, Object>> sheetDatas;
	/**
	 * sheet数据（bean对象保存）
	 */
	List<Object> sheetBeanDatas;

	public int getSheetIndex() {
		return sheetIndex;
	}

	public void setSheetIndex(int sheetIndex) {
		this.sheetIndex = sheetIndex;
	}

	public int getRowNum() {
		return rowNum;
	}

	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public List<Map<String, Object>> getSheetDatas() {
		return sheetDatas;
	}

	public void setSheetDatas(List<Map<String, Object>> sheetDatas) {
		this.sheetDatas = sheetDatas;
	}

	public List<Object> getSheetBeanDatas() {
		return sheetBeanDatas;
	}

	public void setSheetBeanDatas(List<Object> sheetBeanDatas) {
		this.sheetBeanDatas = sheetBeanDatas;
	}

}
