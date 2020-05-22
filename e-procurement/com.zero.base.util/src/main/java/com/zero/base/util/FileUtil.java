package com.zero.base.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 文件操作类
 * 
 * @author
 *
 */
public class FileUtil {

	private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);
	/**
	 * 换行符 (window下是/r/n，linux/unix下是/n)
	 */
	private static final String LINE_SEPARATOR = System.getProperty("line.separator");
	/**
	 * 判断文件是否存在
	 * 
	 * @param destFileName
	 * @return
	 */
	private static final int LOOPTIMES = 500 * 1024 * 1024;// 限制为500M=500*1024Kb

	@SuppressWarnings("unused")
	private static final int BUF_SIZE = 8;// 128kb

	public static boolean fileExists(String destFileName) {
		File file = new File(destFileName);
		if (file.exists()) {
			return true;
		}
		return false;
	}

	private static final int MAX_STR_LEN = Integer.MAX_VALUE;

	/**
	 * @Title: readLine
	 * @Description: 读取一行
	 * @param @param  br
	 * @param @return
	 * @param @throws IOException 参数说明
	 * @return String 返回类型
	 * @throws IOException
	 */
	public static String readLine(BufferedReader br) throws IOException {
		StringBuffer sb = new StringBuffer();
		int intC;
		int i = 0;
		while ((intC = br.read()) != -1) {
			i++;
			if (i > LOOPTIMES)
				break;
			char c = (char) intC;
			if (c == '\n') {
				break;
			}
			if (sb.length() >= MAX_STR_LEN) {
				throw new RuntimeException("readLine input too long");
			}
			sb.append(c);
		}
		if (sb.toString().length() > 0) {
			return sb.toString().replace("\r", "");
		} else if ((char) intC == '\n' && i == 1) {
			return "";
		} else {
			return null;
		}
	}

	/**
	 * @Title: validate
	 * @Description: 根据后缀名，判断文件类型
	 * @param @param  name 文件名
	 * @param @param  fileType 限定的文件类型，逗号隔开
	 * @param @return
	 * @return boolean 返回类型
	 */
	public static boolean validate(String name, String fileType) {
		Boolean validateFlag = false;
		if (StringUtil.isEmpty(fileType)) {
			return true;
		}
		if (fileType.contains(",")) {
			String strs[] = fileType.split(",");
			for (int i = 0; i < strs.length; i++) {
				if (name.endsWith(strs[i])) {
					validateFlag = true;
				}
			}
		} else {
			if (name.endsWith(fileType)) {
				validateFlag = true;
			}
		}
		return validateFlag;
	}

	/**
	 * 创建文件
	 * 
	 * @param destFileName
	 * @return
	 */
	public static boolean createFile(String destFileName) {

		File file = new File(destFileName);
		if (file.exists()) {
			return false;
		}
		if (destFileName.endsWith(File.separator)) {
			logger.info("创建单个文件" + destFileName + "失败，目标不能是目录！");
			return false;
		}
		if (!file.getParentFile().exists()) {
			if (!file.getParentFile().mkdirs()) {
				logger.info("创建目录文件  " + destFileName + " 失败！");
				return false;
			}
		}
		// 创建目标文件
		try {
			if (file.createNewFile()) {
				return true;
			} else {
				logger.info("创建单个文件" + destFileName + "失败！");
				return false;
			}
		} catch (IOException e) {
			logger.error("创建单个文件" + destFileName + "失败！", e);
			return false;
		}

	}

	/**
	 * 创建文件
	 * 
	 * @param destFileName
	 * @return
	 */
	public static File createResFile(String destFileName) {
		File file = new File(destFileName);
		if (file.exists()) {
			return null;
		}
		if (destFileName.endsWith(File.separator)) {
			logger.info("创建单个文件" + destFileName + "失败，目标不能是目录！");
			return null;
		}
		if (!file.getParentFile().exists()) {
			if (!file.getParentFile().mkdirs()) {
				logger.info("创建目录文件  " + destFileName + " 失败！");
				return null;
			}
		}
		// 创建目标文件
		try {
			if (file.createNewFile()) {
				return file;
			} else {
				logger.info("创建单个文件" + destFileName + "失败！");
				return null;
			}
		} catch (IOException e) {
			logger.error("创建单个文件" + destFileName + "失败！", e);
			return null;
		}

	}

	/**
	 * 创建目录
	 * 
	 * @param destDirName
	 * @return
	 */
	public static boolean createDir(String destDirName) {
		File dir = new File(destDirName);
		if (dir.exists()) {
			logger.info("创建临时文件失败，不能创建临时文件所在目录！");
			return false;
		}
		if (!destDirName.endsWith(File.separator))
			destDirName = destDirName + File.separator;
		// 创建单个目录
		if (dir.mkdirs()) {
			logger.info("创建目录" + destDirName + "成功！");
			return true;
		} else {
			logger.info("创建单个目录失败");
			return false;
		}
	}

	/**
	 * 创建临时文件
	 * 
	 * @param prefix
	 * @param suffix
	 * @param dirName
	 * @return
	 */
	public static String createTempFile(String prefix, String suffix, String dirName) {
		File tempFile = null;
		try {
			if (dirName == null) {
				// 在默认文件夹下创建临时文件
				tempFile = File.createTempFile(prefix, suffix);
				return tempFile.getCanonicalPath();
			} else {
				File dir = new File(dirName);
				// 如果临时文件所在目录不存在，首先创建
				if (!dir.exists()) {
					if (!createDir(dirName)) {
						logger.info("创建临时文件失败，不能创建临时文件所在目录！");
						return null;
					}
				}
				tempFile = File.createTempFile(prefix, suffix, dir);
				return tempFile.getCanonicalPath();
			}
		} catch (IOException e) {
			logger.error("创建临时文件失败", e);
			return null;
		}
	}

	/**
	 * 读取文本文件.
	 * 
	 * @param path：指定文件路径和名称
	 * @return
	 */
	public static String readTxtFile(String path) {
		String read;
		String readStr = "";
		FileReader fileread;
		BufferedReader bufread = null;
		try {
			File filename = new File(path);
			fileread = new FileReader(filename);
			bufread = new BufferedReader(fileread);
			try {
				while ((read = FileUtil.readLine(bufread)) != null) {
					readStr = readStr + read + LINE_SEPARATOR;
				}
			} catch (IOException e) {
				logger.error("IO异常", e);
			}
		} catch (FileNotFoundException e) {
			logger.error("文件未找到", e);
		} finally {
			if (bufread != null) {
				try {
					bufread.close();
				} catch (IOException e) {
					logger.error("资源释放异常", e);
				}
			}
		}
		return readStr;
	}

	/**
	 * 写文件.
	 * 
	 * @param newStr:新增写入的内容
	 * @param path:指定文件路径和名称
	 * @throws IOException
	 */
	public static void writeTxtFile(String newStr, String path, boolean isAppend) throws IOException {
		// 写入操作
		String filein = newStr + LINE_SEPARATOR;

		RandomAccessFile mm = null;

		try {
			File filename = new File(path);
			mm = new RandomAccessFile(filename, "rw");
			if (mm != null) {
				if (isAppend) {// 是否追加
					// 文件的长度字节数
					long fileLength = mm.length();
					// 将文件指针移到文件尾
					mm.seek(fileLength);
				}

				// 写入(解决中文乱码问题)
				mm.write(filein.getBytes());
				// mm.write((System.getProperty("line.separator") + filein).getBytes());//写入换行
			}
		} catch (IOException e) {
			throw e;
		} finally {
			if (mm != null) {
				try {
					mm.close();
				} catch (IOException e) {
					throw e;
				}
			}
		}
	}

	/**
	 * 文件大小字节
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static long fileByte(String path) throws IOException {
		File f = new File(path);
		FileInputStream fis = null;
		long result = 0;
		try {
			fis = new FileInputStream(f);
			if (fis != null) {
				result = fis.available();
				return result;
			}
		} catch (FileNotFoundException e2) {
			logger.error("文件未找到", e2);
		} finally {
			if (fis != null) {
				fis.close();
			}
		}
		return 0;

	}

	/**
	 * 计算每mb多少字节
	 * 
	 * @param mbSize
	 * @return
	 */
	public static long fileMbByte(Long mbSize) {
		return mbSize * 1048576;

	}

	/**
	 * 计算字符串大小
	 * 
	 * @param s
	 * @return
	 */
	public static int getWordCount(String s) {
		s = s.replaceAll("[^\\x00-\\xff]", "**");
		int length = s.length();
		return length;
	}

	/**
	 * 转换文件大小单位
	 * 
	 * @param fileS
	 * @return
	 */
	public static String formetFileSize(long fileSize) {// 转换文件大小
		DecimalFormat df = new DecimalFormat("#.00");
		String fileSizeString = "";
		if (fileSize < 1024) {
			fileSizeString = df.format((double) fileSize) + "B";
		} else if (fileSize < 1048576) {
			fileSizeString = df.format((double) fileSize / 1024) + "K";
		} else if (fileSize < 1073741824) {
			fileSizeString = df.format((double) fileSize / 1048576) + "M";
		} else {
			fileSizeString = df.format((double) fileSize / 1073741824) + "G";
		}
		return fileSizeString;
	}

	/**
	 * 以行为单位读取文件，常用于读面向行的格式化文件
	 * 
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static List<String> readFileByLines(String fileName) throws IOException {
		List<String> list = new ArrayList<String>();
		File file = new File(fileName);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = FileUtil.readLine(reader)) != null) {
				list.add(tempString);
			}
		} catch (IOException e) {
			throw e;
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					throw e;
				}
			}
		}
		return list;
	}

	/**
	 * 清空文件内容
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static void clearFileContext(String path) throws IOException {
		File filename = new File(path);
		FileWriter fw = null;
		try {
			fw = new FileWriter(filename);
			if (fw != null) {
				fw.write("");
				fw.flush();
			}
		} catch (IOException e) {
			throw e;
		} finally {
			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					throw e;
				}
			}
		}
	}

	/**
	 * 转换文件大小单位 Mb
	 * 
	 * @param fileS
	 * @return
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
//	public static long fileMbSize2(String pathFile ) throws FileNotFoundException, IOException {//转换文件大小
//		
//		 long size=new FileInputStream(new File(pathFile)).available() / 1024 /1024;  
//		 System.out.println(size+"M");
//		 return size;
//	}

	/*
	 * public static void main(String[] args) throws IOException { // 创建目录 String
	 * dirName = "d:/test"; //createDir(dirName); // 创建文件 String fileName = dirName
	 * + "/testFile1.xml"; CreateFile(fileName); // 创建临时文件 String prefix = "temp";
	 * String suffix = ".txt"; for(int i = 0; i < 10; i++) {
	 * System.out.println("创建了临时文件:" + createTempFile(prefix, suffix, dirName)); }
	 * //读取内容 String path="D:/test1/测试.xml"; //readTxtFile(path); //写入内容
	 * writeTxtFile("测试大小",path,true); //文件大小 //long size=getFileByte(path);
	 * //转换文件大小单位 FormetFileSize(1812);
	 * 
	 * fileByte(path);
	 * 
	 * String s="<?xml version=\"1.0\" encoding=\"UTF-8\"?>aaa";
	 * System.out.println(s.replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>",
	 * "ddd")); }
	 */
	public static byte[] convertInputStreamToByte(InputStream inputStream) {
		try {
			int size = inputStream.available();
			byte[] data = new byte[size];
			inputStream.read(data);
			inputStream.close();
			inputStream = null;
			return data;
		} catch (IOException e) {
			throw new RuntimeException("", e);
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					// 忽略
				}
			}
		}
	}

	/**
	 * 统一文件路径分隔符
	 * 
	 * @param zipFileName
	 * @return
	 */
	public static String replaceFileSeparator(String zipFileName) {
		if (zipFileName == null || "".equals(zipFileName))
			return "";
		String vis = zipFileName.replaceAll("\\\\", "/");
		String reVis = vis.replaceAll("//+", "/");
		return reVis;
	}

	/**
	 * 文件删除
	 * 
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static boolean deleteFile(String path) throws Exception {
		boolean flag = false;
		File file = new File(path);
		// 路径为文件且不为空则进行删除
		if (file.isFile() && file.exists()) {
			file.delete();
			flag = true;
		}
		return flag;
	}

	/**
	 * 删除目录（文件夹）以及目录下的文件
	 * 
	 * @param sPath 被删除目录的文件路径
	 * @return 目录删除成功返回true，否则返回false
	 */
	public static boolean deleteDirectory(String sPath) {
		// 如果sPath不以文件分隔符结尾，自动添加文件分隔符
		if (!sPath.endsWith(File.separator)) {
			sPath = sPath + File.separator;
		}
		File dirFile = new File(sPath);
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			return false;
		}
		boolean flag = true;
		// 删除文件夹下的所有文件(包括子目录)
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 删除子文件
			if (files[i].isFile()) {
				try {
					flag = deleteFile(files[i].getAbsolutePath());
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (!flag)
					break;
			} else { // 删除子目录
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag)
					break;
			}
		}
		if (!flag)
			return false;
		// 删除当前目录
		if (dirFile.delete()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 根据路径删除指定的目录或文件，无论存在与否
	 * 
	 * @param sPath 要删除的目录或文件
	 * @return 删除成功返回 true，否则返回 false。
	 */
	public static boolean deleteFileOrFolder(String sPath) {
		boolean flag = false;
		File file = new File(sPath);
		// 判断目录或文件是否存在
		if (!file.exists()) { // 不存在返回 false
			return flag;
		} else {
			// 判断是否为文件
			if (file.isFile()) { // 为文件时调用删除文件方法
				try {
					return deleteFile(sPath);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else { // 为目录时调用删除目录方法
				return deleteDirectory(sPath);
			}
		}
		return flag;
	}

	public static boolean writeFile(InputStream in, String path) {
		File file = FileUtil.createResFile(path);
		OutputStream out = null;
		try {
			out = new FileOutputStream(file, true);
			byte[] buff = new byte[1024];
			int len = -1;
			while ((len = in.read(buff)) > 0) {
				out.write(buff, 0, len);
			}
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (Exception e) {
				}
			}
		}

	}
}
