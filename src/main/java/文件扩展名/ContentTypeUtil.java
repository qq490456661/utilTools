package �ļ���չ��;

/**
 * Created by linjunjie(490456661@qq.com) on 2016/5/13.
 */
public class ContentTypeUtil {

    /**
     * �����չ��
     * @return
     */
    public static String getExtension(String contentType){
        if("image/png".equals(contentType)){
            return ".png";
        }else if("image/jpeg".equals(contentType)){
            return ".jpg";
        }else if("image/jpg".equals(contentType)){
            return ".jpg";
        }else if("image/gif".equals(contentType)){
            return ".gif";
        }else if("video/mpeg4".equals(contentType)){
            return ".mp4";
        }else if("video/avi".equals(contentType)){
            return ".avi";
        }else if("video/x-ms-wmv".equals(contentType)){
            return ".wmv";
        }
        return "";
    }
}
