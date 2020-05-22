package com.zero.base.util.excelanalysis;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zero.base.util.DateUtil;
import com.zero.base.util.FileTypeUtil;
import com.zero.base.util.FileTypeUtil.FileType;
import com.zero.base.util.excelanalysis.model.CellRule;
import com.zero.base.util.excelanalysis.model.CheckCellInfo;
import com.zero.base.util.excelanalysis.model.SheetDataResult;
import com.zero.base.util.excelanalysis.model.SheetRule;
import com.zero.base.util.excelanalysis.model.SheetTitle;
import com.zero.base.util.excelanalysis.model.WorkBookDataResult;

/**
 * 
 * @ClassName: ExcelUtil
 * @Description: 解析excel数据
 * @author:
 * @date: 2018-8-25 上午9:30:17
 * 
 */
public class ExcelAnalysisUtil {
	private static final Logger logger = LoggerFactory.getLogger(ExcelAnalysisUtil.class);

	/**
	 * 
	 * @param realFileName 异常时显示的文件名
	 * @param filePath     excel文件的路径
	 * @param sheetrules   解析规则
	 * @return
	 */
	public static List<Sheet> analysisFile(String realFileName, String filePath) {
		List<Sheet> sheets = new ArrayList<Sheet>();
		if (!StringUtils.isEmpty(filePath)) {
			File file = new File(filePath);
			if (file.exists()) {
				try {
//					boolean xlsx=FileTypeUtil.validateFileHeader(filePath, FileType.xlsx);
//					boolean xls=FileTypeUtil.validateFileHeader(filePath, FileType.xls);
					Workbook workBook = null;
					workBook = new XSSFWorkbook(new FileInputStream(file));
					/*
					 * if(xlsx){ workBook = new XSSFWorkbook(new FileInputStream(file)); }else
					 * if(xls){ workBook = new HSSFWorkbook(new FileInputStream(file)); }
					 */
					sheets = getAnalysisSheet(workBook);
				} catch (FileNotFoundException e) {
					logger.error("文件不存在");
				} catch (IOException e) {
					logger.error("流异常");
				}
			} else {
				logger.error("文件不存在");
			}
		}
		return sheets;
	}

	/**
	 * 
	 * @param realFileName 异常时显示的文件名
	 * @param filePath     excel文件的路径
	 * @param sheetrules   解析规则
	 * @return
	 */
	public static WorkBookDataResult analysisFile(String realFileName, String filePath, List<SheetRule> sheetrules) {
		WorkBookDataResult workBookDataResult = null;
		if (!StringUtils.isEmpty(filePath)) {
			File file = new File(filePath);
			if (file.exists()) {
				try {
					boolean xlsx = FileTypeUtil.validateFileHeader(filePath, FileType.xlsx);
					boolean xls = FileTypeUtil.validateFileHeader(filePath, FileType.xls);
					if (xlsx) {
						workBookDataResult = loadData(false, new FileInputStream(file), sheetrules);
					} else if (xls) {
						workBookDataResult = loadData(true, new FileInputStream(file), sheetrules);
					}
				} catch (FileNotFoundException e) {
					logger.error("文件不存在");
				}
			} else {
				logger.error("文件不存在");
			}
			if (workBookDataResult != null) {
				workBookDataResult.setFileName(realFileName);
				workBookDataResult.setSaveFilePath(filePath);
			}
		}
		return workBookDataResult;
	}

	/**
	 * @Title: removeFile
	 * @Description: 删除上传的文件
	 * @param filePath
	 * @return void (这里用一句话描述返回结果说明)
	 */
	@SuppressWarnings("unused")
	private static void removeFile(String filePath) {
		File file = new File(filePath);
		file.delete();
	}

	/**
	 * 
	 * @Title: loadData
	 * @Description: 解析数据
	 * @param fileName   文件名
	 * @param filePath   解析文件路径
	 * @param sheetRules 解析sheet规则
	 * @return void
	 */
	@SuppressWarnings("rawtypes")
	private static WorkBookDataResult loadData(boolean isXlsFile, FileInputStream inputStream,
			List<SheetRule> sheetRules) {
		WorkBookDataResult workBookData = new WorkBookDataResult();
		String errorInfo = "";
		boolean sucess = true;
		try {
			Workbook workBook = null;
			if (isXlsFile) {
				workBook = new HSSFWorkbook(inputStream);
			} else {
				workBook = new XSSFWorkbook(inputStream);
			}
			// 取到所有需要解析的sheet页
			List<Sheet> listSheet = getAnalysisSheet(workBook);
			if (sheetRules != null && sheetRules.size() > 0) {
				List<SheetDataResult> workDatas = new ArrayList<SheetDataResult>();
				for (int i = 0; i < sheetRules.size() && sucess; i++) {
					SheetRule sheetRule = sheetRules.get(i);
					// sheet页数
					int sheetIndex = sheetRule.getSheetIndex();
					// 开始虛
					int startRow = sheetRule.getStartRow();
					startRow = startRow < 0 ? 0 : startRow;
					// 开始列
					int startColumn = sheetRule.getStartColumn();
					startColumn = startRow < 0 ? 0 : startColumn;
					// 解析列的大小
					int columnSize = sheetRule.getColumnSize();
					//
					String mapBeanName = sheetRule.getMapBean();
					boolean mapBean = StringUtils.isEmpty(mapBeanName) ? false : true;
					List<CellRule> cellRules = sheetRule.getCellRules();
					HashMap<String, HashMap<String, String>> dictionary = sheetRule.getDictionary();
					// 判断解析的sheet是否否里
					if (sheetIndex < 0 || sheetIndex >= listSheet.size()) {
						sucess = false;
						errorInfo = "sheet(" + sheetIndex + ")不存在";
						break;
					}
					Sheet currentSheet = listSheet.get(sheetIndex);
					String sheetName = currentSheet.getSheetName();
					int rowCmount = currentSheet.getLastRowNum();
					// 解析校验表头
					List<SheetTitle> sheetTitles = sheetRule.getSheetTitles();
					if (sheetTitles != null && sheetTitles.size() > 0) {
						for (SheetTitle sheetTitle : sheetTitles) {
							Row titleRow = currentSheet.getRow(sheetTitle.getRowIndex());
							if (!sheetTitle.getTitleValue()
									.equals(readCell(titleRow.getCell(sheetTitle.getColumnIndex())))) {
								sucess = false;
								errorInfo = "title_error";
								logger.error("导入文件的表头和模板的表头不一致");
								break;
							}
						}
					}
					List<Map<String, Object>> sheetMapDatas = new ArrayList<Map<String, Object>>();
					List<Object> sheetBeanDatas = new ArrayList<Object>();
					// 解析行数据
					for (int row_i = 0 + startRow; row_i <= rowCmount && sucess; row_i++) {
						Row row = currentSheet.getRow(row_i);
						if (ExcelAnalysisUtil.isBlankRow(row)) {// 过滤空行
							continue;
						}
						HashMap<String, Object> mapData = null;
						Object beanData = null;
						Class beanClass = null;
						if (!mapBean) {
							mapData = new HashMap<String, Object>();
						} else {
							beanClass = Class.forName(mapBeanName);
							beanData = beanClass.newInstance();
						}
						// 获取单元格数据
						int keyIndex = 0;
						for (int k = startColumn; k < columnSize + startColumn && sucess; k++) {
							String cellData = readCell(row.getCell(k));
							CheckCellInfo checkCellInfo = checkCelldata(cellRules, keyIndex, cellData);
							if (!checkCellInfo.isSuccess()) {
								sucess = false;
								errorInfo = "第" + (row_i + 1) + "行" + checkCellInfo.getMeg();
								System.out.println(errorInfo);
								break;
							} else {
								String transformDicName = cellRules.get(keyIndex).getTransformDicName();
								if (!StringUtils.isEmpty(transformDicName)) {
									cellData = dictionary.get(transformDicName).get(cellData);
								}
								if (!mapBean && mapData != null) {
									String key = cellRules.get(keyIndex).getMapColumn();
									mapData.put(key, cellData);
								} else if (beanClass != null) {
									String beanField = cellRules.get(keyIndex).getBeanFiled();
									Field field = beanClass.getDeclaredField(beanField);
									field.setAccessible(true);
									field.set(beanData, transFiledData(field, cellData));
								}
							}
							keyIndex++;
						}
						if (!mapBean) {
							sheetMapDatas.add(mapData);
						} else {
							sheetBeanDatas.add(beanData);
						}

					}
					SheetDataResult sheetDataResult = new SheetDataResult();
					sheetDataResult.setRowNum(rowCmount);
					sheetDataResult.setSheetIndex(sheetIndex);
					sheetDataResult.setSheetName(sheetName);
					if (!mapBean) {
						sheetDataResult.setSheetDatas(sheetMapDatas);
					} else {
						sheetDataResult.setSheetBeanDatas(sheetBeanDatas);
					}

					workDatas.add(sheetDataResult);
				}
				workBookData.setWorkDatas(workDatas);
			} else {
				sucess = false;
				errorInfo = "excelRule为空";
			}
		} catch (ClassNotFoundException e) {
			sucess = false;
			errorInfo = "类初始化错误";
			logger.error(errorInfo, e);
		} catch (InstantiationException e) {
			sucess = false;
			errorInfo = "类初始化错误";
			logger.error(errorInfo, e);
		} catch (IllegalAccessException e) {
			sucess = false;
			errorInfo = "类初始化错误";
			logger.error(errorInfo, e);
		} catch (NoSuchFieldException e) {
			errorInfo = "类初始化错误";
			logger.error(errorInfo, e);
		} catch (SecurityException e) {
			errorInfo = "类初始化错误";
			logger.error(errorInfo, e);
		} catch (IOException e) {
			errorInfo = "IO异常";
			logger.error(errorInfo, e);
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					logger.error("流关闭异常", e);
				}
			}
		}
		workBookData.setSuccess(sucess);
		if (!sucess) {
			workBookData.setErrMsg(errorInfo);
			logger.error(errorInfo);
		}
		return workBookData;
	}

	private static Object transFiledData(Field field, Object data) {
		Object value = null;
		String fileType = field.getType().getName();
		fileType = fileType.substring(fileType.lastIndexOf(".") + 1);
		if (data != null) {
			try {
				switch (fileType) {
				case "String":
					value = String.valueOf(data);
					break;
				case "int":
					value = Integer.parseInt(String.valueOf(data));
					break;
				case "Integer":
					value = Integer.parseInt(String.valueOf(data));
					break;
				case "double":
					value = Double.parseDouble(String.valueOf(data));
					break;
				case "float":
					value = Float.parseFloat(String.valueOf(data));
					break;
				case "Date":
					String format = "yyyy-MM-dd HH:mm:ss";
					if (!String.valueOf(data).contains(":")) {
						format = "yyyy-MM-dd";
					}
					SimpleDateFormat sdf = new SimpleDateFormat(format);
					;
					value = sdf.parse(String.valueOf(data));
					break;
				case "boolean":
					value = Boolean.parseBoolean(String.valueOf(data));
					break;
				case "char":
					value = String.valueOf(data).charAt(0);
					break;
				case "long":
					value = Long.parseLong(String.valueOf(data));
					break;
				case "Long":
					value = Long.parseLong(String.valueOf(data));
					break;
				default:
					value = data;
					break;
				}
			} catch (Exception e) {
				logger.error("数据转换异常", e);
				e.printStackTrace();
			}
		}
		return value;
	}

	/**
	 * 
	 * @Title: getAnalysisSheet
	 * @Description: 取到解析的sheet页
	 * @param workBook 工作簿
	 * @return
	 * @return List<Sheet>
	 */
	private static List<Sheet> getAnalysisSheet(Workbook workBook) {
		int sheetNum = workBook.getNumberOfSheets();
		List<Sheet> listSheet = new ArrayList<Sheet>();
		int hiddenSize = 0;
		// 解析所有的sheet
		if (sheetNum > 0) {
			for (int i = 0; i < sheetNum - hiddenSize; i++) {
				if (workBook.isSheetHidden(i)) {// 隐藏sheet跳过处理
					workBook.removeSheetAt(i);
					i--;
					hiddenSize++;
					continue;
				} else {
					listSheet.add(workBook.getSheetAt(i));
				}
			}
		}
		return listSheet;
	}

	/**
	 * 
	 * @Title: isBlankRow
	 * @Description: 判断是不是空行
	 * @param row
	 * @return
	 * @return boolean
	 */
	private static boolean isBlankRow(Row row) {
		boolean isBlankRow = true;
		if (row != null) {
			Cell cell = null;
			int cellNum = row.getLastCellNum();
			for (int i = 0; i <= cellNum; ++i) {
				cell = row.getCell(i);
				if (StringUtils.isNotBlank(readCell(cell))) {
					isBlankRow = false;
					break;
				}
			}
		}
		return isBlankRow;
	}

	/**
	 * 
	 * @Title: readCell
	 * @Description: 读取cell数据
	 * @param cell
	 * @return
	 * @return String
	 */
	@SuppressWarnings("deprecation")
	public static String readCell(Cell cell) {
		if (cell == null)
			return "";
//		cell.setCellType(HSSFCell.CELL_TYPE_STRING); //注释掉by kaixian.zheng
		switch (cell.getCellType()) {
		case 1:
			return cell.getStringCellValue().trim();
		case 0:
			if (HSSFDateUtil.isCellDateFormatted(cell)) {
				return DateUtil.dateToStr(cell.getDateCellValue(), "yyyy-MM-dd");
			}
			return new BigDecimal(cell.getNumericCellValue()).toString().trim();
		case 4:
			return String.valueOf(cell.getBooleanCellValue()).trim();
		case 2:
			return String.valueOf(cell.getCellFormula()).trim();
		case 3:
		}

		try {
			return cell.getStringCellValue().trim();
		} catch (Exception e) {
		}
		return cell.getStringCellValue().trim();
	}

	private static CheckCellInfo checkCelldata(List<CellRule> cellRules, int cellRuleIndex, String value) {
		CheckCellInfo checkCellInfo = null;
		if (cellRules != null && cellRuleIndex >= 0 && cellRuleIndex < cellRules.size()) {
			checkCellInfo = cellRules.get(cellRuleIndex).checkData(value);
		} else {
			checkCellInfo = new CheckCellInfo();
			checkCellInfo.setSuccess(true);
		}
		return checkCellInfo;
	}
}
