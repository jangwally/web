package com.ruanyun.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import org.apache.commons.lang.ArrayUtils;

/**
 * @author Mr.Zheng
 * @date 2014年8月22日 下午1:44:23
 */
public final class RSAUtils {
	private static String RSA = "RSA";
	//返回给app使用的
	public static String PUCLIC_KEY_ALL = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCoMslXzDMiPnm+P6r4dd1f5o4W44m8Vkgu27DKbIphULNucVmv01IP8ccbTlLxPpKX3Bq1OHZ6hkm9QoCjgQTGRQfZf5JyAjbwyGlAhioF4SBHgqDDa64ki74Ki2DVg0i8ZRu/Al9FNAz1EbQJ2CncojNBhGHYlNwFrWsC6Cb11wIDAQAB";
	//解密为APP传值过来的
	public static String PRIVATE_KEY_OTHER = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMpQXMlcJ6J8QW1N\n" +
            "PH+vikA2vz6sAsS0SfHZB9ANfH4J6bb71AtFOsHgzcAWQQV8qveqcIIo86goGuC/\n" +
            "7TygKwAJFXly+1v0P6no0/KCfUyCTdK/eiIqS8JkcF29ioh5RaQbLtVmU5ZCS5af\n" +
            "PYSwRQiJ3iLN3laWtpv0zuM0LOj3AgMBAAECgYEAubV21qcvAqnIbQqe5B5Aa51V\n" +
            "g7PlfqZ30faEg2g9xHxY+szC4e2Ud+9hLle/K8g6L8lWKURqFQtrUK8cl6/2hxnl\n" +
            "9rNtncohqLPHO5CiN9GO/qleJorgMf3fvcYNYPLgnmkj426M5ed/qAvshNV8zS5h\n" +
            "8NwofFrOfw68AQxIiwECQQDovBxVixLTs/3pLUjyytqNXFmOaDlL/+vtXQVcqc69\n" +
            "CohhvzSQGJmFO362QYX1ICp7EgYES1WgQvef/7ufoTK3AkEA3onBGGpzU0pA3ZUD\n" +
            "dEjHtrvEwAZCMdG8g9oCNvlCHPnzo5H51Wuka0QqR0hv2YM1R/xl69vn+BlmtPyR\n" +
            "3za7wQJAT01BrlhMGvzayOhYUfqTC9Xq4h5bX60de+zVVeS6gCmlnQDk7TCkpwRF\n" +
            "wd6DwamrL/JNQItW6tvGuqsOCG+J8wJBAI7x64/0aOnaa3opytM6INcXG9XA72oy\n" +
            "8CW9tuh7CeW1BLRQAyv8/dtNKN8q/3W3m1UHIqzzT7kFD/03s7eu38ECQGjHNqRy\n" +
            "JsynBZTa4LlFVoEFQeJ3jaS7v2Y1O5VUY3C06d2mp8Y4w6hyaZynGS8Ca8ySU7t8\n" +
            "YrhYgNjNaaHu7LE=";

	/**
	 * 随机生成RSA密钥对(默认密钥长度为1024)
	 * 
	 * @return
	 */
	public static KeyPair generateRSAKeyPair() {
		return generateRSAKeyPair(1024);
	}

	/**
	 * 随机生成RSA密钥对
	 * 
	 * @param keyLength
	 *            密钥长度，范围：512～2048<br>
	 *            一般1024
	 * @return
	 */
	public static KeyPair generateRSAKeyPair(int keyLength) {
		try {
			KeyPairGenerator kpg = KeyPairGenerator.getInstance(RSA);
			kpg.initialize(keyLength);
			return kpg.genKeyPair();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 用公钥加密 <br>
	 * 每次加密的字节数，不能超过密钥的长度值减去11
	 * 
	 * @param data
	 *            需加密数据的byte数据
	 * @param pubKey
	 *            公钥
	 * @return 加密后的byte型数据
	 */
	public static byte[] encryptData(byte[] data, PublicKey publicKey) {
		try {
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			// 编码前设定编码方式及密钥
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			
			byte[] enBytes = null;
			for (int i = 0; i < data.length; i += 64) {  
			// 注意要使用2的倍数，否则会出现加密后的内容再解密时为乱码
			    byte[] doFinal = cipher.doFinal(ArrayUtils.subarray(data, i,i + 64));  
			    enBytes = ArrayUtils.addAll(enBytes, doFinal);  
			}
			// 传入编码数据并返回编码结果
			return enBytes;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 功能描述:用公匙加密
	 *
	 * @author yangliu  2016年8月4日 下午3:33:54
	 * 
	 * @param data 需要加密字符串
	 * @return
	 */
	public static String encryptData(String data,String puclicKey) {
		try {
			PublicKey publicKey = RSAUtils.loadPublicKey(puclicKey); // 获取公匙
			// 传入编码数据并返回编码结果
			byte[] bytes = encryptData(Base64Utils.encode(data.getBytes("utf-8")).getBytes("utf-8"), publicKey);
			return Base64Utils.encode(bytes);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 用私钥解密
	 * 
	 * @param encryptedData
	 *            经过encryptedData()加密返回的byte数据
	 * @param privateKey
	 *            私钥
	 * @return
	 */
	public static byte[] decryptData(byte[] encryptedData, PrivateKey privateKey) {
		try {
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < encryptedData.length; i += 128) {
			    byte[] doFinal = cipher.doFinal(ArrayUtils.subarray(encryptedData, i, i + 128));
			    sb.append(new String(doFinal));
			}
			return sb.toString().getBytes();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 功能描述: 用私匙解密
	 *
	 * @author yangliu  2016年8月4日 下午3:35:59
	 * 
	 * @param data 加密字符串
	 * @return
	 */
	public static String decryptData(String data,String privateKey) {
		try {
			PrivateKey privateKeyObject = RSAUtils.loadPrivateKey(privateKey);
			byte [] encryptedData=decryptData(Base64Utils.decode(data), privateKeyObject);
			return new String(encryptedData,"utf-8");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 通过公钥byte[](publicKey.getEncoded())将公钥还原，适用于RSA算法
	 * 
	 * @param keyBytes
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	public static PublicKey getPublicKey(byte[] keyBytes) throws NoSuchAlgorithmException, InvalidKeySpecException {
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(RSA);
		PublicKey publicKey = keyFactory.generatePublic(keySpec);
		return publicKey;
	}

	/**
	 * 通过私钥byte[]将公钥还原，适用于RSA算法
	 * 
	 * @param keyBytes
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	public static PrivateKey getPrivateKey(byte[] keyBytes) throws NoSuchAlgorithmException, InvalidKeySpecException {
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(RSA);
		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
		return privateKey;
	}

	/**
	 * 使用N、e值还原公钥
	 * 
	 * @param modulus
	 * @param publicExponent
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	public static PublicKey getPublicKey(String modulus, String publicExponent)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		BigInteger bigIntModulus = new BigInteger(modulus);
		BigInteger bigIntPrivateExponent = new BigInteger(publicExponent);
		RSAPublicKeySpec keySpec = new RSAPublicKeySpec(bigIntModulus, bigIntPrivateExponent);
		KeyFactory keyFactory = KeyFactory.getInstance(RSA);
		PublicKey publicKey = keyFactory.generatePublic(keySpec);
		return publicKey;
	}

	/**
	 * 使用N、d值还原私钥
	 * 
	 * @param modulus
	 * @param privateExponent
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	public static PrivateKey getPrivateKey(String modulus, String privateExponent)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		BigInteger bigIntModulus = new BigInteger(modulus);
		BigInteger bigIntPrivateExponent = new BigInteger(privateExponent);
		RSAPublicKeySpec keySpec = new RSAPublicKeySpec(bigIntModulus, bigIntPrivateExponent);
		KeyFactory keyFactory = KeyFactory.getInstance(RSA);
		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
		return privateKey;
	}

	/**
	 * 从字符串中加载公钥
	 * 
	 * @param publicKeyStr
	 *            公钥数据字符串
	 * @throws Exception
	 *             加载公钥时产生的异常
	 */
	public static PublicKey loadPublicKey(String publicKeyStr) throws Exception {
		try {
			byte[] buffer = Base64Utils.decode(publicKeyStr);
			KeyFactory keyFactory = KeyFactory.getInstance(RSA);
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
			return (RSAPublicKey) keyFactory.generatePublic(keySpec);
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此算法");
		} catch (InvalidKeySpecException e) {
			throw new Exception("公钥非法");
		} catch (NullPointerException e) {
			throw new Exception("公钥数据为空");
		}
	}

	/**
	 * 从字符串中加载私钥<br>
	 * 加载时使用的是PKCS8EncodedKeySpec（PKCS#8编码的Key指令）。
	 * 
	 * @param privateKeyStr
	 * @return
	 * @throws Exception
	 */
	public static PrivateKey loadPrivateKey(String privateKeyStr) throws Exception {
		try {
			byte[] buffer = Base64Utils.decode(privateKeyStr);
			// X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
			KeyFactory keyFactory = KeyFactory.getInstance(RSA);
			return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此算法");
		} catch (InvalidKeySpecException e) {
			throw new Exception("私钥非法");
		} catch (NullPointerException e) {
			throw new Exception("私钥数据为空");
		}
	}

	/**
	 * 从文件中输入流中加载公钥
	 * 
	 * @param in
	 *            公钥输入流
	 * @throws Exception
	 *             加载公钥时产生的异常
	 */
	public static PublicKey loadPublicKey(InputStream in) throws Exception {
		try {
			return loadPublicKey(readKey(in));
		} catch (IOException e) {
			throw new Exception("公钥数据流读取错误");
		} catch (NullPointerException e) {
			throw new Exception("公钥输入流为空");
		}
	}

	/**
	 * 从文件中加载私钥
	 * 
	 * @param keyFileName
	 *            私钥文件名
	 * @return 是否成功
	 * @throws Exception
	 */
	public static PrivateKey loadPrivateKey(InputStream in) throws Exception {
		try {
			return loadPrivateKey(readKey(in));
		} catch (IOException e) {
			throw new Exception("私钥数据读取错误");
		} catch (NullPointerException e) {
			throw new Exception("私钥输入流为空");
		}
	}

	/**
	 * 读取密钥信息
	 * 
	 * @param in
	 * @return
	 * @throws IOException
	 */
	private static String readKey(InputStream in) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String readLine = null;
		StringBuilder sb = new StringBuilder();
		while ((readLine = br.readLine()) != null) {
			if (readLine.charAt(0) == '-') {
				continue;
			} else {
				sb.append(readLine);
				sb.append('\r');
			}
		}

		return sb.toString();
	}
}
