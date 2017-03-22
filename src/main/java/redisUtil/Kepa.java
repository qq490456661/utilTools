package redisUtil;

import java.io.Serializable;

/**
 * Created by linjunjie(490456661@qq.com) on 2017/2/26.
 */
public class Kepa implements Serializable {


    private static final long serialVersionUID = -1880410946881931777L;
    private String name ;

    private String certNo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCertNo() {
        return certNo;
    }

    public void setCertNo(String certNo) {
        this.certNo = certNo;
    }
}
