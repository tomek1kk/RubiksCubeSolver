import javax.swing.*;
import java.awt.*;

public class WallPanel extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawRect(20, 20, 100, 100);
    }
}
