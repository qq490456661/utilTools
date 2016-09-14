package ��ţ�ƴ洢;

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

    private static QiniuUtil qiniuUtils = null;//����

    //���ú��˺ŵ�ACCESS_KEY��SECRET_KEY
    private static String ACCESS_KEY = "SqaAAOxVR83xZNA8GK1wXZxGK05xbRX0wrjOIJz6";
    private static String SECRET_KEY = "SZ5KytmCShsuZ5tpccRWd8yrgFlJ7t39mNceCjNo";

    //��Կ����
    private static Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);

    //Ҫ�ϴ��Ŀռ�
    private static final String BUCKET_NAME = "osscache";

    //osscache�󶨵�������ַ
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

    //���ϴ���ʹ��Ĭ�ϲ��ԣ�ֻ��Ҫ�����ϴ��Ŀռ����Ϳ�����
    public String getUpToken(){
        return auth.uploadToken(BUCKET_NAME);
    }

    /**
     * ���ļ��ϴ�����
     * @param fileName Զ���Ʊ�����ļ�����
     * @param localFilePath ���ط������ļ�·��
     * @throws IOException
     */
    public void upload(String fileName,String localFilePath) throws Exception {
        int count = 0;
        while(count++ < 3) {
            try {
                //�����ϴ�����
                UploadManager uploadManager = new UploadManager();
                //����put�����ϴ�
                Response res = uploadManager.put(localFilePath, fileName, getUpToken());
                //System.out.println(res.bodyString());
                break;
            } catch (QiniuException e) {
                Response r = e.response;
                logger.error(MessageFormat.format("��ţ�ϴ��ļ�ʧ��,fileName:{0}",new Object[]{fileName}),e);
                try {
                    //��Ӧ���ı���Ϣ
                    System.out.println(r.bodyString());
                } catch (QiniuException e1) {
                    //ignore
                }
            }
        }
    }

    /**
     * ��ȡ��������
     */
    public String getDownloadLink(String fileName){
        return auth.privateDownloadUrl(new StringBuilder(LINK_DOMAIN).append(fileName).toString(),3600);//3600��������Чʱ��
    }


    /**
     * ��ȡ����yyyyMMdd
     * @return
     */
    private static int count = 0;
    private static final int MOD = 1000000;
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
    public static String getPreFileNameByDate(){
        return simpleDateFormat.format(new Date())+(count++ % MOD);
    }

    /**
     * ��Spring�Խ�,�ϴ��Ľӿ�
     * �ȴ洢��upload�ļ��У��ٶ�ȡ�����洢��ǧţ��
     * @param pic  MultipartFile��Spring���ļ�
     * @return �����ڱ����ļ�������
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
            logger.error(MessageFormat.format("���ط���������ͼƬʧ��,fileName:{0},savePath:{1}",new Object[]{fileName,savePath}),e);
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
