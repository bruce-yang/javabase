package com.base.utils;

/*************************************************************************
* Compilation: javac CRC32.java
* Execution: java CRC32 s
*
* Reads in a string s as a command-line argument, and prints out
* its 32 bit Cyclic Redundancy Check (CRC32 or Ethernet / AAL5 or ITU-TSS).
*
* Uses direct table lookup, calculation, and Java library.
*
* % java CRC32 123456789
* CRC32 (via table lookup) = cbf43926
* CRC32 (via direct calculation) = cbf43926
* CRC32 (via Java's library) = cbf43926
*
*
*
* Uses irreducible polynomial:
* 1 + x + x^2 + x^4 + x^5 + x^7 + x^8 +
* x^10 + x^11 + x^12 + x^16 + x^22 + x^23 + x^26
*
* 0000 0100 1100 0001 0001 1101 1011 0111
* 0 4 C 1 1 D B 7
*
* The reverse of this polynomial is
*
* 0 2 3 8 8 B D E
*
*
*
*************************************************************************/
import java.io.UnsupportedEncodingException;

public class CRC32Tool {
	private static final String IMG10_URL = "img10.woyopic.com";
	private static final String IMG11_URL = "img11.woyopic.com";
	private static final String IMG12_URL = "img12.woyopic.com";
	private static final String IMG13_URL = "img13.woyopic.com";

	public static void main(String[] args) {
		String url = "2010/1032/818/60e0e7752df19b1ce6631dacb8cd7d7c.jpg";
			System.out.println(getImgUrl(url));

	}

	/**
	 * 获取真实url
	 * @param imgUrl 
	 * @return
	 */
	public static String getImgUrl(String imgUrl) {
		return "http://" + getImgHost(imgUrl) + "/" + imgUrl;
	}

	/**
	 * 获取host那么
	 * @param imgName
	 * @return
	 */
	public static String getImgHost(String imgName) {
		if (imgName == null || imgName.trim().length() == 0) {
			return "";
		}
		int value = 0;
		try {
			value = getCRC32(imgName) % 4;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return value == 0 ? IMG10_URL : (value == 1) ? IMG11_URL : (value == 2) ? IMG12_URL : IMG13_URL;
	}

	public static int getCRC32(String _source) throws UnsupportedEncodingException {
		int crc = 0xFFFFFFFF; // initial contents of LFBSR
		int poly = 0xEDB88320; // reverse polynomial
		byte[] bytes = _source.getBytes("utf-8");
		for (byte b : bytes) {
			int temp = (crc ^ b) & 0xff;

			// read 8 bits one at a time
			for (int i = 0; i < 8; i++) {
				if ((temp & 1) == 1)
					temp = (temp >>> 1) ^ poly;
				else
					temp = (temp >>> 1);
			}
			crc = (crc >>> 8 ^ temp);
		}

		// flip bits
		crc = crc ^ 0xffffffff;
		return crc;
	}

}