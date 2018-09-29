package cn.bounter.common.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

/**
 * Created by simon on 2017/6/21.
 * 编码工具类，封装了一些常见的编码实现
 */
public class EncodeUtil {

    /**
     * 十六进制 编码
     * @param data
     * @return
     */
    public static String encodeHex(byte[] data) {
        return Hex.encodeHexString(data);
    }

    /**
     * 十六进制 解码
     * @param hexStr
     * @return
     * @throws Exception
     */
    public static byte[] decodeHex(String hexStr) throws Exception {
        return Hex.decodeHex(hexStr.toCharArray());
    }

    /**
     * Base64 编码
     * @param data
     * @return
     */
    public static String encodeBase64(byte[] data) {
        return Base64.encodeBase64String(data);
    }

    /**
     * Base64 解码
     * @param base64Str
     * @return
     */
    public static byte[] decodeBase64(String base64Str) {
        return Base64.decodeBase64(base64Str);
    }
    
    public static void main(String[] args) {
    	System.out.println(encodeBase64("b30bd351371c686298d32281b337e8e9".getBytes()));
    }
}
