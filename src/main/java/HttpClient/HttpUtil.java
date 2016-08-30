package HttpClient;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by linjunjie(490456661@qq.com) on 2016/8/30.
 */
public class HttpUtil {

    public static void main(String[] args) {
        httpGet();
    }

    /**
     * get��������ͼƬ
     */
    public static void httpGet(){
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try{
            //����httpget
            //HttpGet httpget = new HttpGet("http://rs.xidian.edu.cn/forum.php");
            HttpGet httpget = new HttpGet("http://static.qichechaoren.com/thumb/twl/logo/DS.png");
            System.out.println("executing request " + httpget.getURI());
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("wd","wq"));
            String str = EntityUtils.toString(new UrlEncodedFormEntity(params));
            httpget.setURI(new URI(httpget.getURI().toString() +"?" + str));

            //ִ��get����
            CloseableHttpResponse response = httpclient.execute(httpget);
            try{
                //��ȡ��Ӧʵ��
                HttpEntity entity = response.getEntity();
                //��Ӧ״̬
                System.out.println(response.getStatusLine());
                if(entity != null){
                    //���ݳ���
                    System.out.println("Response content length: " + entity.getContentLength());
                    //��Ӧ����
                    //System.out.println("Response content: " + EntityUtils.toString(entity));
                    saveLocal(new ByteArrayInputStream(EntityUtils.toByteArray(entity)),"D://DS.png");
                }
            }finally{
                response.close();
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                httpclient.close();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    //�����ַ
    public static void saveLocal(InputStream in,String localPath){
        OutputStream os = null;
        byte[] b = new byte[2048];
        try {
            int len = -1;
            os = new FileOutputStream(new File(localPath));
            while((len = in.read(b))!=-1){
                os.write(b,0,len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try{
                if(os != null)
                    os.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }

    }
}
