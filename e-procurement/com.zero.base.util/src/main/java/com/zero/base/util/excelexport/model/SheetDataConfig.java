package com.zero.base.util.excelexport.model;

import java.util.List;
import java.util.Map;

import com.zero.base.util.excelexport.enums.WriterObjctType;

/**
 * @ClassName: SheetConfig
 * @Description: 写入sheet的数据配置
 * @author: 
 * @date: 2019年1月29日 下午4:05:53
 *
 */

public class SheetDataConfig {

	/**
	 * @fieldName: dataType
	 * @fieldType: WriterObjctType
	 * @Description: 写入sheet的数据类型格式
	 */

	WriterObjctType dataType;
	/**
	 * @fieldName: sheetName
	 * @fieldType: String
	 * @Description: sheet名称
	 */

	private String sheetName;

	/**
	 * @fieldName: cellTitles
	 * @fieldType: List<CellTitle>
	 * @Description: 标题
	 */

	private List<CellTitle> cellTitles;

	/**
	 * @fieldName: cellRegions
	 * @fieldType: List<CellRegion>
	 * @Description: 合并单元格
	 */

	private List<CellRegion> cellRegions;

	/**
	 * @fieldName: arrayDatas
	 * @fieldType: List<Object[]>
	 * @Description: 导出数组形式在数据
	 */

	private List<Object[]> arrayDatas;

	/**
	 * @fieldName: mapDatas
	 * @fieldType: List<Map<String,Object>>
	 * @Description: 导出map形式的数据
	 */

	private List<Map<String, Object>> mapDatas;

	/**
	 * @fieldName: keys
	 * @fieldType: Object[]
	 * @Description:取map数据对应的key
	 */

	private Object keys[];

	/**
	 * @fieldName: beanDatas
	 * @fieldType: List<?>
	 * @Description: 导出bean形式的数据
	 */

	List<?> beanDatas;

	/**
	 * @fieldName: fileds
	 * @fieldType: String[]
	 * @Description: 取bean数据对应的字段
	 */

	private String fileds[];
	
	public SheetDataConfig(){
		
	}

	/**
	 * @param dataType
	 * @param sheetName
	 * @param cellTitles
	 * @param cellRegions
	 */
	public SheetDataConfig(WriterObjctType dataType, String sheetName, List<CellTitle> cellTitles, List<CellRegion> cellRegions) {
		super();
		this.dataType = dataType;
		this.sheetName = sheetName;
		this.cellTitles = cellTitles;
		this.cellRegions = cellRegions;
	}

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public List<CellTitle> getCellTitles() {
		return cellTitles;
	}

	public void setCellTitles(List<CellTitle> cellTitles) {
		this.cellTitles = cellTitles;
	}

	public List<CellRegion> getCellRegions() {
		return cellRegions;
	}

	public void setCellRegions(List<CellRegion> cellRegions) {
		this.cellRegions = cellRegions;
	}

	public List<Object[]> getArrayDatas() {
		return arrayDatas;
	}

	public void setArrayDatas(List<Object[]> arrayDatas) {
		this.arrayDatas = arrayDatas;
	}

	public List<Map<String, Object>> getMapDatas() {
		return mapDatas;
	}

	public void setMapDatas(List<Map<String, Object>> mapDatas) {
		this.mapDatas = mapDatas;
	}

	public Object[] getKeys() {
		return keys;
	}

	public void setKeys(Object[] keys) {
		this.keys = keys;
	}

	public List<?> getBeanDatas() {
		return beanDatas;
	}

	public void setBeanDatas(List<?> beanDatas) {
		this.beanDatas = beanDatas;
	}

	public String[] getFileds() {
		return fileds;
	}

	public void setFileds(String[] fileds) {
		this.fileds = fileds;
	}

	public WriterObjctType getDataType() {
		return dataType;
	}

	public void setDataType(WriterObjctType dataType) {
		this.dataType = dataType;
	}

}
