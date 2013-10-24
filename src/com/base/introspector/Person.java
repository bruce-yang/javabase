package com.base.introspector;
/**
 * 
 * @功能：javaBean
 * 
 * 关于javaBean的属性，每一个类都会继承Object类，Object类中有一个getClass()属性，
 * 所以，每个类里的属性里都会有这么一个属性
 * 如下：Person类里一共是四个属性(getName()或setName(),getAge()或setAge(),getPassword())
 *         只有get或set方法算是属性，如果没有get或set方法，只有一个字段，并不算是属性，
 *         且同一个字段的get set只能算一个属性
 */
public class Person {

    private String name;
    private int age;
    
    public String getPassword(){
        return null;
    }
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    
}