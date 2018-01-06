package HtmlUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Created by linjunjie(490456661@qq.com) on 2016/2/2.
 */
public class StringUtil {

    public static boolean isEmpty(String ... arg){
        return !isNotEmpty(arg);
    }

    public static boolean isNotEmpty(String ... arg){
        for(int i=0;i<arg.length;i++){
            if(arg[i]==null||(arg[i]!=null&&"".equals(arg[i].trim()))){
                return false;
            }
        }
        return true;
    }

    public static boolean isNotNull(String ... arg){
        for(int i=0;i<arg.length;i++){
            if(arg[i] == null){
                return false;
            }
        }
        return true;
    }

    public static boolean isNull(String ...arg){
        return !isNotNull(arg);
    }

    /**
     * 防止xss攻击
     * @param content
     * @return
     */
    public static String xssFilter(String content){
        content =  content.replaceAll("<[/]*script[^>]*>","");
        /*content = content.replaceAll("<script","&ltscript");
        content = content.replaceAll("</script>","&ltscript&gt");*/
        return content;
    }

    /**
     * 过滤掉所有html标记
     * @param content
     * @return
     */
    public static String htmlFilter(String content){
        content = content.replaceAll("<[^>]*>","");
        return content;
    }

    /**
     * 过滤整个类
     * @param obj
     */
    public static void objFilter(Object obj){
        Field[] fields = obj.getClass().getDeclaredFields();
        Object o = null;
        for(Field item : fields){
            try {
                if(Modifier.isFinal(item.getModifiers())){
                    continue;
                }
                item.setAccessible(true);
                o = item.get(obj);
                if(o != null && o instanceof String)
                    item.set(obj,htmlFilter(String.valueOf(o)));
            } catch (IllegalAccessException e) {
                //ignore
            }
        }
    }

    public static void main(String []args){
        /*TrainingInformationUpdateRequest request = new TrainingInformationUpdateRequest();
        request.setAddressDetail("<script> hello 大家好啊 </script>  <img src='' /> ww");
        objFilter(request);
        System.out.println(request.getAddressDetail());*/
    }

}
