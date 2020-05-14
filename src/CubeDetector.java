import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

public class CubeDetector {
    // CUBE ORIENTATION
    // 7 8 9
    // 4 5 6
    // 1 2 3
    List<CubeWall> walls;
    public CubeDetector() {
        walls = new ArrayList<>();
    }

    public Mat processImage(Mat image) {
        Mat imageCopy = image.clone();
        List<Rect> rectangles = new ArrayList<>();
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
                if (Math.abs(Imgproc.contourArea(points)) > 300 && Math.abs(Imgproc.contourArea(points))< 15000) {
                    Imgproc.drawContours(image, contours, i, new Scalar(150, 0, 0), 3);
                    Rect rect = Imgproc.boundingRect(points);
                    rectangles.add(rect);
                }
            }
        }

        if (rectangles.size() == 9 ) {
            System.out.println("Found all colors!");
            CubeWall wall = generateCubeWall(rectangles, imageCopy);
            if (wall != null)
                walls.add(wall);
        }
        else {
            System.out.println("Cube was not recognized :(");
        }
        return image;
    }

    private CubeWall generateCubeWall(List<Rect> rects, Mat image) {
        Imgcodecs.imwrite("output/edge2.jpg", image);
        if (rects.size() != 9)
            return null;

        rects.sort((r1, r2) -> {
            if (r1.y > r2.y)
                return 1;
            if (r1.y == r2.y)
                return 0;
            return -1;
        });

        List<Rect> rectsBottom = rects.subList(0, 3);
        List<Rect> rectsMiddle = rects.subList(3, 6);
        List<Rect> rectsTop = rects.subList(6, 9);
        rectsBottom.sort((r1, r2) -> {
            if (r1.x > r2.x)
                return 1;
            if (r1.x == r2.x)
                return 0;
            return -1;
        });
        rectsMiddle.sort((r1, r2) -> {
            if (r1.x > r2.x)
                return 1;
            if (r1.x == r2.x)
                return 0;
            return -1;
        });
        rectsTop.sort((r1, r2) -> {
            if (r1.x > r2.x)
                return 1;
            if (r1.x == r2.x)
                return 0;
            return -1;
        });

        return new CubeWall(
                getColor(rectsBottom.get(0), image),
                getColor(rectsBottom.get(1), image),
                getColor(rectsBottom.get(2), image),
                getColor(rectsMiddle.get(0), image),
                getColor(rectsMiddle.get(1), image),
                getColor(rectsMiddle.get(2), image),
                getColor(rectsTop.get(0), image),
                getColor(rectsTop.get(1), image),
                getColor(rectsTop.get(2), image)
        );
    }


    private Color getColor(Rect rect, Mat image) {
        List<RgbColor> colors = RgbColor.cubeColors;
        double[] test = image.get(156, 74);
        double[] center = image.get(rect.x + rect.width / 2, rect.y + rect.height / 2);
        double[] point1 = image.get(rect.x + rect.width / 3, rect.y + rect.height / 3);
        double[] point2 = image.get(rect.x + rect.width * 2 / 3, rect.y + rect.height / 3);
        double[] point3 = image.get(rect.x + rect.width / 3, rect.y + rect.height * 2 / 3);
        double[] point4 = image.get(rect.x + rect.width * 2 / 3, rect.y + rect.height * 2 / 3);

        RgbColor centerColor = new RgbColor(
                (center[2] + point1[2] + point2[2] + point3[2] + point4[2]) / 5,
                (center[1] + point1[1] + point2[1] + point3[1] + point4[1]) / 5,
                (center[0] + point1[0] + point2[0] + point3[0] + point4[0]) / 5
        );
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

    public void printWalls() {
        walls.forEach(w -> w.printWall());
    }

}
