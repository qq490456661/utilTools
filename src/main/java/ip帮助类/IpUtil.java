/**
 * onway.com Inc.
 * Copyright (c) 2016-2016 All Rights Reserved.
 */
package ip帮助类;

/**
 * @author junjie.lin
 * @version $Id: IpUtil.java, v 0.1 2016/8/23 15:57 junjie.lin Exp $
 */
public class IpUtil {

    public static void main(String[] args) {
        String ip = "103.41.132.34";
        String [] iparr = ip.split("\\.");
        int intip = 0;
        for(String i : iparr){
            intip = intip << 8;  //第一次左移也无意义
            intip = intip | (Integer.valueOf(i) & 255);
            System.out.println(intip);
        }
        System.out.println(intip);

        int[] intarr = new int[4];
        for(int i=0;i<intarr.length;i++){
            intarr[i] = intip & 255;
            intip = intip >>> 8;
        }
        System.out.println(intarr[3]+"."+intarr[2]+"."+intarr[1]+"."+intarr[0]);
    }

}
