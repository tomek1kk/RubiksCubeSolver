import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

import javax.swing.*;
import java.awt.image.BufferedImage;

public class CameraLoader {

    public void loadCamera() {
        Mat frame = new Mat();
        VideoCapture camera= new VideoCapture();
        camera.open(0);
        JFrame jframe = new JFrame("Camera");
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel vidpanel = new JLabel();
        jframe.setContentPane(vidpanel);
        jframe.setVisible(true);
        jframe.setSize(650, 500);

        while (true) {
            if (camera.read(frame)) {
                ImageIcon image = new ImageIcon(getImage(frame));
                vidpanel.setIcon(image);
                vidpanel.repaint();
            }
        }
    }

    private BufferedImage getImage(Mat mat){
        int w = mat.cols();
        int h = mat.rows();
        byte[] dat = new byte[w * h * 3];
        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_3BYTE_BGR);
        Imgproc.cvtColor(mat, mat, Imgproc.COLOR_BGR2RGB);
        mat.get(0, 0, dat);
        img.getRaster().setDataElements(0, 0, mat.cols(), mat.rows(), dat);
        return img;
    }
}
