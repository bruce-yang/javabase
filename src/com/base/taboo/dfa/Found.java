package com.base.taboo.dfa;

public class Found implements Comparable<Found> {
	private int startIndex;
	private int endIndex;
	private String value = "";
	private String taboo = "";

	public Found(int startIndex, int endIndex, String value, String taboo) {
		this.startIndex = startIndex;
		this.endIndex = endIndex;
		this.value = value;
		this.taboo = taboo;
	}

	public String getTaboo() {
		return taboo;
	}

	public void setTaboo(String taboo) {
		this.taboo = taboo;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public int getEndIndex() {
		return endIndex;
	}

	public void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}

	@Override
	public int compareTo(Found o) {
		int rst = 0;
		if (this.startIndex > o.startIndex) {
			rst = 1;
		} else if (this.startIndex == o.startIndex) {
			if (this.endIndex > o.endIndex) {
				rst = 1;
			} else if (this.endIndex == o.endIndex) {
				rst = 0;
			} else {
				rst = -1;
			}
		} else {
			rst = -1;
		}
		return rst;
	}

	@Override
	public boolean equals(Object obj) {
		Found another = (Found) obj;
		if (this.startIndex == another.startIndex
				&& this.endIndex == another.endIndex) {
			return true;
		} else {
			return false;
		}
	}
}
