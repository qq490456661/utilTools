package DatabaseParse;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by linjunjie(490456661@qq.com) on 2016/7/15.
 */
public class ParseDBInfo {


    public static void main(String[] args) {
        //待解析的数据库表
        String mytext = "CREATE TABLE `pd_product` (\n" +
                "  `ID` int(11) NOT NULL AUTO_INCREMENT,\n" +
                "  `CODE` varchar(20) NOT NULL COMMENT '产品编号',\n" +
                "  `MIN_PURCHASE_AMOUNT` decimal(21,4) NOT NULL COMMENT '最小申购金额',\n" +
                "  `MAX_PURCHASE_AMOUNT` decimal(21,4) DEFAULT '10000000000.0000' COMMENT '最大申购金额',\n" +
                "  `PRODUCT_NAME` varchar(64) NOT NULL COMMENT '产品名称',\n" +
                "  `PRODUCT_TYPE` varchar(10) DEFAULT '' COMMENT '产品类别:聚财,合盘贷等',\n" +
                "  `COLOCATION_BANK` varchar(16) DEFAULT NULL COMMENT '产品托管行',\n" +
                "  `CHARGE_FEE` decimal(21,4) DEFAULT '0.0000' COMMENT '手续费',\n" +
                "  `BOND_FEE` decimal(21,4) DEFAULT '0.0000' COMMENT '@desc 保证金金额比例',\n" +
                "  `PRODUCT_INFO` text COMMENT '产品说明',\n" +
                "  `LOANER_INFO` text COMMENT '借款人说明',\n" +
                "  `SECURITY_INFO` text COMMENT '资金安全说明',\n" +
                "  `STATUS` varchar(16) DEFAULT NULL COMMENT '产品展示状态  enable:展示  unabled:不展示 ',\n" +
                "  `NHSY` double(10,4) DEFAULT '0.0000' COMMENT '年化收益',\n" +
                "  `SGBZ` varchar(16) DEFAULT NULL COMMENT '申购标识  YES:可申购  NO:不可申购',\n" +
                "  `SECURITY_LEVEL` varchar(16) DEFAULT NULL COMMENT '安全级别',\n" +
                "  `RELEASE_CHANNEL` varchar(16) NOT NULL COMMENT '发布渠道（APP：app渠道，PC：pc渠道，NOT DIFF：不区分）',\n" +
                "  `PURCHASE_LEVEL` varchar(10) DEFAULT '' COMMENT '购买类别:新手,VIP,无限制等',\n" +
                "  `GMT_CREATE` datetime DEFAULT NULL COMMENT '创建日期',\n" +
                "  `GMT_MODIFIED` datetime DEFAULT NULL COMMENT '修改日期',\n" +
                "  `MEMO` varchar(128) DEFAULT NULL COMMENT '备注',\n" +
                "  PRIMARY KEY (`ID`),\n" +
                "  UNIQUE KEY `id_pd_code` (`CODE`) USING BTREE,\n" +
                "  KEY `id_pd_name` (`PRODUCT_NAME`) USING BTREE\n" +
                ") ENGINE=InnoDB AUTO_INCREMENT=16939 DEFAULT CHARSET=utf8 COMMENT='产品基本信息表';\n" +
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
