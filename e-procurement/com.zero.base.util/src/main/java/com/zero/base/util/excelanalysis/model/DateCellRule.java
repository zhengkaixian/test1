
package com.zero.base.util.excelanalysis.model;

import org.apache.commons.lang3.StringUtils;

/**
 * @ClassName: DateCellRule
 * @Description: 日期类型校验规则
 * @author:
 * @date: 2018-9-4 下午3:36:35
 *
 */
public class DateCellRule extends CellRule {
	private String validatorDate = "^[1-9]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$";

	public DateCellRule(String cellName, boolean notNull, String mapColumn, String beanFiled) {
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
			if (!match(value, validatorDate)) {
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
