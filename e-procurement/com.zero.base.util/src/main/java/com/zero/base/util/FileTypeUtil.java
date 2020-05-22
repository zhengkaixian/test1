package com.zero.base.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @ClassName: FileTypeUtil
 * @Description: 文件类型校验
 * @author: 
 * @date: 2019年4月10日 下午4:38:31
 *
 */
public class FileTypeUtil {
	private static Logger logger = LoggerFactory.getLogger(FileTypeUtil.class);
	/**
	 * 特殊文件，还要根据文件名来区别
	 */
	private static final String[] specFiles = { FileType.xls.getName(), FileType.doc.getName(), FileType.xlsx.getName(),
			FileType.docx.getName() };

	/**
	 * @Title: validateFileHeader
	 * @Description: 校验文件类型是否正确
	 * @param filePath
	 * @param checkFileType
	 * @return
	 * @return boolean (这里用一句话描述返回结果说明)
	 */
	public static boolean validateFileHeader(String filePath, FileType checkFileType) {
		boolean result = false;
		if (!StringUtil.isEmpty(filePath) && checkFileType != null) {
			List<FileType> fileTypes = getFileTypeByFilePath(filePath);
			// xls与doc,xlsx与docx的码值相同，需要进一步校验文件名后缀
			boolean checkFileName = isCheckFileName(checkFileType);
			String fileSubType = null;
			if (filePath.indexOf(".") != -1) {
				fileSubType = filePath.substring(filePath.lastIndexOf(".") + 1);
			}
			for (FileType fileType : fileTypes) {
				String code = fileType.getCode();
				if (code.equals(checkFileType.getCode())
						&& (!checkFileName || fileSubType.equalsIgnoreCase(checkFileType.getName()))) {
					result = true;
				}
			}
		} else {
			logger.error("参数输入有误");
		}
		return result;
	}

	/**
	 * @Title: validateFileHeader
	 * @Description: 校验文件类型是否正确
	 * @param is
	 * @param checkFileType
	 * @return
	 * @return boolean (这里用一句话描述返回结果说明)
	 */
	public static boolean validateFileHeader(InputStream is, FileType checkFileType) {
		int matchSize = 0;
		if (is != null && checkFileType != null) {
			List<FileType> fileTypes = getFileTypeByStream(is);
			for (FileType fileType : fileTypes) {
				if (fileType.getCode().equals(checkFileType.getCode())) {
					matchSize++;
				}
			}
		}
		if (matchSize > 1) {
			logger.error("存在多个可以匹配的文件类型，无法准确匹配");
		}
		return matchSize == 1 ? true : false;
	}

	/**
	 * @Title: getFileType
	 * @Description: 根据文件路径，判断文件类型
	 * @param filePath
	 * @return
	 * @return List<String> (这里用一句话描述返回结果说明)
	 */
	public static List<String> getFileType(String filePath) {
		List<String> result = new ArrayList<String>();
		if (!StringUtil.isEmpty(filePath)) {
			File file = new File(filePath);
			if (file.exists()) {
				List<FileType> fileTypes = getFileTypeByFilePath(filePath);
				result = getFileTypeName(fileTypes);
			} else {
				logger.error("文件不存在");
			}
		} else {
			logger.error("文件路径不能为空");
		}
		return result;
	}

	/**
	 * @Title: getFileType
	 * @Description: 根据文件路径，判断文件类型
	 * @param filePath
	 * @return
	 * @return List<FileType> 文件类型
	 */
	private static List<FileType> getFileTypeByFilePath(String filePath) {
		List<FileType> result = new ArrayList<FileType>();
		FileInputStream inputStream = null;
		File file = new File(filePath);
		try {
			inputStream = new FileInputStream(file);
			result = getFileTypeByStream(inputStream);
		} catch (FileNotFoundException e) {
			logger.error("文件加载错误");
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					logger.error("流关闭异常");
				}
			}
		}
		return result;
	}

	/**
	 * @Title: getFileType
	 * @Description: 根据文件流获取文件类型
	 * @param is
	 * @return
	 * @return List<String> (这里用一句话描述返回结果说明)
	 */
	public static List<String> getFileType(InputStream is) {
		List<String> result = new ArrayList<String>();
		if (is != null) {
			List<FileType> types = getFileTypeByStream(is);
			result = getFileTypeName(types);
		}
		return result;
	}

	/**
	 * @Title: getFileTypeName
	 * @Description: 根据文件对象的流信息获取文件类型
	 * @param is
	 * @return
	 * @return String 文件类型
	 */
	private static List<FileType> getFileTypeByStream(InputStream is) {
		List<FileType> result = new ArrayList<FileType>();
		try {
			byte[] b = new byte[4];
			is.read(b, 0, b.length);
			result = getFileTypeByByte(b);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @Title: getFileTypeByByte
	 * @Description: 根据文件头部的自己判断文件类型
	 * @param b
	 * @return
	 * @return List<String>
	 */
	private static List<FileType> getFileTypeByByte(byte[] b) {
		List<FileType> result = new ArrayList<FileType>();
		String filetypeHex = String.valueOf(bytesToHexString(b));// 获取文件流的十六进制字符串表示形式
		FileType filteTypes[] = FileType.values();
		if (filteTypes != null && filteTypes.length > 0) {
			for (FileType filteType : filteTypes) {
				String fileTypeHexValue = filteType.getCode();
				if (filetypeHex.startsWith(fileTypeHexValue)) {
					result.add(filteType);
				}
			}
		}
		return result;
	}

	/**
	 * @Title: bytesToHexString
	 * @Description: 字节转16进制
	 * @param src
	 * @return
	 * @return String (这里用一句话描述返回结果说明)
	 */
	private static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder();
		if (null == src || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v).toUpperCase();
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}

	/**
	 * 需要特殊处理的文件类型
	 * 
	 * @param fileType
	 * @return
	 */
	/**
	 * @Title: isCheckFileName
	 * @Description: 文件头部信息会有相同的码值，所以需要进一步判断该校验文件类型是否还需要校验文件名
	 * @param fileType
	 * @return
	 * @return boolean (这里用一句话描述返回结果说明)
	 */
	private static boolean isCheckFileName(FileType fileType) {
		if (fileType != null && specFiles != null && specFiles.length > 0) {
			for (String type : specFiles) {
				if (type.equals(fileType.getName())) {
					return true;
				}
			}
		}
		return false;
	}

	private static List<String> getFileTypeName(List<FileType> types) {
		List<String> result = new ArrayList<String>();
		if (types != null && types.size() > 0) {
			for (FileType fileType : types) {
				result.add(fileType.getName());
			}
		}
		return result;
	}

	public enum FileType {
		doc("doc", "D0CF11E0"), xls("xls", "D0CF11E0"), xlsx("xlsx", "504B0304"), docx("docx", "504B0304"),
		xml("xml", "3C3F786D"), eap("eap", "00010000");
		/*
		 * bmp("bmp", "424D"), sh("sh", "23212"), jpg("jpg", "FFD8FFE0"), png("png",
		 * "89504E47"), gif("gif", "47494638"), pdf("pdf", "25504446"), flv("flv",
		 * "464C5601"), mp3("mp3", "49443303"), rar("rar", "52617221"), jar("jar",
		 * "504B0304"), exe("exe", "4D5A9000"), jsp("jsp", "3C254020"), avi("avi",
		 * "52494646"), java("java","7061636B"), mp4("mp4", "00000020"), ceb("ceb",
		 * "466F756E"), bat("bat", "40656368")
		 */;

		private String name;
		private String code;

		private FileType(String name, String code) {
			this.name = name;
			this.code = code;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}
	}
}
