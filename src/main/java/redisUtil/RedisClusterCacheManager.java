package redisUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

/**
 * Created by linjunjie(490456661@qq.com) on 2017/2/26.
 *
 * 正确的做法，应该是捕获异常，返回null
 * 外界捕捉到null后，直接去数据库获取，这样才可以容错。
 */
public class RedisClusterCacheManager implements CacheManager{



    @Override
    public Boolean set(String key, String value) {
        JedisCluster jedis = null;
        try {
            jedis = RedisUtil.getJedisCluster();
            if ("OK".equalsIgnoreCase(jedis.set(key, value))) {
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //RedisUtil.returnResource(jedis);
        }
        //失败 或 异常 都是false
        return false;
    }

    @Override
    public Boolean set(String key, String value, int expire) {
        JedisCluster jedis = null;
        try {
            jedis = RedisUtil.getJedisCluster();
            if ("OK".equalsIgnoreCase(jedis.set(key, value))) {
                jedis.expire(key,expire);
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //RedisUtil.returnResource(jedis);
        }
        return false;
    }

    @Override
    public Boolean set(String key, Object value) {
        JedisCluster jedis = null;
        try {
            jedis = RedisUtil.getJedisCluster();
            if ("OK".equalsIgnoreCase(jedis.set(key.getBytes(), SerializeUtil.serialize(value)))) {
                return true;
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            //RedisUtil.returnResource(jedis);
        }
        return false;
    }

    @Override
    public Boolean set(String key, Object value, int expire) {
        JedisCluster jedis = null;
        try {
            jedis = RedisUtil.getJedisCluster();
            if ("OK".equalsIgnoreCase(jedis.set(key.getBytes(), SerializeUtil.serialize(value)))) {
                jedis.expire(key,expire);
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //RedisUtil.returnResource(jedis);
        }
        return false;
    }

    @Override
    public String getString(String key) {
        JedisCluster jedis = null;
        try {
            jedis = RedisUtil.getJedisCluster();
            return jedis.get(key);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //RedisUtil.returnResource(jedis);
        }
        return null;
    }

    @Override
    public Object getObject(String key) {
        JedisCluster jedis = null;
        try {
            jedis = RedisUtil.getJedisCluster();
            return SerializeUtil.unserialize(jedis.get(key.getBytes()));
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //RedisUtil.returnResource(jedis);
        }
        return null;
    }

    @Override
    public <T> T get(String key, Class<T> className) {
        JedisCluster jedis = null;
        try {
            jedis = RedisUtil.getJedisCluster();
            return (T)SerializeUtil.unserialize(jedis.get(key.getBytes()));
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //RedisUtil.returnResource(jedis);
        }
        return null;
    }

    @Override
    public Boolean remove(String key) {
        JedisCluster jedis = null;
        try {
            jedis = RedisUtil.getJedisCluster();
            /*if (jedis.del(key) > 0) {
                return true;
            }else{
                return false;
            }*/
            jedis.del(key);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //RedisUtil.returnResource(jedis);
        }
        return null;
    }

    @Override
    public Boolean exists(String key) {
        JedisCluster jedis = null;
        try {
            jedis = RedisUtil.getJedisCluster();
            return jedis.exists(key);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //RedisUtil.returnResource(jedis);
        }
        return null;
    }
}
