package test.opencv;

import org.junit.Test;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * 图片去噪声
 * @author hecj
 */
public class ImageZaoSheng {
	
	/**
	 * 均值滤波
	 * https://blog.csdn.net/m1109048058/article/details/76531738
	 */
	@Test
    public void blur() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        // 加载时灰度
        Mat src = Imgcodecs.imread("/Users/hecj/Desktop/car/erzhi.jpeg");
        Mat dst = src.clone();
        Imgproc.blur(src, dst, new Size(9,9), new Point(-1, -1), Core.BORDER_DEFAULT);
        Imgcodecs.imwrite("/Users/hecj/Desktop/car/erzhi_1.jpeg", dst);
        
    }
	/**
	 * 高斯滤波
	 * https://blog.csdn.net/m1109048058/article/details/76534468
	 */
	@Test
	public void GaussianBlur() {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		// 加载时灰度
		Mat src = Imgcodecs.imread("/Users/hecj/Desktop/car/erzhi.jpeg");
		Mat dst = src.clone();
	    Imgproc.GaussianBlur(src, dst, new Size(9,9), 0, 0, Core.BORDER_DEFAULT);
		Imgcodecs.imwrite("/Users/hecj/Desktop/car/erzhi_2.jpeg", dst);
	}
	
	/**
	 * 中值滤波
	 * https://blog.csdn.net/m1109048058/article/details/76539705
	 */
	@Test
	public void medianBlur() {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat src = Imgcodecs.imread("/Users/hecj/Desktop/car/erzhi.jpeg");
		Mat dst = src.clone();
	    Imgproc.medianBlur(src, dst, 7);
		Imgcodecs.imwrite("/Users/hecj/Desktop/car/erzhi_3.jpeg", dst);
	}

}