package test.kit;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * 图像识别
 * @author hecj
 *
 */
public class OCRKit {

	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		ImagePrepare imagePrepare = new ImagePrepare();
		Mat src = Imgcodecs.imread("/Users/hecj/Desktop/n2.jpeg");
		Mat formatMat = imagePrepare.formatMatSize(src);
		src.release();
		// 轮廓
		Mat outlineMat = ImagePrepare.imageOutline(formatMat);
		Imgcodecs.imwrite("/Users/hecj/Desktop/3.jpeg", outlineMat);
		List<MatOfPoint> rectangleList = imagePrepare.findRegion(outlineMat);
		
		String text = ocrText(formatMat, rectangleList);
		
		System.out.println(text);
	}
	
	public static String ocrText(Mat formatMat,List<MatOfPoint> rectangleList) {
		Imgproc.drawContours(formatMat, rectangleList, -1, new Scalar(0,0, 255));
		List<Mat> regionMatList = new ArrayList<Mat>();
		// 截取特征目标
		for(int i=0;i<rectangleList.size();i++) {
			MatOfPoint matOfPoint = rectangleList.get(i);
			List<Point> points = matOfPoint.toList();
			int x = (int)points.get(0).x-10;
			int y = (int)points.get(0).y+10;
			int width = (int)(points.get(1).x-points.get(0).x);
			int height = (int)(points.get(2).y-points.get(0).y);
	        Mat regionMat = new Mat(formatMat, new Rect(x, y, width, height));
	        regionMatList.add(regionMat);
		}
		return "";
	}
}
