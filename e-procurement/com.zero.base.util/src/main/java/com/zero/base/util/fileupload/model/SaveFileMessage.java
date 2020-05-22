package com.zero.base.util.fileupload.model;


/**
 * @ClassName: FormData
 * @Description: 保存的文件信息
 * @author: 
 * @date: 2018年12月7日 下午6:07:32
 *
 */

public class SaveFileMessage {
	/**
	 * 上传的文件名
	 */
	String fileName;
	/**
	 * 保存的文件路径
	 */
	String savePath;

	/**
	* <p>Title:       SaveFileMessage</p>
	* <p>Description: 构造函数</p>
	* @param fileName
	* @param savePath
	*/ 
	public SaveFileMessage(String fileName, String savePath) {
		super();
		this.fileName = fileName;
		this.savePath = savePath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getSavePath() {
		return savePath;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

}
