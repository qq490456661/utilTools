/**
 * onway.com Inc.
 * Copyright (c) 2018-2018 All Rights Reserved.
 */

import com.google.common.util.concurrent.RateLimiter;

/**
 * @author junjie.lin
 * @version $Id: YoMain.java, v 0.1 2018/6/19 0019 17:59 junjie.lin Exp $
 */
public class YoMain {
    private static final RateLimiter rateLimiter = RateLimiter.create(1);



    public static void main(String[] args) {
        double days = Math.ceil(1/3600.0/1000);
        System.out.println(days);
        doMethod();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        doMethod();


    }


    public static void doMethod(){
        boolean access = false;
        access = rateLimiter.tryAcquire();
        if(access){
            System.out.println("请求成功");
        }else{
            System.out.println("请求失败");
        }

    }
}
