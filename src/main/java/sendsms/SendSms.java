/**
 * onway.com Inc.
 * Copyright (c) 2017-2017 All Rights Reserved.
 */
package sendsms;


import org.apache.http.HttpException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import java.io.IOException;

/**
 * @author junjie.lin
 * @version $Id: SendSms.java, v 0.1 2017/12/18 0018 15:18 junjie.lin Exp $
 */
public class SendSms {

    private static String Url = "http://106.ihuyi.cn/webservice/sms.php?method=Submit";

    public static void main(String [] args) {

        HttpClient client = new HttpClient();
        PostMethod method = new PostMethod(Url);

        client.getParams().setContentCharset("GBK");
        method.setRequestHeader("ContentType","application/x-www-form-urlencoded;charset=GBK");

        int mobile_code = (int)((Math.random()*9+1)*100000);

        String content = new String("������֤���ǣ�" + mobile_code + "���벻Ҫ����֤��й¶�������ˡ�");

        NameValuePair[] data = {//�ύ����
                new NameValuePair("account", "C96418540"), //�鿴�û����ǵ�¼�û�����->��֤�����->��Ʒ����->APIID
                new NameValuePair("password", "791682d49b9eb40d71ff956e70460e16"),  //�鿴�������¼�û�����->��֤�����->��Ʒ����->APIKEY
                //new NameValuePair("password", util.StringUtil.MD5Encode("����")),
                new NameValuePair("mobile", "15675205364"),
                new NameValuePair("content", content),
        };
        method.setRequestBody(data);

        try {
            client.executeMethod(method);

            String SubmitResult =method.getResponseBodyAsString();

            //System.out.println(SubmitResult);

            Document doc = DocumentHelper.parseText(SubmitResult);
            Element root = doc.getRootElement();

            String code = root.elementText("code");
            String msg = root.elementText("msg");
            String smsid = root.elementText("smsid");

            System.out.println(code);
            System.out.println(msg);
            System.out.println(smsid);

            if("2".equals(code)){
                System.out.println("�����ύ�ɹ�");
            }

        }catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
