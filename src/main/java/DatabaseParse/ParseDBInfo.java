package DatabaseParse;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by linjunjie(490456661@qq.com) on 2016/7/15.
 */
public class ParseDBInfo {


    public static void main(String[] args) {
        //�����������ݿ��
        String mytext = "CREATE TABLE `pd_product` (\n" +
                "  `ID` int(11) NOT NULL AUTO_INCREMENT,\n" +
                "  `CODE` varchar(20) NOT NULL COMMENT '��Ʒ���',\n" +
                "  `MIN_PURCHASE_AMOUNT` decimal(21,4) NOT NULL COMMENT '��С�깺���',\n" +
                "  `MAX_PURCHASE_AMOUNT` decimal(21,4) DEFAULT '10000000000.0000' COMMENT '����깺���',\n" +
                "  `PRODUCT_NAME` varchar(64) NOT NULL COMMENT '��Ʒ����',\n" +
                "  `PRODUCT_TYPE` varchar(10) DEFAULT '' COMMENT '��Ʒ���:�۲�,���̴���',\n" +
                "  `COLOCATION_BANK` varchar(16) DEFAULT NULL COMMENT '��Ʒ�й���',\n" +
                "  `CHARGE_FEE` decimal(21,4) DEFAULT '0.0000' COMMENT '������',\n" +
                "  `BOND_FEE` decimal(21,4) DEFAULT '0.0000' COMMENT '@desc ��֤�������',\n" +
                "  `PRODUCT_INFO` text COMMENT '��Ʒ˵��',\n" +
                "  `LOANER_INFO` text COMMENT '�����˵��',\n" +
                "  `SECURITY_INFO` text COMMENT '�ʽ�ȫ˵��',\n" +
                "  `STATUS` varchar(16) DEFAULT NULL COMMENT '��Ʒչʾ״̬  enable:չʾ  unabled:��չʾ ',\n" +
                "  `NHSY` double(10,4) DEFAULT '0.0000' COMMENT '�껯����',\n" +
                "  `SGBZ` varchar(16) DEFAULT NULL COMMENT '�깺��ʶ  YES:���깺  NO:�����깺',\n" +
                "  `SECURITY_LEVEL` varchar(16) DEFAULT NULL COMMENT '��ȫ����',\n" +
                "  `RELEASE_CHANNEL` varchar(16) NOT NULL COMMENT '����������APP��app������PC��pc������NOT DIFF�������֣�',\n" +
                "  `PURCHASE_LEVEL` varchar(10) DEFAULT '' COMMENT '�������:����,VIP,�����Ƶ�',\n" +
                "  `GMT_CREATE` datetime DEFAULT NULL COMMENT '��������',\n" +
                "  `GMT_MODIFIED` datetime DEFAULT NULL COMMENT '�޸�����',\n" +
                "  `MEMO` varchar(128) DEFAULT NULL COMMENT '��ע',\n" +
                "  PRIMARY KEY (`ID`),\n" +
                "  UNIQUE KEY `id_pd_code` (`CODE`) USING BTREE,\n" +
                "  KEY `id_pd_name` (`PRODUCT_NAME`) USING BTREE\n" +
                ") ENGINE=InnoDB AUTO_INCREMENT=16939 DEFAULT CHARSET=utf8 COMMENT='��Ʒ������Ϣ��';\n" +
                "\n";

        //��ȡ�����ֶ�
        String result = init(mytext);

        System.out.println(result);
        // ��ȡ as ȡ���� other_id as otherId
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
     * ����һ����ȡ���е������ֶ�
     * @param mytext
     * @return
     */
    public static String init(String mytext){
        int index = -1;
        //����KEY�ֶ����������������ݣ�����ɾ����
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
     * ��ȡ�������ֶ�( a.id = #id# )
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

    //�����#�ŵ�
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

    //��ȡ private String xxxx;
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
