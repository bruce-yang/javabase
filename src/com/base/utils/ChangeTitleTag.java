/*
 * Created on 2005-12-29
 * Copyright 1998��2005 Incesoft Inc. All rights reserved. 
 */
 
package com.base.utils;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * @author Andy.zuo
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ChangeTitleTag extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected int num;
	protected String title;
	protected String subjoin;
	
	private void init() {
        num = 0;
        title = "";
        subjoin = "";
    }
	
	public ChangeTitleTag() throws Exception {
        super();
        init();
    }

    public void release() {
        init();
    }    
    
    public int doStartTag() throws JspException {
        try {
        	String title_subjoin=title+subjoin;
            String title_temp="";
            int num_temp=0;//总字符长度
            int num_subjoin=0;//附加长度
            int num_need=0;//所要截取的长度
            int num_title=0;//title 长度
            
            for(int i=0;i<title_subjoin.length();i++){
        		if(isChinese(title_subjoin.charAt(i))) {
        			num_temp+=2;
        		}else{
        			num_temp++;
        		}        		
        	}
            for(int i=0;i<subjoin.length();i++){
        		if(isChinese(subjoin.charAt(i))) {
        			num_subjoin+=2;
        		}else{
        			num_subjoin++;
        		}        		
        	}
            
            num_need = num - num_subjoin;
            num_title = num_temp - num_subjoin;
            
          //判断字符长度是否大于指定长度
            if(num_need < num_title){//大于,那么截取指定长度
            	int nt=0;
            	for(int i=0;i<title.length();i++){
            		if(nt>=num_need){
            			break;
            		}else{
            			title_temp+=title.charAt(i);
            		}
            		
            		if(isChinese(title.charAt(i))) {
            			nt+=2;
            		}else{
            			nt++;
            		}            		
            	}
            	title_temp+="..";
            }else{
            	title_temp=title;
            }
            
            pageContext.getOut().write(title_temp);
            return SKIP_BODY;
        } catch (IOException ex) {
            throw new JspException(ex.getMessage(), ex);
        }
     }
    
    public static boolean isChinese(char a) 
	{ 
		int v=(int)a; 
		//return (v>=19968 && v<=171941);
		return (v>=128);//>128 全角
	} 

	/**
	 * @return Returns the num.
	 */
	public int getNum() {
		return num;
	}

	/**
	 * @param num The num to set.
	 */
	public void setNum(int num) {
		this.num = num;
	}

	/**
	 * @return Returns the title.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title The title to set.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return Returns the subjoin.
	 */
	public String getSubjoin() {
		return subjoin;
	}

	/**
	 * @param subjoin The subjoin to set.
	 */
	public void setSubjoin(String subjoin) {
		this.subjoin = subjoin;
	}
	
}
