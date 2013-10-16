package com.base.algorithm;

import java.io.File;
import java.util.Iterator;

public class Snippet {
	public static void deleteFileAll(File dir) {
		if (!dir.delete()) {
			File[] files = dir.listFiles();
			for (File f : files) {
				if (f.isDirectory()) {
					deleteFileAll(f);
				}
				f.delete();
			}
		}
	}

	public static void main(String[] args) {
		isPrime(24);
	}

	// 求质数
	public static void isPrime(int num) {
		for (int i = 2; i <=num; i++) {
			int j;
			int sqr = (int) Math.sqrt(i);
			for (j = 2; j <= sqr; j++){
				if (i % j == 0) {
					break;
				}
			}
			if (j >sqr) {
				System.out.print(i + " ");
			}

		}
		System.out.println();
	}

}
