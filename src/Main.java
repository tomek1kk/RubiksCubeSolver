import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;

public class Main {
    static {System.loadLibrary(Core.NATIVE_LIBRARY_NAME);}
    public static void main(String[] args) throws InterruptedException {
        CubeDetector cubeDetector = new CubeDetector();

        //Mat image = Imgcodecs.imread("input/cube.jpg");
        //image = cubeDetector.processImage(image);
        //cubeDetector.printWalls();
        //Imgcodecs.imwrite("output/edge.jpg", image);




        CameraLoader camera = new CameraLoader();
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Frame(camera, cubeDetector);
            }
        });

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                camera.loadCamera();
            }
        });
        t1.start();

        //MyKeyListener keyListener = new MyKeyListener(camera, cubeDetector);

//        Imgcodecs.imwrite("output/snap.jpg", cameraSnapshot);
    }


}

