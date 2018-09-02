package test.opencv;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.RotatedRect;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * 文字区域查找
 * 
 * @author hecj
 */
public class Area2 {
	/**
	 * 轮廓提取 https://blog.csdn.net/huobanjishijian/article/details/63685503
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat src = Imgcodecs.imread("/Users/hecj/Desktop/p2.jpeg");
		Mat gray = new Mat();
		// 灰色
		Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);
		Imgcodecs.imwrite("/Users/hecj/Desktop/gray.jpeg", gray);

		Mat rest = preprocess(gray);
		Mat regin = findTextRegion(rest);

//		 # 4. 用绿线画出这些找到的轮廓
//		    for box in region:
//		        cv2.drawContours(img, [box], 0, (0, 255, 0), 2)

//		// 二值化
//		Mat threshold = new Mat();
//		Imgproc.threshold(dst, threshold, 0, 255, Imgproc.THRESH_BINARY | Imgproc.THRESH_OTSU);//灰度图像二值化
//		Imgcodecs.imwrite("/Users/hecj/Desktop/p311.jpeg", threshold);
//        // 卷积核
//        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(3,3));
//        // 最小值滤波
//		Mat dilate = new Mat();
//	    Imgproc.erode(threshold, dilate, kernel);
//	    Imgcodecs.imwrite("/Users/hecj/Desktop/p3111.jpeg", dilate);
	}

	/**
	 * 图片预处理
	 * 
	 * @param gray
	 * @return
	 */
	public static Mat preprocess(Mat gray) {
		// 2. 二值化
		Mat threshold = new Mat();
		Imgproc.threshold(gray, threshold, 0, 255, Imgproc.THRESH_BINARY | Imgproc.THRESH_OTSU);// 灰度图像二值化
		Imgcodecs.imwrite("/Users/hecj/Desktop/threshold.jpeg", threshold);

		// 1. x方向求梯度
		Mat sobel = new Mat();
		Imgproc.Sobel(threshold, sobel, CvType.CV_8U, 1, 0);
		Imgcodecs.imwrite("/Users/hecj/Desktop/sobel.jpeg", sobel);

		// 3.膨胀和腐蚀操作的核函数
		Mat element1 = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(30, 9));
//		Imgcodecs.imwrite("/Users/hecj/Desktop/element1.jpeg", element1);
		Mat element2 = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(80, 20));
//		Imgcodecs.imwrite("/Users/hecj/Desktop/element2.jpeg", element2);
//		// 4. 膨胀一次，让轮廓突出
		Mat dilate = new Mat();
		Imgproc.dilate(sobel, dilate, element2);
		Imgcodecs.imwrite("/Users/hecj/Desktop/dilate.jpeg", dilate);
//		// 5. 腐蚀一次，去掉细节，如表格线等。注意这里去掉的是竖直的线
		Mat erode = new Mat();
		Imgproc.erode(dilate, erode, element1);
		Imgcodecs.imwrite("/Users/hecj/Desktop/erode.jpeg", erode);
//		// 6. 再次膨胀，让轮廓明显一些
		Mat dilate2 = new Mat();
		Imgproc.dilate(erode, dilate2, element2);
		Imgcodecs.imwrite("/Users/hecj/Desktop/dilate2.jpeg", dilate2);

		return dilate2;
	}

	/**
	 * 查找和筛选文字区域
	 * 
	 * @param src
	 * @return
	 */
	public static Mat findTextRegion(Mat src) {
		List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
		Mat hierarchy = new Mat();
		Imgproc.findContours(src, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
		for (MatOfPoint cnt : contours) {
			// 计算该轮廓的面积
			double area = Imgproc.contourArea(cnt);
			// 面积小的都筛选掉
			if (area < 1000) {
				continue;
			}
			// 轮廓近似，作用很小
			double epsilon = 0.001 * Imgproc.arcLength(new MatOfPoint2f(cnt.toArray()), true);
			MatOfPoint2f approx = new MatOfPoint2f();
			Imgproc.approxPolyDP(new MatOfPoint2f(cnt.toArray()), approx, epsilon, true);
			// 找到最小的矩形，该矩形可能有方向
			RotatedRect rect = Imgproc.minAreaRect(new MatOfPoint2f(cnt.toArray()));
			// box是四个点的坐标
			MatOfPoint box = new MatOfPoint();
			Imgproc.boxPoints(rect, box);
			// 计算高和宽
//	        height = Imgproc.abs(box.get(0, 1) - box.get(2, 1));
//	        width = abs(box[0][0] - box[2][0])
//	        # 筛选那些太细的矩形，留下扁的
//	        if(height > width * 1.2):
//	            continue
//	         region.append(box)
		}
		return null;
	}

}