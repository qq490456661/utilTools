/**
 * onway.com Inc.
 * Copyright (c) 2017-2017 All Rights Reserved.
 */
package tianfu;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author junjie.lin
 * @version $Id: ServiceTest.java, v 0.1 2017/7/4 16:03 junjie.lin Exp $
 */
public class ServiceTest {



    public static void main(String[] args) {

        /**建立请求 ..req**/
        HttpClient httpClient = HttpConnectionManager.getHttpClient();
        ContentType contentType = ContentType.create(HTTP.PLAIN_TEXT_TYPE, HTTP.UTF_8);
        HttpPost httpPost = new HttpPost("http://cg.zjcgxt.com/jzh/reg.action");
        MultipartEntityBuilder multipartEntityBuilder =  MultipartEntityBuilder.create();
        /*if(request.getFileAttachments() != null && request.getFileAttachments().size() > 0){
            *//** 有上传文件 **//*
            List<File> files = request.getFileAttachments();
            for(File file:files){
                multipartEntityBuilder.addBinaryBody(file.getName(),file, ContentType.MULTIPART_FORM_DATA,file.getName());
            }
        }*/
        /** 将数据封装 **/
        Map<String,String> allParams = new HashMap<String,String>();
        allParams.put("ver","0.44");
        allParams.put("mchnt_cd","0002900F0342307");
        String serino = UUID.randomUUID().toString().replaceAll("-","").substring(0, 28);
        allParams.put("mchnt_txn_ssn", serino);
        allParams.put("cust_nm", "林杰");
        allParams.put("certif_tp", "0");
        allParams.put("certif_id", "431025199403280018");
        allParams.put("mobile_no", "15867121265");
        allParams.put("city_id", "6530");
        allParams.put("parent_bank_id", "0308");
        allParams.put("capAcntNo", "6214850212331631");
        String src = "||6214850212331631|431025199403280018|6530|林杰|||0002900F0342307|"+serino+"|15867121265|0308|||0.44";
        System.out.println(SecurityUtils.sign(src));
        allParams.put("signature", SecurityUtils.sign(src));
        for(Map.Entry<String,String> entry:allParams.entrySet()){
            if(StringUtils.isNotBlank(entry.getKey())&&StringUtils.isNotEmpty(entry.getValue())){
                multipartEntityBuilder.addTextBody(entry.getKey(),entry.getValue(),contentType);

            }
        }

        httpPost.setEntity(multipartEntityBuilder.build());

        /** 发送请求 **/
        try {
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            //requestParametersHolder.setResponseBody(EntityUtils.toString(httpEntity));
            System.out.println("内容为："+EntityUtils.toString(httpEntity,"UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
