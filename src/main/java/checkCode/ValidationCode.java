package checkCode;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Random;
import javax.imageio.ImageIO;

public class ValidationCode {

    // ͼ����֤����ַ����ϣ�ϵͳ�����������ַ�����ѡ��һЩ�ַ���Ϊ��֤��
    private static String codeChars = "23456789abcdefghkmnpqrstuvwxyzABCDEFGHKLMNPQRSTUVWXYZ";

    // ����һ�������ɫ��Color����
    private static Color getRandomColor(int minColor, int maxColor) {
        Random random = new Random();
        // ����minColor��󲻻ᳬ��255
        if (minColor > 255)
            minColor = 255;
        // ����minColor��󲻻ᳬ��255
        if (maxColor > 255)
            maxColor = 255;
        // ��ú�ɫ�������ɫֵ
        int red = minColor + random.nextInt(maxColor - minColor);
        // �����ɫ�������ɫֵ
        int green = minColor + random.nextInt(maxColor - minColor);
        // �����ɫ�������ɫֵ
        int blue = minColor + random.nextInt(maxColor - minColor);
        return new Color(red, green, blue);
    }

    protected static void getValidationCode() throws IOException {
        try {
            // �����֤�뼯�ϵĳ���
            int charsLength = codeChars.length();
            // ����ͼ����֤��ĳ��Ϳ�ͼ�εĴ�С��
            int width = 120, height = 40;
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics g = image.getGraphics();// �������������ֵ�Graphics����
            Random random = new Random();
            g.setColor(getRandomColor(245, 255));// �������Ҫ������ɫ
            g.fillRect(0, 0, width, height);// ���ͼ�α���
            // ���ó�ʼ����
            g.setFont(new Font("Times New Roman", Font.ITALIC, height));
            g.setColor(getRandomColor(100, 110));// �������������ɫ
            // ���ڱ������������ɵ���֤��
            StringBuilder validationCode = new StringBuilder();
            // ��֤����������
            String[] fontNames = { "Times New Roman", "Book antiqua", "Arial" };
            // �������3����5����֤��
            for(int j = 0;j<10;j++) {
                validationCode = new StringBuilder("");
                for (int i = 0; i < 5; i++) {
                    // ������õ�ǰ��֤����ַ�������
                    g.setFont(new Font(fontNames[random.nextInt(3)], Font.ITALIC, height));
                    // �����õ�ǰ��֤����ַ�
                    char codeChar = codeChars.charAt(random.nextInt(charsLength));
                    validationCode.append(codeChar);
                    // ������õ�ǰ��֤���ַ�����ɫ
                    g.setColor(getRandomColor(10, 100));
                    //�������
                    drawRandomLine(g,width,height);
                    // ��ͼ���������֤���ַ���x��y����������ɵ�
                    drawRandomNum((Graphics2D)g, String.valueOf(codeChar),20 * i + random.nextInt(5), height - random.nextInt(5));
                }
                File file = new File("d:\\code" + j + ".png");
                ImageIO.write(image, "png", file);
                g.setColor(getRandomColor(245, 255));// �������Ҫ������ɫ
                g.fillRect(0, 0, width, height);// ���ͼ�α���
                System.out.println(validationCode.toString());
            }

            //byte[] data = ((DataBufferByte) image.getData().getDataBuffer()).getData();
            g.dispose();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ���������
     *
     * @param g
     */
    private static void drawRandomLine(Graphics g , int width, int height) {
        // ������������������
        for (int i = 0; i < 1; i++) {
            int x1 = new Random().nextInt(width);
            int y1 = new Random().nextInt(height);
            int x2 = new Random().nextInt(width);
            int y2 = new Random().nextInt(height);
            g.drawLine(x1, y1, x2, y2);
        }

    }

    /**
     * ���������
     * @param g
     * @param ch
     * @param x
     * @param y
     * @return
     */
    private static String drawRandomNum(Graphics2D g,String ch,int x, int y) {
        // ����������ת�Ƕ�
        int degree = new Random().nextInt() % 30;
        // ����Ƕ�
        g.rotate(degree * Math.PI / 180, x, 20);
        //����
        g.drawString(ch, x, y);
        // ����Ƕ�
        g.rotate(-degree * Math.PI / 180, x, 20);
        return "";
    }

    public static void main(String[] args) throws IOException{
        getValidationCode();
    }
}