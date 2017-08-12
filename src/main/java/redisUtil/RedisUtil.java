package redisUtil;

import redis.clients.jedis.*;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by linjunjie(490456661@qq.com) on 2017/2/26.
 */
public class RedisUtil {

    //Redis服务器IP
    private static String ADDR = "111.230.100.167";

    //Redis的端口号
    private static int PORT = 7000;

    //访问密码
    private static String AUTH = null;

    //可用连接实例的最大数目，默认值为8；
    //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
    private static int MAX_ACTIVE = 1024;

    //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
    private static int MAX_IDLE = 200;

    //等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
    private static int MAX_WAIT = 10000;

    private static int TIMEOUT = 10000;

    //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
    private static boolean TEST_ON_BORROW = true;

    /** 非集群时用这个 **/
    private static JedisPool jedisPool = null;

    /** 集群时使用这个 **/
    private static JedisCluster jedisCluster = null;

    /**
     * 初始化Redis连接池
     */
    static {
        try {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(MAX_ACTIVE);
            config.setMaxIdle(MAX_IDLE);
            config.setMaxWaitMillis(MAX_WAIT);
            config.setTestOnBorrow(TEST_ON_BORROW);
            jedisPool = new JedisPool(config, ADDR, PORT, TIMEOUT, AUTH);

            Set<HostAndPort> nodes = new LinkedHashSet<HostAndPort>();
            nodes.add(new HostAndPort("111.230.100.167", 7000));
            nodes.add(new HostAndPort("111.230.100.167", 7001));
            nodes.add(new HostAndPort("111.230.100.167", 7002));
            nodes.add(new HostAndPort("111.230.100.167", 6800));
            nodes.add(new HostAndPort("111.230.100.167", 6801));
            nodes.add(new HostAndPort("111.230.100.167", 6802));
            nodes.add(new HostAndPort("111.230.100.167", 6900));
            nodes.add(new HostAndPort("111.230.100.167", 6901));
            nodes.add(new HostAndPort("111.230.100.167", 6902));
            jedisCluster = new JedisCluster(nodes, config);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized  static JedisCluster getJedisCluster(){
        return jedisCluster;
    }

    /**
     * 获取Jedis实例
     * @return
     */
    public synchronized static Jedis getJedis() {
        try {
            if (jedisPool != null) {
                Jedis resource = jedisPool.getResource();
                return resource;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 释放jedis资源
     * @param jedis
     */
    public static void returnResource(final Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }



}
