import org.opencv.core.Mat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Frame extends JFrame implements KeyListener {

    private CameraLoader camera;
    private CubeDetector cubeDetector;
    private static JPanel cubePanel;

    public Frame(CameraLoader camera, CubeDetector cubeDetector) {
        super("Cube Detector");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        cubePanel = new CubePanel();

        getContentPane().add(cubePanel);
        cubePanel.setLayout(new java.awt.BorderLayout());
        setPreferredSize(new Dimension(500, 400));
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

    public static void addWall(CubeWall wall) {
        cubePanel.add(generateWallPanel(wall));
        cubePanel.repaint(0, 0, 200, 200);
    }

    private static WallPanel generateWallPanel(CubeWall wall) {
        WallPanel panel = new WallPanel();
        cubePanel.add(panel);

        switch (wall.wallColor) {
            case RED:
                break;
            case BLUE:
            case GREEN:
            case WHITE:
            case ORANGE:
            case YELLOW:
            case NOT_RECOGNIZED:
        }
        return panel;
    }
}