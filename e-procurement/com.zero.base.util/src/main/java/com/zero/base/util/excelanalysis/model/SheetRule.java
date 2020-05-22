package com.zero.base.util.excelanalysis.model;

import java.util.HashMap;
import java.util.List;

/**
 * @ClassName: SheetRule
 * @Description: 解析sheet规则配置
 * @author: 
 * @date: 2018-8-30 上午9:30:17
 * 
 */

public class SheetRule {
	/**
	 * 解析开始行，第一行为0
	 */
	int startRow;
	/**
	 * 解析开始列，第一列为0
	 */
	int startColumn;
	/**
	 * 解析的列数
	 */
	int columnSize;
	/**
	 * sheet页码，第一页为0
	 */
	int sheetIndex;
	/**
	 * sheet页名称
	 */
	String sheetName;
	/**
	 * 解析映射数据的key
	 */
	String[] mapKeys;
	/**
	 * 对应的bean
	 */
	String mapBean;

	private List<CellRule> cellRules;

	/**
	 * @fieldName: dictionary
	 * @fieldType: HashMap<String,HashMap<String,String>>
	 * @Description: 数据转换字典
	 */

	private HashMap<String, HashMap<String, String>> dictionary;

	/**
	 * @fieldName: sheetTitles
	 * @fieldType: List<SheetTitle>
	 * @Description: 标题值
	 */

	private List<SheetTitle> sheetTitles;

	public SheetRule(int sheetIndex, int startRow, int startColumn, int columnSize, String[] mapKeys) {
		super();
		this.startRow = startRow;
		this.startColumn = startColumn;
		this.columnSize = columnSize;
		this.sheetIndex = sheetIndex;
		this.mapKeys = mapKeys;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getStartColumn() {
		return startColumn;
	}

	public void setStartColumn(int startColumn) {
		this.startColumn = startColumn;
	}

	public int getColumnSize() {
		return columnSize;
	}

	public void setColumnSize(int columnSize) {
		this.columnSize = columnSize;
	}

	public int getSheetIndex() {
		return sheetIndex;
	}

	public void setSheetIndex(int sheetIndex) {
		this.sheetIndex = sheetIndex;
	}

	public String[] getMapKeys() {
		return mapKeys;
	}

	public void setMapKeys(String[] mapKeys) {
		this.mapKeys = mapKeys;
	}

	public String getMapBean() {
		return mapBean;
	}

	public void setMapBean(String mapBean) {
		this.mapBean = mapBean;
	}

	public List<CellRule> getCellRules() {
		return cellRules;
	}

	public void setCellRules(List<CellRule> cellRules) {
		this.cellRules = cellRules;
	}

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public HashMap<String, HashMap<String, String>> getDictionary() {
		return dictionary;
	}

	public void setDictionary(HashMap<String, HashMap<String, String>> dictionary) {
		this.dictionary = dictionary;
	}

	public List<SheetTitle> getSheetTitles() {
		return sheetTitles;
	}

	public void setSheetTitles(List<SheetTitle> sheetTitles) {
		this.sheetTitles = sheetTitles;
	}

}
