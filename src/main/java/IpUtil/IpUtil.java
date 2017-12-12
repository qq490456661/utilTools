package IpUtil; /**
 * onway.com Inc.
 * Copyright (c) 2016-2016 All Rights Reserved.
 */

import java.math.BigDecimal;
import java.util.Random;

/**
 * @author junjie.lin
 * @version $Id: IpUtil.java, v 0.1 2016/8/23 15:57 junjie.lin Exp $
 */
public class IpUtil {

    public static void main(String[] args) {
//        String ip = "103.41.132.34";
//
//        long iplong = ipConvertToInt(ip);
//        String ipAddress = intToConvertToIp(iplong);
//        System.out.println(ipAddress);
//        System.out.println(intToConvertToIp(2130706433));
//
//
//        System.out.println("1.1.4".compareTo("1.1.5"));
        double min = 0.1d;
        double max = 10d;
        double sum = 100D;
        int count = 50;

        BigDecimal resultSum = new BigDecimal(0);
        Random r = new Random();
        int shengyu = 49;
        for(int i= 0;i<count ; i++){
            //System.out.println("shengyu="+shengyu);
            double ro = (r.nextDouble() * (max - min - (shengyu * min)) + min) * 100;
            //System.out.println("ro= "+ro);
            double a = Math.round(ro) * 1.0 /100;
            sum -= a;
            if(sum < max){
                max = sum;
            }
            System.out.println("生成的a="+a);
            resultSum = resultSum.add(new BigDecimal(a+""));
            shengyu --;
        }
        System.out.println("resultSum = "+resultSum);

    }


    /**
     * 字符串Ip地址转换为int数字
     * @param ipAddress
     */
    public static long ipConvertToInt(String ipAddress){
        System.out.println("您的IP地址是:"+ipAddress);
        long intip = -1;
        if(ipAddress == null)
            return intip;
        String[] ips = ipAddress.split("\\.");
        for(String i : ips){
            intip = intip << 8;  //第一次左移也无意义
            intip = intip | (Integer.valueOf(i) & 255);
        }
        return intip;
    }

    /**
     * int转化为IP
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
