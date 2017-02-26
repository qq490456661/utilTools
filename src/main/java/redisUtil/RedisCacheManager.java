package redisUtil;

import redis.clients.jedis.Jedis;

/**
 * Created by linjunjie(490456661@qq.com) on 2017/2/26.
 *
 * ��ȷ��������Ӧ���ǲ����쳣������null
 * ��粶׽��null��ֱ��ȥ���ݿ��ȡ�������ſ����ݴ�
 */
public class RedisCacheManager implements CacheManager{



    @Override
    public Boolean set(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = RedisUtil.getJedis();
            if ("OK".equalsIgnoreCase(jedis.set(key, value))) {
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            RedisUtil.returnResource(jedis);
        }
        //ʧ�� �� �쳣 ����false
        return false;
    }

    @Override
    public Boolean set(String key, String value, Long expire) {
        Jedis jedis = null;
        try {
            jedis = RedisUtil.getJedis();
            if ("OK".equalsIgnoreCase(jedis.set(key, value, "", "", expire))) {
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            RedisUtil.returnResource(jedis);
        }
        return false;
    }

    @Override
    public Boolean set(String key, Object value) {
        Jedis jedis = null;
        try {
            jedis = RedisUtil.getJedis();
            if ("OK".equalsIgnoreCase(jedis.set(key.getBytes(), SerializeUtil.serialize(value)))) {
                return true;
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            RedisUtil.returnResource(jedis);
        }
        return false;
    }

    @Override
    public Boolean set(String key, Object value, Long expire) {
        Jedis jedis = null;
        try {
            jedis = RedisUtil.getJedis();
            if ("OK".equalsIgnoreCase(jedis.set(key.getBytes(), SerializeUtil.serialize(value), null, null, expire))) {
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            RedisUtil.returnResource(jedis);
        }
        return false;
    }

    @Override
    public String getString(String key) {
        Jedis jedis = null;
        try {
            jedis = RedisUtil.getJedis();
            return jedis.get(key);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            RedisUtil.returnResource(jedis);
        }
        return null;
    }

    @Override
    public Object getObject(String key) {
        Jedis jedis = null;
        try {
            jedis = RedisUtil.getJedis();
            return SerializeUtil.unserialize(jedis.get(key.getBytes()));
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            RedisUtil.returnResource(jedis);
        }
        return null;
    }

    @Override
    public <T> T get(String key, Class<T> className) {
        Jedis jedis = null;
        try {
            jedis = RedisUtil.getJedis();
            return (T)SerializeUtil.unserialize(jedis.get(key.getBytes()));
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            RedisUtil.returnResource(jedis);
        }
        return null;
    }

    @Override
    public Boolean remove(String key) {
        Jedis jedis = null;
        try {
            jedis = RedisUtil.getJedis();
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
            RedisUtil.returnResource(jedis);
        }
        return null;
    }

    @Override
    public Boolean exists(String key) {
        Jedis jedis = null;
        try {
            jedis = RedisUtil.getJedis();
            return jedis.exists(key);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            RedisUtil.returnResource(jedis);
        }
        return null;
    }
}
