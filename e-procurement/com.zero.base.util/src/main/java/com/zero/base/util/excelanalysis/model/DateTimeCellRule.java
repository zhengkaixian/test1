package com.zero.base.util.excelanalysis.model;

import org.apache.commons.lang3.StringUtils;

/**
 * @ClassName: DateTimeCellRule
 * @Description: 日期时间类型校验规则
 * @author: 
 * @date: 2018-9-4 下午3:37:39
 *
 */

public class DateTimeCellRule extends CellRule {
	String validatorDateTime = "^[1-9]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])\\s(20|21|22|23|[0-1]\\d):[0-5]\\d:[0-5]\\d$";

	public DateTimeCellRule(String cellName, boolean notNull, String mapColumn, String beanFiled) {
		super();
		setCellName(cellName);
		setNotNull(notNull);
		setMapColumn(mapColumn);
		setBeanFiled(beanFiled);
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
			if (!match(value, validatorDateTime)) {
				success = false;
				meg = getCellName() + "请输入正确的执法日期格式";
			}
		}
		CheckCellInfo checkCellInfo = new CheckCellInfo();
		checkCellInfo.setSuccess(success);
		checkCellInfo.setMeg(meg);
		return checkCellInfo;
	}
}
