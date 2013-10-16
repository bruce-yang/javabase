package com.base.summary;

import net.sf.classifier4J.summariser.SimpleSummariser;

/**
 * 文本自动摘要
 * @author Administrator
 *
 */
public class Summariser {
    public static void main(String[] args) {
    	 String input="Classifier4J is a java package for working with text. Classifier4J includes a summariser. A Summariser allows the summary of text. A Summariser is really cool. I don't think there are any other java summarisers.";
    	 SimpleSummariser summary=new SimpleSummariser();
    	 String result=summary.summarise(input,100);
    	 System.out.println(result);
    }
}
