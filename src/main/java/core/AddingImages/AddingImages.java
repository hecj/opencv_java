package core.AddingImages;

import java.util.Locale;
import java.util.Scanner;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;

/**
 * 图片融合
 * addWeighted
 * https://blog.csdn.net/u011503970/article/details/18615537
1、 第1个参数，输入图片1， 
2、第2个参数，图片1的融合比例
3、第3个参数，输入图片2
4、第4个参数，图片2的融合比例
5、第5个参数，偏差
6、第6个参数，输出图片
 * @author hecj
 *
 */
class AddingImagesRun{
    public void run() {
        double alpha = 0.5; double beta; double input;

        Mat src1, src2, dst = new Mat();

        System.out.println(" Simple Linear Blender ");
        System.out.println("-----------------------");
        System.out.println("* Enter alpha [0.0-1.0]: ");
        Scanner scan = new Scanner( System.in ).useLocale(Locale.US);
        input = scan.nextDouble();

        if( input >= 0.0 && input <= 1.0 )
            alpha = input;

        //! [load]
        src1 = Imgcodecs.imread("/Users/hecj/workspace/demo/opencv_java/src/main/resources/data/LinuxLogo.jpg");
        src2 = Imgcodecs.imread("/Users/hecj/workspace/demo/opencv_java/src/main/resources/data/WindowsLogo.jpg");
        //! [load]

        if( src1.empty() == true ){ System.out.println("Error loading src1"); return;}
        if( src2.empty() == true ){ System.out.println("Error loading src2"); return;}

        //! [blend_images]
        beta = ( 1.0 - alpha );
        Core.addWeighted( src1, alpha, src2, beta, 0.0, dst);
        //! [blend_images]

        //![display]
        HighGui.imshow("Linear Blend", dst);
        HighGui.waitKey(0);
        //![display]

        System.exit(0);
    }
}

public class AddingImages {
    public static void main(String[] args) {
        // Load the native library.
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        new AddingImagesRun().run();
    }
}
