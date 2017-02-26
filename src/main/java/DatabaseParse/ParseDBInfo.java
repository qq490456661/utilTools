package DatabaseParse;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by linjunjie(490456661@qq.com) on 2016/7/15.
 */
public class ParseDBInfo {


    public static void main(String[] args) {
        //待解析的数据库表
        String mytext = "CREATE TABLE `shop_order` (\n" +
                "  `id` bigint(20) NOT NULL AUTO_INCREMENT,\n" +
                "  `goods_name` varchar(200) DEFAULT NULL COMMENT '商品信息',\n" +
                "  `goods_num` int(11) DEFAULT NULL COMMENT '本单出售的商品数量',\n" +
                "  `goods_id` varchar(32) DEFAULT NULL COMMENT '商品id',\n" +
                "  `goods_category` varchar(32) DEFAULT NULL COMMENT '类目名称',\n" +
                "  `shop_name` varchar(32) DEFAULT NULL COMMENT '店铺名称',\n" +
                "  `shop_owner` varchar(32) DEFAULT NULL COMMENT '店铺掌柜',\n" +
                "  `shop_platform` varchar(8) DEFAULT NULL COMMENT '店铺平台：天猫，淘宝',\n" +
                "  `income_rate` decimal(24,2) DEFAULT NULL COMMENT '收入比率',\n" +
                "  `share_rate` decimal(24,2) DEFAULT NULL COMMENT '分成比率',\n" +
                "  `pay_price` decimal(24,2) DEFAULT NULL COMMENT '付款金额',\n" +
                "  `pay_evaluate` decimal(24,2) DEFAULT NULL COMMENT '效果预估',\n" +
                "  `settle_price` decimal(24,2) DEFAULT NULL COMMENT '结算金额',\n" +
                "  `settle_income` decimal(24,2) DEFAULT NULL COMMENT '预估结算收入(结工资按此字段来结)',\n" +
                "  `order_no` varchar(32) DEFAULT '' COMMENT '订单号',\n" +
                "  `order_status` varchar(16) DEFAULT NULL COMMENT '订单状态 （这是淘宝给我们的状态，订单付款，订单失效，订单结算）',\n" +
                "  `order_create` timestamp NULL DEFAULT NULL COMMENT '订单创建时间',\n" +
                "  `settle_time` timestamp NULL DEFAULT NULL COMMENT '订单结算时间',\n" +
                "  `settle_status` smallint(6) DEFAULT NULL COMMENT '结算状态  0未结算 1已结算  （给代理发钱时标记为1）',\n" +
                "  `billboard_id` varchar(16) DEFAULT NULL COMMENT '广告位id',\n" +
                "  `billboard_name` varchar(32) DEFAULT NULL COMMENT '广告位名字',\n" +
                "  `pay_platform` varchar(8) DEFAULT NULL COMMENT '成交平台 无线，pc',\n" +
                "  `created` timestamp NULL DEFAULT NULL,\n" +
                "  `modified` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,\n" +
                "  `status` smallint(6) DEFAULT NULL,\n" +
                "  PRIMARY KEY (`id`)\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=latin1;\n" +
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
        mytext = mytext.substring(0,(index = mytext.indexOf("KEY")) == -1 ? mytext.length() : index);

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
