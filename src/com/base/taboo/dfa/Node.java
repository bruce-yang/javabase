package com.base.taboo.dfa;

import java.util.SortedMap;
import java.util.TreeMap;

public class Node {
	private static final int HASH_PRIME = 71;
	CharSequence me;
	SortedMap<Character, Node> next = new TreeMap<Character, Node>();
	boolean over = false;

	public Node(CharSequence cs) {
		this.me = cs;
	}

	@Override
	public boolean equals(Object obj) {
		Node another = (Node) obj;
		if (this.me.equals(another.me) && this.next.equals(another.next)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		int result = 0;
		result = HASH_PRIME * result + me.hashCode();
		result = HASH_PRIME * result + next.hashCode();
		return result;
	}

}
