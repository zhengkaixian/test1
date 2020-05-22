package com.zero.base.util.fileupload;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.zero.base.util.DateUtil;
import com.zero.base.util.FileUtil;
import com.zero.base.util.SecurePathUtil;
import com.zero.base.util.StringUtil;
import com.zero.base.util.ValidateStr;
import com.zero.base.util.fileupload.model.FormFieldData;
import com.zero.base.util.fileupload.model.SaveFileMessage;
import com.zero.base.util.fileupload.model.UploadResult;

public class FileUploadUtil {
	private static final Logger logger = LoggerFactory.getLogger(FileUploadUtil.class);

	/**
	 * 
	 * @Title: saveFile
	 * @Description: 保存上传的文件到指定路径
	 * @param savePath
	 * @param request
	 * @param uploadSession
	 * @return
	 * @return Map<String,Object> 保存是否成功和保存成功文件的路径
	 */
	public static UploadResult saveFile(String savePath, HttpServletRequest request) {
		boolean saveResult = false;
		String errorMsg = "";
		List<FormFieldData> formFields = new ArrayList<FormFieldData>();
		List<SaveFileMessage> fileMessages = new ArrayList<SaveFileMessage>();
		UploadResult map = new UploadResult();
		try {
			File file = new File(savePath);
			if (!file.exists()) {
				boolean flag = file.mkdir();
				if (!flag) {
					logger.error("创建目录失败");
				}
			}
			MultipartHttpServletRequest multiRequest = ((MultipartHttpServletRequest) request);
			Enumeration<String> parameterNames = multiRequest.getParameterNames();
			while (parameterNames.hasMoreElements()) {
				String parameterName = parameterNames.nextElement();
				String value = multiRequest.getParameter(parameterName);
				// 解决普通输入项的数据的中文乱码问题
				if (value == null || "".equals(value.trim())) {
					continue;
				}
				FormFieldData formField = new FormFieldData(parameterName, value);
				formFields.add(formField);
			}
			Iterator<String> fileNames = multiRequest.getFileNames();
			while (fileNames.hasNext()) {
				MultipartFile multipartFile = multiRequest.getFile(fileNames.next());
				SaveFileMessage saveFileMessage = saveMultipartFil(multipartFile, savePath);
				fileMessages.add(saveFileMessage);
			}
			saveResult = true;

		} catch (Exception e1) {
			errorMsg = "文件上传失败";
			logger.error(DateUtil.getCurrentTime() + "文件上传失败", e1);
		}
		if (!saveResult) {
			map.setErrorMsg(errorMsg);
		} else {
			map.setFileMessage(fileMessages);
			map.setFormFields(formFields);
		}
		map.setUpSuccess(saveResult);
		return map;
	}

	public static SaveFileMessage saveMultipartFil(MultipartFile multipartFile, String savePath) {
		File file = new File(savePath);
		if (!file.exists()) {
			boolean flag = file.mkdirs();
			if (!flag) {
				logger.error("创建目录失败");
			}
		}
		// 得到上传的文件名称，
		String filename = multipartFile.getOriginalFilename();
		if (filename == null || "".equals(filename.trim())) {
			return null;
		}
		// 注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如： c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
		// 处理获取到的上传文件的文件名的路径部分，只保留文件名部分
		filename = filename.substring(filename.lastIndexOf("\\") + 1);
		String reName = reNameFile(filename);
		InputStream inputStream = null;
		FileOutputStream outputStream = null;
		SaveFileMessage saveFileMessage = null;
		try {
			inputStream = multipartFile.getInputStream();
			String filePath = savePath + File.separator + reName;
			if (SecurePathUtil.isSecurePath(filePath)) {
				outputStream = new FileOutputStream(filePath);
				byte[] buffer = new byte[1048];
				int len = 0;
				while ((len = inputStream.read(buffer)) > 0) {
					outputStream.write(buffer, 0, len);
				}
				outputStream.close();
				inputStream.close();
				saveFileMessage = new SaveFileMessage(filename, filePath);
			} else {
				logger.error(DateUtil.getCurrentTime() + "存在不安全的文件路径");
				// throw new ServiceException("存在不安全的文件路径!");
			}

		} catch (Exception e) {
			logger.error("文件保存失败：", e);
		} finally {
			closeStream(inputStream, outputStream);
		}
		return saveFileMessage;
	}

	public static File getUploadFile(MultipartFile multipartFile, String savePath, String filetype) {
		SaveFileMessage savefile = saveMultipartFil(multipartFile, savePath);
		File fileTmp = new File(savefile.getSavePath());
		String newFilePath = savefile.getSavePath().replace(".tmp", filetype);
		File f = new File(newFilePath);
		fileTmp.renameTo(f);
		return f;
	}

	/**
	 * 
	 * @Title: closeStream
	 * @Description: 关闭数据流
	 * @param inputStream  输入流
	 * @param outputStream 输出流
	 * @return void
	 */
	private static void closeStream(InputStream inputStream, OutputStream outputStream) {
		if (outputStream != null) {
			try {
				outputStream.close();
			} catch (IOException e) {
				logger.error("关闭输出流失败:", e);
			}
		}
		if (inputStream != null) {
			try {
				inputStream.close();
			} catch (IOException e) {
				logger.error("关闭输入流失败:", e);
			}
		}
	}

	/**
	 * 
	 * @Title: reNameFile
	 * @Description: 重新命名保存文件的名称
	 * @param fileType
	 * @return
	 * @return String
	 */
	private static String reNameFile(String fileName) {
		String fileType = "";
		if (!StringUtils.isEmpty(fileName) && fileName.indexOf(".") > 0) {
			fileType = fileName.substring(fileName.indexOf(".") + 1);
		}
		fileName = new StringBuilder(DateUtil.getCurrentTime("yyyyMMddHHmmss")).append("-").append(StringUtil.getUuid())
				.append(".").append(fileType).toString();
		return fileName;
	}

	public static String uploadFile(byte[] bytes, String filePath) {
		if (bytes != null) {
			FileOutputStream fos = null;
			BufferedOutputStream bos = null;
			try {
				// 使用file流写文件
				File file = new File(filePath);
				// 判断父目录是否存在，不存在就创建
				if (!file.getParentFile().exists()) {
					file.getParentFile().mkdirs();
				}
				fos = new FileOutputStream(file);
				bos = new BufferedOutputStream(fos);
				bos.write(bytes);
			} catch (Exception e) {
				return null;
			} finally {
				try {
					if (bos != null) {
						bos.close();
					}
				} catch (IOException e) {
				} finally {
					try {
						if (fos != null) {
							fos.close();
						}
					} catch (IOException e) {
					}
				}
			}

			return filePath;
		} else {
			return null;
		}
	}

	/**
	 * 验证上传文件名、文件路径、文件类型、限制文件大小
	 * 
	 * @param fileName
	 * @param filePath
	 * @param fileType
	 * @param fileSize
	 * @return
	 */
	public static String validateFile(String fileName, String filePath, String fileType, long fileSize) {
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload servletFileUpload = new ServletFileUpload(factory);
		servletFileUpload.setHeaderEncoding("UTF-8");
		long sizeMax = fileSize;
		servletFileUpload.setFileSizeMax(sizeMax);
		String valName = "";
		if (!StringUtil.isEmpty(fileName)) {
			if (fileName.contains(".")) {
				valName = fileName.substring(0, fileName.lastIndexOf("."));
			}
			if (ValidateStr.isFileName(valName)) {// 非法字符校验
				return "文件名存在非法字符";
			}
		}
		if (!FileUtil.validate(fileName.toLowerCase(), fileType)) {
			return "只允许上传" + fileType + "类型的文件！";
		}
		if (fileName.contains("%00")) {
			return "文件名存在非法字符";
		}
		if (!SecurePathUtil.isSecurePath(filePath)) {
			return "存在不安全的文件路径";
		}
		return null;
	}

}
