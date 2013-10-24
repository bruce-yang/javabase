package com.base.introspector;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

import org.junit.Test;

/**
 * 
 * @功能：内省
 */
public class IntropectorTest {

    @Test
    public void test1() throws Exception{
        //得到Bean的所有属性
        BeanInfo allBeanInfo = Introspector.getBeanInfo(Person.class);
        //得到Person的属性，但不包括其父类Object的属性(即：开始于Person类，止于Object类)
        BeanInfo beanInfo = Introspector.getBeanInfo(Person.class, Object.class);
        
        PropertyDescriptor[] pds1 = allBeanInfo.getPropertyDescriptors();
        PropertyDescriptor[] pds2 = beanInfo.getPropertyDescriptors();
        
        for (PropertyDescriptor pd1 : pds1) {
            System.out.println(pd1.getName());
            //结果：age，class， name， password
        }
        System.out.println("==================");
        for (PropertyDescriptor pd2 : pds2) {
            System.out.println(pd2.getName());
            //结果：age， name， password
        }
    }
    
    @Test
    public void test2() throws Exception{
        Person p = new Person();
        //得到name的属性，第一个参数为想要得到的属性的名称，第三个为javaBean的类
        PropertyDescriptor pd = new PropertyDescriptor("name", Person.class);
        //得到写的属性
        Method writeMethod = pd.getWriteMethod();
        //对属性进行赋值，第一个参数传一个对象进去
        writeMethod.invoke(p, "张三");
        //获取读的属性
        Method readMethod = pd.getReadMethod();
        //获取属性值并打印（结果：张三）
        System.out.println(readMethod.invoke(p, null));
    }
    
    @Test
    public void test3() throws Exception{
        PropertyDescriptor pd = new PropertyDescriptor("age", Person.class);
        //获取属性并打印（int）
        System.out.println(pd.getPropertyType());
    }
}