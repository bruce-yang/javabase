package com.base.taboo.io.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TextReader extends Reader {
	String name = "";

	public TextReader(String name) {
		this.name = name;
	}

	/**
	 * Get List<String> from taboos.txt file.
	 * 
	 * @return BufferedReader object
	 * @throws FileNotFoundException
	 */
	public List<String> readLines2List() {
		List<String> taboos = new ArrayList<String>();
		try {
			File resource = getResource(name);
			BufferedReader br = new BufferedReader(new FileReader(resource));
			String tmp = null;
			while ((tmp = br.readLine()) != null) {
				tmp = tmp.trim();
				if (!tmp.startsWith("#")) {
					taboos.add(tmp);
				}
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return taboos;
	}

	/**
	 * Get set from taboos.txt file.
	 * 
	 * @return BufferedReader object
	 * @throws FileNotFoundException
	 */
	public List<Character> readLines2Chars() {
		List<Character> chars = new ArrayList<Character>();
		try {
			File resource = getResource(name);
			BufferedReader br = new BufferedReader(new FileReader(resource));
			String tmp = null;
			while ((tmp = br.readLine()) != null) {
				chars.add(Character.valueOf(tmp.charAt(0)));
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return chars;
	}

	/**
	 * Get String from taboos.txt file.
	 * 
	 * @return BufferedReader object
	 * @throws FileNotFoundException
	 */
	public String read2String() {
		StringBuffer sb = new StringBuffer();
		try {
			File resource = getResource(name);
			BufferedReader br = new BufferedReader(new FileReader(resource));
			char[] buf = new char[4096];
			int c = br.read(buf);
			while (c != -1) {
				sb.append(buf, 0, c);
				c = br.read(buf);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	/**
	 * Get String from taboos.txt file.
	 * 
	 * @return BufferedReader object
	 * @throws FileNotFoundException
	 */
	public String readLines2String() {
		StringBuffer sb = new StringBuffer();
		try {
			File resource = getResource(name);
			BufferedReader br = new BufferedReader(new FileReader(resource));
			String tmp = null;
			while ((tmp = br.readLine()) != null) {
				sb.append(tmp);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	/**
	 * Get String[] from taboos.txt file.
	 * 
	 * @return BufferedReader object
	 * @throws FileNotFoundException
	 */
	public String[] readLines2Array() {
		List<String> taboos = readLines2List();
		String[] result = new String[taboos.size()];
		taboos.toArray(result);
		return result;
	}
}
