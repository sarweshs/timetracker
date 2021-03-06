package com.ibm.timetracker.util;


import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class EncryptionDecryptionUtil {
	private static final String secretKeyStr;
	static Cipher cipher;
	static SecretKey mySecretKey;
	static
	{
		secretKeyStr = PropertiesFileUtil.getProperty("http.upload.password");
		KeyGenerator keyGenerator;
		try {
			if(secretKeyStr != null)
			{
				// decode the base64 encoded string
				byte[] decodedKey = Base64.getDecoder().decode(secretKeyStr);
				// rebuild key using SecretKeySpec
				mySecretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES"); 
			}
			else
			{
				keyGenerator = KeyGenerator.getInstance("AES");
				keyGenerator.init(128);
				mySecretKey = keyGenerator.generateKey();
				// get base64 encoded version of the key
				String encodedKey = Base64.getEncoder().encodeToString(mySecretKey.getEncoded());
				System.out.println(encodedKey);
			}
			cipher = Cipher.getInstance("AES");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static void main(String[] args) throws Exception {

		String plainText = "AES Symmetric Encryption Decryption";
		System.out.println("Plain Text Before Encryption: " + plainText);

		String encryptedText = encrypt(plainText, mySecretKey);
		System.out.println("Encrypted Text After Encryption: " + encryptedText);

		String decryptedText = decrypt(encryptedText, mySecretKey);
		System.out.println("Decrypted Text After Decryption: " + decryptedText);
	}

	public static String encrypt(String plainText, SecretKey secretKey)
			throws Exception {
		if(secretKey == null)
		{
			secretKey = mySecretKey;
		}
		byte[] plainTextByte = plainText.getBytes();
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		byte[] encryptedByte = cipher.doFinal(plainTextByte);
		Base64.Encoder encoder = Base64.getEncoder();
		String encryptedText = encoder.encodeToString(encryptedByte);
		return encryptedText;
	}

	public static String decrypt(String encryptedText, SecretKey secretKey)
			throws Exception {
		if(secretKey == null)
		{
			secretKey = mySecretKey;
		}
		Base64.Decoder decoder = Base64.getDecoder();
		byte[] encryptedTextByte = decoder.decode(encryptedText);
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		byte[] decryptedByte = cipher.doFinal(encryptedTextByte);
		String decryptedText = new String(decryptedByte);
		return decryptedText;
	}
}
