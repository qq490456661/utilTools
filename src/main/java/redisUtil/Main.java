package redisUtil;

import com.alibaba.fastjson.JSON;
import com.google.gson.JsonObject;
import org.apache.commons.lang.time.DateUtils;
import redis.clients.jedis.Jedis;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by linjunjie(490456661@qq.com) on 2016/11/29.
 */
public class Main {


    public static void main(String[] args) {
       /* String startTime = "2016-11-38";
        String endTime = "2016-11-35";
        String tar = "2016-11-29 20:00:00";
        System.out.println(endTime.compareTo(startTime));
        System.out.println(endTime.compareTo(tar));
        System.out.println(tar.compareTo(endTime));
*/

        //����redis��������192.168.0.100:6379
       /* Jedis jedis = new Jedis("118.184.52.11",19000);
        System.out.println(jedis.get("name"));*/

        /** Jedis����*/
       /* RedisCacheManager redisCacheManager = new RedisCacheManager();
        Kepa kepa = new Kepa();
        kepa.setCertNo("431024343445984999XXXpdpsappfpdf000WPPREOFODFDJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJK00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000888");
        kepa.setName("�ֿ���");
        redisCacheManager.set("flag",kepa);

        Kepa temp = redisCacheManager.get("flag",Kepa.class);
        //Kepa temp = (Kepa)redisCacheManager.getObject("xx");
        System.out.println(temp);
        System.out.println(temp.getName());
        System.out.println(temp.getCertNo());*/

        /*//���ܵ�
        Calendar weekCurrent = Calendar.getInstance();
        weekCurrent.set(weekCurrent.get(Calendar.YEAR), weekCurrent.get(Calendar.MONTH), weekCurrent.get(Calendar.DATE) - 1, 0, 0, 0);
        weekCurrent.set(Calendar.MILLISECOND, 0);
        //����һ0��
        int day = weekCurrent.get(Calendar.DAY_OF_WEEK);
        System.out.println(day);

        weekCurrent.set(Calendar.DAY_OF_WEEK, weekCurrent.getFirstDayOfWeek());
        System.out.println("����ĩ");
        weekCurrent.add(Calendar.DATE, 1);
        Date firstDay = weekCurrent.getTime();
        //��һ������һ0��
        weekCurrent.add(Calendar.DATE, 7);
        Date lastDay = weekCurrent.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(sdf.format(firstDay));
        System.out.println(sdf.format(lastDay));*/


        //���ܵ�
        Calendar weekCurrent = Calendar.getInstance();
        weekCurrent.set(weekCurrent.get(Calendar.YEAR), weekCurrent.get(Calendar.MONTH), weekCurrent.get(Calendar.DATE), 0, 0, 0);
        weekCurrent.set(Calendar.MILLISECOND, 0);

        int k = weekCurrent.get(Calendar.DAY_OF_WEEK);
        System.out.println(k);
        //����һ0��
        if(k == 1) {
            weekCurrent.add(Calendar.DATE, -6);
        }else{
            weekCurrent.add(Calendar.DATE, 2-k);
        }
        Date firstDay = weekCurrent.getTime();
        //��һ������һ0��
        weekCurrent.add(Calendar.DATE, 7);
        Date lastDay = weekCurrent.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(sdf.format(firstDay));
        System.out.println(sdf.format(lastDay));



        Long a = 128L,b = 128L;
        System.out.println(a==b);

    }
}
