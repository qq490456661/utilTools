/**
 * onway.com Inc.
 * Copyright (c) 2018-2018 All Rights Reserved.
 */
package voiceRecognition;

import com.baidu.aip.speech.AipSpeech;
import org.json.JSONObject;

/**
 * @author junjie.lin
 * @version $Id: VoiceTest.java, v 0.1 2018/1/22 0022 17:38 junjie.lin Exp $
 */
public class VoiceTest {

    //����APPID/AK/SK
    public static final String APP_ID = "10730249";
    public static final String API_KEY = "Z443gDujvOxU4jT6h1qGlS4r";
    public static final String SECRET_KEY = "7SbPja8qcPxwNykcGZCMknwmTsjfHNmj";

    public static void main(String[] args) {

        // ��ʼ��һ��AipSpeech
        AipSpeech client = new AipSpeech(APP_ID, API_KEY, SECRET_KEY);

        // ��ѡ�������������Ӳ���
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // ���ýӿ�
        JSONObject res = client.asr("D:\\1.wav", "wav", 16000, null);
        System.out.println(res.toString(2));

    }


}
