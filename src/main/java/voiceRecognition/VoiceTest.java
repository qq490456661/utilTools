/**
 * onway.com Inc.
 * Copyright (c) 2018-2018 All Rights Reserved.
 */
package voiceRecognition;

import MD5.MD5Util;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author junjie.lin
 * @version $Id: VoiceTest.java, v 0.1 2018/1/22 0022 17:38 junjie.lin Exp $
 */
public class VoiceTest {

    //????APPID/AK/SK
    public static final String APP_ID = "10730249";
    public static final String API_KEY = "Z443gDujvOxU4jT6h1qGlS4r";
    public static final String SECRET_KEY = "7SbPja8qcPxwNykcGZCMknwmTsjfHNmj";

    public static void main(String[] args) {
        final HashMap<String,String> map = new HashMap<String, String>();
        String st = "100000000000";

//        String startTime = "2018-04-10 12:40";
//        String endTime = "2018-08-30 15:20";
//        Date endTimeDate = null;
//        Calendar c = Calendar.getInstance();
//        Calendar c2 = Calendar.getInstance();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
//        try {
//            c.setTime(sdf.parse(startTime));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        try {
//            endTimeDate = sdf.parse(endTime);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        Date firstDate = c.getTime();
//
//        while(true){
//            c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE) + 1, 0, 0, 0);
//
//            Date temp = c.getTime();
//            if(temp.after(endTimeDate)){
//                double hour = (double)Math.round((endTimeDate.getTime() - firstDate.getTime())/1000.0/36.0)/100.0;
//                System.out.println(sdf2.format(firstDate.getTime())+"---间隔小时："+hour);
//                break;
//            }
//            double hour = (double)Math.round((temp.getTime() - firstDate.getTime())/1000.0/36.0)/100.0;
//
//            System.out.println(sdf2.format(firstDate.getTime())+"---间隔小时："+hour);
//            firstDate = temp;
//
//        }
        TreeMap<String,String> treeMap = new TreeMap<String, String>();
        treeMap.put("userId","");
        treeMap.put("token","3FA58F14-83F7-4DC2-9E5C-AF3BFC5906B6");
        treeMap.put("version","1.1.5");
        treeMap.put("appType","IOS");
        treeMap.put("sign","");
        treeMap.put("stime","1523849596655");

        StringBuilder sb = new StringBuilder("DONGFANG|").append("ANDROID").append("|")
                .append("1524032375293").append("|").append("866592032157504").append("|").append("").append("|").append("1.1.5");
        System.out.println(sb.toString());
        String k = null;
        k = MD5Util.encodeByMD5(MD5Util.encodeByMD5(sb.toString()));
        System.out.println(k);
    }


}
