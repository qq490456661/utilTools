package redisUtil;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.*;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by linjunjie(490456661@qq.com) on 2017/2/26.
 */
public class RedisUtil {

    //Redis������IP
    private static String ADDR = "118.178.135.106";

    //Redis�Ķ˿ں�
    private static int PORT = 7000;

    //��������
    private static String AUTH = "qweasdzxc";

    //��������ʵ���������Ŀ��Ĭ��ֵΪ8��
    //�����ֵΪ-1�����ʾ�����ƣ����pool�Ѿ�������maxActive��jedisʵ�������ʱpool��״̬Ϊexhausted(�ľ�)��
    private static int MAX_ACTIVE = 1024;

    //����һ��pool����ж��ٸ�״̬Ϊidle(���е�)��jedisʵ����Ĭ��ֵҲ��8��
    private static int MAX_IDLE = 200;

    //�ȴ��������ӵ����ʱ�䣬��λ���룬Ĭ��ֵΪ-1����ʾ������ʱ����������ȴ�ʱ�䣬��ֱ���׳�JedisConnectionException��
    private static int MAX_WAIT = 10000;

    private static int TIMEOUT = 3000;

    //��borrowһ��jedisʵ��ʱ���Ƿ���ǰ����validate���������Ϊtrue����õ���jedisʵ�����ǿ��õģ�
    private static boolean TEST_ON_BORROW = true;

    /** �Ǽ�Ⱥʱ����� **/
    private static JedisPool jedisPool = null;

    /** ��Ⱥʱʹ����� **/
    private static JedisCluster jedisCluster = null;

    /**
     * ��ʼ��Redis���ӳ�
     */
    static {
        try {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(MAX_ACTIVE);
            config.setMaxIdle(MAX_IDLE);
            config.setMaxWaitMillis(MAX_WAIT);
            config.setTestOnBorrow(TEST_ON_BORROW);

            jedisPool = new JedisPool(config, ADDR, PORT, TIMEOUT, AUTH);
            String host = ADDR;
            Set<HostAndPort> nodes = new LinkedHashSet<HostAndPort>();
            nodes.add(new HostAndPort(host, 7000));
            nodes.add(new HostAndPort(host, 7001));
            nodes.add(new HostAndPort(host, 7002));
            nodes.add(new HostAndPort(host, 7003));
            nodes.add(new HostAndPort(host, 7004));
            nodes.add(new HostAndPort(host, 7005));
            jedisCluster = new JedisCluster(nodes,3000,3000,5,AUTH,config);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized  static JedisCluster getJedisCluster(){
        return jedisCluster;
    }

    /**
     * ��ȡJedisʵ��
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
     * �ͷ�jedis��Դ
     * @param jedis
     */
    public static void returnResource(final Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }



}
