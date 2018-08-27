package test.opencv;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * 图片灰度化处理
 * @author hecj
 */
public class Grayscaler {

	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		// 第一种 加载时灰度
		Mat mat = Imgcodecs.imread("/Users/hecj/Desktop/car/WechatIMG18223_2.jpeg", Imgcodecs.IMREAD_GRAYSCALE);
		Imgcodecs.imwrite("/Users/hecj/Desktop/car/WechatIMG18223_3.jpeg", mat);
		
//		// 第二种 转换灰度图像
//		Mat src = Imgcodecs.imread("/data/data/WindowsLogo.jpg", Imgcodecs.IMREAD_UNCHANGED);
//		Mat dst = new Mat();
//		Imgproc.cvtColor(src, dst, Imgcodecs.IMREAD_GRAYSCALE);
//		Imgcodecs.imwrite("/Users/hecj/Desktop/WindowsLogo2.jpg", mat);

	}

}
