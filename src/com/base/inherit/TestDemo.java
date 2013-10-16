package com.base.inherit;

//存在继承关系时，静态方法是隐藏，实例方法是覆盖，实例方法不能覆盖父类的静态方法
//子类实例方法不能覆盖父类的静态方法；子类的静态方法也不能覆盖父类的实例方法(编译时报错)，总结为方法不能交叉覆盖
//方法隐藏只有一种形式，就是父类和子类存在相同的静态方
//属性只能被隐藏，不能被覆盖
//子类实例变量/静态变量可以隐藏父类的实例/静态变量，总结为变量可以交叉隐藏
//因为静态方法不能被覆盖，如果在子类出现了同签名的就是隐藏，非静态方法称之为覆盖
public class TestDemo {
	public static void main(String[] args) {
		new BaseSon();
	}
}

class Base {
	private static int ty = 20;

	public Base() {
		display();
	}

	public void display() {
		System.out.println(this.ty);
	}
}

class BaseSon extends Base {
	private  int ty = 200;

	public BaseSon() {
		
	}

	public void display() {
		System.out.println(this.ty);
	}
}
