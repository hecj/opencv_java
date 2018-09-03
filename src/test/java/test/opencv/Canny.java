package test.opencv;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * 边缘检测
 * 
 * @author hecj
 *
 */
public class Canny {

	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat src = Imgcodecs.imread("/Users/hecj/Desktop/n1.jpeg");
		// 灰度
		Mat gray = new Mat();
		Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);
		Imgcodecs.imwrite("/Users/hecj/Desktop/gray.jpeg", gray);
		
		Mat sobel = new Mat();
		Imgproc.Sobel(gray,sobel, CvType.CV_8U, 1, 0);
		Imgcodecs.imwrite("/Users/hecj/Desktop/sobel.jpeg", sobel);
		
//		// 二值
		Mat threshold = new Mat();
        Imgproc.threshold(sobel, threshold, 0, 255, Imgproc.THRESH_BINARY | Imgproc.THRESH_OTSU);//灰度图像二值化
        Imgcodecs.imwrite("/Users/hecj/Desktop/threshold.jpeg", threshold);
//		// 边缘图
//		Mat cannyMat = new Mat();
//		Imgproc.Canny(erzhi, cannyMat, 50, 300);
//		
//		Imgcodecs.imwrite("/Users/hecj/Desktop/n4.jpeg", cannyMat);
	}

}
