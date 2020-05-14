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
        CubeDetector cubeDetector = new CubeDetector();
        image = cubeDetector.processImage(image);
        cubeDetector.printWalls();
        Imgcodecs.imwrite("output/edge.jpg", image);

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Frame();
            }
        });
        CameraLoader camera = new CameraLoader();
        camera.loadCamera();

    }


}

