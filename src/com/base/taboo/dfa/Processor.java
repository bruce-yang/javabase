package com.base.taboo.dfa;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.base.taboo.io.util.TextReader;





public class Processor {
	protected static Node root = new Node(null);
	private static Set<Monitor> temp = new HashSet<Monitor>();
	private static List<Monitor> monitor = new ArrayList<Monitor>();
	private static List<Found> found = new ArrayList<Found>();
	private static List<Character> ignore = new ArrayList<Character>();

	static {
		TextReader reader = new TextReader("ignore.txt");
		ignore = reader.readLines2Chars();
		ignore.add('\r');
		ignore.add('\n');
	}

	protected static List<Found> execute(char[] chars) {
		return execute(chars, true);
	}

	protected static List<Found> execute(char[] chars, boolean ignoreCase) {
		found.clear();
		Character ch = null;

		for (int i = 0; i < chars.length; i++) {
			ch = convertCase(chars[i], ignoreCase);
			if (ignore.contains(ch)) {
				continue;
			}
			processNodesAlreadyExist(chars, i, ignoreCase);
			processANewOne(ch, i);
		}
		monitor.clear();
		Collections.sort(found);
		return found;
	}

	protected static void print() {
		Collections.sort(found);
		for (Found f : found) {
			System.out.format("Start:%-10dEnd:%-10dValue:%-40sTaboo:%-20s\n", f
					.getStartIndex(), f.getEndIndex(), f.getValue(), f
					.getTaboo());
		}
	}

	private static void processNodesAlreadyExist(char[] chars, int index,
			boolean ignoreCase) {
		temp.clear();
		Character ch = convertCase(chars[index], ignoreCase);
		for (Monitor m : monitor) {
			if (Validator.hasNext(m.node, ch)) {
				Node n = Validator.nextNode(m.node, ch);
				Monitor tmp = new Monitor(n, m.index);
				temp.add(tmp);
				if (tmp.node.over) {
					String value = new String(chars, tmp.index, index
							- tmp.index + 1);
					found.add(new Found(tmp.index, index, value, tmp.node.me
							.toString()));
				}
			}
		}
		monitor.clear();
		monitor.addAll(temp);
	}

	private static void processANewOne(Character c, int startIndex) {
		if (Validator.hasNext(root, c)) {
			Monitor m = new Monitor(Validator.nextNode(root, c), startIndex);
			monitor.add(m);
		}
	}

	private static Character convertCase(char ch, boolean ignoreCase) {
		ch = Character.toLowerCase(ch);
		return ch;
	}
}
