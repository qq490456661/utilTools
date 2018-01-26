/**
 * onway.com Inc.
 * Copyright (c) 2018-2018 All Rights Reserved.
 */
package MatrixToImageWriter;

import com.itextpdf.text.pdf.qrcode.BitMatrix;
import com.itextpdf.text.pdf.qrcode.EncodeHintType;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Hashtable;

/**
 * @author junjie.lin
 * @version $Id: MatrixToImageWriter.java, v 0.1 2018/1/25 0025 10:35 junjie.lin Exp $
 */
public class MatrixToImageWriter {

    private MatrixToImageWriter() {
    }


    public static void main(String[] args) throws Exception {
        int imageWidth = 0;
        int imageHeight = 0;
        int headWidth = 135;
        int headHeight = 135;
        String msg = "�����ҹ�����������ߴߴߴ��������������ߴߴ������������";
        int row = msg.length()/10;

        //��ȡ����ͼƬ,���ñ���
        BufferedImage image = ImageIO.read(new File("D:\\С���򿪷�\\¼���ϴ�����\\record\\images\\forward.png"));
        imageWidth = image.getWidth();
        imageHeight = image.getHeight();

        //��ȡͷ��
        BufferedImage headImage = ImageIO.read(new File("D://head.jpg"));

        //��ȡ����ͼƬ�Ļ���
        Graphics g = image.getGraphics();

        //��ͷ���ڱ���������
        //head.jpg
        int positionX = imageWidth/2 - headWidth/2;
        g.drawImage(headImage, positionX , 20,headWidth,headHeight,null);
        g.setFont(new Font("����", Font.BOLD, 50));
        //RGB(255,231,141)
        g.setColor(new Color(255, 231, 141));

        int msgRowNum = (int)Math.ceil(msg.length() / 10.0);

        String sbSplit = "";
        int msgPositionX = 0;
        int msgPositionY = msgRowNum * 20;
        for(int i=0;i<msgRowNum;i++){
            if(i == (msgRowNum - 1)){//���һ��
                sbSplit = msg.substring(i*10);
                msgPositionX = imageWidth / 2 - sbSplit.length() * 55 / 2;
                g.drawString(sbSplit, msgPositionX, (i+1)*50 + 280 - msgPositionY);
            }else{
                sbSplit = msg.substring(i*10,(i+1)*10);
                msgPositionX = imageWidth / 2 - sbSplit.length() * 55 / 2;
                g.drawString(sbSplit, msgPositionX, (i+1)*50 + 280 - msgPositionY);
            }
        }

        //���ö�ά���ڱ���ͼƬ��
        ImageIO.write(image, "png",new FileOutputStream("D://1.png"));
    }

}
