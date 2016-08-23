package pdf;


import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

/**
 * Created by linjunjie(490456661@qq.com) on 2016/8/15.
 */
public class GeneratePDF {

    public static void main(String[] args)throws Exception {


    }

    /**
     * 生成pdf
     * @param savePath  保存路径
     * @param pics      图片坐标和url地址
     * @param content   内容
     */
    public static void generatePDF(String savePath,Picture[] pics,String content){
        Document document = new Document();
        PdfWriter writer = null;
        try {
            //savePath = "D://mypdf.pdf" 文件不存在会自动创建
            writer = PdfWriter.getInstance(document, new FileOutputStream(savePath));
            document.open();

            //图片
            Image image = null;
            for(Picture p : pics){
                image = Image.getInstance(p.getPicPath());
                image.setAbsolutePosition(450f, 550f); //设置x,y坐标
                //image.scaleAbsolute(100, 100);         //设置缩放比例,宽高
                document.add(image);
            }
            document.add(new Paragraph(content));

            document.close();
            writer.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try{
                if(document != null){
                    document.close();
                }
                if(writer != null){
                    writer.close();
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public static class Picture{
        //水印坐标
        private float x;
        private float y;
        //图片路径
        private String picPath;

        public float getX() {
            return x;
        }

        public void setX(float x) {
            this.x = x;
        }

        public float getY() {
            return y;
        }

        public void setY(float y) {
            this.y = y;
        }

        public String getPicPath() {
            return picPath;
        }

        public void setPicPath(String picPath) {
            this.picPath = picPath;
        }
    }

}
