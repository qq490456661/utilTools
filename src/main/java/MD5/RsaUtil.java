/**
 * onway.com Inc.
 * Copyright (c) 2018-2018 All Rights Reserved.
 */
package MD5;

import org.bouncycastle.util.encoders.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author junjie.lin
 * @version $Id: RsaUtil.java, v 0.1 2018/1/6 0006 10:38 junjie.lin Exp $
 */
public class RsaUtil {


    public static String data="hello world";

    public static void main(String[] args) throws Exception {

        //这是公钥,在H5前端
        RSAPublicKey rsaPublicKey = loadPublicKeyByStr("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDTykrDv1TEKVjDeE29kVLo5M7m" +
                "ctlE65WlHSMN8RVL1iA9jXsF9SMNH1AErs2lqxpv18fd3TOAw0pBaG+cXOxApKdv" +
                "RDKgxyuHnONOBzxr6EyWOQlRZt94auL1ESVbLdvYa7+cISkVe+MphfQh7uI/64tG" +
                "Q34aRNmvFKv9PEeBTQIDAQAB");

        byte[] encryByte = encrypt(rsaPublicKey,data.getBytes());
        String str = org.apache.commons.codec.binary.Base64.encodeBase64String(encryByte);
        //前端加密后的密码
        System.out.println(str);

//        String privateKeyStr = loadPrivateKeyByFile("D:\\360Downloads\\secret_key_tools_RSA_win\\RSA\\rsa_private_key_pkcs8.pem");
        //秘钥
        String privateKeyStr = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBANPKSsO/VMQpWMN4" +
                "Tb2RUujkzuZy2UTrlaUdIw3xFUvWID2NewX1Iw0fUASuzaWrGm/Xx93dM4DDSkFo" +
                "b5xc7ECkp29EMqDHK4ec404HPGvoTJY5CVFm33hq4vURJVst29hrv5whKRV74ymF" +
                "9CHu4j/ri0ZDfhpE2a8Uq/08R4FNAgMBAAECgYAudf1KzelhkxR25ShgwsWmc7Nz" +
                "2JQTS38rlqW/BDlAxruR5TysxOcolMrwDAzvIAcPrA2bfVA0GiNrD+cULUpiSYvr" +
                "o3As7XW479p/KwjqjKM08kk/mg6lnkYoezY1ieGtba9DHQgaF8hwIQef63mu0vQB" +
                "+pogAIIm5yvTAZjMoQJBAPqtCjaCxykwLgEEulN+PGILlcPgrQjuoZhDMHwTe4U/" +
                "uciRJhVEZSJUBLwYnUd3O1nSgXYj5mxYj6vN+BikWmkCQQDYSdMyQ6KaNpEC9bBl" +
                "co1fA2rga4Gjru0ipjNG5/HIo66KdEPdAXYks2qiS+TlEfs+yIvY1eQ9mvhRuVqZ" +
                "/6tFAkBSOJ+48XxRzjPIsbxL1oQHxLUC4pbe7yahwqgMindhZV2So8lSAHULpXNI" +
                "Huq3niwhciJ0laHmt8WoHI7bxkzBAkBrJVdcpSjeIH4B61hxSShDk1vUxS05uyR6" +
                "b78jNzAN5xhGOoaL63dtgvwmXlaMLDY8yfiNeJhS9Hxjb+E0PPXJAkAqiDV6F7Y7" +
                "/bsRfDDmhoW8ziD1pwYRmqpkC0VrMKNd3jYP91R5yCWyMyyLtlqA6qlcZvc6NXAt" +
                "lA1giukHAY/G";
        RSAPrivateKey rsaPrivateKey = loadPrivateKeyByStr(privateKeyStr);

        //从H5前端传输过来的密码
        String encryPassword = "VECmRb5pNbp6YWKEBHPHgFUpRiZOgi5+g0P4pT5lJAmi5l1GOa5+DzrnIMDH4BeH1RjsxPhj837KkpQloTBAksJxBC2r3LIUcH5rj1txPfGaVDlI88gwIu+/gaIv9W7La+nKvB0jI3YKlaXbDkhBQU+edPJ7AEnFytnZdKLdeg8=";

        byte[] decryPassByte = decrypt(rsaPrivateKey, org.apache.commons.codec.binary.Base64.decodeBase64(encryPassword));

        System.out.println("私钥解密后的内容："+new String(decryPassByte,"UTF-8"));
    }

    /**
     * 公钥加密过程
     *
     * @param publicKey
     *            公钥
     * @param plainTextData
     *            明文数据
     * @return
     * @throws Exception
     *             加密过程中的异常信息
     */
    public static byte[] encrypt(RSAPublicKey publicKey, byte[] plainTextData)
            throws Exception {
        if (publicKey == null) {
            throw new Exception("加密公钥为空, 请设置");
        }
        Cipher cipher = null;
        try {
            // 使用默认RSA
            cipher = Cipher.getInstance("RSA");
            // cipher= Cipher.getInstance("RSA", new BouncyCastleProvider());
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] output = cipher.doFinal(plainTextData);
            return output;
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此加密算法");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidKeyException e) {
            throw new Exception("加密公钥非法,请检查");
        } catch (IllegalBlockSizeException e) {
            throw new Exception("明文长度非法");
        } catch (BadPaddingException e) {
            throw new Exception("明文数据已损坏");
        }
    }

    /**
     * 私钥解密过程
     *
     * @param privateKey
     *            私钥
     * @param cipherData
     *            密文数据
     * @return 明文
     * @throws Exception
     *             解密过程中的异常信息
     */
    public static byte[] decrypt(RSAPrivateKey privateKey, byte[] cipherData)
            throws Exception {
        if (privateKey == null) {
            throw new Exception("解密私钥为空, 请设置");
        }
        Cipher cipher = null;
        try {
            // 使用默认RSA
            cipher = Cipher.getInstance("RSA");
//            cipher= Cipher.getInstance("RSA", new BouncyCastleProvider());
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] output = cipher.doFinal(cipherData);
            return output;
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此解密算法");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidKeyException e) {
            throw new Exception("解密私钥非法,请检查");
        } catch (IllegalBlockSizeException e) {
            throw new Exception("密文长度非法");
        } catch (BadPaddingException e) {
            throw new Exception("密文数据已损坏");
        }
    }

    /**
     * 从字符串中加载公钥
     *
     * @param publicKeyStr
     *            公钥数据字符串
     * @throws Exception
     *             加载公钥时产生的异常
     */
    public static RSAPublicKey loadPublicKeyByStr(String publicKeyStr)
            throws Exception {
        try {
            byte[] buffer = Base64.decode(publicKeyStr);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
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
     * 从字符串中加载私钥
     * @param privateKeyStr
     * @return
     * @throws Exception
     */
    public static RSAPrivateKey loadPrivateKeyByStr(String privateKeyStr)
            throws Exception {
        try {
            byte[] buffer = Base64.decode(privateKeyStr);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
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
     * 从文件中加载私钥
     *
     * @param path
     *            私钥文件名
     * @return 是否成功
     * @throws Exception
     */
    public static String loadPrivateKeyByFile(String path) throws Exception {
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String readLine = null;
            StringBuilder sb = new StringBuilder();
            while ((readLine = br.readLine()) != null) {
                sb.append(readLine);
            }
            br.close();
            return sb.toString();
        } catch (IOException e) {
            throw new Exception("私钥数据读取错误");
        } catch (NullPointerException e) {
            throw new Exception("私钥输入流为空");
        }
    }

    /**
     * 公钥解密过程
     *
     * @param publicKey
     *            公钥
     * @param cipherData
     *            密文数据
     * @return 明文
     * @throws Exception
     *             解密过程中的异常信息
     */
    public static byte[] decrypt(RSAPublicKey publicKey, byte[] cipherData)
            throws Exception {
        if (publicKey == null) {
            throw new Exception("解密公钥为空, 请设置");
        }
        Cipher cipher = null;
        try {
            // 使用默认RSA
            cipher = Cipher.getInstance("RSA");
            // cipher= Cipher.getInstance("RSA", new BouncyCastleProvider());
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            byte[] output = cipher.doFinal(cipherData);
            return output;
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此解密算法");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidKeyException e) {
            throw new Exception("解密公钥非法,请检查");
        } catch (IllegalBlockSizeException e) {
            throw new Exception("密文长度非法");
        } catch (BadPaddingException e) {
            throw new Exception("密文数据已损坏");
        }
    }

}
