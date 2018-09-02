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
		Mat src = Imgcodecs.imread("/Users/hecj/Desktop/p3.jpeg");
		Mat dst = new Mat();
		// 灰色
		Imgproc.cvtColor(src, dst, Imgproc.COLOR_BGR2GRAY);
		Imgcodecs.imwrite("/Users/hecj/Desktop/p31.jpeg", dst);
		// 二值滤波
		Mat threshold = new Mat();
        Imgproc.threshold(dst, threshold, 0, 255, Imgproc.THRESH_BINARY | Imgproc.THRESH_OTSU);//灰度图像二值化
        Imgcodecs.imwrite("/Users/hecj/Desktop/p311.jpeg", threshold);
        // 中值滤波
		Mat median = new Mat();
	    Imgproc.medianBlur(threshold, median, 5);
	    Imgcodecs.imwrite("/Users/hecj/Desktop/p3111.jpeg", median);
	}
	
	/**
	 * 最大值滤波(膨胀)
	 */
	@Test
	public void dilate() {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat src = Imgcodecs.imread("/Users/hecj/Desktop/p3.jpeg");
		Mat dst = new Mat();
		// 灰色
		Imgproc.cvtColor(src, dst, Imgproc.COLOR_BGR2GRAY);
		Imgcodecs.imwrite("/Users/hecj/Desktop/p31.jpeg", dst);
		// 二值滤波
		Mat threshold = new Mat();
		Imgproc.threshold(dst, threshold, 0, 255, Imgproc.THRESH_BINARY | Imgproc.THRESH_OTSU);//灰度图像二值化
		Imgcodecs.imwrite("/Users/hecj/Desktop/p311.jpeg", threshold);
       
		// 卷积核
        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(3,3));
        // 最大值滤波
		Mat dilate = new Mat();
	    Imgproc.dilate(threshold, dilate, kernel);
	    Imgcodecs.imwrite("/Users/hecj/Desktop/p3111.jpeg", dilate);
	}
	
	/**
	 * 中值滤波->膨胀
	 */
	@Test
	public void medianBlurdilate() {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat src = Imgcodecs.imread("/Users/hecj/Desktop/p311111.jpeg");
		// 卷积核
        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(3,3));
        // 最大值滤波
		Mat dilate = new Mat();
	    Imgproc.dilate(src, dilate, kernel);
	    Imgcodecs.imwrite("/Users/hecj/Desktop/p3111111.jpeg", dilate);
	}
	
	/**
	 * 最小值滤波(腐蚀)
	 */
	@Test
	public void erode() {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat src = Imgcodecs.imread("/Users/hecj/Desktop/p3.jpeg");
		Mat dst = new Mat();
		// 灰色
		Imgproc.cvtColor(src, dst, Imgproc.COLOR_BGR2GRAY);
		Imgcodecs.imwrite("/Users/hecj/Desktop/p31.jpeg", dst);
		// 二值化
		Mat threshold = new Mat();
		Imgproc.threshold(dst, threshold, 0, 255, Imgproc.THRESH_BINARY | Imgproc.THRESH_OTSU);//灰度图像二值化
		Imgcodecs.imwrite("/Users/hecj/Desktop/p311.jpeg", threshold);
        // 卷积核
        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(3,3));
        // 最小值滤波
		Mat dilate = new Mat();
	    Imgproc.erode(threshold, dilate, kernel);
	    Imgcodecs.imwrite("/Users/hecj/Desktop/p3111.jpeg", dilate);
	}

	;
	
}