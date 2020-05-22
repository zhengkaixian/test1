
package com.zero.base.util.excelanalysis.model;

import org.apache.commons.lang3.StringUtils;

/**
 * @ClassName: DoubleCellRule1
 * @Description: double型校验规则
 * @author: 
 * @date: 2018-9-4 下午3:35:53
 *
 */

public class DoubleCellRule extends CellRule {
	/**
	 * 整形长度
	 */
	private int maxLength;
	/**
	 * 小数位数
	 */
	private int decimalLength;

	public DoubleCellRule(String cellName, boolean notNull, String mapColumn, String beanFiled, int maxLenght,
			int decimalLength) {
		super();
		setCellName(cellName);
		setNotNull(notNull);
		setMapColumn(mapColumn);
		setBeanFiled(beanFiled);
		this.maxLength = maxLenght;
		this.decimalLength = decimalLength;
	}

	public int getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}

	public int getDecimalLength() {
		return decimalLength;
	}

	public void setDecimalLength(int decimalLength) {
		this.decimalLength = decimalLength;
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
			if (maxLength <= 0 || decimalLength <= 0) {
				success = false;
				meg = getCellName() + "最大长度或小数点位数配置有误";
			} else {
				String expressionInt = "^(-?(0|[1-9][0-9]{0," + (maxLength - 1) + "}(\\.[0-9]{1," + decimalLength
						+ "})?))$";
				if (!match(value, expressionInt)) {
					success = false;
					meg = getCellName() + "输入0-" + maxLength + "位整数,最多" + decimalLength + "位小数";
				}
			}
		}
		CheckCellInfo checkCellInfo = new CheckCellInfo();
		checkCellInfo.setSuccess(success);
		checkCellInfo.setMeg(meg);
		return checkCellInfo;
	}
}
