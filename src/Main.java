import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;

public class Main {
    static {System.loadLibrary(Core.NATIVE_LIBRARY_NAME);}
    public static void main(String[] args) {
        System.out.println("Hello world");
        System.out.println(Core.VERSION);

        Mat image = Imgcodecs.imread("input/cube.jpg");
        Mat grayImage = new Mat();
        Imgproc.cvtColor(image, grayImage, Imgproc.COLOR_RGB2GRAY);
        Mat blurred = new Mat();
        Imgproc.blur(grayImage, blurred, new Size(3,3));
        Mat canny = new Mat();
        Imgproc.Canny(blurred, canny, 20, 20, 2, true);

        Imgcodecs.imwrite("output/edge.jpg", canny);

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Frame();
            }
        });
    }
}

