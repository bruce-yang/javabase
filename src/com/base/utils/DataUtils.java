package com.base.utils;

import java.text.NumberFormat;

public class DataUtils {
	/**
	 * 精确float类型小数点后面为两位数
	 * @param value
	 * @param point
	 * @return
	 */
	public static String FormatFloat(float value,int point){
		NumberFormat   numFormat   =   NumberFormat.getNumberInstance();   
        numFormat.setMaximumFractionDigits(point);   
        String   str   =   numFormat.format(value);
        return str;
	}

}
