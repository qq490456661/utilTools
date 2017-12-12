package checkCode;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Random;
import javax.imageio.ImageIO;

public class ValidationCode {

    // 图形验证码的字符集合，系统将随机从这个字符串中选择一些字符作为验证码
    private static String codeChars = "23456789abcdefghkmnpqrstuvwxyzABCDEFGHKLMNPQRSTUVWXYZ";

    // 返回一个随机颜色（Color对象）
    private static Color getRandomColor(int minColor, int maxColor) {
        Random random = new Random();
        // 保存minColor最大不会超过255
        if (minColor > 255)
            minColor = 255;
        // 保存minColor最大不会超过255
        if (maxColor > 255)
            maxColor = 255;
        // 获得红色的随机颜色值
        int red = minColor + random.nextInt(maxColor - minColor);
        // 获得绿色的随机颜色值
        int green = minColor + random.nextInt(maxColor - minColor);
        // 获得蓝色的随机颜色值
        int blue = minColor + random.nextInt(maxColor - minColor);
        return new Color(red, green, blue);
    }

    protected static void getValidationCode() throws IOException {
        try {
            // 获得验证码集合的长度
            int charsLength = codeChars.length();
            // 设置图形验证码的长和宽（图形的大小）
            int width = 120, height = 40;
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics g = image.getGraphics();// 获得用于输出文字的Graphics对象
            Random random = new Random();
            g.setColor(getRandomColor(245, 255));// 随机设置要填充的颜色
            g.fillRect(0, 0, width, height);// 填充图形背景
            // 设置初始字体
            g.setFont(new Font("Times New Roman", Font.ITALIC, height));
            g.setColor(getRandomColor(100, 110));// 随机设置字体颜色
            // 用于保存最后随机生成的验证码
            StringBuilder validationCode = new StringBuilder();
            // 验证码的随机字体
            String[] fontNames = { "Times New Roman", "Book antiqua", "Arial" };
            // 随机生成3个到5个验证码
            for(int j = 0;j<10;j++) {
                validationCode = new StringBuilder("");
                for (int i = 0; i < 5; i++) {
                    // 随机设置当前验证码的字符的字体
                    g.setFont(new Font(fontNames[random.nextInt(3)], Font.ITALIC, height));
                    // 随机获得当前验证码的字符
                    char codeChar = codeChars.charAt(random.nextInt(charsLength));
                    validationCode.append(codeChar);
                    // 随机设置当前验证码字符的颜色
                    g.setColor(getRandomColor(10, 100));
                    //画随机线
                    drawRandomLine(g,width,height);
                    // 在图形上输出验证码字符，x和y都是随机生成的
                    drawRandomNum((Graphics2D)g, String.valueOf(codeChar),20 * i + random.nextInt(5), height - random.nextInt(5));
                }
                File file = new File("d:\\code" + j + ".png");
                ImageIO.write(image, "png", file);
                g.setColor(getRandomColor(245, 255));// 随机设置要填充的颜色
                g.fillRect(0, 0, width, height);// 填充图形背景
                System.out.println(validationCode.toString());
            }

            //byte[] data = ((DataBufferByte) image.getData().getDataBuffer()).getData();
            g.dispose();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 画随机线条
     *
     * @param g
     */
    private static void drawRandomLine(Graphics g , int width, int height) {
        // 设置线条个数并画线
        for (int i = 0; i < 1; i++) {
            int x1 = new Random().nextInt(width);
            int y1 = new Random().nextInt(height);
            int x2 = new Random().nextInt(width);
            int y2 = new Random().nextInt(height);
            g.drawLine(x1, y1, x2, y2);
        }

    }

    /**
     * 画随机数字
     * @param g
     * @param ch
     * @param x
     * @param y
     * @return
     */
    private static String drawRandomNum(Graphics2D g,String ch,int x, int y) {
        // 设置字体旋转角度
        int degree = new Random().nextInt() % 30;
        // 正向角度
        g.rotate(degree * Math.PI / 180, x, 20);
        //画字
        g.drawString(ch, x, y);
        // 反向角度
        g.rotate(-degree * Math.PI / 180, x, 20);
        return "";
    }

    public static void main(String[] args) throws IOException{
        getValidationCode();
    }
}