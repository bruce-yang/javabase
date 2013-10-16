package com.base.taboo.dfa;

public class Monitor implements Comparable<Monitor> {
	private static final int HASH_PRIME = 97;
	int index;
	Node node;

	public Monitor(Node node, int index) {
		this.node = node;
		this.index = index;
	}

	@Override
	public int compareTo(Monitor o) {
		int rst = 0;
		if (this.index > o.index) {
			rst = 1;
		} else if (this.index == o.index) {
			rst = 0;
		} else {
			rst = -1;
		}
		return rst;
	}

	@Override
	public boolean equals(Object obj) {
		Monitor another = (Monitor) obj;
		if (this.index == another.index && this.node.equals(another.node)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		int result = 0;
		result = HASH_PRIME * result + index;
		result = HASH_PRIME * result + node.hashCode();
		return result;
	}

}
