package com.zero.base.util.excelanalysis.model;

/**
 * @ClassName: CheckCellInfo
 * @Description: 检验单元格信息
 * @author: 
 * @date: 2018-9-4 下午4:13:54
 *
 */

public class CheckCellInfo {
	/**
	 * 是否正确
	 */
	boolean success;
	/**
	 * 错误信息
	 */
	String meg;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMeg() {
		return meg;
	}

	public void setMeg(String meg) {
		this.meg = meg;
	}

}
