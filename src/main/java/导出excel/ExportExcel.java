/**
 * onway.com Inc.
 * Copyright (c) 2016-2016 All Rights Reserved.
 */
package ����excel;

import jxl.Cell;
import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;

/**
 * @author junjie.lin
 * @version $Id: ExportExcel.java, v 0.1 2016/8/3 17:58 junjie.lin Exp $
 */
public class ExportExcel {
    //����ģ������
    private static final int COMMON_COLS = 1;

    //COMMON_COLS ������ģ��
    private static final String[] colsName = {
            "���","������","���֤","����","������","��������","����","�α���ʼʱ��","����ʱ��","�ײ�",
            "ʵ�ʻ���","�ı仧��","����״̬","����״̬","�α���","�����","�ܷ���","�ֻ���","����ʱ��"
    };

    //����·��
    private static final String saveFilePath = "/usr/local/tomcat_1/webapps/userattachment/myexcel";

    //��չ��
    private static final String SUFFIX = ".xls";

    /**
     * �����������������ж���
     * @param query ��ѯ����
     * ��������·��
     * @return
     */
    public String exportExcel(){
        //����·��
        String fileDownloadPath = null;
        //����
        String[] cols = getColsByType(COMMON_COLS);
        //�����ļ�
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String dayFormat = simpleDateFormat.format(new Date());
        //����Ŀ¼
        String directory = buildDirectory(dayFormat+File.separator);
        fileDownloadPath = new StringBuilder(directory).append(dayFormat)
                .append("������").append(SUFFIX).toString();
        File file = new File(fileDownloadPath);

        //����excel���
        WritableWorkbook workbook = null;
        try {
            workbook = Workbook.createWorkbook(file);
            //������������
            WritableSheet sheet = workbook.createSheet("������",0);
            //д������
            queryData(sheet);
            //������Ӧ
            setColsAutoChangeWidth(sheet);
            //д���ļ������ҹر�
            workbook.write();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (workbook != null)
                    workbook.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }


        return "";
    }

    /**
     * ���ģ�������
     * @param type
     * @return
     */
    public static String[] getColsByType(int type){
        if(type == COMMON_COLS){
            return colsName;
        }
        return null;
    }

    /**
     * ��ȡ���ֵĸ���
     * @param context
     * @return
     */
    public static int getChineseNum(String context){    ///ͳ��context���Ǻ��ֵĸ���
        int lenOfChinese=0;
        java.util.regex.Pattern p = java.util.regex.Pattern.compile("[\u4e00-\u9fa5]");    //���ֵ�Unicode���뷶Χ
        Matcher m = p.matcher(context);
        while(m.find()){
            lenOfChinese++;
        }
        return lenOfChinese;
    }

    /**
     * ����Ŀ¼
     * @param suffix
     * @return
     */
    public static String buildDirectory(String suffix){
        String directory = saveFilePath+suffix;
        File file = new File(directory);
        if(!file.exists()){
            file.mkdirs();
        }
        return directory;
    }

    /**
     * ��ҳ��ѯ����д��
     * @param sheet �����ռ�
     */
    public void queryData(WritableSheet sheet){

    }

    /**
     * ������Ӧ
     * @param sheet
     */
    public void setColsAutoChangeWidth(WritableSheet sheet){
        //������������Ӧ�еĿ��
        int maxlength = 0;
        for(int i=0;i<sheet.getColumns();i++){
            maxlength = 0;
            for(int j=0;j<sheet.getRows();j++){
                Cell c = sheet.getCell(i,j);
                if(c.getContents() != null){
                    maxlength = Math.max(maxlength,c.getContents().length() + getChineseNum(c.getContents()));
                };
            }
            sheet.setColumnView(i,maxlength+2);
        }
    }

}
