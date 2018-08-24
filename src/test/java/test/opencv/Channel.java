package test.opencv;

import org.junit.Test;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
/**
 * 通道理解
 * @author hecj
 1通道的是灰度图。
 3通道的是彩色图像，比如RGB图像。
 4通道的图像是RGBA，是RGB加上一个A通道，也叫alpha通道，表示透明度。PNG图像是一种典型的4通道图像。alpha通道可以赋值0到1，或者0到255，表示透明到不透明。
 2通道的图像是RGB555和RGB565。2通道图在程序处理中会用到，如傅里叶变换，可能会用到，一个通道为实数，一个通道为虚数，主要是编程方便。RGB555是16位的，2个字节，5+6+5，第一字节的前5位是R，后三位+第二字节是G，第二字节后5位是B，可见对原图像进行压缩了。
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
	 * 通道分解、合成
	 */
	@Test
	public void channels_split() {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat src = Imgcodecs.imread("/Users/hecj/Desktop/color.png");
		
	}
}
