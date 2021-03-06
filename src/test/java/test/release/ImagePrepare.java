package test.release;

import java.io.File;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * 图片批量预处理 
 * @author hecj 
 */
public class ImagePrepare {

	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		// 目录
		String path = "/Users/hecj/Desktop/ocrdata/20180830/image2";
		File dir = new File(path);
		String files [] = dir.list();
		for(String file : files) {
			if(".DS_Store".equals(file)) {
				continue;
			}
			dealImage(path, file);
		}
	}
	
	public static void dealImage(String dir,String file) {
		Mat src = Imgcodecs.imread(dir+"/"+file);
		// 大小
		int newRows = 300;
        int newCols = (int)((double)newRows/src.size().width * src.size().height);
        Mat size = new Mat();
        Imgproc.resize(src, size, new Size(newRows, newCols));
		// 灰度
		Mat hui = new Mat();
		Imgproc.cvtColor(size, hui, Imgproc.COLOR_BGR2GRAY);
		// 二值
		Mat erzhi = new Mat();
		Imgproc.threshold(hui, erzhi, 0, 255, Imgproc.THRESH_BINARY | Imgproc.THRESH_OTSU);// 灰度图像二值化
//		// 去噪
		Mat quzao = new Mat();
		Imgproc.medianBlur(erzhi, quzao, 7);
		Imgcodecs.imwrite(dir+"/ocr_"+file, quzao);
		
		src.release();
		hui.release();
		erzhi.release();
//		quzao.release();
		
	}
	
	
	
	
}