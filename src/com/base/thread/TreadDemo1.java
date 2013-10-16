package com.base.thread;

public class TreadDemo1 implements Runnable {
	private int countDown = 10;
	@Override
	// 在run方法中定义任务
	public void run() {
		while (countDown-- > 0) {
			System.out.println("#" + Thread.currentThread().getName() + "("
					+ countDown + ")");
		}
	}

	public static void main(String[] args) {
		// Runnable中run方法是一个空方法，并不会产生任何线程行为，必须显式地将一个任务附着到线程上
		TreadDemo1 tt=new TreadDemo1();
		new Thread(tt).start();
		new Thread(tt).start();
		System.out.println("火箭发射前倒计时：");
	}
}
