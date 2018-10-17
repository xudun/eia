package com.lheia.eia.tools

import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import sun.misc.BASE64Decoder
import sun.misc.BASE64Encoder

import javax.imageio.ImageIO
import java.awt.BasicStroke
import java.awt.Color
import java.awt.Graphics2D
import java.awt.geom.RoundRectangle2D
import java.awt.image.BufferedImage

/**
 * Created by XinXi-001 on 2018/5/12.
 * 生成二维码图片的方法(导出word时只需要base64过的字节流转成的字符串)
 */
class QRCodeTools {


    private static final int BLACK = 0xFF000000;//用于设置图案的颜色

    private static final int WHITE = 0xFFFFFFFF; //用于背景色


    public static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, (matrix.get(x, y) ? BLACK : WHITE));
//              image.setRGB(x, y,  (matrix.get(x, y) ? Color.YELLOW.getRGB() : Color.CYAN.getRGB()));
            }
        }
        return image;
    }


    public static void writeToFile(BitMatrix matrix, String format, File file) throws IOException {
        BufferedImage image = toBufferedImage(matrix);
        //设置logo图标
//        image = this.LogoMatrix(image,"D:\\pic.jpg");
        if (!ImageIO.write(image, format, file)) {
            throw new IOException("Could not write an image of format " + format + " to " + file);
        } else {
            System.out.println("图片生成成功！");
        }
    }

    public static void writeToStream(BitMatrix matrix, String format, OutputStream stream) throws IOException {
        BufferedImage image = toBufferedImage(matrix);
        //设置logo图标
        image = this.LogoMatrix(image);

        if (!ImageIO.write(image, format, stream)) {
            throw new IOException("Could not write an image of format " + format);
        }
    }

    /**
     * 设置 logo
     * @param matrixImage 源二维码图片
     * @return 返回带有logo的二维码图片
     * @throws IOException
     * @author Administrator sangwenhao
     */
    public BufferedImage LogoMatrix(BufferedImage matrixImage, String logoPath) throws IOException {
        /**
         * 读取二维码图片，并构建绘图对象
         */

        Graphics2D g2 = matrixImage.createGraphics();
        int matrixWidth = matrixImage.getWidth();
        int matrixHeigh = matrixImage.getHeight();
        /**
         * 读取Logo图片
         */
        BufferedImage logo = ImageIO.read(new File(logoPath));
        //开始绘制图片
        g2.drawImage(logo, (matrixWidth / 5 * 2).intValue(), (matrixHeigh / 5 * 2).intValue(), (matrixWidth / 5).intValue(), (matrixHeigh / 5).intValue(), null);
//绘制
        BasicStroke stroke = new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        g2.setStroke(stroke);// 设置笔画对象
        //指定弧度的圆角矩形
        RoundRectangle2D.Float round = new RoundRectangle2D.Float(matrixWidth / 5 * 2, matrixHeigh / 5 * 2, matrixWidth / 5, matrixHeigh / 5, 20, 20);
        g2.setColor(Color.white);
        g2.draw(round);// 绘制圆弧矩形
        //设置logo 有一道灰色边框
        BasicStroke stroke2 = new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        g2.setStroke(stroke2);// 设置笔画对象
        RoundRectangle2D.Float round2 = new RoundRectangle2D.Float(matrixWidth / 5 * 2 + 2, matrixHeigh / 5 * 2 + 2, matrixWidth / 5 - 4, matrixHeigh / 5 - 4, 20, 20);
        g2.setColor(new Color(128, 128, 128));
        g2.draw(round2);// 绘制圆弧矩形
        g2.dispose();
        matrixImage.flush();
        return matrixImage;
    }

    /**
     * 将图片文件转化为字节数组字符串，并对其进行Base64编码处理(暂时不用)
     * @param imgFile
     * @return
     */
    public static String getImageStr(String imgFile) {
        //String imgFile = "d:\\111.jpg";// 待处理的图片
        InputStream inp = null;
        byte[] data = null;
// 读取图片字节数组
        try {
            inp = new FileInputStream(imgFile);
            data = new byte[inp.available()];
            inp.read(data);
            inp.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
// 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);// 返回Base64编码过的字节数组字符串
    }

    /**
     * 将字符串转为图片
     * @param imgStr
     * @return
     */
    public static boolean generateImage(String imgStr, String imgFile) throws Exception {// 对字节数组字符串进行Base64解码并生成图片
        if (imgStr == null) // 图像数据为空
            return false;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
// Base64解码
            byte[] b = decoder.decodeBuffer(imgStr);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {// 调整异常数据
                    b[i] += 256;
                }
            }

            String imgFilePath = imgFile;// 新生成的图片
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            throw e;
        }
    }

    //图片流转为字节流
    public static String ImageToBase64(BufferedImage image) {
        OutputStream out = new ByteArrayOutputStream()
        ImageIO.write(image, "jpg", out);
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(out.toByteArray());// 返回Base64编码过的字节数组字符串
    }
/**
 *
 * @param contents
 * @param format 二维码的图片格式
 * @param width 二维码图片宽度
 * @param height 二维码图片高度
 */
    static makeQrCode(String contents, String format, Integer width, Integer height) {
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        // 指定纠错等级,纠错级别（L 7%、M 15%、Q 25%、H 30%）
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        // 内容所使用字符集编码
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        //      hints.put(EncodeHintType.MAX_SIZE, 350);//设置图片的最大值
        //      hints.put(EncodeHintType.MIN_SIZE, 100);//设置图片的最小值
        hints.put(EncodeHintType.MARGIN, 1);//设置二维码边的空度，非负数
        BitMatrix bitMatrix = new MultiFormatWriter().encode(contents,//要编码的内容
                //编码类型，目前zxing支持：Aztec 2D,CODABAR 1D format,Code 39 1D,Code 93 1D ,Code 128 1D,
                //Data Matrix 2D , EAN-8 1D,EAN-13 1D,ITF (Interleaved Two of Five) 1D,
                //MaxiCode 2D barcode,PDF417,QR Code 2D,RSS 14,RSS EXPANDED,UPC-A 1D,UPC-E 1D,UPC/EAN extension,UPC_EAN_EXTENSION
                BarcodeFormat.QR_CODE,
                width, //条形码的宽度
                height, //条形码的高度
                hints);//生成条形码时的一些配置,此项可选
        BufferedImage image = toBufferedImage(bitMatrix);
        // 生成二维码
        def imgStr = ImageToBase64(image)
        return imgStr
    }

    /*public static void main(String[] args) throws Exception {
        *//**——————图片和字符串互转——————**//*
        //QRCodeTools.generateImage("", "")

        *//**——————生成二维码——————**//*
        String contents = "ZXing 二维码内容1234!"; // 二维码内容
        int width = 430; // 二维码图片宽度 300
        int height = 430; // 二维码图片高度300
        String format = "jpg";// 二维码的图片格式
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        // 指定纠错等级,纠错级别（L 7%、M 15%、Q 25%、H 30%）
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        // 内容所使用字符集编码
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        //      hints.put(EncodeHintType.MAX_SIZE, 350);//设置图片的最大值
        //      hints.put(EncodeHintType.MIN_SIZE, 100);//设置图片的最小值
        hints.put(EncodeHintType.MARGIN, 1);//设置二维码边的空度，非负数
        BitMatrix bitMatrix = new MultiFormatWriter().encode(contents,//要编码的内容
                //编码类型，目前zxing支持：Aztec 2D,CODABAR 1D format,Code 39 1D,Code 93 1D ,Code 128 1D,
                //Data Matrix 2D , EAN-8 1D,EAN-13 1D,ITF (Interleaved Two of Five) 1D,
                //MaxiCode 2D barcode,PDF417,QR Code 2D,RSS 14,RSS EXPANDED,UPC-A 1D,UPC-E 1D,UPC/EAN extension,UPC_EAN_EXTENSION
                BarcodeFormat.QR_CODE,
                width, //条形码的宽度
                height, //条形码的高度
                hints);//生成条形码时的一些配置,此项可选
        // 生成二维码
        File outputFile = new File("e:" + File.separator + "new-1.jpg");//指定输出路径
        QRCodeTools.writeToFile(bitMatrix, format, outputFile);
        // 生成二维码
        OutputStream outputStream = new FileOutputStream("d:/new2.png")
        QRCodeTools.writeToStream(bitMatrix, format, outputStream)
    }*/
}
