package com.base.inherit;
public class Son extends Father {
 public static void statMethod(){
	 System.out.println("子类静态方法");
  }
  public void nonstatMethod() {
	  System.out.println("子类实例方法");
  }
  
  public static void main(String[] args) {
	  Father father = new Son();
	  father.statMethod();
	  father.nonstatMethod();
	  
	  new Son().statMethod();
	  new Son().nonstatMethod();
  }
}