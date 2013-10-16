package com.base.taboo.dfa;

import java.util.SortedMap;

public class Validator {

	static boolean hasNext(Node node, Character c) {
		SortedMap<Character, Node> next = node.next;
		if (next.containsKey(c)) {
			return true;
		} else {
			return false;
		}
	}

	static Node nextNode(Node node, Character c) {
		Node rtn = null;
		SortedMap<Character, Node> next = node.next;
		if (next.containsKey(c)) {
			rtn = node.next.get(c);
		}
		return rtn;
	}
}
