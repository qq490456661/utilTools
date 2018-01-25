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

    private static final int BLACK = 0xFF000000;
    private static final int WHITE = 0xFFFFFFFF;

    private MatrixToImageWriter() {
    }

    public static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
            }
        }
        return image;
    }

    public static void writeToFile(BitMatrix matrix, String format, File file)
            throws IOException {
        BufferedImage image = toBufferedImage(matrix);
        if (!ImageIO.write(image, format, file)) {
            throw new IOException("Could not write an image of format "
                    + format + " to " + file);
        }
    }

    public static void writeToStream(BitMatrix matrix, String format,
                                     OutputStream stream) throws IOException {
        BufferedImage image = toBufferedImage(matrix);
        if (!ImageIO.write(image, format, stream)) {
            throw new IOException("Could not write an image of format " + format);
        }
    }

    public static void main(String[] args) throws Exception {
        int imageWidth = 0;
        int imageHeight = 0;
        int headWidth = 135;
        int headHeight = 135;
        String msg = "ÒÔ×ÔÎÒ¹þ¹þ¹þ¹þ¹þ¹þß´ß´ß´ÄäÃûÎûÎûÎû¶þ¶þ";
        int row = msg.length()/10;

        //¶ÁÈ¡±³¾°Í¼Æ¬,ÉèÖÃ±³¾°
        BufferedImage image = ImageIO.read(new File("D://wx2.jpg"));
        imageWidth = image.getWidth();
        imageHeight = image.getHeight();

        //¶ÁÈ¡Í·Ïñ
        BufferedImage headImage = ImageIO.read(new File("D://head.jpg"));

        //»ñÈ¡±³¾°Í¼Æ¬µÄ»­°å
        Graphics g = image.getGraphics();

        //»­Í·ÏñÔÚ±³¾°»­°åÉÏ
        //head.jpg
        int positionX = imageWidth/2 - headWidth/2;
        g.drawImage(headImage, positionX , 20,headWidth,headHeight,null);
        g.setFont(new Font("ºÚÌå", Font.BOLD, 50));
        //RGB(255,231,141)
        g.setColor(new Color(255, 231, 141));

        int msgRowNum = (int)Math.ceil(msg.length() / 10.0);

        String sbSplit = "";
        int msgPositionX = 0;
        int msgPositionY = msgRowNum * 20;
        for(int i=0;i<msgRowNum;i++){
            if(i == (msgRowNum - 1)){//×îºóÒ»¸ö
                sbSplit = msg.substring(i*10);
                msgPositionX = imageWidth / 2 - sbSplit.length() * 55 / 2;
                g.drawString(sbSplit, msgPositionX, (i+1)*50 + 280 - msgPositionY);
            }else{
                sbSplit = msg.substring(i*10,(i+1)*10);
                msgPositionX = imageWidth / 2 - sbSplit.length() * 55 / 2;
                g.drawString(sbSplit, msgPositionX, (i+1)*50 + 280 - msgPositionY);
            }
        }

        //ÉèÖÃ¶þÎ¬ÂëÔÚ±³¾°Í¼Æ¬ÉÏ



        ImageIO.write(image, "JPG",new FileOutputStream("D://1.jpg"));
    }

}
