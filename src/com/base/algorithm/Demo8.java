package com.base.algorithm;

public class Demo8 {
	public static void main(String[] args) {
		int arr[] = { 2, 6, 8, 9, 35, 25, 26, 28, 35, 40 };
		Test t = new Test();
		t.find(0, arr.length - 1, 35, arr);
	}
}

class Test {
	public void find(int leftIndex, int rightIndex, int val, int arr[]) {
		int midIndex = (leftIndex + rightIndex) / 2;
		int midval = arr[midIndex];
		if (midval == val) {
			System.out.println("找到index为：" + midIndex);
		} else if (midval > val) {
			find(leftIndex, midIndex - 1, val, arr);
		} else if (midval < val) {
			find(midIndex + 1, rightIndex, val, arr);
		}
	}
}