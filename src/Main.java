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

        List<Rect> rectangles = new ArrayList<>();
        Mat image = Imgcodecs.imread("input/cube.jpg");
        Mat grayImage = new Mat();
        Imgproc.cvtColor(image, grayImage, Imgproc.COLOR_RGB2GRAY);
        Mat blurred = new Mat();
        Imgproc.blur(grayImage, blurred, new Size(3,3));
        Mat canny = new Mat();
        Imgproc.Canny(blurred, canny, 20, 40);

        Mat kernel = Imgproc.getStructuringElement(2, new Size(5,5));
        Mat dilated = new Mat();
        Imgproc.dilate(canny,dilated, kernel);

        // FIND COUNTURS
        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(dilated, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
        Mat drawing = Mat.zeros(dilated.size(), CvType.CV_8UC3);

        List<MatOfPoint2f> squareContours = new ArrayList<>();
        for (int i = 0; i < contours.size(); i++) {
            MatOfPoint2f approx = new MatOfPoint2f();
            double epsilon = 0.1 * Imgproc.arcLength(new MatOfPoint2f(contours.get(i).toArray()),true);
            Imgproc.approxPolyDP(new MatOfPoint2f(contours.get(i).toArray()), approx, epsilon, true);
            if (approx.rows() == 4 && approx.cols() == 1) {
                MatOfPoint points = new MatOfPoint(approx.toArray());
                if (Math.abs(Imgproc.contourArea(points)) > 500) {
                    Imgproc.drawContours(image, contours, i, new Scalar(150, 0, 0), 3);
                    Rect rect = Imgproc.boundingRect(points);
                    rectangles.add(rect);

                }
            }
        }

        if (rectangles.size() == 9 ) {
            System.out.println("Found all colors!");
            for (int i = 0; i < 9; i++) {
                Rect rect = rectangles.get(i);
                System.out.println("Rectangle: " + rect.x + "," + rect.y + ". Color: " + getColor(rect, image).toString());
            }
        }
        else {
            System.out.println("Cube was not recognized :(");
        }

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

    private static Color getColor(Rect rect, Mat image) {
        List<RgbColor> colors = RgbColor.cubeColors;
        double[] center = image.get(rect.x + rect.width / 2, rect.y + rect.height / 2);
        RgbColor centerColor = new RgbColor(center[0], center[1], center[2]);
        RgbColor closestColor = new RgbColor(0,0,0);

        double min = 999999;
        for (int i = 0; i < colors.size(); i++) {
            double diff = RgbColor.colorsDiff(centerColor, colors.get(i));
            if (diff < min) {
                min = diff;
                closestColor = new RgbColor(colors.get(i).R, colors.get(i).G, colors.get(i).B);
            }
        }
        return closestColor.getColor();
    }
}

