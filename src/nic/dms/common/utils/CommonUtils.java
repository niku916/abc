package nic.dms.common.utils;

import java.io.UnsupportedEncodingException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import nic.dms.common.constant.CommonConstant;
import resources.ApplicationConfig;

public class CommonUtils {
	private static final String characterEncoding = "UTF-8";
	private static final String cipherTransformation = "AES/CBC/PKCS5Padding";
	private static final String aesEncryptionAlgorithm = "AES";
	private static final nic.org.apache.log4j.Logger LOGGER = nic.org.apache.log4j.Logger.getLogger(CommonUtils.class);

	public static String getFileUploadPath() {
		String fileUploadPath = "";
		try {
			fileUploadPath = System.getProperty("catalina.base") + new ApplicationConfig(ApplicationConfig.PROP_DMS_URL)
					.getProperties(ApplicationConfig.UPLOADED_FILE_PATH);
			return fileUploadPath;
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
			return null;
		}
	}

	public static boolean isNullOrBlank(String strCheck) {
		if ((strCheck == null) || ("null".equalsIgnoreCase(strCheck))
				|| (CommonConstant.BLANK_STRING.equalsIgnoreCase(strCheck)) || (strCheck.trim().length() <= 0)) {
			return true;
		} else {
			return false;
		}
	}

	public static String encrypt(String plainText, String key) {
		try {
			byte[] plainTextbytes = plainText.getBytes(characterEncoding);
			byte[] keyBytes = getKeyBytes(key);

			return Base64.encodeBase64String(encrypt(plainTextbytes, keyBytes, keyBytes));

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	public static byte[] getKeyBytes(String key) throws UnsupportedEncodingException {
		byte[] keyBytes = new byte[16];
		byte[] parameterKeyBytes = key.getBytes(characterEncoding);
		System.arraycopy(parameterKeyBytes, 0, keyBytes, 0, Math.min(parameterKeyBytes.length, keyBytes.length));
		return keyBytes;
	}

	public static byte[] encrypt(byte[] plainText, byte[] key, byte[] initialVector) {
		try {
			Cipher cipher = Cipher.getInstance(cipherTransformation);
			SecretKeySpec secretKeySpec = new SecretKeySpec(key, aesEncryptionAlgorithm);
			IvParameterSpec ivParameterSpec = new IvParameterSpec(initialVector);
			cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
			plainText = cipher.doFinal(plainText);
			return plainText;
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return null;

	}
}
