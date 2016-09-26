package MD5;


import org.apache.log4j.Logger;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * Created by linjunjie(490456661@qq.com) on 2016/3/21.
 */
public class MD5Util {

    private static Logger logger = Logger.getLogger(MD5Util.class);
    public static final String KEY_SHA="SHA";
    public static final String KEY_SHA1="SHA-1";
    public static final String KEY_MD5="MD5";
    public static final String KEY_HMAC_MD5="HmacMD5";

    /**
     * MD5加密
     * @param data
     * @return
     */
    public static byte[] encodeByMD5(byte[] data){
        try {
            MessageDigest md = MessageDigest.getInstance(KEY_MD5);
            md.update(data);
            return md.digest();
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return null;
    }

    /**
     * HmacMD5加密
     * @param data 待加密的明文
     * @param secretKey 密钥
     * @return 密文
     */
    public static byte[] encodeByHmacMD5(byte[] data,String secretKey){
        try {
            SecretKey sk = new SecretKeySpec(secretKey.getBytes(),KEY_HMAC_MD5);
            Mac mac = Mac.getInstance(KEY_HMAC_MD5);
            mac.init(sk);
            return mac.doFinal(data);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return null;
    }

    /**
     * SHA加密
     * @param data
     * @return
     */
    public static byte[] encodeBySHA(byte[] data){
        try {
            MessageDigest md = MessageDigest.getInstance(KEY_SHA);
            md.update(data);
            return md.digest();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * SHA1加密
     * @param data
     * @return
     */
    public static byte[] encodeBySHA1(byte[] data){
        try {
            MessageDigest md = MessageDigest.getInstance(KEY_SHA1);
            md.update(data);
            return md.digest();
        } catch (NoSuchAlgorithmException e) {
            logger.error(e.getMessage(), e);
        }

        return null;
    }

    /**
     * 转换为十六进制
     * @param data
     * @return
     */
    public static final char[] hexChar = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
    public static String toHexString(byte[] data){
        StringBuilder hex = new StringBuilder();
        int temp;
        for(int i=0;i<data.length;i++){
            temp = data[i]&0xff;
            hex.append(hexChar[temp>>>4]).append(hexChar[temp&0x0f]);
        }
        return hex.toString();
    }

    public static void main(String[] args){
        String data = "linjunjie";
        System.out.println("MD5:"+toHexString(encodeByMD5(data.getBytes())));
        System.out.println("HmacMD5:"+toHexString(encodeByHmacMD5(data.getBytes(),"shen")));
        System.out.println("SHA:"+toHexString(encodeBySHA(data.getBytes())));

    }



}
