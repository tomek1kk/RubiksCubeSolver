import org.opencv.core.Mat;

import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Frame extends JFrame implements KeyListener {


    private CameraLoader camera;
    private CubeDetector cubeDetector;
    public Frame(CameraLoader camera, CubeDetector cubeDetector) {
        super("Hello world");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setPreferredSize(new Dimension(300, 100));
        addKeyListener(this);
        this.camera = camera;
        this.cubeDetector = cubeDetector;
        pack();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void keyPressed(KeyEvent evt) {
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            camera.saveNextFrame = true;
            Mat cameraSnapshot = null;
            while (cameraSnapshot == null) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                cameraSnapshot = camera.getFrame();
            }
            cameraSnapshot = cubeDetector.processImage(cameraSnapshot);
        }
    }

    @Override
    public void keyReleased(KeyEvent evt) {

    }

    @Override
    public void keyTyped(KeyEvent evt) {

    }
}