package com.base.taboo.dfa;

public class Tree {
	static void parse(Node tree, String str) {
		parse(tree, str, true);
	}

	public static void parse(Node tree, String str, boolean caseIgnore) {
		Node thiz = tree;
		Node tmp = null;
		CharSequence cs = null;
		for (int i = 0; i < str.length(); i++) {
			Character ch = convertCase(str.charAt(i), caseIgnore);
			cs = str.subSequence(0, i + 1);
			if (Validator.hasNext(thiz, ch)) {
				thiz = Validator.nextNode(thiz, ch);
			} else {
				tmp = new Node(cs);
				thiz.next.put(ch, tmp);
				thiz = tmp;
			}
			thiz.over = (i == str.length() - 1 ? true : false);
		}
	}

	private static Character convertCase(char ch, boolean ignoreCase) {
		ch = Character.toLowerCase(ch);
		return ch;
	}
}
