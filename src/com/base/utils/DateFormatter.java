
package com.base.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Title: Description: Copyright: Copyright (c) 2001 Company:
 *
 * @author not attributable
 * @version 1.0
 */
public class DateFormatter {
    private static SimpleDateFormat formatter;

    public DateFormatter() {
    }

    public static String shortDate(Date aDate) {
        formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(aDate);
    }
    public static int getAge(Date birthDay){
        Calendar cal = Calendar.getInstance();
         if(null==birthDay){
        	   return 0;
           }
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
                //monthNow==monthBirth
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;
                } else {
                    //do nothing
                }
            } else {
                //monthNow>monthBirth
                age--;
            }
        } else {
            //monthNow<monthBirth
            //donothing
        }

        return age;
    }
    public static String shortmailDate(Date aDate) {
        formatter = new SimpleDateFormat("yyyyMMdd");
        return formatter.format(aDate);
    }
    public static String formatDate(Date aDate) {
    	formatter = new SimpleDateFormat("yyyy-MM-dd");
    	return formatter.format(aDate);
    }
    
    
    public static String mailDate(Date aDate) {
        formatter = new SimpleDateFormat("yyyyMMddHHmm");
        return formatter.format(aDate);
    }

    public static String longDate(Date aDate) {
        formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(aDate);
    }

    public static String shortDateGB(Date aDate) {
        formatter = new SimpleDateFormat("yyyy'��'MM'��'dd'��'");
        return formatter.format(aDate);
    }

    public static String longDateGB(Date aDate) {
        formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(aDate);
    }

    public static String formatDate(Date aDate, String formatStr) {
        if(aDate==null){
            return null;
        }
        formatter = new SimpleDateFormat(formatStr);
        return formatter.format(aDate);

    }

    public static String LDAPDate(Date aDate) {
        return formatDate(aDate, "yyyyMMddHHmm'Z'");
    }

    public static Date getDate(String yyyymmdd) {
        if ((null == yyyymmdd) || (yyyymmdd.trim().length() == 0))
            return null;
        int year = Integer.parseInt(yyyymmdd.substring(0, yyyymmdd.indexOf("-")));
        int month = Integer.parseInt(yyyymmdd.substring(yyyymmdd.indexOf("-") + 1, yyyymmdd.lastIndexOf("-")));
        int day = Integer.parseInt(yyyymmdd.substring(yyyymmdd.lastIndexOf("-") + 1));
        Calendar cal = Calendar.getInstance();
       cal.set(year, month - 1, day);
        return cal.getTime();


    }
     public static Date getDateFull(String yyyymmdd) {
        if ((null == yyyymmdd) || (yyyymmdd.trim().length() == 0))
            return null;
        int year = Integer.parseInt(yyyymmdd.substring(0, yyyymmdd.indexOf("-")));
        int month = Integer.parseInt(yyyymmdd.substring(yyyymmdd.indexOf("-") + 1, yyyymmdd.lastIndexOf("-")));
        int day = Integer.parseInt(yyyymmdd.substring(yyyymmdd.lastIndexOf("-") + 1));
        Calendar cal = Calendar.getInstance();
        cal.set(year,month-1,day,24,60,60);
        return cal.getTime();


    }

    public static Date parser(String strDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return sdf.parse(strDate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static Date parserShortDate(String strDate){
    	return parser(strDate,"yyyy-MM-dd");
    }
    public static Date parser(String strDate, String formatter) {
    	if("未填".equals(strDate)||null == strDate ||"null"==strDate|| (strDate.trim().length()==0)){
    		return null;
    	}
        SimpleDateFormat sdf = new SimpleDateFormat(formatter);
        try {
            return sdf.parse(strDate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    /**yyyy-MM-dd'T'HH:mm:ss.SSSZ*/
    public static String parserToStandardDateTime(String source) {
    	if(null == source ||"null"==source|| (source.trim().length()==0)){
    		return null;
    	}
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	try {
    		int index=source.indexOf("T");
    		String pre=source.substring(0,index);
    		String next=source.substring(index+1,source.length());
    		String dateTime=pre+" "+next;
    		return sdf.format(	sdf.parse(dateTime));
    	} catch (Exception e) {
    		e.printStackTrace();
    		return null;
    	}
    }
    /**
     * get Date with only date num. baoxy add
     *
     * @param yyyymmdd String
     * @return java.util.Date
     */
    public static Date getDateOnly(String yyyymmdd) {
        if ((null == yyyymmdd) || (yyyymmdd.trim().length() == 0))
            return null;
        int year = Integer.parseInt(yyyymmdd.substring(0, yyyymmdd.indexOf("-")));
        int month = Integer.parseInt(yyyymmdd.substring(yyyymmdd.indexOf("-") + 1, yyyymmdd.lastIndexOf("-")));
        int day = Integer.parseInt(yyyymmdd.substring(yyyymmdd.lastIndexOf("-") + 1));
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, day, 0, 0, 0);
        return cal.getTime();
    }


    public static Date addMonth(Date myDate, int amount) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(myDate);
        boolean isEndDayOfMonth_old = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH) == cal.get(GregorianCalendar.DAY_OF_MONTH);
        cal.add(GregorianCalendar.MONTH, amount);
        boolean isEndDayOfMonth_new = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH) == cal.get(GregorianCalendar.DAY_OF_MONTH);
        if (isEndDayOfMonth_old && !isEndDayOfMonth_new) {
            cal.set(GregorianCalendar.DATE, cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH));
        }
        return cal.getTime();
    }

    public static Date addDay(Date myDate, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(myDate);
        cal.add(Calendar.DAY_OF_MONTH, amount);
        return cal.getTime();
    }
    //-------------------�������-----------------------------------
    public static Date addMinute(Date myDate, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(myDate);
        int minute=0;
        amount=-(amount);

        if(amount>60 ){

                int hour=(int)amount / 60;

                if(hour*60>amount){
                        minute=hour*60-amount;

                        cal.add(Calendar.HOUR_OF_DAY,-hour);
                        cal.add(Calendar.MINUTE,minute);

                }else if(hour*60<amount){

                        minute=amount-hour*60;
                        cal.add(Calendar.HOUR_OF_DAY,-hour);
                        cal.add(Calendar.MINUTE,-minute);

                }else{
                        cal.add(Calendar.HOUR_OF_DAY,-hour);
                }

        }else{

            cal.add(Calendar.MINUTE, -amount);
        }
        return cal.getTime();
    }

    public static Date addYear(Date myDate, int amount) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(myDate);
        boolean isEndDayOfMonth_old = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH) == cal.get(GregorianCalendar.DAY_OF_MONTH);
        cal.add(GregorianCalendar.YEAR, amount);
        boolean isEndDayOfMonth_new = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH) == cal.get(GregorianCalendar.DAY_OF_MONTH);
        if (isEndDayOfMonth_old && !isEndDayOfMonth_new) {
            cal.set(GregorianCalendar.DATE, cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH));
        }
        return cal.getTime();
    }

    /*
    the mapping from jdk is :
    1-Sun; 2-Mon;3-Tues; 4-Weds; 5-Thur;6-Fri; 7-Sat;
    */
    public static int getWeekDay(Date myDate) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(myDate);
//        int dayForWeek = 0;
//        if(cal.get(Calendar.DAY_OF_WEEK) == 1){   
//        	  dayForWeek = 7;   
//        	 }else{   
//        	  dayForWeek = cal.get(Calendar.DAY_OF_WEEK) - 1;   
//        	 }   

        return cal.get(GregorianCalendar.DAY_OF_WEEK);
    }

    /*
    the mapping from vas2005 is :
    1-Mon;2-Tues; 3-Weds; 4-Thur;5-Fri; 6-Sat;7-Sun;
   */
    public static int getConvertWeekDay(Date myDate) {
        int day = getWeekDay(myDate);
        int result = day - 1;
        if (result == 0) result = 7;
        return result;
    }

    public static int getTimeFromDate(Date myDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("hhmmss");
        int result = Integer.parseInt(sdf.format(myDate));
        return result;
    }

    /**
     * ȡ��}��ʱ��֮����������
     *
     * @param startDate Date
     * @param endDate Date
     * @return long
     */
    public static long getDaysBetweenDate(Date startDate, Date endDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        startDate = cal.getTime();
        cal.setTime(endDate);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return (cal.getTime().getTime() - startDate.getTime()) / 86400000;

    }
  /**
   * 时间戳(Timestamp)转本地时间yyyy-MM-dd HH:mm:ss
   */
    public static String convertToDate(Long millis){
          SimpleDateFormat formate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          return formate.format(millis);
    }
    /**
     * 
     * @param date  yyyy-MM-dd HH:mm:ss
     * @return 格式类型 2010-11-23 周二
     */
    public static String convertToSpecialDate(String strDate){
    	Date d=parser(strDate);
    	int date=getWeekDay(d);
    	String strD="";
    	 switch (date) {
		case 0:
			strD="周一";
			break;
		case 1:
			strD="周日";
			break;
		case 2:
			strD="周一";
			break;
		case 3:
			strD="周二";
			break;
		case 4:
			strD="周三";
			break;
		case 5:
			strD="周五";
			break;
		case 6:
			strD="周六";
			break;
		default:
			break;
		}
		return formatDate(d)+" "+strD;
	}
    public static void main(String[] args) {
    	System.out.println(convertToSpecialDate("2010-11-23 00:00:00"));
	}
}
