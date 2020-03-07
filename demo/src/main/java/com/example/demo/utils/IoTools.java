package com.example.demo.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 工具类
 **/
public class IoTools {

	/**
	 * 输入流转字符串读取,不对输入流进行关闭操作
	 * 
	 * @param InputStream in 输入流
	 * @throws Exception
	 * @return String
	 **/
	public static String inputStreatToString(InputStream in, String charset) throws Exception {
		return new String(IoTools.inputStreatToBytes(in), charset).trim();
	}

	/**
	 * 输入流转字节数组,不对输入流进行关闭操作
	 * 
	 * @param InputStream in 输入流
	 * @throws Exception
	 * @return byte[]
	 **/
	public static byte[] inputStreatToBytes(InputStream in) throws Exception {
		ByteArrayOutputStream outArray = new ByteArrayOutputStream();
		byte[] tempArray = new byte[512];
		int len = 0;
		byte[] data = null;

		// 批量读取数据
		while ((len = in.read(tempArray)) != -1)
			outArray.write(tempArray, 0, len);

		data = outArray.toByteArray();
		outArray.close();

		return data;
	}

	public static File inputStreatToFile(InputStream in, File file) throws Exception {
		FileOutputStream outFile = new FileOutputStream(file);
		byte[] tempArray = new byte[2048];
		int len = 0;

		// 批量读取数据
		while ((len = in.read(tempArray)) != -1) {
			outFile.write(tempArray, 0, len);
			outFile.flush();
		}

		outFile.close();

		return file;
	}

	/**
	 * 提取字符串
	 **/
	public static String loadStrByRegx(String regx, String str) throws Exception {
		Pattern pattern = Pattern.compile(regx);
		Matcher matcher = pattern.matcher(str);
		if (matcher.find())
			return str.substring(matcher.start(), matcher.end());
		else
			return "";
	}

	/**
	* 
	* **/
	public static String FileToStr(File file) throws Exception {
		FileInputStream fin = new FileInputStream(file);
		String text = new String(inputStreatToBytes(fin));
		fin.close();
		return text;
	}

	/**
	 * 
	 * **/
	public static void StrToFile(byte[] text, File file) throws Exception {
		FileOutputStream fot = new FileOutputStream(file);
		fot.write(text, 0, text.length);
		fot.flush();
		fot.close();
	}

}