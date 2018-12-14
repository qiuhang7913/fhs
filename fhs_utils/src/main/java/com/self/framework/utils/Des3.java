package com.self.framework.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;

public class Des3 {
	private static final String Algorithm = "DESede"; // ���� �����㷨,���� //

	// DES,DESede,Blowfish

	// keybyteΪ������Կ������Ϊ24�ֽ� //srcΪ�����ܵ����ݻ�������Դ��
	public static byte[] encryptMode(byte[] keybyte, byte[] src) {
		try { // ������Կ
			SecretKey deskey = new SecretKeySpec(keybyte, Algorithm); // ����
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.ENCRYPT_MODE, deskey);
			return c1.doFinal(src);
		} catch (java.security.NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (javax.crypto.NoSuchPaddingException e2) {
			e2.printStackTrace();
		} catch (Exception e3) {
			e3.printStackTrace();
		}
		return null;

	}

	private static byte[] formatKey(String keys) {
		byte[] key = new byte[24];
		byte[] tmp = keys.getBytes();
		for (byte i = 0; i < 24; i++) {
			if (tmp != null && i < tmp.length) {
				key[i] = tmp[i];
			} else {
				key[i] = i;
			}
		}
		return key;
	}

	public static String enc(String key, String src) {
		if (src == null || key == null) {
			return null;
		}
		try {
			byte[] bs = encryptMode(formatKey(key), src.getBytes("GBK"));
			return StrTool.encode(bs);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public static String dec(String key, String src) {
		if (src == null) {
			return null;
		}
		byte[] bs = StrTool.decodeBytes(src);

		if (bs == null) {
			return null;
		}

		byte[] rv = decryptMode(formatKey(key), bs);
		if (rv == null) {
			return null;
		}
		return new String(rv);
	}

	// keybyteΪ������Կ������Ϊ24�ֽ� //srcΪ���ܺ�Ļ�����
	public static byte[] decryptMode(byte[] keybyte, byte[] src) {
		try { // ������Կ
			SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);
			// ����
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.DECRYPT_MODE, deskey);
			return c1.doFinal(src);
		} catch (java.security.NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (javax.crypto.NoSuchPaddingException e2) {
			e2.printStackTrace();
		} catch (Exception e3) {
			e3.printStackTrace();
		}
		return null;
	}

	public static void main(String[] a) {
		String str = "583BB8AB9DC145408A2AEA786B562145";
		//str = enc("1234567890qwsder%^&*(", str);
		//System.out.println(str);
		str = dec("abc1234567890", str);
		System.out.println(str);
	}

}