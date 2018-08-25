package test.opencv;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * 通道理解
 * 3通道顺序 BGR
 * 
 * @author hecj 1通道的是灰度图。 3通道的是彩色图像，比如RGB图像。
 *         4通道的图像是RGBA，是RGB加上一个A通道，也叫alpha通道，表示透明度。PNG图像是一种典型的4通道图像。alpha通道可以赋值0到1，或者0到255，表示透明到不透明。
 *         2通道的图像是RGB555和RGB565。2通道图在程序处理中会用到，如傅里叶变换，可能会用到，一个通道为实数，一个通道为虚数，主要是编程方便。RGB555是16位的，2个字节，5+6+5，第一字节的前5位是R，后三位+第二字节是G，第二字节后5位是B，可见对原图像进行压缩了。
 */
public class Channel {

	/**
	 * 通道转换
	 */
	@Test
	public void channels() {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat src = Imgcodecs.imread("/Users/hecj/Desktop/color.png");
		// 通道数 3
		System.out.println(src.channels());

		Mat imageGRAY = new Mat();
		Mat imageRGBA = new Mat();
		Mat imageRGB555 = new Mat();

		Imgproc.cvtColor(src, imageGRAY, Imgproc.COLOR_RGB2GRAY);
		Imgproc.cvtColor(src, imageRGBA, Imgproc.COLOR_RGB2RGBA);
		Imgproc.cvtColor(src, imageRGB555, Imgproc.COLOR_RGB2BGR555);
		// 1
		System.out.println(imageGRAY.channels());
		// 4
		System.out.println(imageRGBA.channels());
		// 2
		System.out.println(imageRGB555.channels());

		Imgcodecs.imwrite("/Users/hecj/Desktop/color1.png", imageGRAY);
		Imgcodecs.imwrite("/Users/hecj/Desktop/color2.png", imageRGBA);
		// 2通道无法显示
//		Imgcodecs.imwrite("/Users/hecj/Desktop/WindowsLogo3.jpg", imageRGB555);
	}

	/**
	 * 通道分解
	 */
	@Test
	public void channels_split() {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat src = Imgcodecs.imread("/Users/hecj/Desktop/color.png");
		List<Mat> channels = new ArrayList<Mat>();
		Core.split(src, channels);
		Mat B = channels.get(0);
		Mat G = channels.get(1);
		Mat R = channels.get(2);
		Imgcodecs.imwrite("/Users/hecj/Desktop/B.png", B);
		Imgcodecs.imwrite("/Users/hecj/Desktop/G.png", G);
		Imgcodecs.imwrite("/Users/hecj/Desktop/R.png", R);
	}

	/**
	 * 通道合成
	 */
	@Test
	public void channels_merge() {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat src = Imgcodecs.imread("/Users/hecj/Desktop/color.png");
		List<Mat> channels = new ArrayList<Mat>();
		Core.split(src, channels);
		// 1通道图像
//		Mat mat1 = new Mat(src.size(), CvType.CV_8UC1, new Scalar(0));
		Mat mat1 = new Mat(src.size(), CvType.CV_8UC1, new Scalar(255));
		// 新的3通道
		List<Mat> newChannels = new ArrayList<Mat>(3);

		// 3通道图像
		Mat newImageB = new Mat(src.size(), CvType.CV_8UC3);
		newChannels.clear();
		newChannels.add(0, channels.get(0));
		newChannels.add(1, mat1);
		newChannels.add(2, mat1);
		// 新的3通道合并为图像
		Core.merge(newChannels, newImageB);
		Imgcodecs.imwrite("/Users/hecj/Desktop/newImageB.png", newImageB);

		// 3通道图像
		Mat newImageG = new Mat(src.size(), CvType.CV_8UC3);
		newChannels.clear();
		newChannels.add(0, mat1);
		newChannels.add(1, channels.get(1));
		newChannels.add(2, mat1);
		// 新的3通道合并为图像
		Core.merge(newChannels, newImageG);
		Imgcodecs.imwrite("/Users/hecj/Desktop/newImageG.png", newImageG);

		// 3通道图像
		Mat newImageR = new Mat(src.size(), CvType.CV_8UC3);
		newChannels.clear();
		newChannels.add(0, mat1);
		newChannels.add(1, mat1);
		newChannels.add(2, channels.get(2));
		// 新的3通道合并为图像
		Core.merge(newChannels, newImageR);
		Imgcodecs.imwrite("/Users/hecj/Desktop/newImageR.png", newImageR);
	}
	
	/**
	 * 制作图像透明图
	 */
	@Test
	public void channels_transparent() {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat src = Imgcodecs.imread("/Users/hecj/Desktop/timg.jpeg");
		
		// 定义透明度A通道
		Mat img0 = new Mat(src.size(), src.depth(), new Scalar(85));
		
		// 定义合成后的RGBA透明度图像
		Mat imgRGBA = new Mat(src.size(), CvType.CV_8UC4);
		
		List<Mat> matList = new ArrayList<Mat>();
		matList.add(0, src);
		matList.add(1, img0);
		
		Core.mixChannels(matList, Arrays.asList(imgRGBA), new MatOfInt(0,0));
		Imgcodecs.imwrite("/Users/hecj/Desktop/imgRGBA.png", imgRGBA);

	}
	
}
