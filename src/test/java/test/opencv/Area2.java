package test.opencv;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
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
		Mat src = Imgcodecs.imread("/Users/hecj/Desktop/3.jpeg");
		Mat gray = new Mat();
		// 灰色
		Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);
		Imgcodecs.imwrite("/Users/hecj/Desktop/gray.jpeg", gray);

		Mat rest = preprocess(gray);
		Mat regin = findTextRegion(src,rest);

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
		Imgproc.threshold(gray, threshold, 100, 120, Imgproc.THRESH_BINARY | Imgproc.THRESH_OTSU);// 灰度图像二值化
		Imgcodecs.imwrite("/Users/hecj/Desktop/threshold.jpeg", threshold);

		// 中值腐蚀
		Mat medianBlur = new Mat();
	    Imgproc.medianBlur(threshold, medianBlur, 5);
		Imgcodecs.imwrite("/Users/hecj/Desktop/medianBlur.jpeg", medianBlur);

//		// 1. x方向求梯度
		Mat sobel = new Mat();
		Imgproc.Sobel(medianBlur, sobel, CvType.CV_8U, 1, 0);
//		Imgcodecs.imwrite("/Users/hecj/Desktop/sobel.jpeg", sobel);

		// 4. 膨胀一次，让轮廓突出
		Mat element2 = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(120, 10));
		Mat dilate = new Mat();
//		Imgproc.dilate(sobel, dilate, element2,new Point(0, 0),1);
		Imgproc.dilate(sobel, dilate, element2);
		Imgcodecs.imwrite("/Users/hecj/Desktop/dilate.jpeg", dilate);

//		// 5. 腐蚀一次，去掉细节，如表格线等。注意这里去掉的是竖直的线
		Mat element1 = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(10, 10));
		Mat erode = new Mat();
		Imgproc.erode(dilate, erode, element1);
		Imgcodecs.imwrite("/Users/hecj/Desktop/erode.jpeg", erode);

//		// 1. x方向求梯度
//		Mat sobel = new Mat();
//		Imgproc.Sobel(threshold, sobel, CvType.CV_8U, 1, 0);
//		Imgcodecs.imwrite("/Users/hecj/Desktop/sobel.jpeg", sobel);
//
//		// 3.膨胀和腐蚀操作的核函数
//		
////		Imgcodecs.imwrite("/Users/hecj/Desktop/element1.jpeg", element1);
//		Mat element2 = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(80, 20));
////		Imgcodecs.imwrite("/Users/hecj/Desktop/element2.jpeg", element2);
////		// 4. 膨胀一次，让轮廓突出
//		Mat dilate = new Mat();
//		Imgproc.dilate(sobel, dilate, element2);
//		Imgcodecs.imwrite("/Users/hecj/Desktop/dilate.jpeg", dilate);
////		
////		// 6. 再次膨胀，让轮廓明显一些
//		Mat dilate2 = new Mat();
//		Imgproc.dilate(erode, dilate2, element2);
//		Imgcodecs.imwrite("/Users/hecj/Desktop/dilate2.jpeg", dilate2);

		return erode;
	}

	/**
	 * 查找和筛选文字区域
	 * 
	 * @param src
	 * @return
	 */
	public static Mat findTextRegion(Mat src,Mat erode) {
		
		int maxWidth = erode.cols();
		int maxHeight = erode.rows();
		
		List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
		Mat hierarchy = new Mat();
		Imgproc.findContours(erode, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
		
		// 移除面积小的
		List<MatOfPoint> contoursResult = new ArrayList<MatOfPoint>();
		for (MatOfPoint cnt : contours) {
			double area = Imgproc.contourArea(cnt);
			if (area < 1200) {
				continue;
			}
			contoursResult.add(cnt);
		}
		
		// 将多边形转成矩形
		List<MatOfPoint> rectangleList = new ArrayList<MatOfPoint>();
		for (MatOfPoint m : contoursResult) {
			Point[] points = m.toArray();
			double minX=Double.MAX_VALUE,maxX=0,minY=Double.MAX_VALUE,maxY=0;
			for(Point p : points) {
				if(p.x<minX) {
					minX = p.x;
				}
				if(p.x>maxX) {
					maxX = p.x;
				}
				if(p.y<minY) {
					minY = p.y;
				}
				if(p.y>maxY) {
					maxY = p.y;
				}
			}
			// 过滤 靠近边框的矩形
			double marginMax = 10;
			if(minX < marginMax || minY < marginMax || maxWidth - maxX < marginMax || maxHeight- maxY < marginMax) {
				continue;
			}
			// 过滤 纵向的矩形(如高大于宽的1.5倍)
			if((maxY - minY ) > (maxX - minX)*1.5d ) {
				continue;
			}
			// 过滤 横条的矩形(如宽大于高的5倍)
			if(( maxX - minX) > (maxY - minY)*8d ) {
				continue;
			}
			
			Point[] targetPoints = new Point[] {
					new Point(minX,minY),
					new Point(maxX,minY),
					new Point(maxX,maxY),
					new Point(minX,maxY)
			};
			rectangleList.add(new MatOfPoint(targetPoints));
		}
		
		Imgproc.drawContours(src, rectangleList, -1, new Scalar(0,0, 255));
		Imgcodecs.imwrite("/Users/hecj/Desktop/drawContours.jpeg", src);
		
		// 截取特征目标
		for(int i=0;i<rectangleList.size();i++) {
			MatOfPoint matOfPoint = rectangleList.get(i);
			List<Point> points = matOfPoint.toList();
			int x = (int)points.get(0).x;
			int y = (int)points.get(0).y;
			int width = (int)(points.get(1).x-points.get(0).x);
			int height = (int)(points.get(2).y-points.get(0).y);
	        Mat target = new Mat(src, new Rect(x, y, width, height));
	        Imgcodecs.imwrite("/Users/hecj/Desktop/"+i+".jpeg", target);
		}
		
//		for (MatOfPoint cnt : contoursResult) {
			// 计算该轮廓的面积
//			double area = Imgproc.contourArea(cnt);
//			// 面积小的都筛选掉
//			if (area < 1000) {
//				continue;
//			}
			// 轮廓近似，作用很小
			// 计算高和宽
//	        height = Imgproc.abs(box.get(0, 1) - box.get(2, 1));
//	        width = abs(box[0][0] - box[2][0])
//	        # 筛选那些太细的矩形，留下扁的
//	        if(height > width * 1.2):
//	            continue
//	         region.append(box)
//		}
		return null;
	}

}