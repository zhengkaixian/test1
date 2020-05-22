package com.zero.base.util.excelanalysis.model;

import java.util.List;

/**
 * @ClassName: SheetDataResult
 * @Description: 解析Excel的结果数据
 * @author: 
 * @date: 2018-8-30 上午11:38:08
 * 
 */
public class WorkBookDataResult {
	/**
	 * 解析是否成功
	 */
	boolean success;
	/**
	 * 解析错误信息
	 */
	String errMsg;
	/**
	 * 保存的文件
	 */
	String saveFilePath;
	/**
	 * 上传的文件名称
	 */
	String fileName;
	/**
	 * 解析结果数据
	 */
	List<SheetDataResult> workDatas;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getSaveFilePath() {
		return saveFilePath;
	}

	public void setSaveFilePath(String saveFilePath) {
		this.saveFilePath = saveFilePath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public List<SheetDataResult> getWorkDatas() {
		return workDatas;
	}

	public void setWorkDatas(List<SheetDataResult> workDatas) {
		this.workDatas = workDatas;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

}
