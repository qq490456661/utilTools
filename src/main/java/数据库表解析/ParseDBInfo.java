package 数据库表解析;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by linjunjie(490456661@qq.com) on 2016/7/15.
 */
public class ParseDBInfo {


    public static void main(String[] args) {
        //待解析的数据库表
        String mytext = "CREATE TABLE `student_school_log` (\n" +
                "  `id` bigint(20) NOT NULL AUTO_INCREMENT,\n" +
                "  `user_id` bigint(20) DEFAULT NULL COMMENT '机构id',\n" +
                "  `study_no` varchar(16) DEFAULT NULL COMMENT '学号',\n" +
                "  `course_name` varchar(32) DEFAULT NULL COMMENT '课程名字',\n" +
                "  `condition` varchar(64) DEFAULT NULL COMMENT '孩子状况',\n" +
                "  `remark` varchar(512) DEFAULT NULL COMMENT '老师备注',\n" +
                "  `created` timestamp NULL DEFAULT NULL,\n" +
                "  `modified` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,\n" +
                "  `status` smallint(6) DEFAULT NULL COMMENT '状态 0删除 1正常',\n" +
                "  PRIMARY KEY (`id`)\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='学生每天上课的状况日志';\n" +
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
