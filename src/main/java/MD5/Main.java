/**
 * onway.com Inc.
 * Copyright (c) 2016-2016 All Rights Reserved.
 */
package MD5;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.time.DateUtils;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author junjie.lin
 * @version $Id: Main.java, v 0.1 2016/12/15 13:52 junjie.lin Exp $
 */
public class Main {

    public static void main(String[] args)throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = sdf.parse("2017-01-30");

        Calendar c = Calendar.getInstance();
        c.setTime(date1);
        c.add(Calendar.MONTH, 1);
        c.add(Calendar.DAY_OF_MONTH,-1);
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(c.getTime()));
        Date date2 = c.getTime();

        Calendar c1 = new GregorianCalendar();
        c1.setTime(date1);
        Calendar c2 = new GregorianCalendar();
        c2.setTime(date2);

        System.out.println("间隔天数"+ (date2.getTime() - date1.getTime()) / (1000*24*60*60) );

        System.out.println("间隔天数"+ (c2.getTimeInMillis() - c1.getTimeInMillis()) / (1000*24*60*60) );

        for(int i=1;i<=28;i++){
            if(i<10) {
                date1 = sdf.parse("2017-02-0"+i);
            }else{
                date1 = sdf.parse("2017-02-"+i);
            }
            c = Calendar.getInstance();
            c.setTime(date1);
            c.add(Calendar.MONTH, 2);
            c.add(Calendar.DAY_OF_MONTH,-1);
            System.out.println((c.getTimeInMillis() - date1.getTime() )/ (1000*24*60*60) + 1 +"天");
        }

        Map<String,String> map = new HashMap<String,String>();
        map.put("","");
        /*String mall_id = "110684";
        String realname = "陈优优";
        String cardnum = "6227001487520179483";
        String idcard = "331023199203025842";
        String bankPreMobile = "15268871327";
        String tm = ""+System.currentTimeMillis();
        String appkey = "711d7a395221d8058c2813a7095d7af5";
        StringBuilder str = new StringBuilder();
        str.append(mall_id)
                .append(realname)
                .append(cardnum)
                .append(tm)
                .append(appkey);
        String sign = MD5Util.encodeByMD5(str.toString());
        StringBuilder requestPath = new StringBuilder("http://121.41.42.121:8080/v3/card4-server?")
                .append("mall_id=").append(mall_id).append("&")
                .append("realname=").append(realname).append("&")
                .append("cardnum=").append(cardnum).append("&")
                .append("bankPreMobile=").append(bankPreMobile).append("&")
                .append("idcard=").append(idcard).append("&")
                .append("tm=").append(tm).append("&")
                .append("sign=").append(sign);
        System.out.println(requestPath.toString());


        Pattern ratePattern = Pattern.compile("[0-9.]+%");
        String context = "加息6.8%嗨翻全场";
        Matcher rateMatcher = ratePattern.matcher(context);
        if(rateMatcher.find()) {
            System.out.println(rateMatcher.group());
        }*/
    }

}
