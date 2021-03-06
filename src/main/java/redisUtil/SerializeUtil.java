package redisUtil;

import java.io.*;

/**
 * Created by linjunjie(490456661@qq.com) on 2017/2/26.
 */
public class SerializeUtil {

    /**
     * 序列化
     * @param object
     * @return
     */
    public static byte[] serialize(Object object) {
        if(object == null){
            return null;
        }
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        try {
            //序列化
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            byte[] bytes = baos.toByteArray();
            return bytes;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 反序列化
     * @param bytes
     * @return
     */
    public static Object unserialize(byte[] bytes) {
        if(bytes == null){
            return null;
        }
        ByteArrayInputStream bais = null;
        ObjectInputStream    ois  = null;
        try {
            //反序列化
            bais = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bais);

            return ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

}
