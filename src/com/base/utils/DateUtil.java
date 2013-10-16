package com.base.utils;



import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang.StringUtils;

public class DateUtil {

	private static transient int gregorianCutoverYear = 1582;
	
	public static int compDate(Date date1,Date date2){
		Calendar ca1 = Calendar.getInstance();
		Calendar ca2 = Calendar.getInstance();
		ca1.setTime(date1); 
		ca2.setTime(date2);  
		int distanceMin = ca1.get(Calendar.MINUTE) - ca2.get(Calendar.MINUTE); 
		return distanceMin;	
	}
	
	public static int compDate1(Date date1,Date date2){
		Calendar ca1 = Calendar.getInstance();
		Calendar ca2 = Calendar.getInstance();
		ca1.setTime(date1); 
		ca2.setTime(date2);  
		int distanceMin = ca1.get(Calendar.SECOND) - ca2.get(Calendar.SECOND); 
		return distanceMin;	
	}
	
	
	
	public static int compDate2(Date date1,Date date2){
		Calendar ca1 = Calendar.getInstance();
		Calendar ca2 = Calendar.getInstance();
		ca1.setTime(date1); 
		ca2.setTime(date2);  
		int distanceMin = ca1.get(Calendar.DAY_OF_MONTH) - ca2.get(Calendar.DAY_OF_MONTH); 
		return distanceMin;	
	}
	
	public static int compDate3(Date date1,Date date2){
		Calendar ca1 = Calendar.getInstance();
		Calendar ca2 = Calendar.getInstance();
		ca1.setTime(date1);
		ca2.setTime(date2);
		int day1=ca1.get(Calendar.DAY_OF_MONTH);
		int year1=ca1.get(Calendar.YEAR);
		int month1=ca1.get(Calendar.MONTH);
		int day2=ca2.get(Calendar.DAY_OF_MONTH);
		int year2=ca2.get(Calendar.YEAR);
		int month2=ca2.get(Calendar.MONTH);
		ca1.set(year1, month1,day1);
		ca2.set(year2, month2,day2);
		int distanceMin = (int)((ca1.getTimeInMillis()-ca2.getTimeInMillis())/(1000*60*60*24)); 
		return distanceMin;	
	}
	
	public static int compDate5(Date date1,Date date2){
		Calendar ca1 = Calendar.getInstance();
		Calendar ca2 = Calendar.getInstance();
		ca1.setTime(date1);
		ca2.setTime(date2);
		int day1=ca1.get(Calendar.DAY_OF_MONTH);
		int year1=ca1.get(Calendar.YEAR);
		int month1=ca1.get(Calendar.MONTH);
		int day2=ca2.get(Calendar.DAY_OF_MONTH);
		int year2=ca2.get(Calendar.YEAR);
		int month2=ca2.get(Calendar.MONTH);
		ca1.set(year1, month1,day1);
		ca2.set(year2, month2,day2);
		int distanceMin = (int)((ca1.getTimeInMillis()-ca2.getTimeInMillis())/(1000*60*60)); 
		return distanceMin;	
	}
	
	public static int compDate4(Date date1,Date date2){
		Calendar ca1 = Calendar.getInstance();
		Calendar ca2 = Calendar.getInstance();
		ca1.setTime(date1);
		ca2.setTime(date2);
		int day1=ca1.get(Calendar.DAY_OF_MONTH);
		int year1=ca1.get(Calendar.YEAR);
		int month1=ca1.get(Calendar.MONTH);
		int day2=ca2.get(Calendar.DAY_OF_MONTH);
		int year2=ca2.get(Calendar.YEAR);
		int month2=ca2.get(Calendar.MONTH);
		ca1.set(year1, month1,day1);
		ca2.set(year2, month2,day2);
		int distanceMin = (int)((ca1.getTimeInMillis()-ca2.getTimeInMillis())); 
		return distanceMin;	
	}
	
	/**
	 * 计算相差几天几小时几秒
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static String comDateStr(Date date1,Date date2){
		int day=0,hours, minutes, seconds;
		int time=DateUtil.compDate4(date1,date2);
		int timeInSeconds=time/1000;
		hours = timeInSeconds /3600;
		if(hours>23){
			day=hours/24;
			hours=hours%24;
		}
		timeInSeconds = timeInSeconds -(day*3600*24)-(hours * 3600);
		minutes = timeInSeconds / 60;
		timeInSeconds = timeInSeconds - (minutes * 60);
		seconds = timeInSeconds;
		String outstr="";
		if(day>0)outstr+=day+"天";
		if(hours>0)outstr+=hours+"小时";
		if(minutes>0)outstr+=minutes+"分";
		outstr+=seconds+"秒";
		return outstr;	
	}
	
	public static int comDateInt(Date date1,Date date2){
		int day=0,hours, minutes, seconds;
		int time=DateUtil.compDate4(date1,date2);
		int timeInSeconds=time/1000;
		hours = timeInSeconds /3600;
		return hours;
	}
	public static Date strToDate(String str) {
		
		Date date = null;
		try {
			// date = java.sql.Date.valueOf(str);
			SimpleDateFormat sdate = new SimpleDateFormat("yyyy-MM-dd");
			date = (Date) sdate.parse(str);
		} catch (Exception e) {
			e.getStackTrace();
		}
		return date;
	}
	
	public static Date strToDate1(String str) {
		Date date = null;
		try {
			// date = java.sql.Date.valueOf(str);
			SimpleDateFormat sdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			date = (Date) sdate.parse(str);
		} catch (Exception e) {
			e.getStackTrace();
		}
		return date;
	}
	
	public static Date strToDate2(String str) {
		Date date = null;
		try {
			// date = java.sql.Date.valueOf(str);
			SimpleDateFormat sdate = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			date = (Date) sdate.parse(str);
		} catch (Exception e) {
			e.getStackTrace();
		}
		return date;
	}

	
	public static String dateToStr3(Date date) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat sdate = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return sdate.format(date);
	}	

	public static Timestamp strToTimestamp(String str) {
		Date date = null;
		try {
			// date = java.sql.Date.valueOf(str);
			SimpleDateFormat sdate = new SimpleDateFormat("yyyy-MM-dd");
			date = (Date) sdate.parse(str);
		} catch (Exception e) {
			e.getStackTrace();
		}
		return new Timestamp(date.getTime());
	}


	public static String dateToStr1(Date date) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat sdate = new SimpleDateFormat("yyyyMMddHHmmss");
		return sdate.format(date);
	}
	
	public static Date stringToDate(String str) {
		if (str == null) {
			return new Date();
		}
		Date date =null;
		SimpleDateFormat sdate = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date= sdate.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
			date=new Date();
		}
		return date;
	}
	
	public static String dateToStr(Date date) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat sdate = new SimpleDateFormat("yyyy-MM-dd");
		return sdate.format(date);
	}
	
	
	public static String datetoStr(Date date) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat sdate = new SimpleDateFormat("yyyyMMdd");
		return sdate.format(date);
	}
	
	public static String dateToStr2(Date date) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat sdate = new SimpleDateFormat("MM-dd");
		return sdate.format(date);
	}
	
	public static String datetoStr3(Date date) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat sdate = new SimpleDateFormat("yyyy/MM/dd");
		return sdate.format(date);
	}	

	public static String toChar(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(date);
	}


	public static boolean isLeapYear(int year) {
		return year >= gregorianCutoverYear ? ((year % 4 == 0) && ((year % 100 != 0) || (year % 400 == 0))) : // Gregorian
				(year % 4 == 0); // Julian
	}

	public static int getYear(String date) {
		String yaer = (String) date.subSequence(0, 4);

		return new Integer(yaer).intValue();

	}

	public static int getMonth(String date) {
		String month = (String) date.subSequence(5, 7);

		return new Integer(month).intValue();

	}


	public static int getData(String date) {

		String data = (String) date.subSequence(8, 10);

		return new Integer(data).intValue();

	}

	public static int getAge(String date) {

		String nowDate = dateToStr(new Date());
		int nowData = getData(nowDate);
		int nowMonth = getMonth(nowDate);
		int nowYear = getYear(nowDate);

		int brthData = getData(date);
		int brthMonth = getMonth(date);
		int brthYear = getYear(date);
		int age = 0;

		if ((nowData - brthData) < 0) {
			nowMonth = nowMonth - 1;
			if (nowMonth - brthMonth < 0) {
				age = nowYear - brthYear - 1;
			} else {
				age = nowYear - brthYear;
			}
		} else if (nowMonth - brthMonth < 0) {
			age = nowYear - brthYear - 1;
		} else {
			age = nowYear - brthYear;
		}
		return age;
	}
	
	public static String getAge(Date birthDay) throws Exception {
		if(null == birthDay)
			return "-";
        Calendar cal = Calendar.getInstance();
        if (cal.before(birthDay)) {
            throw new IllegalArgumentException(
                "The birthDay is before Now.It's unbelievable!");
        }

        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthDay);

        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                // monthNow==monthBirth
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;
                } else {
                    // do nothing
                }
            } else {
                // monthNow>monthBirth
                age--;
            }
        } else {
            // monthNow
            // donothing
        }
        
        if(age == 0)
        	return "-";

        return String.valueOf(age);
    }

	
	
	public static String getAge1(Date birthDay) throws Exception {
		if(null == birthDay)
			return "-";
        Calendar cal = Calendar.getInstance();
        if (cal.before(birthDay)) {
            throw new IllegalArgumentException(
                "The birthDay is before Now.It's unbelievable!");
        }

        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthDay);

        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                // monthNow==monthBirth
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;
                } else {
                    // do nothing
                }
            } else {
                // monthNow>monthBirth
                age--;
            }
        } else {
            // monthNow
            // donothing
        }
      if(age == 0)
        	return "0";

        return String.valueOf(age);
    }

	
	public static Date addDay(String str, int dayNum) {
		Date date = null;
		SimpleDateFormat sdate = new SimpleDateFormat("yyyy-MM-dd");
		GregorianCalendar gc = new GregorianCalendar();
		try {
			date = (Date) sdate.parse(str);
			gc.setTime(date);
			gc.add(GregorianCalendar.DATE, dayNum);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return gc.getTime();
	}
	
	public static Date addDay1(String str, int dayNum) {
		Date date = null;
		SimpleDateFormat sdate = new SimpleDateFormat("yyyyMMdd");
		GregorianCalendar gc = new GregorianCalendar();
		try {
			date = (Date) sdate.parse(str);
			gc.setTime(date);
			gc.add(GregorianCalendar.DATE, dayNum);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return gc.getTime();
	}

	public static Date addDay(Date date, int dayNum) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		gc.add(GregorianCalendar.DATE, dayNum);
		return gc.getTime();
	}
	
	public static Date trim(Date date) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		gc.set(Calendar.HOUR, 0);
		gc.set(Calendar.SECOND, 0);
		gc.set(Calendar.MINUTE, 0);
		gc.set(Calendar.MILLISECOND, 0);
		return gc.getTime();
	}
	
	public static Date getDayTime(Date date,int hour,int minute,int second) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		gc.set(Calendar.HOUR_OF_DAY, hour);
		gc.set(Calendar.MINUTE, minute);
		gc.set(Calendar.SECOND, second);
		return gc.getTime();
	}
	
	
	/**
	 * 根据年龄转化为数据类型
	 * 
	 * @param age
	 * @return
	 */
	public static Date getAge(int age){
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH)+1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int newYear = year - age;
		String newDate = String.valueOf(newYear)+"-"+String.valueOf(month)+"-"+String.valueOf(day);
		return strToDate(newDate);
	}
	
	public static String dateToChineseStr(Date date) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat sdate = new SimpleDateFormat("yyyy年MM月dd日");
		return sdate.format(date);
	}

	
	/**
	 * 计算天数
	 * 
	 * @param date
	 * @return
	 */
	public static String getDay(Date date){
		// 用当前时间减去创建时间
		long secondesMills =System.currentTimeMillis()-date.getTime();
		long day = secondesMills/86400000;
		String strDay = "";
		if(0<=day && day<=7){
			Calendar calendar = Calendar.getInstance();
			int day1 = calendar.get(calendar.DAY_OF_YEAR);
			calendar.setTime(date);
			int day2 = calendar.get(calendar.DAY_OF_YEAR);
			if(day1-day2 == 0){
				strDay="当天";				
			}else if(day1-day2<0){
				strDay=day+"天前";
			}else{
				int day3 = day1- day2;
				strDay=day3+"天前";
			}
		}
		
		if(day >= 7 && day <31)
			strDay=day/7+"周前";
		if(day >= 31 && day < 365)
			strDay=day/31+"月前";
		if(day >=365)
			strDay=day/365+"年前";
		return strDay;
	}
	
	public static long getDay1(Date date){
		long secondesMills =date.getTime()-System.currentTimeMillis();
		long day = secondesMills/86400000;
		return day;
	}
	
	/**
	 * 取出当前时间加一秒
	 * 
	 * @param date
	 * @return
	 */
	public static Date addSeconds(Date date){
		long mills = date.getTime();
		mills +=1000;
		return new Date(mills);
	}

	/**
	 * 取出当前时间加n秒
	 * 
	 * @param date
	 * @return
	 */
	public static Date addSeconds2(Date date,long addSeconds){
		long mills = date.getTime();
		mills +=addSeconds;
		return new Date(mills);
	}

	
	public static Date strToDate3(String str) {
		Date date = null;
		try {
			// date = java.sql.Date.valueOf(str);
			SimpleDateFormat sdate = new SimpleDateFormat("yyyyMMddHHmmss");
			date = (Date) sdate.parse(str);
		} catch (Exception e) {
			e.getStackTrace();
		}
		return date;
	}
	
	/**
	 * 判断星座
	 * 
	 * @param date
	 * @return
	 */
	public static String showConstellation(Date date){
		
		Calendar calendar = Calendar.getInstance();
		
		calendar.setTime(date);
		
		int year = calendar.get(Calendar.YEAR);
		
		// 白羊座
		if(date.compareTo(strToDate(year+"-03-21"))>=0 && (date.compareTo(strToDate(year+"-04-19"))<=0)){
			return "1";
		}
		// 金牛座
		if(date.compareTo(strToDate(year+"-04-20"))>=0 && (date.compareTo(strToDate(year+"-05-20"))<=0)){
			return "2";
		}
		// 双子座
		if(date.compareTo(strToDate(year+"-05-21"))>=0 && (date.compareTo(strToDate(year+"-06-21"))<=0)){
			return "3";
		}
		// 巨蟹座
		if(date.compareTo(strToDate(year+"-06-22"))>=0 && (date.compareTo(strToDate(year+"-07-22"))<=0)){
			return "4";
		}
		// 狮子座
		if(date.compareTo(strToDate(year+"-07-23"))>=0 && (date.compareTo(strToDate(year+"-08-22"))<=0)){
			return "5";
		}
		// 处女座
		if(date.compareTo(strToDate(year+"-08-23"))>=0 && (date.compareTo(strToDate(year+"-09-22"))<=0)){
			return "6";
		}
		// 天枰座
		if(date.compareTo(strToDate(year+"-09-23"))>=0 && (date.compareTo(strToDate(year+"-10-23"))<=0)){
			return "7";
		}
		// 天蝎座
		if(date.compareTo(strToDate(year+"-10-24"))>=0 && (date.compareTo(strToDate(year+"-11-22"))<=0)){
			return "8";
		}
		// 射手座
		if(date.compareTo(strToDate(year+"-11-23"))>=0 && (date.compareTo(strToDate(year+"-12-21"))<=0)){
			return "9";
		}
		// 魔蝎座
		if(date.compareTo(strToDate(year+1+"-01-19"))<=0){
			return "10";
		}
		// 水瓶座
		if(date.compareTo(strToDate(year+"-01-20"))>=0 && (date.compareTo(strToDate(year+"-02-18"))<=0)){
			return "11";
		}
		// 双鱼座
		if(date.compareTo(strToDate(year+"-02-19"))>=0 && (date.compareTo(strToDate(year+"-03-20"))<=0)){
			return "12";
		}
		return "";
	}
	
	/**
	 * 显示剩余时间
	 * 
	 * @param date
	 *            时间
	 * @return
	 */
	public static String showMinutes(Date date){
		long mills = System.currentTimeMillis();		
		long oldMills = date.getTime();
		long minutes = (mills-oldMills)/60000;
		
		if(minutes < 1){
			return "1分钟前";
		}
		
		if(minutes < 60){
			return String.valueOf(minutes)+"分钟前";
		}
		
		long hours = minutes/60;
		if(hours>=24){
			long days= hours/24;
			if(days<1){
				days=1;
			}
			return String.valueOf(days)+"天前";
		}else{
			return String.valueOf(hours)+"小时前";
		}
		
		
	}
	
	public static String compareHours(Date date, Date cdates) {
		long secondesMills =date.getTime()-cdates.getTime();
		long hours = secondesMills/3600000;
		long day=hours/24;
		Long minutes=secondesMills/60000;
		if(hours>=1 && hours<24){
			return String.valueOf(hours)+"小时前";
		}if(hours>=24){
			return String.valueOf(day)+"天前";
		}if(minutes<60&&minutes>1){
			return String.valueOf(minutes)+"分钟前";
		}
	     return "1 分钟前";
	  
	}
	
	
	//通用,GregorianCalendarNum表示GregorianCalendar.DATE或者GregorianCalendar.YEAR,num表示前后,-1表示前一天或者前一年
	public static Date getDateByNum(int GregorianCalendarNum,int num)
	{
		GregorianCalendar g=new GregorianCalendar();
		g.setTime(new Date());
		g.add(GregorianCalendarNum, num );
		Date date1=g.getTime();
		return date1;
	}
	
	//获得前一天时间
	public static Date getDateBeforeDay()
	{
		//获得前一天的日期对象
		GregorianCalendar g=new GregorianCalendar();
		g.setTime(new Date());
		g.add(GregorianCalendar.DATE, - 1 );
		Date date1=g.getTime();
		date1.setHours(0);
		date1.setMinutes(0);
		date1.setSeconds(0);
		return date1;
	}
	//获得后一年时间
	public static Date getDateAfterYear()
	{
		GregorianCalendar g=new GregorianCalendar();
		g.setTime(new Date());
		g.add(GregorianCalendar.YEAR, 1);
		Date date1=g.getTime();
		//date1.setHours(0);
		//date1.setMinutes(0);
		//date1.setSeconds(0);
		return date1;
	}
	
	public static Date addDate(String str,int field, int dayNum) {
		Date date = null;
		SimpleDateFormat sdate = new SimpleDateFormat("yyyy-MM-dd");
		GregorianCalendar gc = new GregorianCalendar();
		try {
			date = (Date) sdate.parse(str);
			gc.setTime(date);
			gc.add(field, dayNum);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return gc.getTime();
	}
	
	//时间戳转化为格式化
	public static String timeStamp2DateStr(String timestampString,String format)
	{
		if(StringUtils.isBlank(timestampString))return "";
		 Long timestamp = Long.parseLong(timestampString)*1000;   
		  String date = new java.text.SimpleDateFormat(format).format(new java.util.Date(timestamp));   
		  return date;
	}
	
	//获得年月
	public static String getYearAndMonth(Date date)
	{
		String ym="";
		if(date!=null){
			ym=new SimpleDateFormat("yyyyMM").format(date);
		}
		return ym;
	}
	
	
	public static void main(String[] args) {
	//	System.out.println(timeStamp2DateStr("1257131200","yyyy-MM-dd hh:mm:ss"));
		System.out.println(getYearAndMonth(new Date()));
	}
}