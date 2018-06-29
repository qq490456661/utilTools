package MD5;


import org.apache.log4j.Logger;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
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
     * MD5加密  (一定要以UTF-8获取getByte("UTF-8"))
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

    public static String encodeByMD5(String s) {
        char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        try {
            byte[] btInput = s.getBytes("utf-8");
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            mdInst.update(btInput);
            byte[] md = mdInst.digest();
            int j = md.length;
            char[] str = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[(k++)] = hexDigits[(byte0 >>> 4 & 0xF)];
                str[(k++)] = hexDigits[(byte0 & 0xF)];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
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

    public static void main(String[] args) throws UnsupportedEncodingException {
        String data = "123456789";
        byte[] bytes = encodeByMD5(data.getBytes("UTF-8"));
        for(int i=0;i<bytes.length;i++){
            System.out.print(bytes[i]+" ");
        }
        System.out.println("MD5:"+toHexString(encodeByMD5(data.getBytes())));
        System.out.println("HmacMD5:"+toHexString(encodeByHmacMD5(data.getBytes(),"qq@MAO360#zzz%%%#")));
        System.out.println("SHA:"+toHexString(encodeBySHA(data.getBytes())));

    }





}
