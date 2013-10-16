package com.base.thread;

public class ThreadDemo2 extends Thread {
	private int countDown = 10;

	@Override
	// 在run方法中定义任务
	public void run() {
		while (countDown-- > 0) {
			System.out.println("#" + this.getName() + "(" + countDown + ")");
		}
	}

	public static void main(String[] args) {
		new ThreadDemo2().start();
		new ThreadDemo2().start();
		// 由于start方法迅速返回，所以main线程可以执行其他的操作,此时有两个独立的线程在并发运行
		System.out.println("火箭发射前倒计时：");
	}
}
