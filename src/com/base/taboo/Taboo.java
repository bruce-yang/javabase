package com.base.taboo;

import java.util.ArrayList;
import java.util.List;

import com.base.taboo.dfa.Found;
import com.base.taboo.dfa.Processor;
import com.base.taboo.dfa.Tree;
import com.base.taboo.io.util.TextReader;


public class Taboo extends Processor {
	private static List<String> taboos = new ArrayList<String>();

	static {
		TextReader reader = new TextReader("taboos.txt");
		taboos = reader.readLines2List();
		String[] result = new String[taboos.size()];
		taboos.toArray(result);
		for (String taboo : taboos) {
			Tree.parse(root, taboo, true);
		}
	}

	public static boolean validate(String source) {
		List<Found> rst = Processor.execute(source.toCharArray());
		return rst.size() > 0 ? false : true;
	}

	public static List<Found> find(String source) {
		return Processor.execute(source.toCharArray());
	}
}
