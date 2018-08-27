package test.opencv;

import org.junit.Test;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * 图片灰度、二值、去噪声
 * 
 * @author hecj
 */
public class ImageMain {

	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		// 灰度
		Mat src = Imgcodecs.imread("/Users/hecj/Desktop/car/n1.jpeg", Imgcodecs.IMREAD_GRAYSCALE);
		Imgcodecs.imwrite("/Users/hecj/Desktop/car/n1_hui.jpeg", src);
		// 二值
		Mat dst = new Mat();
		Imgproc.threshold(src, dst, 0, 255, Imgproc.THRESH_BINARY | Imgproc.THRESH_OTSU);// 灰度图像二值化
		Imgcodecs.imwrite("/Users/hecj/Desktop/car/n1_erzhi.jpeg", dst);
		// 去噪
		Mat quzao = new Mat();
		Imgproc.medianBlur(dst, quzao, 7);
		Imgcodecs.imwrite("/Users/hecj/Desktop/car/n1_quzao.jpeg", quzao);
	}
}