package pojoConvert;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by linjunjie(490456661@qq.com) on 2016/7/22.
 */
public class PojoConvert {

    private static final String[] chars = {"0","1","2","3","4","5","6","7","8","9","A"
    ,"B","C","D","E","F","G","H","I","j","K","L","M","N","O","P","Q","R","S","T","V","W","X","Y","Z"};
    public static String generateShortUuid() {
        StringBuffer shortBuffer = new StringBuffer();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < 6; i++) {
            String str = uuid.substring(i * 2, i * 2 + 2);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(chars[x % chars.length]);
        }
        return shortBuffer.toString();
    }

    public static void main(String[] args) {

        Desktop deskapp = Desktop.getDesktop();
        try {
            File file = new File("D:\\QQ");
            File[] files = file.listFiles();
            for(File f : files){
                deskapp.open(f);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        /*
        SysConfigInfo sysConfigInfo = new SysConfigInfo();
        SysConfigPojo sysConfigPojo = new SysConfigPojo();
        convert(sysConfigInfo, sysConfigPojo);
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setRoundingMode(RoundingMode.HALF_EVEN);
        numberFormat.setMaximumFractionDigits(2);
        String num = numberFormat.format(303.235);
        System.out.println(num);

        String pid = "mm_117988838_16540707_61550074";
        String goodsUrl = "https://detail.tmall.com/item.htm?spm=a1z10.5-b-s.w4011-14523694618.135.HWt45N&id=14825338045&rn=ad669dcc171370be5a8e1154d5c49aa7&abbucket=15&skuId=3194849223223";
        String bonusUrl = "http://uland.taobao.com/coupon/edetail?activityId=eaeee3fee5d34df7bfd968b7051966b2&itemId=539728858594&pid=mm_117988838_17702793_63946751&src=czhk_cztkl";
        StringBuilder value = null;
        if(bonusUrl.indexOf("pid") != -1){

            value = new StringBuilder(bonusUrl.replaceAll("pid=[^&]*",new StringBuilder("pid=").append(pid).toString()));
            System.out.println(bonusUrl);
        }else {
            value = new StringBuilder("http://uland.taobao.com/coupon/edetail?");
            //获取活动id
            Pattern activityPattern = Pattern.compile("activity_id=[^&]*");
            Matcher matcher = activityPattern.matcher(bonusUrl);
            if (matcher.find()) {
                value.append("activityId=").append(matcher.group().split("=")[1]);
            }
            //获取宝贝id
            Pattern itemPattern = Pattern.compile("id=[^&]*");
            Matcher matcher2 = itemPattern.matcher(goodsUrl);
            if (matcher2.find()) {
                value.append("&itemId=").append(matcher2.group().split("=")[1]);
            }
            value.append("&pid=").append(pid);
            System.out.println(value.toString());

        }
        //生成插入码
        String key = generateShortUuid();
        //将key和value插入数据库
        //TODO

        //返回给用户
        StringBuilder links = new StringBuilder("http://1117c.com/coupon/")
                .append(key);
        System.out.println(links);*/
    }

    public static void numberFormat(String amount){
        BigDecimal money = new BigDecimal(amount);

    }

    public static void convert(Object current,Object target) {
        try {
            Class now = current.getClass();
            Class newer = target.getClass();
            char[] chars = null;
            char[] nowname = now.getSimpleName().toCharArray();
            char[] newname = newer.getSimpleName().toCharArray();
            nowname[0] = Character.toLowerCase(nowname[0]);
            newname[0] = Character.toLowerCase(newname[0]);
            System.out.println(newer.getSimpleName()+" "+String.valueOf(nowname)+" = new "+newer.getSimpleName()+"();");
            System.out.println(now.getSimpleName()+" "+String.valueOf(nowname)+" = new "+now.getSimpleName()+"();");
            Field[] fields = now.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                if("serialVersionUID".equals(fields[i].getName())){
                    continue;
                }
                chars = fields[i].getName().toCharArray();
                chars[0] = Character.toUpperCase(chars[0]);
                StringBuilder sbuid = new StringBuilder().append(nowname)
                        .append(".set")
                        .append(String.valueOf(chars))
                        .append("(")
                        .append(newname)
                        .append(".get")
                        .append(String.valueOf(chars))
                        .append("());");
                System.out.println(sbuid.toString());
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void revert(){

    }

}
