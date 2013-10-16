package com.base.utils;

public class MathRandom {

	/**
	 * 0出现的概率为%50 谢谢慧顾
	 */
	public static double rate0 = 0.50;

	/**
	 * 1出现的概率为%30 中10个积分
	 */
	public static double rate1 = 0.30;

	/**
	 * 2出现的概率为%15 中20个积分
	 */
	public static double rate2 = 0.15;

	/**
	 * 3出现的概率为%5 中50个积分
	 */
	public static double rate3 = 0.5;

	/**
	 * Math.random()产生一个double型的随机数，判断一下 例如0出现的概率为%50，则介于0到0.50中间的返回0
	 * 
	 * @return int
	 * 
	 */
	public static int PercentageRandom() {
		double randomNumber;
		randomNumber = Math.random();
		System.out.println("randomNumber="+randomNumber);
		if (randomNumber >= 0 && randomNumber <= rate0) {
			return 0;
		} else if (randomNumber >= rate0 / 100 && randomNumber <= rate0 + rate1) {
			return 1;
		} else if (randomNumber >= rate0 + rate1
				&& randomNumber <= rate0 + rate1 + rate2) {
			return 2;
		} else if (randomNumber >= rate0 + rate1 + rate2
				&& randomNumber <= rate0 + rate1 + rate2 + rate3) {
			return 3;
		} 
		return -1;
	}
	
	public static String getRanmod(){
		double randomNumber;
		randomNumber = Math.random();
		//System.out.println("randomNumber="+String.valueOf(randomNumber).indexOf("0"));
	String aa=	 (String) String.valueOf(randomNumber).substring(String.valueOf(randomNumber).indexOf(".")+1, String.valueOf(randomNumber).indexOf(".")+4); 
	//System.out.println("aa="+aa);
	return aa;
	}  
	
	
	
	
	
	
	
	public static void main(String[] args) {
		System.out.println("cc:"+getRanmod());
	}
}
