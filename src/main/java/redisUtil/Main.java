package redisUtil;

import com.google.gson.JsonObject;
import redis.clients.jedis.Jedis;

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

        //连接redis服务器，192.168.0.100:6379
       /* Jedis jedis = new Jedis("118.184.52.11",19000);
        System.out.println(jedis.get("name"));*/

        /** Jedis测试*/
        RedisCacheManager redisCacheManager = new RedisCacheManager();
//        Kepa kepa = new Kepa();
//        kepa.setCertNo("bfg");
//        kepa.setName("林俊杰");
//        redisCacheManager.set("flag",kepa);
//        Kepa temp = redisCacheManager.get("flag",Kepa.class);
//        Kepa temp = (Kepa)redisCacheManager.getObject("xx");
//        System.out.println(temp);
//        System.out.println(temp.getName());
//        System.out.println(temp.getCertNo());
//        redisCacheManager.set("lin","haole");
//        String result = redisCacheManager.getString("lin");
//        System.out.println(result);
        Jedis jedis = RedisUtil.getJedis();
        jedis.set("lin","henhao");
        String result = jedis.get("lin");
        System.out.println(result);
    }
}
