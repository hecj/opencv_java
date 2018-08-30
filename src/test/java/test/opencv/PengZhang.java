package test.opencv;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

/**
 * 膨胀腐蚀
 * @author hecj
 */
public class PengZhang {

	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat src = Imgcodecs.imread("/Users/hecj/Desktop/quzao.jpeg");
		
		
		
		
		
//		Imgcodecs.imwrite("/Users/hecj/Desktop/n1_quzao.jpeg", quzao);
	}
}