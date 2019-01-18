package com.self.framework.utils;

import org.bouncycastle.util.encoders.UrlBase64;

import java.io.UnsupportedEncodingException;

/**
 * @des url base64加解密
 */
public class UrlBase64Crypto {
	public final static String ENCODING = "UTF-8";

	public static String encode(String data) {
		return encode(data, ENCODING);
	}

	public static String encode(String data, final String CHARSET) {
		if (data == null) {
			return null;
		}

		try {
			byte[] b = UrlBase64.encode(data.getBytes(CHARSET));

			return new String(b, CHARSET);

		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}

	public static String decode(String data) {
		return decode(data, ENCODING);
	}

	public static String decode(String data, final String CHARSET) {
		if (data == null) {
			return null;
		}

		try {
			byte[] b = UrlBase64.decode(data.getBytes(CHARSET));

			return new String(b, CHARSET);

		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}
}
