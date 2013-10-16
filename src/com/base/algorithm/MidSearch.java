package com.base.algorithm;

public class MidSearch {
	public static void main(String[] args) {
		double[] a = { 1, 3, 5, 6, 6, 9, 51 };
		//double[] a = { 1.008, 3.11, 5.56, 6.08, 6.08, 9.67, 51.787 };
		int l= a.length;
		System.out.println(binarysearch(a, 6, l));
	    System.out.println(findPostionInArray(6, a));
	}

	private static int findPostionInArray(double score, double[] array) {
		int pos = 0;
		for (int i = 0; i < array.length - 1; i++) {
			if (score >= array[i] && score <= array[i + 1]) {
				pos = i + 1;
				break;
			}
		}

		return pos;
	}
	
	static int binarysearch(double a[], double x, int l) {
		int left, right, pos;
		left = 0;
		right =l - 1;
		while (left <= right) {
			pos = (left + right) / 2;
			if (x>=a[pos]&&x<=a[pos+1])
				return pos+1;
			if (x > a[pos])
				left = pos + 1;
			else
				right = pos - 1;
		}
		return -1;
	}
}
