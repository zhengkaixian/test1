package com.zero.base.util.excelexport;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zero.base.util.StringUtil;
import com.zero.base.util.excelexport.enums.TitleLevel;
import com.zero.base.util.excelexport.enums.WriterObjctType;
import com.zero.base.util.excelexport.model.CellRegion;
import com.zero.base.util.excelexport.model.CellTitle;
import com.zero.base.util.excelexport.model.SheetDataConfig;

/**
 * @ClassName: ExcelExportUtil
 * @Description:导出数据到excel
 * @author: 
 * @date: 2018年12月12日 下午7:09:55
 *
 */

public class ExcelExportUtil {
	private static final Logger logger = LoggerFactory.getLogger(ExcelExportUtil.class);
	
	/**
	* @Title:       exportSheetsData
	* @Description: 导出多个sheet数据
	* @param sheetsConfigs
	* @return
	* @return HSSFWorkbook 
	*/ 
	public static Workbook exportSheetsData(List<SheetDataConfig> sheetsConfigs,boolean exportXlsx){
		Workbook workbook=createWorkbook(exportXlsx);
		if(sheetsConfigs!=null&&sheetsConfigs.size()>0){
			for(SheetDataConfig sheetDataConfig:sheetsConfigs){
				Sheet sheet=createSheet(workbook, sheetDataConfig.getSheetName());
				List<CellTitle> cellTitles=sheetDataConfig.getCellTitles();
				List<CellRegion> cellRegions=sheetDataConfig.getCellRegions();
				WriterObjctType dataType=sheetDataConfig.getDataType();
				if(WriterObjctType.array.equals(dataType)){
					exportDataToExcel(workbook,sheet,cellTitles, cellRegions, dataType, sheetDataConfig.getArrayDatas(), null, null, null, null);
				}else if(WriterObjctType.map.equals(dataType)){
					exportDataToExcel(workbook,sheet,cellTitles, cellRegions,dataType, null,sheetDataConfig.getMapDatas(), sheetDataConfig.getKeys(), null, null);
				}else if(WriterObjctType.bean.equals(dataType)){
					exportDataToExcel(workbook,sheet,cellTitles, cellRegions,dataType, null, null, null,sheetDataConfig.getBeanDatas(), sheetDataConfig.getFileds());
				}
			}
		}
		return workbook;
	}
	/**
	 * @Title: exportArrayData
	 * @Description: 导出数组结构的数据到excel
	 * @param cellTitles 标题
	 * @param cellRegions  合并单元格配置
	 * @param arrayDatas  导出的数据
	 * @return
	 * @return HSSFWorkbook
	 */
	public static Workbook exportArrayData(List<CellTitle> cellTitles, List<CellRegion> cellRegions,
			List<Object[]> arrayDatas,boolean exportXlsx) {
		Workbook workbook=createWorkbook(exportXlsx);
		Sheet sheet=createSheet(workbook, null);
		return exportDataToExcel(workbook,sheet,cellTitles, cellRegions, WriterObjctType.array, arrayDatas, null, null, null, null);
	}

	/**
	 * @Title: exportMapData
	 * @Description: 导出map结构的数据到excel
	 * @param cellTitles 标题
	 * @param cellRegions 合并单元格配置
	 * @param mapDatas 导出的数据
	 * @param keys 导出对应列的取值key
	 * @return
	 * @return HSSFWorkbook
	 */
	public static Workbook exportMapData(List<CellTitle> cellTitles, List<CellRegion> cellRegions,
			List<Map<String, Object>> mapDatas, Object keys[],boolean exportXlsx) {
		Workbook workbook=createWorkbook(exportXlsx);
		Sheet sheet=createSheet(workbook, null);
		return exportDataToExcel(workbook,sheet,cellTitles, cellRegions, WriterObjctType.map, null, mapDatas, keys, null, null);
	}

	/**
	 * @Title: exportBeanData
	 * @Description: 导出bean对象的数据到excel
	 * @param cellTitles 标题
	 * @param cellRegions 合并单元格配置
	 * @param beanDatas 导出的数据
	 * @param fileds 导出对应列的取值字段
	 * @return
	 * @return HSSFWorkbook (这里用一句话描述返回结果说明)
	 */
	public static HSSFWorkbook exportBeanData(List<CellTitle> cellTitles, List<CellRegion> cellRegions, List<?> beanDatas,
			String fileds[]) {
		return (HSSFWorkbook) exportBeanData(cellTitles, cellRegions, beanDatas, fileds, false);
	}
	/**
	* @Title: exportBeanData
	 * @Description: 导出bean对象的数据到excel
	 * @param cellTitles 标题
	 * @param cellRegions 合并单元格配置
	 * @param beanDatas 导出的数据
	 * @param fileds 导出对应列的取值字段
	* @param exportXlsx 是否导出高版本excel
	* @return
	* @return Workbook (这里用一句话描述返回结果说明)
	*/ 
	public static Workbook exportBeanData(List<CellTitle> cellTitles, List<CellRegion> cellRegions, List<?> beanDatas,
			String fileds[],boolean exportXlsx) {
		Workbook workbook=createWorkbook(exportXlsx);
		Sheet sheet=createSheet(workbook, null);
		return exportDataToExcel(workbook,sheet,cellTitles, cellRegions, WriterObjctType.bean, null, null, null, beanDatas, fileds);
	}
    private static Workbook createWorkbook(boolean exportXlsx){
    	if(exportXlsx){
    		return new  XSSFWorkbook();
    	}else{
    	    return new HSSFWorkbook();
    	}
    }
    private static Sheet createSheet(Workbook workbook,String sheetName){
    	if(!StringUtil.isEmpty(sheetName)){
    		return workbook.createSheet(sheetName);
    	}else{
    		return workbook.createSheet();
    	}
    }
	private static Workbook exportDataToExcel(Workbook workbook,Sheet sheet,List<CellTitle> cellTitles, List<CellRegion> cellRegions,
			WriterObjctType writerObjctType, List<Object[]> arrayDatas, List<Map<String, Object>> mapDatas, Object keys[],
			List<?> beanDatas, String fileds[]) {
		Integer dataStartRow = 0, dataStartColumn = 256;
		groubSheetSet(sheet);
		// 写标题
		if (cellTitles != null && cellTitles.size() > 0) {
			for (CellTitle cellTitle : cellTitles) {
				int rowIndex = cellTitle.getRowIndex();
				int columnIndex = cellTitle.getColumnIndex();
				writeTitle(workbook, sheet, cellTitle);
				if (rowIndex > dataStartRow) {
					dataStartRow = rowIndex;
				}
				if (columnIndex < dataStartColumn) {
					dataStartColumn = columnIndex;
				}
			}
		}
		// 合并单元格
		if (cellRegions != null && cellRegions.size() > 0 && sheet != null) {
			for (CellRegion cellRegion : cellRegions) {
				int startCol = cellRegion.getStartCol();
				int endRow = cellRegion.getEndRow();
				if (endRow > dataStartRow) {
					dataStartRow = endRow;
				}
				if (startCol < dataStartColumn) {
					dataStartColumn = startCol;
				}
				regionCell(sheet, cellRegion);
			}
		}
		// 写入数据
		if (dataStartColumn == 256) {
			dataStartColumn = 0;
		}
		dataStartRow = dataStartRow + 1;
		String typeKey = writerObjctType.getKey();
		if (WriterObjctType.array.getKey().equals(typeKey)) {
			writeArrayData(sheet, arrayDatas,dataStartRow,dataStartColumn);
		} else if (WriterObjctType.map.getKey().equals(typeKey)) {
			writeMapData(sheet, mapDatas, keys,dataStartRow,dataStartColumn);
		} else if (WriterObjctType.bean.getKey().equals(typeKey)) {
			writeBeanData(sheet, beanDatas, fileds,dataStartRow,dataStartColumn);
		}
		return workbook;
	}

	/**
	 * @Title: writeTitle
	 * @Description: 写入标题数据
	 * @param workbook
	 * @param sheet
	 * @param cellTitles 标题列表
	 * @return void
	 */
	private static void writeTitle(Workbook workbook, Sheet sheet, CellTitle cellTitle) {
		int rowIndex = cellTitle.getRowIndex();
		int columnIndex = cellTitle.getColumnIndex();
		Row row = sheet.getRow(rowIndex);
		if (row == null) {
			row = sheet.createRow(rowIndex);// 创建行
		}
		Cell cell = row.createCell(columnIndex);// 在某位置创建列
		cell.setCellStyle(getTitleCellStyle(workbook, cellTitle.getTitleLevel()));
		cell.setCellValue(cellTitle.getTitleText());
		if (cellTitle.getColWidth() != null) {
			sheet.setColumnWidth(columnIndex, cellTitle.getColWidth());
		}
	}

	/**
	 * @Title: regionCell
	 * @Description: 合并单元格操作
	 * @param sheet
	 * @param cellRegion
	 * @return void
	 */
	private static void regionCell(Sheet sheet, CellRegion cellRegion) {
		int startRow = cellRegion.getStartRow();
		int startCol = cellRegion.getStartCol();
		int endRow = cellRegion.getEndRow();
		int endCol = cellRegion.getEndCol();
		CellRangeAddress cellRangeAddress = new CellRangeAddress(startRow, endRow, startCol, endCol);
		sheet.addMergedRegion(cellRangeAddress);
	}

	/**
	 * @Title: writeArrayData
	 * @Description: 写入数组形式的数据
	 * @param sheet
	 * @param arrayDatas
	 * @return void
	 */
	private static void writeArrayData(Sheet sheet, List<Object[]> arrayDatas,Integer dataStartRow,Integer dataStartColumn) {
		if (arrayDatas != null && arrayDatas.size() > 0) {
			for (int i = 0; i < arrayDatas.size(); i++) {
				Row row = sheet.createRow(i + dataStartRow);
				Object obj[] = arrayDatas.get(i);
				// 循环插入每行多少列
				for (int j = 0; j < obj.length; j++) {
					if (null != obj[j]) {
						row.createCell(j + dataStartColumn).setCellValue(obj[j].toString());
					}
				}
			}
		}
	}

	/**
	 * @Title: writeMapData
	 * @Description: 写入Map形式的数据
	 * @param sheet
	 * @param mapDatas
	 * @param keys
	 * @return void
	 */
	private static void writeMapData(Sheet sheet, List<Map<String, Object>> mapDatas, Object keys[],Integer dataStartRow,Integer dataStartColumn) {
		if (mapDatas != null && mapDatas.size() > 0 && keys != null && keys.length > 0) {
			for (int i = 0; i < mapDatas.size(); i++) {
				Row row = sheet.createRow(i + dataStartRow);
				Map<String, Object> data = mapDatas.get(i);
				// 循环插入每行多少列
				for (int j = 0; j < keys.length; j++) {
					Object value = data.get(keys[j]);
					if (value != null) {
						row.createCell(j + dataStartColumn).setCellValue(value.toString());
					}
				}
			}
		}
	}

	/**
	 * @Title: writeBeanData
	 * @Description: 写入bean形式的数据
	 * @param sheet
	 * @param beanDatas
	 * @param fileds
	 * @return void 
	 */
	private static void writeBeanData(Sheet sheet, List<?> beanDatas, String fileds[],Integer dataStartRow,Integer dataStartColumn) {
	if(beanDatas!=null&&beanDatas.size()>0){
		for (int i = 0; i < beanDatas.size(); i++) {
			Row row = sheet.createRow(i + dataStartRow);
			Object obj = beanDatas.get(i);
			for (int j = 0; j < fileds.length; j++) {
				Field field;
				try {
					field = obj.getClass().getDeclaredField(fileds[j]);
					field.setAccessible(true);
					Object value = field.get(obj);
					if (value != null) {
						row.createCell(j + dataStartColumn).setCellValue(value.toString());
					}
				} catch (NoSuchFieldException e) {
					logger.error("数据读取异常", e);
				} catch (SecurityException e) {
					logger.error("数据读取异常", e);
				} catch (IllegalArgumentException e) {
					logger.error("数据读取异常", e);
				} catch (IllegalAccessException e) {
					logger.error("数据读取异常", e);
				}

			}
		}
	  }
	}

	/**
	 * @Title: groubSheetSet
	 * @Description: 全局sheet设置
	 * @param sheet
	 * @return void
	 */
	private static void groubSheetSet(Sheet sheet) {
		for (int i = 0; i < 256; i++) { // 循环遍历每个字段占位宽度
			sheet.setColumnWidth(i, 6000);
		}
	}

	/**
	 * @Title: getTitleCellStyle
	 * @Description: 获取标题风格
	 * @param workbook
	 * @return
	 * @return HSSFCellStyle (这里用一句话描述返回结果说明)
	 */
	private static CellStyle getTitleCellStyle(Workbook workbook, TitleLevel titleLevel) {
		CellStyle cellStyle = workbook.createCellStyle();
		Font fontStyle = workbook.createFont();
		fontStyle.setFontName("宋体");
		fontStyle.setBold(true);
		short defaultPoints = 20;
		if (titleLevel != null) {
			defaultPoints = titleLevel.getValue();
		}
		fontStyle.setFontHeightInPoints(defaultPoints);
		cellStyle.setFont(fontStyle);
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		return cellStyle;
	}

/*	public static void main(String[] args) {
		List<CellTitle> cellTitles = new ArrayList<CellTitle>();
		
		 * cellTitles.add(new CellTitle(0,0, "专题",TitleLevel.level1,null)); cellTitles.add(new CellTitle(0, 1,
		 * "专题名称",TitleLevel.level1,null)); cellTitles.add(new CellTitle(0, 2, "工作项",TitleLevel.level1,null));
		 
		cellTitles.add(new CellTitle(1, 3, "专题2", TitleLevel.level2, null));
		cellTitles.add(new CellTitle(1, 4, "基于聚类分析技术的数据治理", TitleLevel.level2, null));
		cellTitles.add(new CellTitle(1, 5, "需求设计", TitleLevel.level2, null));
		cellTitles.add(new CellTitle(2, 5, "界面开发、后台开发", TitleLevel.level2, null));
		cellTitles.add(new CellTitle(3, 5, "挖掘模型开发", TitleLevel.level3, null));
		cellTitles.add(new CellTitle(4, 5, "界面与模型联调", TitleLevel.level3, null));
		List<CellRegion> cellRegions = new ArrayList<>();
		cellRegions.add(new CellRegion(1, 3, 4, 3));
		cellRegions.add(new CellRegion(1, 4, 4, 4));
		List<Object[]> arrays = new ArrayList<>();
		arrays.add(new Object[] { "a", "b", "c", "d" });
		arrays.add(new Object[] { 1, 2, 3, 4 });
		arrays.add(new Object[] { "北京", "上海", "广东", "深圳" });
		// HSSFWorkbook workbook=ExcelExportUtil.exportArrayData(cellTitles,cellRegions,arrays);
		HashMap<String, Object> map1 = new HashMap<>();
		map1.put("name", "lisi");
		map1.put("sex", "男");
		map1.put("age", 17);
		map1.put("bith", "2001-09-23");
		HashMap<String, Object> map2 = new HashMap<>();
		map2.put("name", "hanxue");
		map2.put("sex", "女");
		map2.put("age", 20);
		map2.put("bith", "1998-09-23");
		List<Map<String, Object>> mapDatas = new ArrayList<>();
		mapDatas.add(map1);
		mapDatas.add(map2);
		// HSSFWorkbook workbook=ExcelExportUtil.exportMapData(cellTitles, cellRegions, mapDatas, new
		// Object[]{"name","sex","bith","age"});
		HSSFWorkbook workbook = ExcelExportUtil.exportBeanData(cellTitles, cellRegions, cellTitles,
				new String[] { "titleText", "rowIndex", "columnIndex", "colWidth" });
		try {
			String fpath = "C://Users//lenovo//Desktop//model//23.xls";
			File file = new File(fpath);
			file.delete();
			workbook.write(new File(fpath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}*/
}
