/**
 * Created on 2012-11-5
 * 
 * Copyright (c) Comprice, Inc. 2010. All rights reserved.
 */
package org.housemart.server.util;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class EncryptUtils {

	/**
	 * 加密函数
	 * 
	 * @param content
	 *            加密的内容
	 * @param strKey
	 *            密钥
	 * @return 返回十六进制字符串
	 * @throws Exception
	 */
	public static String enCrypt(String content, String strKey) throws Exception {
		KeyGenerator keygen;
		SecretKey desKey;
		Cipher c;
		byte[] cByte;
		String str = content;

		keygen = KeyGenerator.getInstance("DES");
		SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
		secureRandom.setSeed(strKey.getBytes("UTF-8"));
		keygen.init(56, secureRandom);
		desKey = keygen.generateKey();
		c = Cipher.getInstance("DES");
		c.init(Cipher.ENCRYPT_MODE, desKey);
		cByte = c.doFinal(str.getBytes("UTF-8"));

		return parseByteToHexStr(cByte);
	}

	/**
	 * 解密函数
	 * 
	 * @param src
	 *            加密过的十六进制字符串
	 * @param strKey
	 *            密钥
	 * @return
	 * @throws Exception
	 */
	public static String deCrypt(String src, String strKey) throws Exception {
		KeyGenerator keygen;
		SecretKey desKey;
		Cipher c;
		byte[] cByte;

		keygen = KeyGenerator.getInstance("DES");
		SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
		secureRandom.setSeed(strKey.getBytes("UTF-8"));
		keygen.init(56, secureRandom);
		desKey = keygen.generateKey();
		c = Cipher.getInstance("DES");
		c.init(Cipher.DECRYPT_MODE, desKey);
		cByte = c.doFinal(parseHexStrToByte(src));

		return new String(cByte, "UTF-8");
	}

	/**
	 * 2进制转化成16进制
	 * 
	 * @param buf
	 * @return
	 */
	public static String parseByteToHexStr(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

	/**
	 * 将16进制转换为二进制
	 * 
	 * @param hexStr
	 * @return
	 */
	public static byte[] parseHexStrToByte(String hexStr) {
		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}

	public static void main(String[] args) throws Exception {
		String str = "qinpeng@pinwoo.com";
		String key = "pinwoo20101012";
		System.out.println(deCrypt("3B7B79B1382AA8D94DDB105C3AD6DB0FE49F9360817BCCE4", key));
		String enString = enCrypt(str, key);
		System.out.println("加密前：" + str);
		System.out.println("加密后HEX：" + enString);
		System.out.println("解密后：" + deCrypt(enString, key));
	}
}
