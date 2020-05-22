package com.zero.base.util.excelanalysis.model;

import org.apache.commons.lang3.StringUtils;

/**
 * @ClassName: Regular
 * @Description: TODO (这里用一句话描述这个类的作用)
 * @author: 
 * @date: 2018-9-5 上午10:30:22
 *
 */

public class RegularCellRule extends CellRule {
	private String expression;

	public RegularCellRule(String cellName, boolean notNull, String mapColumn, String beanFiled, String expression) {
		super();
		setCellName(cellName);
		setNotNull(notNull);
		setMapColumn(mapColumn);
		setBeanFiled(beanFiled);
		this.expression = expression;
	}

	@Override
	public CheckCellInfo checkData(String value) {
		boolean success = true;
		String meg = "";
		if (StringUtils.isEmpty(expression)) {
			success = false;
			meg = "表达式配置为空";
		} else {
			if (StringUtils.isEmpty(value)) {
				if (isNotNull() && !match(value, expression)) {
					success = false;
					meg = getCellName() + "输入有误(" + value + ")";
				}
			} else {
				if (!match(value, expression)) {
					success = false;
					meg = getCellName() + "输入有误(" + value + ")";
				}
			}
		}
		CheckCellInfo checkCellInfo = new CheckCellInfo();
		checkCellInfo.setSuccess(success);
		checkCellInfo.setMeg(meg);
		return checkCellInfo;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}
}
