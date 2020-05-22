package com.zero.base.util.excelanalysis.model;

import org.apache.commons.lang3.StringUtils;

/**
 * @ClassName: IntegerCellRule
 * @Description: 整形校验规则
 * @author: 
 * @date: 2018-9-4 下午3:34:53
 * 
 */

public class IntegerCellRule extends CellRule {
	/**
	 * 最大长度
	 */
	private int maxLength;

	public IntegerCellRule(String cellName, boolean notNull, String mapColumn, String beanFiled, int maxLenght) {
		super();
		setCellName(cellName);
		setNotNull(notNull);
		setMapColumn(mapColumn);
		setBeanFiled(beanFiled);
		this.maxLength = maxLenght;
	}

	public int getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}

	@Override
	public CheckCellInfo checkData(String value) {
		boolean success = true;
		String meg = "";
		if (StringUtils.isEmpty(value)) {
			if (isNotNull()) {// 必填判断
				success = false;
				meg = getCellName() + "不能为空";
			}
		} else {
			String expressionInt;
			if (maxLength <= 0) {
				success = false;
				meg = getCellName() + "最大长度配置有误";
			} else {
				expressionInt = "^(-?(0|[1-9][0-9]{0," + (maxLength - 1) + "}))$";
				if (maxLength == 1) {
					expressionInt = "^(-?([0-9]))$";
				}
				if (!match(value, expressionInt)) {
					success = false;
					meg = getCellName() + "输入0-" + maxLength + "数字";
				}
			}
		}
		CheckCellInfo checkCellInfo = new CheckCellInfo();
		checkCellInfo.setSuccess(success);
		checkCellInfo.setMeg(meg);
		return checkCellInfo;
	}

}
