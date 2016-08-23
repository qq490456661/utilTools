package ���ݿ�����;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by linjunjie(490456661@qq.com) on 2016/7/15.
 */
public class ParseDBInfo {


    public static void main(String[] args) {
        //�����������ݿ��
        String mytext = "CREATE TABLE `cif_user` (\n" +
                "  `ID` int(10) NOT NULL AUTO_INCREMENT,\n" +
                "  `USER_ID` varchar(32) NOT NULL COMMENT '�û�id',\n" +
                "  `PLATFORM_CODE` varchar(32) DEFAULT NULL COMMENT 'ƽ̨��ţ���Բ�ͬ���̻�����',\n" +
                "  `REAL_NAME` varchar(64) DEFAULT NULL COMMENT '�û�����',\n" +
                "  `STATUS` varchar(32) NOT NULL COMMENT '״̬',\n" +
                "  `CERT_TYPE` varchar(32) DEFAULT NULL COMMENT '֤������',\n" +
                "  `CERT_NO` varchar(128) DEFAULT NULL COMMENT '֤����',\n" +
                "  `CELL` varchar(16) NOT NULL COMMENT '�ֻ���',\n" +
                "  `GMT_CREATE` datetime NOT NULL COMMENT '����ʱ��',\n" +
                "  `CREATER` varchar(32) DEFAULT NULL COMMENT '������',\n" +
                "  `GMT_MODIFIED` datetime NOT NULL,\n" +
                "  `MODIFIER` varchar(32) DEFAULT NULL,\n" +
                "  `LOGIN_PASSWD` varchar(32) DEFAULT NULL COMMENT '��¼����',\n" +
                "  `PAY_PASSWD` varchar(32) DEFAULT NULL COMMENT '֧������',\n" +
                "  `REGISTER_FROM_ID` varchar(16) DEFAULT NULL COMMENT '�û���ԴID',\n" +
                "  `REGISTER_FROM_TYPE` varchar(32) DEFAULT NULL COMMENT '�û���Դ����',\n" +
                "  `EXT_INFO` varchar(32) DEFAULT NULL COMMENT '��������Ϣ',\n" +
                "  `RETRY_PAY_PASSWORD` int(11) DEFAULT NULL,\n" +
                "  `VALID` varchar(16) DEFAULT NULL COMMENT '���֤��Ч��',\n" +
                "  `POLL_CODE` varchar(16) DEFAULT NULL COMMENT '�ƹ���',\n" +
                "  `GMT_REGISTER` datetime DEFAULT NULL,\n" +
                "  `EMAIL` varchar(32) DEFAULT NULL,\n" +
                "  `ADDRESS` varchar(256) DEFAULT NULL,\n" +
                "  PRIMARY KEY (`ID`),\n" +
                "  UNIQUE KEY `IDX_PLATFORM_CODE_AND_CELL` (`PLATFORM_CODE`,`CELL`) USING BTREE\n" +
                ") ENGINE=InnoDB AUTO_INCREMENT=5072 DEFAULT CHARSET=utf8;\n" +
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
