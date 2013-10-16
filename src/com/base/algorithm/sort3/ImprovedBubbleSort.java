package com.base.algorithm.sort3;

public class ImprovedBubbleSort {
	/**
	 * 改进后的冒泡排序算法的实现：
	 * @param list 欲排序的数组
	 * 内层循环判断出有序后，便不再进行循环，在一定程度提高了效率
	 */
	public static void improvedBubbleSort(int[] list) {
		long start=System.currentTimeMillis();
		boolean needNextPass = true;
		for (int k = 1; k < list.length && needNextPass; k++) {
			needNextPass = false;
			for (int i = 0; i < list.length - k; i++) {
				if (list[i] > list[i + 1]) {
					int temp = list[i];
					list[i] = list[i + 1];
					list[i + 1] = temp;
					needNextPass = true;
				}
			}
		}
		long end=System.currentTimeMillis();
		System.out.println("耗时--\t"+(end-start));
	}
	
	public static void main(String[] args) {
		/* Ini. array */
		int[] array = new int[100];
		for (int k = 0; k < array.length; k++) {
			array[k] = (int) (Math.random() * 1000);
		}
		
		System.out.println("Before Sorting:");
		for (int k = 0; k < array.length; k++) {
			System.out.print(array[k] + "  ");
		}
		
		System.out.println("\n\nAfter Sorting:");
		improvedBubbleSort(array);
		
		for (int k = 0; k < array.length; k++) {
			System.out.print(array[k] + "  ");
		}
	}
}
