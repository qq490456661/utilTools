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
     * get请求下载图片
     */
    public static void httpGet(){
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try{
            //创建httpget
            //HttpGet httpget = new HttpGet("http://rs.xidian.edu.cn/forum.php");
            HttpGet httpget = new HttpGet("http://static.qichechaoren.com/thumb/twl/logo/DS.png");
            System.out.println("executing request " + httpget.getURI());
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("wd","wq"));
            String str = EntityUtils.toString(new UrlEncodedFormEntity(params));
            httpget.setURI(new URI(httpget.getURI().toString() +"?" + str));

            //执行get请求
            CloseableHttpResponse response = httpclient.execute(httpget);
            try{
                //获取响应实体
                HttpEntity entity = response.getEntity();
                //响应状态
                System.out.println(response.getStatusLine());
                if(entity != null){
                    //内容长度
                    System.out.println("Response content length: " + entity.getContentLength());
                    //响应内容
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

    //保存地址
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
