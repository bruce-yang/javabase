package com.base.taboo;

import java.util.List;

import org.apache.log4j.Logger;

import com.base.taboo.dfa.Found;



public class Usage {
	private static Logger log=Logger.getLogger(Usage.class);
	public static void main(String[] args) {
		String source = " 共产党，向前冲";
		boolean rtn = Taboo.validate(source);
		log.info( rtn);
		List<Found> rst = Taboo.find(source);
		for (Found found : rst) {
			//log.info(found.getValue());
			source=source.replaceAll(found.getValue(), "***");
		}
		log.info(source);
	}
}
