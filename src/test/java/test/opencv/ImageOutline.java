package test.opencv;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * 图像轮廓
 * @author hecj
 */
public class ImageOutline {

	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat src = Imgcodecs.imread("/Users/hecj/Desktop/car/quzao.jpeg");
		
		Mat dst = new Mat();
		Imgproc.Canny(src, dst, 3, 9, 3,false);
		Imgcodecs.imwrite("/Users/hecj/Desktop/quzao_1.jpeg", dst);
	}
}