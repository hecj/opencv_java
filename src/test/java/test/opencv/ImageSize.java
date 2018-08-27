package test.opencv;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * 图片大小处理
 * @author hecj
 */
public class ImageSize {

    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        // 加载时灰度
        Mat src = Imgcodecs.imread("/Users/hecj/Desktop/WechatIMG1822.jpeg", Imgcodecs.IMREAD_GRAYSCALE);
        Mat dst = new Mat();
        
        int newRows = 300;
        int newCols = (int)((double)newRows/src.size().width * src.size().height);
        
        Imgproc.resize(src, dst, new Size(newRows, newCols));
        Imgcodecs.imwrite("/Users/hecj/Desktop/WechatIMG18223.jpeg", dst);
        
    }

}