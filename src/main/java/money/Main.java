package money;

import com.alibaba.fastjson.JSONObject;
import net.sf.json.JSON;
import net.sf.json.xml.XMLSerializer;
import org.apache.commons.lang.time.DateUtils;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by linjunjie(490456661@qq.com) on 2016/8/16.
 */
public class Main {

    public static void main(String[] args)throws Exception {
        Money money = new Money(1000000.5558);
        System.out.println(money.toLevelString());
        System.out.println(money.toIntMoney());

        System.out.println(new BigDecimal(-100.5).setScale(0,BigDecimal.ROUND_HALF_DOWN));

        BigDecimal bigDecimal = new BigDecimal(11510.0512,new MathContext(4));
        System.out.println(bigDecimal);

        Integer a = 1;
        Integer b = 1;
        System.out.println(a==1);
        Long l1 = 130L;
        Long l2 = 130L;
        System.out.println(l1 == l2);
       /* String xml = "<?xml version=\\\"1.0\\\" encoding=\\\"UTF-8\\\" standalone=\\\"no\\\"?><RESPONSE><VERSION>2.0</VERSION><RESPONSECODE>10M2</RESPONSECODE><RESPONSEMSG>超出借记卡同商户当月累计金额限制</RESPONSEMSG><MCHNTORDERID>2820161112000344</MCHNTORDERID><ORDERID>16111218170703148587</ORDERID><AMT>200</AMT><BANKCARD>6214********5525</BANKCARD><REM1>1.5</REM1><REM2/><REM3/><SIGNTP>MD5</SIGNTP><MCHNTCD>0003310F0323516</MCHNTCD><SIGN>205ca992e44287af42430ece4dcf1c00</SIGN><TYPE>02</TYPE><VER>sdk2.0</VER></RESPONSE>";
        String s = xmlToJsonString(xml);
        System.out.println(s);*/


    }

    public static String xmlToJsonString(String xmlStr){
        XMLSerializer xmlSerializer = new XMLSerializer();
        xmlStr = xmlStr.substring(xmlStr.indexOf("?>")+2);
        JSON json = xmlSerializer.read(xmlStr);
        return JSONObject.toJSONString(json);
    }

}
