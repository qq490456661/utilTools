package IpUtil; /**
 * onway.com Inc.
 * Copyright (c) 2016-2016 All Rights Reserved.
 */

/**
 * @author junjie.lin
 * @version $Id: IpUtil.java, v 0.1 2016/8/23 15:57 junjie.lin Exp $
 */
public class IpUtil {

    public static void main(String[] args) {
        String ip = "103.41.132.34";

        long iplong = ipConvertToInt(ip);
        String ipAddress = intToConvertToIp(iplong);
        System.out.println(ipAddress);
        System.out.println(intToConvertToIp(2130706433));


        System.out.println("1.1.4".compareTo("1.1.5"));
    }


    /**
     * �ַ���Ip��ַת��Ϊint����
     * @param ipAddress
     */
    public static long ipConvertToInt(String ipAddress){
        System.out.println("����IP��ַ��:"+ipAddress);
        long intip = -1;
        if(ipAddress == null)
            return intip;
        String[] ips = ipAddress.split("\\.");
        for(String i : ips){
            intip = intip << 8;  //��һ������Ҳ������
            intip = intip | (Integer.valueOf(i) & 255);
        }
        return intip;
    }

    /**
     * intת��ΪIP
     * @param intip
     * @return
     */
    public static String intToConvertToIp(long intip){
        StringBuilder ipAddress = new StringBuilder();
        int[] intarr = new int[4];
        for(int i=0;i<intarr.length;i++){
            intarr[i] = (int)(intip & 255);
            intip = intip >>> 8;
            ipAddress.insert(0,intarr[i]);
            if(i < intarr.length - 1){
                ipAddress.insert(0,".");
            }
        }
        return ipAddress.toString();
    }

}
