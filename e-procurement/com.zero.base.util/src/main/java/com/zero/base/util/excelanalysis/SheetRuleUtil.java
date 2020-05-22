package com.zero.base.util.excelanalysis;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zero.base.util.excelanalysis.enums.DataType;
import com.zero.base.util.excelanalysis.model.CellRule;
import com.zero.base.util.excelanalysis.model.DateCellRule;
import com.zero.base.util.excelanalysis.model.DateTimeCellRule;
import com.zero.base.util.excelanalysis.model.DoubleCellRule;
import com.zero.base.util.excelanalysis.model.IntegerCellRule;
import com.zero.base.util.excelanalysis.model.RegularCellRule;
import com.zero.base.util.excelanalysis.model.SheetRule;
import com.zero.base.util.excelanalysis.model.SheetTitle;
import com.zero.base.util.excelanalysis.model.StringCellRule;

/**
 * @ClassName: AnaylisisXml
 * @Description: 解析xml配置文件
 * @author: 
 * @date: 2018-9-5 上午8:50:07
 * 
 */

public class SheetRuleUtil {
	private static final Logger logger = LoggerFactory.getLogger(SheetRuleUtil.class);

	@SuppressWarnings("unchecked")
	public static List<SheetRule> analysiXml(InputStream in) {
		// 解析xml生成对应的bean
		SAXReader saxReader = new SAXReader();
		List<SheetRule> sheetRules = new ArrayList<SheetRule>();
		try {
			if (in != null) {
				Document document = saxReader.read(in);
				Element rootElement = document.getRootElement();
				Iterator<Element> sheets = rootElement.element("sheets").elements().iterator();
				while (sheets.hasNext()) {
					Element sheet = sheets.next();
					SheetRule sheetRule = analysisSheetRule(sheet);
					List<CellRule> cellRules = analysisCellRules(sheet);
					sheetRule.setCellRules(cellRules);
					HashMap<String, HashMap<String, String>> dictionary = analysisDictionary(sheet);
					sheetRule.setDictionary(dictionary);
					List<SheetTitle> sheetTitles = analysisSheetTitle(sheet);
					sheetRule.setSheetTitles(sheetTitles);
					sheetRules.add(sheetRule);
				}
			}
		} catch (Exception e) {
			logger.error("解析xml配置异常", e);
		} finally {
			closeResouce(in);
		}
		return sheetRules;
	}

	/**
	 * 
	 * @Title: analysisSheetRule
	 * @Description: 解析sheet规则
	 * @param sheet
	 * @return
	 * @return SheetRule
	 */
	private static SheetRule analysisSheetRule(Element sheet) {
		int sheetIndex = Integer.parseInt(sheet.attributeValue("sheetIndex"));
		int startRow = Integer.parseInt(sheet.attributeValue("startRow"));
		int startColumn = Integer.parseInt(sheet.attributeValue("startColumn"));
		int columnSize = Integer.parseInt(sheet.attributeValue("columnSize"));
		String sheetName = sheet.attributeValue("sheetName");
		String mapBean = sheet.attributeValue("mapBean");
		SheetRule sheetRule = new SheetRule(sheetIndex, startRow, startColumn, columnSize, null);
		sheetRule.setSheetName(sheetName);
		sheetRule.setMapBean(mapBean);
		return sheetRule;
	}

	/**
	 * 
	 * @Title: analysisSheetRule
	 * @Description: 解析sheet规则
	 * @param sheet
	 * @return
	 * @return SheetRule
	 */
	@SuppressWarnings("unchecked")
	private static List<CellRule> analysisCellRules(Element sheet) {
		Iterator<Element> cells = sheet.element("cells").elements().iterator();
		List<CellRule> cellRules = new ArrayList<CellRule>();
		while (cells.hasNext()) {
			Element cell = cells.next();
			String cellName = cell.attributeValue("cellName");
			boolean notNull = "true".equals(cell.attributeValue("notNull")) ? true : false;
			String mapColumn = cell.attributeValue("mapColumn");
			String beanFiled = cell.attributeValue("beanFiled");
			String dataType = cell.attributeValue("dataType");
			String transformDicName = cell.attributeValue("transformDicName");
			CellRule cellRule = null;
			switch (DataType.getCodeValue(dataType)) {
			case 1:
				int sLeg = Integer.parseInt(cell.attributeValue("maxLength"));
				boolean checkIllegalChar = "false".equals(cell.attributeValue("checkIllegalChar")) ? false : true;
				cellRule = new StringCellRule(cellName, notNull, mapColumn, beanFiled, sLeg, checkIllegalChar);
				break;
			case 2:
				int maxLength = Integer.parseInt(cell.attributeValue("maxLength"));
				cellRule = new IntegerCellRule(cellName, notNull, mapColumn, beanFiled, maxLength);
				break;
			case 3:
				int maxLg = Integer.parseInt(cell.attributeValue("maxLength"));
				int decimalLength = Integer.parseInt(cell.attributeValue("decimalLength"));
				cellRule = new DoubleCellRule(cellName, notNull, mapColumn, beanFiled, maxLg, decimalLength);
				break;
			case 4:
				cellRule = new DateCellRule(cellName, notNull, mapColumn, beanFiled);
				break;
			case 5:
				cellRule = new DateTimeCellRule(cellName, notNull, mapColumn, beanFiled);
				break;
			case 6:
				String expression = cell.attributeValue("expression");
				cellRule = new RegularCellRule(cellName, notNull, mapColumn, beanFiled, expression);
				break;
			}
			;
			if (cell != null) {
				cellRule.setTransformDicName(transformDicName);
			}
			cellRules.add(cellRule);
		}
		return cellRules;
	}

	/**
	 * 
	 * @Title: analysisDictionary
	 * @Description: 解析字典表
	 * @param sheet
	 * @return
	 * @return HashMap<String,HashMap<String,Object>> (这里用一句话描述返回结果说明)
	 */
	@SuppressWarnings("unchecked")
	private static HashMap<String, HashMap<String, String>> analysisDictionary(Element sheet) {
		HashMap<String, HashMap<String, String>> dicsMap = new HashMap<String, HashMap<String, String>>();
		Element dicsElement = sheet.element("dics");
		if (dicsElement != null) {
			Iterator<Element> dics = dicsElement.elements().iterator();
			while (dics.hasNext()) {
				Element dic = dics.next();
				String dicName = dic.attributeValue("name");
				Iterator<Element> kv = dic.elements().iterator();
				HashMap<String, String> dicMap = new HashMap<String, String>();
				while (kv.hasNext()) {
					Element dicElementData = kv.next();
					String key = dicElementData.attributeValue("key");
					String value = dicElementData.getTextTrim();
					dicMap.put(key, value);
				}
				dicsMap.put(dicName, dicMap);
			}
		}
		return dicsMap;
	}

	/**
	 * @Title: analysisSheetTitle
	 * @Description: 解析表头标题数据
	 * @param sheet
	 * @return
	 * @return List<SheetTitle>
	 */
	@SuppressWarnings("unchecked")
	private static List<SheetTitle> analysisSheetTitle(Element sheet) {
		List<SheetTitle> list = new ArrayList<>();
		Element titlesElements = sheet.element("titles");
		if (titlesElements != null) {
			Iterator<Element> titles = titlesElements.elements().iterator();
			while (titles.hasNext()) {
				Element title = titles.next();
				int rowIndex = Integer.parseInt(title.attributeValue("rowIndex"));
				Iterator<Element> texts = title.elements().iterator();
				while (texts.hasNext()) {
					Element value = texts.next();
					int colIndex = Integer.parseInt(value.attributeValue("columnIndex"));
					String titleValue = value.getTextTrim();
					SheetTitle sheetTitle = new SheetTitle(rowIndex, colIndex, titleValue);
					list.add(sheetTitle);
				}
			}
		}
		return list;
	}

	@SuppressWarnings("unused")
	private static InputStream getFileInputStream(String filePath) {
		InputStream in = null;

		/*
		 * ClassLoader classLoader = SheetRuleUtil.class.getClassLoader(); if
		 * (classLoader != null) { in = classLoader.getResourceAsStream("rulexml/" +
		 * fileName); }
		 */
		/* String path="D/usr/datamanage/fileRepository/excelUpload"+fileName; */
		try {
			File file = new File(filePath);
			if (file.exists()) {
				in = new FileInputStream(file);
			} else {
				logger.error("sheet解析规则文件不存在" + filePath);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return in;
	}

	/**
	 * 关闭资源
	 * 
	 * @Title: closeResouce
	 * @Description: 关闭流
	 * @return void
	 */
	private static void closeResouce(InputStream in) {
		if (in != null) {
			try {
				in.close();
			} catch (IOException e) {
				logger.error("流关闭失败", e);
			}
		}
	}

	@SuppressWarnings("static-access")
	public static void main(String[] args) throws FileNotFoundException {
		SheetRuleUtil analysisXmlUtil = new SheetRuleUtil();
		File file = new File(
				"E:/eclipseWork2/datamanage3/datamanage/datamanage-microservice/datamanage-collect/datamanage-collect-model/src/main/resources/rulexml/objectVersion.xml");
		InputStream inputStream = new FileInputStream(file);
		List<SheetRule> rules = analysisXmlUtil.analysiXml(inputStream);
		System.out.println(rules.size());
	}
}
