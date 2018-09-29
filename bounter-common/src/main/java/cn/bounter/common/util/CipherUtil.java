package cn.bounter.common.util;

import org.apache.commons.codec.digest.DigestUtils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by simon on 2018/5/31. 密码工具类
 */
public class CipherUtil {

	// 默认的128位秘钥,即16字节英文字符串
	private static final String DEFAULT_SECRET_KEY = "Bounter.cn!!!!!!";
	// 默认的私钥，2048位，Base64编码
	private static final String DEFAULT_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCnaxvDzMXqUaIg+qkkcoCLzzKXzWvlURqNwTeGCRJ5N17/kde3fZRCmD73SLt7GE6GweBPwE0lmSrkrXV/4KP1zPCM53PwQ/1kFO9ZYpDHc/s4P44JFIjzvTgyBL30V334RfLDa4vmu3ST3PXoFxg2RnEB0qmMUFJhN0BKGpa0UDXAOvaQ7UeuYAWbild2pt0NZQFnXkFlx1fqRkQPW7rJ61ScBP2ilXc8ak6tOTB2m70+/VTmOfZBkMF01gGcQ9DrzZZWDDz3X0n0qE8PmnDMQiKxjVzsgh+5V65l5ZiYWGTW9zD1cvskInLWfBZtO4KiRVWinRAAZcmuHbawW9f7AgMBAAECggEAbL11+DQd7Lb/Dlqg1+qDMcvveYfUZ8rI63+vF6D23cKoMmlA6o5naOXIKAeWtjowbvioQESQFzaoFIXRxrBa7oEhUPPYaxtODt5GJBGQJexgJMWq9Jzg4ydow5wu4PCfAk40gtdss/3Xl16yXf/6Gu6f4eY5d8iH6VrgqAEjF5PgftCvAnDDJL6WJlIzfkeEb2bsvBJfbXy9esNxwhr8wq11OI2ZF1H/KRt3X4dnE+g74HVLyjNcWkt2/F5BUVqoNaDb36ghpjU3usbXK25DZyiOSFq8fPMF8wi19H4noMKnihal3yd051x55MKcyVUqNq6K9nrGe/+4vaglk1kBcQKBgQDUjf7hSrV9Of4NrRjUK12IlNwA1YHOpeV7Sntu9I7F9pUwOf7HXb1HxGYlyfzkqu3Rpi2Wkz+W9SeUxRGnG+emqVdRrl0B+Ff6RRzs/2inNy7JcTREqNLc+BuW5qCZ4nIiYGbhkCBUOkQJYe35B0rR+Ps3Id9fnqrlUbch+9jyCQKBgQDJo1b/PPIVohjbOI1rTqLea+vqGe8w5w6IzB9QjT/eGbez3zzjSZ59YVPWQrpaV3xwIV8XwxdL574mwHFIii/ngICjr5fKt8fq5XT8sFcIjxRvPUAsa1sw5IuV5jF6iMfFyZYmZ70QcXJ9AWtNX2Ka2vLxD4QcV19jzWfFL/nq4wKBgQDIdeKbt0nTAI6Jd2ajMwLTfRC/+Jyjt/HlhSlES4EbUqMBCLEwdUe+F4v+t7C6ODpyLrdv+w9N34ek1vb/E2I13B9t+XPeF8lhTy0olvisO270rL8OwemcXKIofvmYJ+hcY26j7LmQtiLUdndpFWeK/7ZtGJUCKTIjdB2y/0JrgQKBgC5EeqvqkioAne9/lIOw8ZJT9TgsZ0QzYJRnMjBLmQIfqJVvfgsxU1q1wIUDnrsoI/ee0LElJO1X6bv+oH/ipYdbZykoG456iTcPG773v6DJJAk6ZsVKhQw9BQKKZYQWarCpxvhdCXcCcSDZ+IMyhe++mcxSoR/XtJ2z/kGy9oe9AoGABw8G5tU7TnZZLu2Yfak+kHHCfpf4zpMUGZYOSw8ko1XNiNtY4EROvV2BNFVGuTvkkQc2XkpJz1oPaYRnAf1CjW3wsS+actMGgNS0z4aYy9b53ZWzFJsKPJUvYa2/McK9Spdu6GrVdPImznVSdVMBmgU+NnfHCR0LFTp8S4oQVyY=";
	// 默认的公钥，2048位，Base64编码
	private static final String DEFAULT_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAp2sbw8zF6lGiIPqpJHKAi88yl81r5VEajcE3hgkSeTde/5HXt32UQpg+90i7exhOhsHgT8BNJZkq5K11f+Cj9czwjOdz8EP9ZBTvWWKQx3P7OD+OCRSI8704MgS99Fd9+EXyw2uL5rt0k9z16BcYNkZxAdKpjFBSYTdAShqWtFA1wDr2kO1HrmAFm4pXdqbdDWUBZ15BZcdX6kZED1u6yetUnAT9opV3PGpOrTkwdpu9Pv1U5jn2QZDBdNYBnEPQ682WVgw8919J9KhPD5pwzEIisY1c7IIfuVeuZeWYmFhk1vcw9XL7JCJy1nwWbTuCokVVop0QAGXJrh22sFvX+wIDAQAB";

	
	/**
	 * MD5摘要
	 * @param data
	 * @return			十六进制摘要结果
	 */
	public static String md5Hex(String data) {
		return DigestUtils.md5Hex(data);
	}

	/**
	 * MD5摘要
	 * @param data
	 * @return			Base64摘要结果
	 */
	public static String md5Base64(String data) {
		return EncodeUtil.encodeBase64(DigestUtils.md5(data));
	}

	/**
	 * SHA-256摘要
	 * @param data
	 * @return			十六进制摘要结果
	 */
	public static String sha256Hex(String data) {
        return DigestUtils.sha256Hex(data);
    }
	
	/**
	 * AES加密
	 * 
	 * @param secret
	 *            128位秘钥
	 * @param data
	 *            明文
	 * @return Base64编码的密文
	 * @throws Exception
	 */
	public static String aesEncrypt(String secret, String data) throws Exception {
		SecretKey secretKey = new SecretKeySpec(secret.getBytes(), "AES");

		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		return EncodeUtil.encodeBase64(cipher.doFinal(data.getBytes()));
	}

	/**
	 * AES加密，默认秘钥
	 * 
	 * @param data
	 *            明文
	 * @return Base64编码的密文
	 * @throws Exception
	 */
	public static String aesEncrypt(String data) throws Exception {
		SecretKey secretKey = new SecretKeySpec(DEFAULT_SECRET_KEY.getBytes(), "AES");

		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		return EncodeUtil.encodeBase64(cipher.doFinal(data.getBytes()));
	}

	/**
	 * AES解密
	 * 
	 * @param secret
	 *            128位秘钥
	 * @param data
	 *            Base64编码的密文
	 * @return 明文
	 * @throws Exception
	 */
	public static String aesDecrypt(String secret, String data) throws Exception {
		SecretKey secretKey = new SecretKeySpec(secret.getBytes(), "AES");

		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		return new String(cipher.doFinal(EncodeUtil.decodeBase64(data)));
	}

	/**
	 * AES解密，默认秘钥
	 * 
	 * @param data
	 *            Base64编码的密文
	 * @return
	 * @throws Exception
	 */
	public static String aesDecrypt(String data) throws Exception {
		SecretKey secretKey = new SecretKeySpec(DEFAULT_SECRET_KEY.getBytes(), "AES");

		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		return new String(cipher.doFinal(EncodeUtil.decodeBase64(data)));
	}

	/**
	 * 生成1024位十六进制形式的RSA密钥对，公钥："public",私钥："private"
	 * 
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> genRSAHexKeyMap() throws Exception {
		// 创建密钥对容器
		Map<String, String> keyMap = new HashMap<>();
		// 生成公钥、私钥
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		keyPairGenerator.initialize(1024);
		KeyPair keyPair = keyPairGenerator.generateKeyPair();
		RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
		keyMap.put("public", EncodeUtil.encodeHex(((Key) rsaPublicKey).getEncoded()));
		RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
		keyMap.put("private", EncodeUtil.encodeHex(((Key) rsaPrivateKey).getEncoded()));
		return keyMap;
	}

	/**
	 * RSA公钥加密
	 * 
	 * @param base64PublicKey
	 *            Base64编码的公钥
	 * @param data
	 *            明文
	 * @return Base64编码的密文
	 * @throws Exception
	 */
	public static String rsaEncrypt(String base64PublicKey, String data) throws Exception {
		X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(EncodeUtil.decodeBase64(base64PublicKey));
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);

		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		return EncodeUtil.encodeBase64(cipher.doFinal(data.getBytes()));
	}

	/**
	 * RSA私钥解密
	 * 
	 * @param base64PrivateKey
	 *            Base64编码的私钥
	 * @param data
	 *            Base64编码的密文
	 * @return 明文
	 * @throws Exception
	 */
	public static String rsaDecrypt(String base64PrivateKey, String data) throws Exception {
		PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(EncodeUtil.decodeBase64(base64PrivateKey));
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);

		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		return new String(cipher.doFinal(EncodeUtil.decodeBase64(data)));
	}

	/**
	 * RSA签名
	 * 
	 * @param base64PrivateKey
	 *            Base64编码的私钥
	 * @param data
	 *            签名的内容(utf-8)
	 * @return Base64编码的签名
	 * @throws Exception
	 */
	public static String rsaSign(String base64PrivateKey, String data) throws Exception {
		PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(EncodeUtil.decodeBase64(base64PrivateKey));
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);

		Signature signature = Signature.getInstance("SHA256WithRSA");
		signature.initSign(privateKey);
		signature.update(data.getBytes("utf-8"));
		return EncodeUtil.encodeBase64(signature.sign());
	}
	
	
	/**
	 * RSA签名（默认私钥）
	 * 
	 * @param data
	 *            签名的内容(utf-8)
	 * @return Base64编码的签名
	 * @throws Exception
	 */
	public static String rsaSign(String data) throws Exception {
		PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(EncodeUtil.decodeBase64(DEFAULT_PRIVATE_KEY));
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);

		Signature signature = Signature.getInstance("SHA256WithRSA");
		signature.initSign(privateKey);
		signature.update(data.getBytes("utf-8"));
		return EncodeUtil.encodeBase64(signature.sign());
	}

	/**
	 * RSA验签
	 * 
	 * @param base64PublicKey
	 *            Base64编码的公钥
	 * @param base64Sign
	 *            Base64编码的签名
	 * @param data
	 *            签名的内容(utf-8)
	 * @return
	 * @throws Exception
	 */
	public static Boolean rsaVerify(String base64PublicKey, String base64Sign, String data) throws Exception {
		X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(EncodeUtil.decodeBase64(base64PublicKey));
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);

		Signature signature = Signature.getInstance("SHA256WithRSA");
		signature.initVerify(publicKey);
		signature.update(data.getBytes("utf-8"));
		return signature.verify(EncodeUtil.decodeBase64(base64Sign));
	}

	public static void main(String[] args) throws Exception {
		String data = "bounter.cn";
		String sign = rsaSign(DEFAULT_PRIVATE_KEY, data);
		System.out.println(sign);
		System.out.println(rsaVerify(DEFAULT_PUBLIC_KEY, sign, data));
		String rsaResult = rsaEncrypt(DEFAULT_PUBLIC_KEY, data);
		System.out.println(rsaResult);
		System.out.println(rsaDecrypt(DEFAULT_PRIVATE_KEY, rsaResult));
		String aesResult = aesEncrypt(DEFAULT_SECRET_KEY, data);
		System.out.println(aesResult);
		System.out.println(aesDecrypt(DEFAULT_SECRET_KEY, aesResult));
		System.out.println(aesEncrypt("SBkHnZ89ZVCsZ1u0"));
	}
}
