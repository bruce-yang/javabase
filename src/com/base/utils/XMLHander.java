package com.base.utils;
 import java.io.FileWriter;
import java.io.IOException;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;


   
 public class XMLHander {  
      public static Document createDocument(){
    	 return DocumentHelper.createDocument();  
      }
       public static synchronized Element createNode(Document document, String nodeName){
    	    return   document.addElement(nodeName);
       }
       public static synchronized Element createAtrribute(Element nodeName,String atrributeName,String atrributeValue){
    	   return nodeName.addAttribute(atrributeName, atrributeValue);
       }
       public static synchronized Element createNodeText(Element nodeName,String nodeText){
    	   return nodeName.addText(nodeText);
       }
     /** 
      * 写入xml文件地址 
      * @param document 所属要写入的内容 
      * @param outFile 文件存放的地址 
      */  
     public static void writeDocument(Document document, String outFile){  
         try{  
             //读取文件  
             FileWriter fileWriter = new FileWriter(outFile);  
             //设置文件编码  
             OutputFormat xmlFormat = new OutputFormat();  
             xmlFormat.setEncoding("UTF-8");  
             //创建写文件方法  
             XMLWriter xmlWriter = new XMLWriter(fileWriter,xmlFormat);  
             //写入文件  
             xmlWriter.write(document);  
             //关闭  
             xmlWriter.close();  
         }catch(IOException e){  
             System.out.println("文件没有找到");  
             e.printStackTrace();  
         }  
     }  
//      @Test
       public  void testMain(){  
    	  String a = "abc";
    	  String b = "abc";
    	  System.out.println(""+ a==b ); 
    		Document document=XMLHander.createDocument();
        	Element  root=XMLHander.createNode(document, "root");
            Element students=root.addElement("students");
        	Element student=students.addElement("student");
        	Element  name=student.addElement("username");
        	name.addText("高然");
        	Element phone=student.addElement("phone");
        	phone.addText("13564857965");
        	Element picture=student.addElement("picture");
        	picture.addAttribute("href", "http://www.woyo.com");
        	picture.addText("点击我的头像");
        	XMLHander.writeDocument(document,"d:/test.xml");  
     }  
 }