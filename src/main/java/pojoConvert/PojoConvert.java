package pojoConvert;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;

/**
 * Created by linjunjie(490456661@qq.com) on 2016/7/22.
 */
public class PojoConvert {



    public static void main(String[] args) {
        SysConfigInfo sysConfigInfo = new SysConfigInfo();
        SysConfigPojo sysConfigPojo = new SysConfigPojo();
        convert(sysConfigInfo, sysConfigPojo);
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setRoundingMode(RoundingMode.HALF_EVEN);
        numberFormat.setMaximumFractionDigits(2);
        String num = numberFormat.format(303.235);
        System.out.println(num);





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
