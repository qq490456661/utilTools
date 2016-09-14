package 七牛云存储;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by linjunjie(490456661@qq.com) on 2016/7/10.
 */
public class QiniuUtil {

    private static final Logger logger = Logger.getLogger(QiniuUtil.class);

    private static QiniuUtil qiniuUtils = null;//单例

    //设置好账号的ACCESS_KEY和SECRET_KEY
    private static String ACCESS_KEY = "SqaAAOxVR83xZNA8GK1wXZxGK05xbRX0wrjOIJz6";
    private static String SECRET_KEY = "SZ5KytmCShsuZ5tpccRWd8yrgFlJ7t39mNceCjNo";

    //密钥配置
    private static Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);

    //要上传的空间
    private static final String BUCKET_NAME = "osscache";

    //osscache绑定的外链地址
    private static final String LINK_DOMAIN = "http://o8y9vs86j.bkt.clouddn.com/";

    private QiniuUtil(){

    }

    public static QiniuUtil getInstance(){
        if(qiniuUtils == null) {
            synchronized (QiniuUtil.class) {
                if (qiniuUtils == null) {
                    qiniuUtils = new QiniuUtil();
                }
            }
        }
        return qiniuUtils;
    }

    //简单上传，使用默认策略，只需要设置上传的空间名就可以了
    public String getUpToken(){
        return auth.uploadToken(BUCKET_NAME);
    }

    /**
     * 简单文件上传功能
     * @param fileName 远程云保存的文件名字
     * @param localFilePath 本地服务器文件路径
     * @throws IOException
     */
    public void upload(String fileName,String localFilePath) throws Exception {
        int count = 0;
        while(count++ < 3) {
            try {
                //创建上传对象
                UploadManager uploadManager = new UploadManager();
                //调用put方法上传
                Response res = uploadManager.put(localFilePath, fileName, getUpToken());
                //System.out.println(res.bodyString());
                break;
            } catch (QiniuException e) {
                Response r = e.response;
                logger.error(MessageFormat.format("七牛上传文件失败,fileName:{0}",new Object[]{fileName}),e);
                try {
                    //响应的文本信息
                    System.out.println(r.bodyString());
                } catch (QiniuException e1) {
                    //ignore
                }
            }
        }
    }

    /**
     * 获取下载链接
     */
    public String getDownloadLink(String fileName){
        return auth.privateDownloadUrl(new StringBuilder(LINK_DOMAIN).append(fileName).toString(),3600);//3600是链接有效时间
    }


    /**
     * 获取日期yyyyMMdd
     * @return
     */
    private static int count = 0;
    private static final int MOD = 1000000;
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
    public static String getPreFileNameByDate(){
        return simpleDateFormat.format(new Date())+(count++ % MOD);
    }

    /**
     * 与Spring对接,上传的接口
     * 先存储在upload文件夹，再读取出来存储在千牛云
     * @param pic  MultipartFile是Spring的文件
     * @return 保存在本地文件的名字
     */
    /*
    public String uploadPic(MultipartFile pic)throws Exception{
        int index = pic.getOriginalFilename().lastIndexOf(".");
        StringBuilder fileName = new StringBuilder(index == -1 ? pic.getOriginalFilename():pic.getOriginalFilename().substring(0,index));
        fileName.append(getPreFileNameByDate())
                .append(ContentTypeUtil.getExtension(pic.getContentType()));
        StringBuilder savePath = new StringBuilder(WebContextConstant.webRealPath)
                .append("/resources/upload/")
                .append(fileName);
        File localFile = new File(savePath.toString());
        try {
            pic.transferTo(localFile);
        }catch(Exception e){
            logger.error(MessageFormat.format("本地服务器保存图片失败,fileName:{0},savePath:{1}",new Object[]{fileName,savePath}),e);
        }
        QiniuUtil.getInstance().upload(fileName.toString(),savePath.toString());
        return fileName.toString();
    }
    */


    public static void main(String args[]) throws IOException{

        String u = "http://o8y9vs86j.bkt.clouddn.com/my-java.jpg";
        String downloadRUL = auth.privateDownloadUrl(u);
        System.out.println(downloadRUL);
    }


}
