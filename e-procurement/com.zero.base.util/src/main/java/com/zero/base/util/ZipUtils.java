package com.zero.base.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * zip压缩工具类
 * @author kaixian.zheng
 *
 */
public class ZipUtils {
	
	/**
	 * 文件或文件夹压缩成.zip文件
	 * @param zipFileName 目的地Zip文件夹全路径 (D:/test/temp.zip)
	 * @param sourceFileName 源文件（带压缩的文件或文件夹） (D:/test/temp)
	 * @throws Exception
	 */
	public static void fileOrFolderToZip(String zipFileName, String sourceFileName) throws Exception {
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName)); // 创建zip输出流
		BufferedOutputStream bos = new BufferedOutputStream(out); // 创建缓冲输出流
		File sourceFile = new File(sourceFileName);

		// 调用函数
		compress(out, bos, sourceFile, sourceFile.getName());

		bos.close();
		out.close();
	}
	
	/**
	 * 压缩文件或文件夹
	 * @param zipOut zip输出流
	 * @param bos 缓冲输出流
	 * @param sourceFile 
	 * @param base 要压缩的文件或文件夹名称
	 * @throws Exception
	 */
	public static void compress(ZipOutputStream zipOut, BufferedOutputStream bos, File sourceFile, String base) throws Exception {
		// 如果路径为目录（文件夹）
		if (sourceFile.isDirectory()) {
			// 取出文件夹中的文件（或子文件夹）
			File[] flist = sourceFile.listFiles();

			if (flist.length == 0) { // 如果文件夹为空，则只需在目的地zip文件中写入一个目录进入点
				zipOut.putNextEntry(new ZipEntry(base + "/"));
			} else { // 如果文件夹不为空，则递归调用compress，文件夹中的每一个文件（或文件夹）进行压缩
				for (int i = 0; i < flist.length; i++) {
					compress(zipOut, bos, flist[i], base + "/" + flist[i].getName());
				}
			}
		} else {	// 如果不是目录（文件夹），即为文件，则先写入目录进入点，之后将文件写入zip文件中
			zipOut.putNextEntry(new ZipEntry(base));
			FileInputStream fos = new FileInputStream(sourceFile);
			BufferedInputStream bis = new BufferedInputStream(fos);

			int tag;
			
			// 将源文件写入到zip文件中
			while ((tag = bis.read()) != -1) {
				zipOut.write(tag);
			}
			bis.close();
			fos.close();
		}
	}
	
	public static void main(String[] args) {
		//1. -----------------------文件及文件夹压缩测试开始---------------------
		String zipFileName = "D:/test/test.zip"; // 目的地Zip文件
		String sourceFileName = "D:/test/temp"; // 源文件夹（压缩的文件夹）
//		String sourceFileName = "D:/test/temp/test1.docx"; // 源文件（压缩的文件）
		try {
			fileOrFolderToZip(zipFileName, sourceFileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//1. -----------------------文件及文件夹压缩测试结束---------------------
	}
	
}
