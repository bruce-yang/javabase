package com.base.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class Daily {
	
	public static Map getDate(int i){
		
		Calendar ca1 = Calendar.getInstance();
		
		Date date1=new Date();
		
		date1=DateFormatter.addMonth(date1, i);
		
		ca1.setTime(date1);
		
		int week=ca1.get(Calendar.DAY_OF_WEEK);
		
		week=week-1;
		
		int day=ca1.get(Calendar.DAY_OF_MONTH);
		
		int year=ca1.get(Calendar.YEAR);
		
		int month=ca1.get(Calendar.MONTH);
		
		month=month+1;
		
		Map<String, Integer> Mapdaily=new HashMap<String, Integer>(); 
		
		Mapdaily.put("day", Integer.valueOf(day));
		
		Mapdaily.put("year", Integer.valueOf(year));
		
		Mapdaily.put("week", Integer.valueOf(week));
		
		Mapdaily.put("month", Integer.valueOf(month));
		
		return Mapdaily;
	}
	
	public static int dateOfMon(int mo,int year){
		int day=0;
		
		if(mo==1 || mo==3 || mo==5 || mo==7 || mo==8 || mo==10 || mo==12){
			day=31;
		}
		if(mo==4 || mo==6 || mo==9 || mo==11){
			day=30;
		}
		if(mo==2){
			if(year%4==0 && year%100!=0){
					day=29;
			}else{
				day=28;
			}
		}
		return day;
	}
	/*
	 * 取得当月的第一天
	 * */
	public static Date getFirstMonth(){
		
		Calendar ca1= Calendar.getInstance();
		
		ca1.setTime(new Date());
		
		int   minDate   =   ca1.getActualMinimum(Calendar.DATE);
		 
		ca1.set(Calendar.DATE,minDate);
		
		int year=ca1.get(Calendar.YEAR);
		
		int month=ca1.get(Calendar.MONTH);
		
		ca1.set(year, month, minDate, 0, 0, 0);
		
		Date da=ca1.getTime();
		
		 return da;
	}
	/*
	 * 取得变动月份第一天
	 * */
	public static Date getFirstMonth(int i){
		
		Calendar ca1= Calendar.getInstance();
		
		Date date1=new Date();
		
		date1=DateFormatter.addMonth(date1, i);
		
		ca1.setTime(date1);
		
		int   minDate   =   ca1.getActualMinimum(Calendar.DATE);
		 
		ca1.set(Calendar.DATE,minDate);
		
		int year=ca1.get(Calendar.YEAR);
		
		int month=ca1.get(Calendar.MONTH);
		
		ca1.set(year, month, minDate, 0, 0, 0);
			
		Date da=ca1.getTime();
		
		 return da;
	}
	/*
	 * 
	 * 取得当月的最后一天
	 * */
	public static Date getEndMonth(){
		
		Calendar ca1= Calendar.getInstance();
		
		ca1.setTime(new Date());
		
		int   maxDate   =   ca1.getActualMaximum(Calendar.DATE);
		
		ca1.set(Calendar.DATE,maxDate);
		
		int year=ca1.get(Calendar.YEAR);
		
		int month=ca1.get(Calendar.MONTH);
		
		ca1.set(year, month, maxDate, 23, 59, 59);
		
		Date da=ca1.getTime();
		 
		return da;
	}
	/*
	 * 取得变动月的最后一天
	 * */
	public static Date getEndMonth(int i){
		
		Calendar ca1= Calendar.getInstance();
		
		Date date1=new Date();
		
		date1=DateFormatter.addMonth(date1, i);
		
		ca1.setTime(date1);
		
		int   maxDate   =   ca1.getActualMaximum(Calendar.DATE);
		
		ca1.set(Calendar.DATE,maxDate);
		
		int year=ca1.get(Calendar.YEAR);
		
		int month=ca1.get(Calendar.MONTH);
		
		ca1.set(year, month, maxDate, 23, 59, 59);
		
		Date da=ca1.getTime();
		 
		return da;
		
	}
	/*
	 * 本月１号星期几
	 * */
	public static int getFristWeek(){
		
		Calendar ca1= Calendar.getInstance();
		
		Date da1=getFirstMonth();
		
		ca1.setTime(da1);
		
		int week=ca1.get(Calendar.DAY_OF_WEEK);
		
		return week-1;
	}
	
	/*
	 *变动月份的１号星期几 
	 * */
	public static int getFristWeek(int i){
		
		Calendar ca1= Calendar.getInstance();
		
		Date da1=getFirstMonth(i);
		
		ca1.setTime(da1);
		
		int week=ca1.get(Calendar.DAY_OF_WEEK);
		
		return week-1;
	}
	
	public static int getMothDay(Date date){
		
		Calendar ca1 = Calendar.getInstance();
		
		ca1.setTime(date);
		
		int day=ca1.get(Calendar.DAY_OF_MONTH);
		
		return day;
	}
	
	public static void main(String[] args){
		
		//Date da=getEndMonth(1);
		
		//System.out.println("getEndMonth()"+dateOfMon(2,2008));
	}
	

}
