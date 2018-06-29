package DatabaseParse;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by linjunjie(490456661@qq.com) on 2016/7/15.
 */
public class ParseDBInfo {


    public static void main(String[] args) {

        String s1 = new StringBuilder("计算机").append("软件").toString();
        System.out.println(s1.intern() == s1);

        String s2 = new StringBuilder("ja").append("va").toString();
        System.out.println(s2.intern() == s2);

        //待解析的数据库表
        String mytext = "CREATE TABLE `ord_fund_repay_invest_extra` (\n" +
                "  `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
                "  `invest_no` varchar(32) DEFAULT NULL,\n" +
                "  `fund_code` varchar(32) DEFAULT NULL,\n" +
                "  `prod_name` varchar(64) DEFAULT NULL,\n" +
                "  `user_id` varchar(32) DEFAULT NULL,\n" +
                "  `coupon_add_amount` decimal(24,4) DEFAULT NULL COMMENT '加息券加息金额',\n" +
                "  `coupon_add_desc` varchar(64) DEFAULT NULL COMMENT '加息券描述（5%加息券）',\n" +
                "  `extra_add_amount` decimal(24,4) DEFAULT NULL COMMENT '额外加息金额',\n" +
                "  `extra_add_desc` varchar(64) DEFAULT NULL COMMENT '额外加息描述（2%）',\n" +
                "  `cash_occupy_amount` decimal(24,4) DEFAULT NULL COMMENT '资金占用费',\n" +
                "  `cash_occupy_desc` varchar(64) DEFAULT NULL COMMENT '资金占用费天数（1天）',\n" +
                "  `repurchase_add_amount` decimal(24,4) DEFAULT NULL COMMENT '复投加息金额',\n" +
                "  `repurchase_add_desc` varchar(64) DEFAULT NULL COMMENT '复投加息百分比描述',\n" +
                "  `deduction_bonus_amount` decimal(24,4) DEFAULT NULL COMMENT '抵扣红包金额',\n" +
                "  `deduction_bonus_desc` varchar(64) DEFAULT NULL COMMENT '抵扣红包描述',\n" +
                "  `gmt_create` timestamp NULL DEFAULT NULL,\n" +
                "  `gmt_modified` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,\n" +
                "  `memo` varchar(255) DEFAULT NULL,\n" +
                "  PRIMARY KEY (`id`)\n" +
                ") ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='回款额外收益';\n" +
                "\n";

        //获取所有字段
        String result = init(mytext);

        System.out.println(result);
        // 获取 as 取别名 other_id as otherId
        System.out.println(getAs(result));
        //a.status
        System.out.println(getAAs(result));
        //status
        System.out.println(getFirstUppercase(result));
        //#status#
        System.out.println(getJin(result));
        //#{status}
        System.out.println(getJinMybatis(result));
        //a.status = #status#
        System.out.println(getFieldsEqualsJin(result));
        //private String xxx;
        System.out.println(getMemberProperties(result));


    }

    /**
     * 方法一：获取表中的所有字段
     * @param mytext
     * @return
     */
    public static String init(String mytext){
        int index = -1;
        //由于KEY字段是最后面的索引内容，可以删除掉
        mytext = mytext.substring(0,(index = mytext.indexOf(" KEY ")) == -1 ? mytext.length() : index);

        Pattern pattern = Pattern.compile("(`.*`)*");
        Matcher matcher = pattern.matcher(mytext);

        //System.out.println(mytext.replaceAll("\\?:(`[a-zA-Z_-]*`)",""));
        //System.out.println(matcher.group());

        StringBuilder str = new StringBuilder();
        while(matcher.find()){
            if("".equals(matcher.group())){
                continue;
            }
            str.append(matcher.group().toLowerCase());
        }
        String result = str.toString().replaceAll("``",",");
        result = result.replaceAll("`","");
        return result;
    }

    /**
     * 获取这样的字段( a.id = #id# )
     * @param result
     * @return
     */
    public static String getFieldsEqualsJin(String result){
        String[] strs = result.split(",");
        char[] chars = null;
        StringBuilder sbchar = new StringBuilder();
        int flag = 0;
        for(String item : strs){
            chars = item.toCharArray();
            sbchar.append("a.").append(item);
            if(item.indexOf("_") != -1){
                sbchar.append(" = #");
                for(char c : chars){
                    if(c == '_'){
                        flag = 1;
                    }else{
                        if(flag == 1){
                            flag = 0;
                            sbchar.append(Character.toUpperCase(c));
                        }else{
                            sbchar.append(c);
                        }
                    }
                }
                sbchar.append("#");
            }else{
                sbchar.append(" = #")
                        .append(item)
                        .append("#");
            }
            sbchar.append(",");
        }
        return sbchar.substring(0,sbchar.length()-1).toString();
    }


    //person,user_id as userId,other_id as otherId,created,modified,status
    public static String getAs(String result){
        String[] strs = result.split(",");
        char[] chars = null;
        StringBuilder sbchar = new StringBuilder();
        int flag = 0;
        for(String item : strs){
            chars = item.toCharArray();
            sbchar.append(item);
            if(item.indexOf("_") != -1){
                sbchar.append(" as ");
                for(char c : chars){
                    if(c == '_'){
                        flag = 1;
                    }else{
                        if(flag == 1){
                            flag = 0;
                            sbchar.append(Character.toUpperCase(c));
                        }else{
                            sbchar.append(c);
                        }
                    }
                }
            }
            sbchar.append(",");
        }

        return sbchar.substring(0,sbchar.length()-1).toString();
    }

    public static String getAAs(String result){
        String[] strs = result.split(",");
        char[] chars = null;
        StringBuilder sbchar = new StringBuilder();
        int flag = 0;
        for(String item : strs){
            chars = item.toCharArray();
            sbchar.append("a.").append(item);
            if(item.indexOf("_") != -1){
                sbchar.append(" as ");
                for(char c : chars){
                    if(c == '_'){
                        flag = 1;
                    }else{
                        if(flag == 1){
                            flag = 0;
                            sbchar.append(Character.toUpperCase(c));
                        }else{
                            sbchar.append(c);
                        }
                    }
                }
            }
            sbchar.append(",");
        }

        return sbchar.substring(0,sbchar.length()-1).toString();
    }

    //person;userId;otherId;created;modified;status
    public static String getFirstUppercase(String result){
        String[] strs = result.split(",");
        char[] chars = null;
        StringBuilder sbchar = new StringBuilder();
        int flag = 0;
        for(String item : strs){
            chars = item.toCharArray();
            if(item.indexOf("_") != -1){
                for(char c : chars){
                    if(c == '_'){
                        flag = 1;
                    }else{
                        if(flag == 1){
                            flag = 0;
                            sbchar.append(Character.toUpperCase(c));
                        }else{
                            sbchar.append(c);
                        }
                    }
                }
            }else{
                sbchar.append(item);
            }
            sbchar.append(";");
        }

        return sbchar.substring(0,sbchar.length()-1).toString();
    }

    //获得有#号的
    public static String getJin(String result){
        String[] strs = result.split(",");
        char[] chars = null;
        StringBuilder sbchar = new StringBuilder();
        int flag = 0;
        for(String item : strs){
            chars = item.toCharArray();
            sbchar.append("#");
            if(item.indexOf("_") != -1){
                for(char c : chars){
                    if(c == '_'){
                        flag = 1;
                    }else{
                        if(flag == 1){
                            flag = 0;
                            sbchar.append(Character.toUpperCase(c));
                        }else{
                            sbchar.append(c);
                        }
                    }
                }
            }else{
                sbchar.append(item);
            }
            sbchar.append("#,");
        }

        return sbchar.substring(0,sbchar.length()-1).toString();
    }

    //获得有#{}号的
    public static String getJinMybatis(String result){
        String[] strs = result.split(",");
        char[] chars = null;
        StringBuilder sbchar = new StringBuilder();
        int flag = 0;
        for(String item : strs){
            chars = item.toCharArray();
            sbchar.append("#{");
            if(item.indexOf("_") != -1){
                for(char c : chars){
                    if(c == '_'){
                        flag = 1;
                    }else{
                        if(flag == 1){
                            flag = 0;
                            sbchar.append(Character.toUpperCase(c));
                        }else{
                            sbchar.append(c);
                        }
                    }
                }
            }else{
                sbchar.append(item);
            }
            sbchar.append("},");
        }

        return sbchar.substring(0,sbchar.length()-1).toString();
    }

    //获取 private String xxxx;
    public static String getMemberProperties(String result){
        String[] strs = result.split(",");
        char[] chars = null;
        StringBuilder sbchar = new StringBuilder();
        int flag = 0;
        for(String item : strs){
            sbchar.append("private String ");
            chars = item.toCharArray();
            if(item.indexOf("_") != -1){
                for(char c : chars){
                    if(c == '_'){
                        flag = 1;
                    }else{
                        if(flag == 1){
                            flag = 0;
                            sbchar.append(Character.toUpperCase(c));
                        }else{
                            sbchar.append(c);
                        }
                    }
                }
            }else{
                sbchar.append(item);
            }
            sbchar.append(";\n");
        }

        return sbchar.substring(0,sbchar.length()-1).toString();
    }

}
