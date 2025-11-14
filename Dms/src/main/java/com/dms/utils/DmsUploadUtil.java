package com.dms.utils;

import java.io.UnsupportedEncodingException;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.tomcat.util.codec.binary.Base64;

public class DmsUploadUtil {
//    public static final String key = "VCSToken";
//    private static final String characterEncoding = "UTF-8";
//    private static final String cipherTransformation = "AES/CBC/PKCS5Padding";
//    private static final String aesEncryptionAlgorithm = "AES";

	public static byte[] getKeyBytes(String key) throws UnsupportedEncodingException {
		byte[] keyBytes = new byte[16];
		byte[] parameterKeyBytes = key.getBytes(CommonConstant.characterEncoding);
		System.arraycopy(parameterKeyBytes, 0, keyBytes, 0, Math.min(parameterKeyBytes.length, keyBytes.length));
		return keyBytes;
	}

	public static String decrypt(String encryptedText, String key) {

		try {
			byte[] cipheredBytes = Base64.decodeBase64(encryptedText);
			byte[] keyBytes = getKeyBytes(key);
			return new String(decrypt(cipheredBytes, keyBytes, keyBytes), CommonConstant.characterEncoding);
		} catch (Exception e) {
			//
			e.printStackTrace();
		}
		return null;

	}

	public static byte[] decrypt(byte[] cipherText, byte[] key, byte[] initialVector) {

		try {

			Cipher cipher = Cipher.getInstance(CommonConstant.cipherTransformation);
			SecretKeySpec secretKeySpecy = new SecretKeySpec(key, CommonConstant.aesEncryptionAlgorithm);
			IvParameterSpec ivParameterSpec = new IvParameterSpec(initialVector);
			cipher.init(Cipher.DECRYPT_MODE, secretKeySpecy, ivParameterSpec);
			cipherText = cipher.doFinal(cipherText);
			return cipherText;
		} catch (Exception e) {
			e.printStackTrace();

		}
		return null;

	}
}
