package com.base.crawl;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.LoggerFactory;

public class CrawlMade {
// log4j
   protected static Logger log=Logger.getLogger(CrawlMade.class);
   //logback
   private static org.slf4j.Logger logback=LoggerFactory.getLogger(CrawlMade.class);
   public static void main(String[] args) {
	  String url="http://www.made.com/sofas-and-armchairs";
	  Connection con=Jsoup.connect(url);
	  try {
		 
		Document doc=con.get();
		Elements pngs =doc.select("div.category-products").get(0).select("img[src$=.jpg]");
		for (Element element : pngs ) {
			logback.info(element.attr("title"));
			System.out.println(element.attr("src"));
		
		}
		
	} catch (IOException e) {
		e.printStackTrace();
	}
   }
}
