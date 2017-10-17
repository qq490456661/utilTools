package tianfu;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * 1.�̻���˽Կ��ʼ��
 * 2.�̻���ǩ�Ĺ�Կ��ʼ��
 * 3.�����̻��ż�������
 * 4.��ǩ���ѷ�������
 * @author 2441240397
 *
 */

public class SecurityUtils {
	/**
	 * ˽Կ ,���ѷ�����̻���
	 */
	public static PrivateKey privateKey;
	/**
	 * ��Կ�����ѵĹ�Կ
	 */
	public static PublicKey publicKey;
	/**
	 * ˽Կ�ļ�·�� �磺D:/rsa/prkey.key
	 */
	private static String privateKeyPath="D:\\prkey.key";
	
	/**
	 * ��Կ�ļ�·�� �磺D:/rsa/pbkey.key
	 */
	private static String publicKeyPath="D:\\pbkey.key";
	
	
	
	static {
		  try {
	            java.security.Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
	        }
	        catch (Exception e) {
	        	e.printStackTrace();
	        	System.out.println("��Կ��ʼ��ʧ��");
	        }
	}
	/**
	 *  init:��ʼ��˽Կ
	 */
	public static void initPrivateKey(){
		try {
				if(privateKey==null){
					privateKey = getPrivateKey(privateKeyPath);
				}
		} catch (Exception e) {
			System.out.println("SecurityUtils��ʼ��ʧ��" + e.getMessage());
			e.printStackTrace();
			System.out.println("��Կ��ʼ��ʧ��");
		}
	}
	/**
	 * ��ʼ����Կ
	 */
	public static void initPublicKey(){
		try {
			if(publicKey==null){
				publicKey = getPublicKey(publicKeyPath);
			}
		} catch (Exception e) {
			System.out.println("SecurityUtils��ʼ��ʧ��" + e.getMessage());
			e.printStackTrace();
			System.out.println("��Կ��ʼ��ʧ��");
		}
	}
	/**
	 * �Դ����ַ�������ǩ��
	 * @param inputStr
	 * @return
	 * @ 
	 */
	public static String sign(String inputStr) {
		String result = null;
		  try {
			    if(privateKey==null){
			    	//��ʼ��
			    	initPrivateKey();
			    }
	            byte[] tByte;
	            Signature signature = Signature.getInstance("SHA1withRSA","BC");
	            signature.initSign(privateKey);
	            signature.update(inputStr.getBytes("UTF-8"));
	            tByte = signature.sign();
	            result = Base64.encode(tByte);
	        }
	        catch (Exception e) {
	        	e.printStackTrace();
	        	System.out.println("��Կ��ʼ��ʧ��");
	        }
		return result;
	}
	/**
	 * �Ը��ѷ��ص����ݽ�����ǩ
	 * @param src ������������
	 * @param signValue ��������ǩ��
	 * @return
	 */
	public static boolean verifySign(String src,String signValue) {
		  boolean bool = false;
		  try {
			  	if(publicKey==null){
			  		initPublicKey();
				}
	            Signature signature = Signature.getInstance("SHA1withRSA","BC");
	            signature.initVerify(publicKey);
	            signature.update(src.getBytes("UTF-8"));
	            bool = signature.verify(Base64.decode(signValue));
	        }
	        catch (Exception e) {
	        	e.printStackTrace();
	        	System.out.println("��Կ��ʼ��ʧ��");
	        }
		return bool;
	}
	private static PrivateKey getPrivateKey(String filePath) {
		String base64edKey = readFile(filePath);
		KeyFactory kf;
		PrivateKey privateKey = null;
		try {
			kf = KeyFactory.getInstance("RSA", "BC");
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.decode(base64edKey));
			privateKey = kf.generatePrivate(keySpec);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("��Կ��ʼ��ʧ��");
		}
		return privateKey;
	}
	private static PublicKey getPublicKey(String filePath){
		String base64edKey = readFile(filePath);
		KeyFactory kf;
		PublicKey   publickey = null;
		try {
			kf = KeyFactory.getInstance("RSA", "BC"); 
			X509EncodedKeySpec   keySpec   =   new   X509EncodedKeySpec(Base64.decode(base64edKey));
			publickey   =   kf.generatePublic(keySpec);   
		 } catch (Exception e) {
			e.printStackTrace();
			System.out.println("��Կ��ʼ��ʧ��");
		}
		return publickey;
	}
	private static String readFile(String fileName) {
      try {
      	File f = new File(fileName);
          FileInputStream in = new FileInputStream(f);
          int len = (int)f.length();
          
          byte[] data = new byte[len];
          int read = 0;
          while (read <len) {
              read += in.read(data, read, len-read);
          }
          in.close();
          return new String(data);
      } catch (IOException e) {
          return null;
      }
  }
}
