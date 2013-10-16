package com.base.utils;

import java.util.Locale;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.apache.log4j.Logger;

/**
 * 取中文字母
 * @author lucene_yang
 * @email yangfuchao2010@gmail.com
 * @date 2010-6-5下午02:09:32
 * @project_name gamechannel
 *
 */
public class ChineseSpellingExtract {
	private static Logger log = Logger.getLogger(ChineseSpellingExtract.class);
	// 字母Z使用了两个标签，这里有２７个值
	// i, u, v都不做声母, 跟随前面的字母
	private char[] chartable = { '啊', '芭', '擦', '搭', '蛾', '发', '噶', '哈', '哈', '击', '喀', '垃', '妈', '拿', '哦', '啪', '期',
			'然', '撒', '塌', '塌', '塌', '挖', '昔', '压', '匝', '座' };

	private char[] alphatable = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
			'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
	private int[] table = new int[27];
	// 初始化
	{
		for (int i = 0; i < 27; ++i) {
			table[i] = gbValue(chartable[i]);
		}
	}

	public ChineseSpellingExtract() {
	}

	// 主函数,输入字符,得到他的声母,
	// 英文字母返回对应的大写字母
	// 其他非简体汉字返回 '0'

	public char Char2Alpha(char ch) {

		if (ch >= 'a' && ch <= 'z')
			return (char) (ch - 'a' + 'A');
		if (ch >= 'A' && ch <= 'Z')
			return ch;
		int gb = gbValue(ch);
		if (gb < table[0])
			return '0';
		int i;
		for (i = 0; i < 26; ++i) {
			if (match(i, gb))
				break;
		}
		if (i >= 26)
			return '0';
		else
			return alphatable[i];
	}

	// 根据一个包含汉字的字符串返回一个汉字拼音首字母的字符串
	public String String2Alpha(String SourceStr) {

		String Result = "";
		int StrLength = SourceStr.length();
		int i;
		try {
			for (i = 0; i < StrLength; i++) {
				Result += Char2Alpha(SourceStr.charAt(i));
			}
		} catch (Exception e) {
			Result = "";
		}
		return Result;
	}

	private boolean match(int i, int gb) {

		if (gb < table[i])
			return false;
		int j = i + 1;

		// 字母Z使用了两个标签
		while (j < 26 && (table[j] == table[i]))
			++j;
		if (j == 26)
			return gb <= table[j];
		else
			return gb < table[j];
	}

	// 取出汉字的编码
	private int gbValue(char ch) {

		String str = new String();
		str += ch;
		try {
			byte[] bytes = str.getBytes("GB2312");
			if (bytes.length < 2)
				return 0;
			return (bytes[0] << 8 & 0xff00) + (bytes[1] & 0xff);
		} catch (Exception e) {
			return 0;
		}
	}

	// ==================================建议使用以下方法===================================
	/**
	 * 得到 全拼
	 */
	public static String getPingYin(String src) {
		if(StringUtilsExtends.isNotBlank(StringUtilsExtends.trimToNull(src))){
		char[] singleChar = null;
		singleChar = src.toCharArray();
		String[] plingYinArray = new String[singleChar.length];
		HanyuPinyinOutputFormat outputPingYin = new HanyuPinyinOutputFormat();
		outputPingYin.setCaseType(HanyuPinyinCaseType.UPPERCASE);
		outputPingYin.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		outputPingYin.setVCharType(HanyuPinyinVCharType.WITH_V);
		String result = "";
		int t0 = singleChar.length;
		try {
			for (int i = 0; i < t0; i++) {
				// 判断是否为汉字字符
				if (java.lang.Character.toString(singleChar[i]).matches("[\\u4E00-\\u9FA5]+")) {
					plingYinArray = PinyinHelper.toHanyuPinyinStringArray(singleChar[i], outputPingYin);
					result += plingYinArray[0];
				} else {
					result += java.lang.Character.toString(singleChar[i]);
				}
			}
			return result;
		} catch (BadHanyuPinyinOutputFormatCombination e1) {
			e1.printStackTrace();
		}
		return result;
		}
		return "W";
	}

	/**
	 * 得到中文首字母
	 * 
	 * @param str
	 * @return
	 */
	public static String getPinYinHeadChar(String str) {
		if(StringUtilsExtends.isNotBlank(StringUtilsExtends.trimToNull(str))){
		String convert = "";
		for (int j = 0; j < str.length(); j++) {
			char word = str.charAt(j);
			String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
			if (pinyinArray != null) {
				convert += pinyinArray[0].charAt(0);
			} else {
				convert += word;
			}
		}
		convert = convert.toUpperCase(Locale.CHINA);			
		convert=convert.replaceAll(" ","");
		return convert;
		}
		else{
			return "W";
		}
	}

	/**
	 * 将字符串转移为ASCII码
	 * 
	 * @param cnStr
	 * @return
	 */
	public static String getCnASCII(String cnStr) {
		StringBuffer strBuf = new StringBuffer();
		byte[] bGBK = cnStr.getBytes();
		for (int i = 0; i < bGBK.length; i++) {
			strBuf.append(Integer.toHexString(bGBK[i] & 0xff));
		}
		return strBuf.toString();
	}

	public static void main(String[] args) {
		ChineseSpellingExtract spelling = new ChineseSpellingExtract();
		
		log.info("===>"+getPinYinHeadChar(" 子弹头"));
		return;
	}
}