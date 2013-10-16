package com.base.utils;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Vector;

public class StringCode {
	
	
	public static  String getMarkString(BigDecimal bd){
		
		
		
		String mark_string = "";

	
		mark_string = String.valueOf(bd);

		if ("10.0".equals(mark_string)) {
			mark_string = "10";
		} else if (!"null".equals(mark_string)) {
			mark_string = mark_string.length() > 3 ? mark_string
					.substring(0, 3) : mark_string;
		}

		else if ("null".equals(mark_string)) {
			mark_string = null;
		}
		
		
		return mark_string;
	}
	
	
	
	

	private static int[] key = { 181, 141, 91, 193, 61, 59, 39, 75, 135, 155,
			189, 57, 9, 81, 171, 231, 193, 123, 132 };

	/**
	 * cleanString Ensure that the string has no characters that are out of
	 * bounds of the normal character set. Used to avoid char conversion errors
	 * when saving strings to the datavas.base.
	 */
	public static String cleanString(String value) {
		// Right now use the simple screen of 1 to 127
		// Check to see if value is ok - if yet return it
		if (value == null) {
			return value;
		}

		int l = value.length();
		int i;
		boolean ok = true;

		for (i = 0; i < l; i++) {
			if (value.charAt(i) < 1 || value.charAt(i) > 127) {
				ok = false;
				break;
			}
		}
		if (ok) {
			return value;
		}

		// Not OK, build new string replacing unwanted character values
		StringBuffer result = new StringBuffer(value);
		for (i = 0; i < l; i++) {
			if (result.charAt(i) < 1 || result.charAt(i) > 127) {
				result.setCharAt(i, '-');
			}
		}
		return result.toString();
	}

	public static String encodeStringToHex(String string) {
		byte[] value = string.getBytes();
		xorByteArray(value);
		String result = toHexString(value);
		return result;
	}

	public static String decodeHexString(String value) {
		byte[] bytes = parseHexString(value);
		xorByteArray(bytes);
		String result = new String(bytes);
		return result;
	}

	public static byte[] encodeString(String string) {
		byte[] value = string.getBytes();
		xorByteArray(value);
		return value;
	}

	public static String decodeString(byte[] value) {
		xorByteArray(value);
		return new String(value);
	}

	public static byte[] parseHexString(String value) {
		// parse every two bytes
		int size = value.length() / 2;
		byte[] result = new byte[size];

		for (int i = 0; i < size; i++) {
			byte b1 = parseHexChar(value.charAt(i * 2));
			byte b2 = parseHexChar(value.charAt(i * 2 + 1));
			result[i] = (byte) ((b1 << 4) + b2);
		}
		return result;
	}

	public static String toHexString(byte[] buffer) {
		StringBuffer result = new StringBuffer();

		for (int i = 0; i < buffer.length; i++) {
			String val = Integer.toHexString((int) buffer[i] & 0xff);
			if (val.length() == 1) {
				result.append("0");
			}
			result.append(val);
		}
		return result.toString();
	}

	private static void xorByteArray(byte[] value) {
		int c = 0;
		for (int i = 0; i < value.length; i++) {
			value[i] = (byte) (value[i] ^ key[c]);
			c = (c + 1) % key.length;
		}
	}

	private static byte parseHexChar(char c) {
		if (c >= '0' && c <= '9') {
			return (byte) (c - '0');
		}
		if (c >= 'a' && c <= 'z') {
			return (byte) ((c - 'a') + 10);
		}
		if (c >= 'A' && c <= 'Z') {
			return (byte) ((c - 'A') + 10);
		}
		return 0;
	}

	public static String encrypt(String source) {
		if (source == null) {
			return "";
		}
		return (toHexString(encodeString(source)));
	}

	public static String decrypt(String source) {
		if (source == null) {
			return "";
		}
		return (decodeString(parseHexString(source)));
	}

	public static String genRandomString(int length) {
		Random rand = new Random(System.currentTimeMillis());
		String str = "";
		int j;
		int k;
		for (int i = 0; i < length; i++) {
			j = rand.nextInt(26);
			k = rand.nextInt(2);
			switch (k) {
			case 0:
				j += 65; // A-Z
				break;
			case 1:
				j += 97; // a-z
			}
			char c = (char) j;
			str += c;
		}
		return str;
	}

	/**
	 * 本方法替换作为模板的String中的MacroVar - 用${}括起来的变量
	 * 
	 * @param source
	 * @param propMacroVars
	 *            包含宏变量的 name-value 对
	 * @return
	 */
	public static String replaceMacroVars(String source,
			Properties propMacroVars) {
		for (int index = 0; index != -1;) {
			index = source.indexOf("${");
			if (index != -1) {
				int startIndex = index + 2;
				index = source.indexOf("}", index);
				String varkey = source.substring(startIndex, index);
				String varvalue = propMacroVars.getProperty(varkey, varkey);
				source = source.substring(0, startIndex - 2) + varvalue
						+ source.substring(index + 1);
				index = startIndex - 2 + varvalue.length();
			}
		}
		return source;
	}

	/**
	 * 本方法替换作为模板的String中的MacroVar - 用${}括起来的变量
	 * 
	 * @param source
	 * @param propMacroVars
	 *            包含宏变量的 name-value 对
	 * @return
	 */
	public static String replaceMacroVars(String source, String[] propMacroVars) {
		Properties p = new Properties();
		for (int i = 0; i < propMacroVars.length; i++) {
			if (propMacroVars[i] != null) {
				p.setProperty(i + "", propMacroVars[i]);
			}
		}
		return replaceMacroVars(source, p);
	}

	public static long[] strings2long(String[] ids) {
		if (null == ids) {
			return null;
		}
		long[] ret = new long[ids.length];
		for (int i = 0; i < ids.length; i++) {
			try {
				ret[i] = Long.parseLong(ids[i]);
			} catch (NumberFormatException ex) {
				ret[i] = 0;
			}
		}
		return ret;
	}

	public static long[] string2Longs(String str) {
		Vector ret = new Vector();
		int index = str.indexOf(",");
		while (index >= 0) {
			String sid = str.substring(0, index);
			try {
				Long lid = new Long(sid);
				ret.add(lid);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			str = str.substring(index + 1);
			index = str.indexOf(",");
		}
		if (str != null) {
			try {
				Long lid = new Long(str);
				ret.add(lid);
			} catch (Exception ex) {
				ex.printStackTrace();
			}

		}
		long[] ls = new long[ret.size()];
		for (int i = 0; i < ret.size(); i++) {
			ls[i] = ((Long) ret.elementAt(i)).longValue();
		}
		return ls;
	}

	/**
	 * 分割字符串. for jdk1.3
	 * 
	 * @param source
	 * @param split
	 * @return
	 */
	public static String[] split(String source, String split) {
		ArrayList wordArray = new ArrayList();

		String theSource = source.trim();
		int index = theSource.indexOf(split);
		while (index != -1) {
			String word = theSource.substring(0, index);
			wordArray.add(word);
			theSource = theSource.substring(index + split.length(), theSource
					.length());
			index = theSource.indexOf(split);
		}

		wordArray.add(theSource);

		String[] ret = new String[wordArray.size()];
		wordArray.toArray(ret);
		return ret;
	}

	/**
	 * 将byte数组转换为表示16进制值的字符串， 如：byte[]{8,18}转换为：0813， 和public static byte[]
	 * hexStr2ByteArr(String strIn) 互为可逆的转换过程
	 * 
	 * @param arrB
	 *            byte[]
	 * @return String
	 * @throws Exception
	 */
	public static String byteArr2HexStr(byte[] arrB) throws Exception {
		int iLen = arrB.length;
		// 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
		StringBuffer sb = new StringBuffer(iLen * 2);
		for (int i = 0; i < iLen; i++) {
			int intTmp = arrB[i];
			// 把负数转换为正数
			while (intTmp < 0) {
				intTmp = intTmp + 256;
			}
			// 小于0F的数需要在前面补0
			if (intTmp < 16) {
				sb.append("0");
			}
			sb.append(Integer.toString(intTmp, 16));
		}
		return sb.toString();
	}

	/**
	 * 将表示16进制值的字符串转换为byte数组， 和public static String byteArr2HexStr(byte[] arrB)
	 * 互为可逆的转换过程
	 * 
	 * @param strIn
	 *            String
	 * @return byte[]
	 * @throws Exception
	 */
	public static byte[] hexStr2ByteArr(String strIn) throws Exception {
		byte[] arrB = strIn.getBytes();
		int iLen = arrB.length;

		// 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
		byte[] arrOut = new byte[iLen / 2];
		for (int i = 0; i < iLen; i = i + 2) {
			String strTmp = new String(arrB, i, 2);
			arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
		}
		return arrOut;
	}

	/**
	 * 将Object中的一些属性都打印出来
	 * 
	 * @param obj
	 * @return
	 */
	// public static String toString(Object obj){
	// if(obj==null){
	// return "Object is NULL";
	// }
	// StringBuffer buffer = new StringBuffer(300);
	// buffer.append("Object Class = ").append(obj.getClass().getName());
	//
	// Method[] methods = EntityConvertor.getMethods(obj.getClass());
	// for(int i=0; i< methods.length; i++){
	// Method method = methods[i];
	// String name = method.getName();
	// try {
	// if(name.substring(0,3).equalsIgnoreCase("get")){
	// Object value = method.invoke(obj,null);
	// buffer.append("\n\tAttribute: ").append(name.substring(3,name.length())).append(" = ").append(value);
	// }else if(name.substring(0,2).equalsIgnoreCase("is")){
	// Object value = method.invoke(obj,null);
	// buffer.append("\n\tAttribute: ").append(name.substring(2,name.length())).append(" = ").append(value);
	// }
	// } catch (Exception ex) {
	// ex.printStackTrace();
	// }
	// }
	// return buffer.toString();
	// }

	public static boolean isBlank(String str) {
		return (str == null || str.trim().length() == 0);
	}

	public static boolean isNotBlank(String str) {
		return !isBlank(str);
	}

	public static boolean isNumber(String value) {
		if (isBlank(value)) {
			return false;
		}

		for (int i = 0; i < value.length(); i++) {
			if (value.charAt(i) > '9' || value.charAt(i) < '0') {
				return false;
			}
		}
		return true;
	}

	public static boolean isMobileNumber(String value) {
		return isNumber(value) && value.length() == 11
				&& value.startsWith("13");
	}

	public static boolean isEmail(String email) {
		return !isBlank(email) && email.indexOf("@") > 0
				&& email.indexOf(".") > 0;
	}

	public static boolean isStr(String loginName) {

		byte[] temp = loginName.getBytes();
		for (int i = 0; i < temp.length; i++) {
			if (temp[i] >= 0)
				return false;
		}
		return true;
	}

	public static boolean isNum(String msg) {
		if (java.lang.Character.isDigit(msg.charAt(0))) {
			return true;
		}
		return false;
	}

	/**
	 * 处理特殊字符
	 * 
	 * @param input
	 * @return
	 */
	public static boolean hasSpecialChars(String input)// 判断特殊字符
	{
		boolean flag = false;
		if ((input != null) && (input.length() > 0)) {
			char c;
			for (int i = 0; i <= input.length() - 1; i++) {
				c = input.charAt(i);
				switch (c) {
				case '>':
					flag = true;
					break;
				case '<':
					flag = true;
					break;
				case '"':
					flag = true;
					break;
				case '&':
					flag = true;
					break;
				case '+':
					flag = true;
					break;
				case '!':
					flag = true;
					break;
				case '$':
					flag = true;
					break;
				case '^':
					flag = true;
					break;
				case '*':
					flag = true;
					break;
				case '#':
					flag = true;
					break;
				case ' ':
					flag = true;
					break;
				}
			}
		}
		return flag;
	}

	/*
	 * 16进制数字字符集
	 */
	private static String hexString = "0123456789ABCDEF";

	/*
	 * 将字符串编码成16进制数字
	 */
	private static String encodeHexStr(String str) {
		// 根据默认编码获取字节数组
		byte[] bytes = str.getBytes();
		StringBuilder sb = new StringBuilder(bytes.length * 2);
		// 将字节数组中每个字节拆解成2位16进制整数
		for (int i = 0; i < bytes.length; i++) {
			sb.append(hexString.charAt((bytes[i] & 0xf0) >> 4));
			sb.append(hexString.charAt((bytes[i] & 0x0f) >> 0));
		}
		return sb.toString();
	}

	/*
	 * 将16进制数字解码成字符串
	 */
	private static String decodeHexStr(String bytes) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream(
				bytes.length() / 2);
		// 将每2位16进制整数组装成一个字节
		for (int i = 0; i < bytes.length(); i += 2)
			baos.write((hexString.indexOf(bytes.charAt(i)) << 4 | hexString
					.indexOf(bytes.charAt(i + 1))));
		return new String(baos.toByteArray());
	}

	/**
	 * 规则重写敏感数据加密
	 * 
	 * @param args
	 */

	public static String encryptForUrlReWrite(String source) {
		if (source == null)
			return null;
		return encrypt(encodeHexStr(String.valueOf(source)));
	}

	/**
	 * 规则重写敏感数据解密
	 * 
	 * @param args
	 */
	public static String decryptForUrlReWrite(String code) {
		if (code == null)
			return null;
		return decodeHexStr((decrypt(code)));
	}

	/**
	 * 规则重写敏感数据加密
	 * 
	 * @param args
	 */

	public static String encryptForUrlReWrite(String source, String key) {
		if (source == null)
			return null;
		return encrypt(encodeHexStr(String.valueOf(source + "-" + key)));
	}

	/**
	 * 规则重写敏感数据解密
	 * 
	 * @param args
	 */
	public static String decryptForUrlReWrite(String code, String key) {
		if (code == null)
			return null;
		return StringUtilsExtends.substringBefore(
				decodeHexStr((decrypt(code))), "-");
	}

	public static final String randomString(int length) {
		Random randGen = null;
		char[] numbersAndLetters = null;
		Object initLock = new Object();

		if (length < 1) {
			return null;
		}
		if (randGen == null) {
			synchronized (initLock) {
				if (randGen == null) {
					randGen = new Random();
					numbersAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyz"
							+ "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ")
							.toCharArray();
					// numbersAndLetters =
					// ("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
				}
			}
		}
		char[] randBuffer = new char[length];
		for (int i = 0; i < randBuffer.length; i++) {
			randBuffer[i] = numbersAndLetters[randGen.nextInt(71)];
			// randBuffer[i] = numbersAndLetters[randGen.nextInt(35)];
		}
		return new String(randBuffer);
	}
	
	public static final String randomNumString(int length) {
		Random randGen = null;
		char[] numbersAndLetters = null;
		Object initLock = new Object();

		if (length < 1) {
			return null;
		}
		if (randGen == null) {
			synchronized (initLock) {
				if (randGen == null) {
					randGen = new Random();
					numbersAndLetters = ("0123456789" + "0123456789" + "0123456789" + "0123456789")
							.toCharArray();
					// numbersAndLetters =
					// ("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
				}
			}
		}
		char[] randBuffer = new char[length];
		for (int i = 0; i < randBuffer.length; i++) {
			randBuffer[i] = numbersAndLetters[randGen.nextInt(39)];
			// randBuffer[i] = numbersAndLetters[randGen.nextInt(35)];
		}
		return new String(randBuffer);
	}

	public static String selectObjectIdString(List selectObjectIdList) {

		StringBuilder sb = new StringBuilder();
		if (selectObjectIdList != null && !selectObjectIdList.isEmpty()) {
			for (int i = 0; i < selectObjectIdList.size(); i++) {

				Integer objectId = (Integer) Integer.parseInt(String
						.valueOf(selectObjectIdList.get(i)));

				if (objectId != null) {
					if (i != selectObjectIdList.size() - 1) {
						sb.append(objectId + ",");
					} else {
						sb.append(objectId);
					}
				}
			}
		}
		String selectObjectIdString = "(" + sb.toString() + ")";

		if (selectObjectIdString.equals("()")) {
			selectObjectIdString = "(0)";
		}

		return selectObjectIdString;
	}

	public static void main(String[] args) {

		// System.out.println("-----"+java.net.URLEncoder.encode("怀化"));
		// String encodeStr=encryptForUrlReWrite("10");
		// String dncodeStr=decryptForUrlReWrite(encodeStr);
		// System.out.println(encodeStr+"========"+dncodeStr);
		for (int i = 0; i < 100; i++) {
			System.out.println(randomNumString(32));
		}
	}

}