package test.opencv;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * 图片二值化处理
 * @author hecj
 */
public class Threshold {

    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        // 加载时灰度
        Mat src = Imgcodecs.imread("/Users/hecj/Desktop/car/WechatIMG18223_31.jpeg", Imgcodecs.IMREAD_GRAYSCALE);
        // 保存灰度
//        Imgcodecs.imwrite("/Users/hecj/Desktop/b160_1.png", src);
        Mat target = new Mat();
        // 二值化处理
        Imgproc.threshold(src, target, 0, 255, Imgproc.THRESH_BINARY | Imgproc.THRESH_OTSU);//灰度图像二值化
//        Imgproc.threshold(src, target, 100, 255, Imgproc.THRESH_BINARY);//灰度图像二值化
        // 保存二值化后图片
        Imgcodecs.imwrite("/Users/hecj/Desktop/car/WechatIMG18223_311.jpeg", target);
//       
//        Imgproc.adaptiveThreshold(src, target, 255, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY, 5, 0);
//        // 保存二值化后图片
//        Imgcodecs.imwrite("/Users/hecj/Desktop/b160_3.png", target);
    }

}