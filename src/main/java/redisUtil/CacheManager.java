package redisUtil;

/**
 * Created by linjunjie(490456661@qq.com) on 2017/2/26.
 */
public interface CacheManager {

    /** set 操作 */
    public Boolean set(String key,String value);

    public Boolean set(String key,String value,int expire);

    public Boolean set(String key,Object value);

    public Boolean set(String key,Object value,int expire);

    /** get 操作*/
    public String getString(String key);

    public Object getObject(String key);

    public <T>T get(String key,Class<T> className);


    /** remove 操作*/
    public Boolean remove(String key);

    /** 是否存在*/
    public Boolean exists(String key);

}
