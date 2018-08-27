package test.opencv;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * 图片去噪声
 * @author hecj
 */
public class ImageZaoSheng {

    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        // 加载时灰度
        Mat src = Imgcodecs.imread("/Users/hecj/Desktop/WechatIMG1822.jpeg", Imgcodecs.IMREAD_GRAYSCALE);
       
        
        Imgcodecs.imwrite("/Users/hecj/Desktop/WechatIMG18223.jpeg", src);
        
    }

}