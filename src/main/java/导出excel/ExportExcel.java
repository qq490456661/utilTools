/**
 * onway.com Inc.
 * Copyright (c) 2016-2016 All Rights Reserved.
 */
package 导出excel;

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
    //列名模板类型
    private static final int COMMON_COLS = 1;

    //COMMON_COLS 的列名模板
    private static final String[] colsName = {
            "序号","办理人","身份证","地区","代办人","订单类型","基数","参保开始时间","结束时间","套餐",
            "实际户籍","改变户籍","交易状态","办理状态","参保费","服务费","总费用","手机号","创建时间"
    };

    //保存路径
    private static final String saveFilePath = "/usr/local/tomcat_1/webapps/userattachment/myexcel";

    //扩展名
    private static final String SUFFIX = ".xls";

    /**
     * 导出符合条件的所有订单
     * @param query 查询条件
     * 返回下载路径
     * @return
     */
    public String exportExcel(){
        //下载路径
        String fileDownloadPath = null;
        //列名
        String[] cols = getColsByType(COMMON_COLS);
        //生成文件
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String dayFormat = simpleDateFormat.format(new Date());
        //生成目录
        String directory = buildDirectory(dayFormat+File.separator);
        fileDownloadPath = new StringBuilder(directory).append(dayFormat)
                .append("订单表").append(SUFFIX).toString();
        File file = new File(fileDownloadPath);

        //创建excel句柄
        WritableWorkbook workbook = null;
        try {
            workbook = Workbook.createWorkbook(file);
            //创建工作区间
            WritableSheet sheet = workbook.createSheet("订单表",0);
            //写入数据
            queryData(sheet);
            //列自适应
            setColsAutoChangeWidth(sheet);
            //写入文件，并且关闭
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
     * 获得模板的列名
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
     * 获取汉字的个数
     * @param context
     * @return
     */
    public static int getChineseNum(String context){    ///统计context中是汉字的个数
        int lenOfChinese=0;
        java.util.regex.Pattern p = java.util.regex.Pattern.compile("[\u4e00-\u9fa5]");    //汉字的Unicode编码范围
        Matcher m = p.matcher(context);
        while(m.find()){
            lenOfChinese++;
        }
        return lenOfChinese;
    }

    /**
     * 创建目录
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
     * 分页查询数据写入
     * @param sheet 工作空间
     */
    public void queryData(WritableSheet sheet){

    }

    /**
     * 列自适应
     * @param sheet
     */
    public void setColsAutoChangeWidth(WritableSheet sheet){
        //根据内容自适应列的宽度
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
