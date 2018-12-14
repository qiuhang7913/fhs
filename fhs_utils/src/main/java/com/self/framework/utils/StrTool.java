package com.self.framework.utils;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StrTool {
	private static String hexString = "0123456789ABCDEF";

	public static final SimpleDateFormat yyyy_MM_dd_HH_mm_format = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm");

	public static boolean is_empty(String str) {
		return (str == null || str.length() == 0);
	}

	public static long yyyy_MM_dd_HH_mm_2_ms(String dateStr) {
		try {
			Date date = yyyy_MM_dd_HH_mm_format.parse(dateStr);
			return date.getTime();
		} catch (ParseException e) {
			return 0;
		}
	}

	public static String now_yyyy_MM_dd_HH_mm() {
		Date date = new Date();
		return yyyy_MM_dd_HH_mm_format.format(date);
	}

	public static long yyyy_MM_dd_HH_mm_2_sec(String dateStr) {
		try {
			Date date = yyyy_MM_dd_HH_mm_format.parse(dateStr);
			return date.getTime() / 1000;
		} catch (ParseException e) {
			return 0;
		}

	}

	public static String sec_2_yyyy_MM_dd_HH_mm(long sec) {
		Date date = new Date();
		date.setTime(sec * 1000);
		return yyyy_MM_dd_HH_mm_format.format(date);
	}

	public static String sha(String str) {
		String rv = null;
		
		if (str == null) {
			return rv;
		}
		
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA");
			byte[] b = str.getBytes("gbk");
			byte[] hash = md.digest(b);
			rv = "{SHA}" + (new sun.misc.BASE64Encoder()).encode(hash);

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return rv;

	}

	public static String encode(String str) {
		byte[] bytes = str.getBytes();
		return encode(bytes);
	}

	public static String encode(byte[] bytes) {
		StringBuilder sb = new StringBuilder(bytes.length * 2);
		for (int i = 0; i < bytes.length; i++) {
			sb.append(hexString.charAt((bytes[i] & 0xf0) >> 4));
			sb.append(hexString.charAt((bytes[i] & 0x0f) >> 0));
		}
		return sb.toString();
	}

	public static String decode(String bytes) {
		return new String(decodeBytes(bytes));
	}

	public static byte[] decodeBytes(String bytes) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream(
					bytes.length() / 2);
			for (int i = 0; i < bytes.length(); i += 2) {
				baos.write((hexString.indexOf(bytes.charAt(i)) << 4 | hexString
						.indexOf(bytes.charAt(i + 1))));
			}
			return baos.toByteArray();
		} catch (Exception e) {
			return null;
		}
	}

	public static String escape_null(String value) {
		if (value == null) {
			return "";
		}
		return value;
	}

	public static boolean is_num(String str) {
		if (str == null) {
			return false;
		}

		for (int i = 0; i < str.length(); i++) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}

		return true;
	}
	
	public static long str2long(String str) {
		if (str == null || str.trim().length() == 0) {
			return 0;
		}
		
		try {
			return Long.parseLong(str);
		} catch (Exception e) {			
		}
		
		return 0;
	}

	public static void main(String[] a) {
		System.out.println(sha("1"));
	}

}
