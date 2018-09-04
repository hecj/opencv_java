package test.kit;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

/**
 * 图片预处理
 * 
 * @author hecj
 */
public class ImagePrepare {

	/**
	 * 格式化大小
	 * 
	 * @param src
	 * @return
	 */
	public static Mat formatMatSize(Mat src) {
		return src.clone();
	}

	/**
	 * 灰色
	 */
	public static Mat gray(Mat src) {
		Mat gray = new Mat();
		Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);
		return gray;
	}

	/**
	 * 二值
	 */
	public static Mat threshold(Mat src) {
		Mat threshold = new Mat();
		Imgproc.threshold(src, threshold, 100, 120, Imgproc.THRESH_BINARY | Imgproc.THRESH_OTSU);
		return threshold;
	}

	/**
	 * 图片轮廓
	 * @param gray
	 * @return
	 */
	public static Mat imageOutline(Mat src) {
		// 灰度 二值
		Mat gray = gray(src);
		Mat threshold = threshold(gray);
		// 中值腐蚀
		Mat medianBlur = new Mat();
		Imgproc.medianBlur(threshold, medianBlur, 5);
		// 膨胀 腐蚀
		Mat sobel = new Mat();
		Imgproc.Sobel(medianBlur, sobel, CvType.CV_8U, 1, 0);
		
		Mat kernel1 = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(120, 10));
		Mat dilate = new Mat();
		Imgproc.dilate(sobel, dilate, kernel1);

		Mat kernel2 = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(10, 10));
		Mat erode = new Mat();
		Imgproc.erode(dilate, erode, kernel2);
		return erode;
	}
	
	/**
	 * 查找和筛选文字区域
	 * @param src
	 * @return
	 */
	public static List<MatOfPoint> findRegion(Mat erode) {
		
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
		return rectangleList;
	}
	
}
