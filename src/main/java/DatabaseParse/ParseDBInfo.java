package DatabaseParse;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by linjunjie(490456661@qq.com) on 2016/7/15.
 */
public class ParseDBInfo {


    public static void main(String[] args) {

        //�����������ݿ��
        String mytext = "CREATE TABLE `stat_role` (\n" +
                "  `id` bigint(20) NOT NULL AUTO_INCREMENT,\n" +
                "  `name` varchar(64) DEFAULT NULL COMMENT '��ɫ��',\n" +
                "  `created` timestamp NULL DEFAULT NULL,\n" +
                "  `modified` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,\n" +
                "  `status` smallint(6) DEFAULT NULL,\n" +
                "  PRIMARY KEY (`id`)\n" +
                ") ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='��ɫ';\n" +
                "\n";

        //��ȡ�����ֶ�
        TableModel tableModel = init(mytext);
        String result = tableModel.getOtherFields();
        System.out.println("==="+result);

        tableModel.setCommonFields(result);
        // ��ȡ as ȡ���� other_id as otherId
        tableModel.setAs(getAs(result));
        //a.status
        tableModel.setADianAs(getAAs(result));
        //status
        tableModel.setFirstUppercase(getFirstUppercase(result));
        //#status#
        tableModel.setJing(getJing(result));
        //#{status}
        tableModel.setJingMybatis(getJingMybatis(result));
        //a.status = #status#
        tableModel.setFieldsEqualsJing(getFieldsEqualsJing(result));
        //private String xxx;
        System.out.println(getMemberProperties(result));

        buildIbatisMysqlXml(tableModel);
    }

    /** ����mysql��ibatisXml�ļ� **/
    //freemarker
    public static void buildIbatisMysqlXml(TableModel tableModel){
        String pojoClassPath = "com.onway.module.pojo";
        String TEMPLATE_PATH = "D://XSBDownload";//ģ��λ��
        String TEMPLATE_NAME= "ibatis_template.xml";//ģ������
        String OUTPUT_PATH= "D://XSBDownload//";//���ɵ�ַ

        Configuration configuration = new Configuration();
        Writer out = null;
        try {
            // step2 ��ȡģ��·��
            configuration.setDirectoryForTemplateLoading(new File(TEMPLATE_PATH));
            // step3 ��������ģ��
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("namespace", tableModel.getTableName());
            dataMap.put("tableName", tableModel.getTableName());
            dataMap.put("pojoName", tableModel.getPojoName());
            dataMap.put("pojoClassPath", pojoClassPath+"."+tableModel.getPojoName());
            dataMap.put("fieldsAs", tableModel.getAs());
            dataMap.put("insertFieldKey", tableModel.getCommonFields());
            dataMap.put("insertFieldValue", tableModel.getJing());
            // step4 ����ģ���ļ�
            Template template = configuration.getTemplate(TEMPLATE_NAME,"UTF-8");
            // step5 ��������
            File docFile = new File(OUTPUT_PATH+tableModel.getTableName()+"-sqlmap.xml");
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(docFile),"UTF-8"));
            // step6 ����ļ�
            template.process(dataMap, out);
            System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^xml �ļ������ɹ� !");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != out) {
                    out.flush();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

    }

    /**
     * ����һ����ȡ���е������ֶ�
     * @param mytext
     * @return
     */
    public static TableModel init(String mytext){
        int index = -1;
        //����KEY�ֶ����������������ݣ�����ɾ����
        mytext = mytext.substring(0,(index = mytext.indexOf(" KEY ")) == -1 ? mytext.length() : index);
        //ɾ��(ǰ������
//        mytext = mytext.substring(mytext.indexOf("("));
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
        String tableName = result.substring(0,result.indexOf(","));
        //��װ���ؽ��
        TableModel tableModel = new TableModel();
        tableModel.setTableName(tableName);
        tableModel.setPojoName(toTuoFengName(tableName,true)+"Pojo");
        result = result.substring(result.indexOf(",")+1);
        tableModel.setOtherFields(result);
        return tableModel;
    }

    /**
     * ��ȡ�������ֶ�( a.id = #id# )
     * @param result
     * @return
     */
    public static String getFieldsEqualsJing(String result){
        String[] strs = result.split(",");
        StringBuilder sbchar = new StringBuilder();
        for(String item : strs){
            sbchar.append("a.").append(item);
            if(item.indexOf("_") != -1){
                sbchar.append(" = #");
                sbchar.append(toTuoFengName(item,false));
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
        StringBuilder sbchar = new StringBuilder();
        for(String item : strs){
            sbchar.append(item);
            if(item.indexOf("_") != -1){
                sbchar.append(" as ");
                sbchar.append(toTuoFengName(item,false));
            }
            sbchar.append(",");
        }
        return sbchar.substring(0,sbchar.length()-1).toString();
    }

    //�շ�������
    public static String toTuoFengName(String item,boolean firstUp){
        //firstUp == true��ʾ����ĸ��д
        int flag = 0;//Ϊ1��ʾ��һ����ĸ��д
        if(firstUp == true){
            flag = 1;
        }
        StringBuilder sbchar = new StringBuilder("");
        char[] chars = item.toCharArray();

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
        return sbchar.toString();
    }

    public static String getAAs(String result){
        String[] strs = result.split(",");
        StringBuilder sbchar = new StringBuilder();
        for(String item : strs){
            sbchar.append("a.").append(item);
            if(item.indexOf("_") != -1){
                sbchar.append(" as ");
                sbchar.append(toTuoFengName(item,false));
            }
            sbchar.append(",");
        }
        return sbchar.substring(0,sbchar.length()-1).toString();
    }

    //person;userId;otherId;created;modified;status
    public static String getFirstUppercase(String result){
        String[] strs = result.split(",");
        StringBuilder sbchar = new StringBuilder();
        int flag = 0;
        for(String item : strs){
            if(item.indexOf("_") != -1){
                sbchar.append(toTuoFengName(item,false));
            }else{
                sbchar.append(item);
            }
            sbchar.append(";");
        }
        return sbchar.substring(0,sbchar.length()-1).toString();
    }

    //�����#�ŵ�
    public static String getJing(String result){
        String[] strs = result.split(",");
        StringBuilder sbchar = new StringBuilder();
        int flag = 0;
        for(String item : strs){
            sbchar.append("#");
            if(item.indexOf("_") != -1){
                sbchar.append(toTuoFengName(item,false));
            }else{
                sbchar.append(item);
            }
            sbchar.append("#,");
        }

        return sbchar.substring(0,sbchar.length()-1).toString();
    }

    //�����#{}�ŵ�
    public static String getJingMybatis(String result){
        String[] strs = result.split(",");
        StringBuilder sbchar = new StringBuilder();
        int flag = 0;
        for(String item : strs){
            sbchar.append("#{");
            if(item.indexOf("_") != -1){
                sbchar.append(toTuoFengName(item,false));
            }else{
                sbchar.append(item);
            }
            sbchar.append("},");
        }
        return sbchar.substring(0,sbchar.length()-1).toString();
    }

    //��ȡ private String xxxx;
    public static String getMemberProperties(String result){
        String[] strs = result.split(",");
        StringBuilder sbchar = new StringBuilder();
        for(String item : strs){
            sbchar.append("private String ");
            if(item.indexOf("_") != -1){
                sbchar.append(toTuoFengName(item,false));
            }else{
                sbchar.append(item);
            }
            sbchar.append(";\n");
        }

        return sbchar.substring(0,sbchar.length()-1).toString();
    }

}
