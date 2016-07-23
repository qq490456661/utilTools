package pojoConvert;

import java.io.Serializable;

/**
 * Created by linjunjie(490456661@qq.com) on 2016/7/22.
 */
public class SysConfigPojo implements Serializable{

    private static final long serialVersionUID = -4661464239298036969L;
    private Long id;

    private String key;

    private String value;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
