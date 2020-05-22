package com.zero.base.util.fileupload.model;

import java.util.List;

/**
 * @ClassName: UploadResult
 * @Description: 上传结果信息
 * @author: 
 * @date: 2018年12月7日 下午6:00:07
 *
 */

public class UploadResult {
	/**
	 * 是否上传成功
	 */
	boolean upSuccess;
	/**
	 * 上传失败信息
	 */
	String errorMsg;
	/**
	 * 表单数据
	 */
	List<FormFieldData> formFields;
	/**
	 * 上传的表单数据
	 */
	List<SaveFileMessage> fileMessage;

	public boolean isUpSuccess() {
		return upSuccess;
	}

	public void setUpSuccess(boolean upSuccess) {
		this.upSuccess = upSuccess;
	}

	public List<FormFieldData> getFormFields() {
		return formFields;
	}

	public void setFormFields(List<FormFieldData> formFields) {
		this.formFields = formFields;
	}

	public List<SaveFileMessage> getFileMessage() {
		return fileMessage;
	}

	public void setFileMessage(List<SaveFileMessage> fileMessage) {
		this.fileMessage = fileMessage;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
}
