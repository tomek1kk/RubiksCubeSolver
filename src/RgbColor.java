import java.util.ArrayList;
import java.util.List;

public class RgbColor {
    public double R;
    public double G;
    public double B;

    public static List<RgbColor> cubeColors = getCubeColors();

    public RgbColor() {
    }

    public RgbColor(double R, double G, double B) {
        this.R = R;
        this.G = G;
        this.B = B;
    }

    public Color getColor() {
        if (R == 255 && G == 0 && B == 0)
            return Color.RED;
        if (R == 0 && G == 255 && B == 0)
            return Color.GREEN;
        if (R == 0 && G == 0 && B == 255)
            return Color.BLUE;
        if (R == 255 && G == 255 && B == 255)
            return Color.WHITE;
        if (R == 255 && G == 100 && B == 0)
            return Color.ORANGE;
        if (R == 255 && G == 255 && B == 0)
            return Color.YELLOW;
        return Color.NOT_RECOGNIZED;
    }

    private static List<RgbColor> getCubeColors() {
        List<RgbColor> colors = new ArrayList<>();
        colors.add(new RgbColor(255, 0, 0)); // RED
        colors.add(new RgbColor(0, 255, 0)); // GREEN
        colors.add(new RgbColor(0, 0, 255)); // BLUE
        colors.add(new RgbColor(255, 255, 255)); // WHITE
        colors.add(new RgbColor(255, 100, 0)); // ORANGE
        colors.add(new RgbColor(255, 255, 0)); // YELLOW
        return colors;
    }

    public static double colorsDiff(RgbColor c1, RgbColor c2) {
        return Math.pow((c1.R - c2.R) * 0.3, 2) + Math.pow((c1.G - c2.G) * 0.59, 2) + Math.pow((c1.B - c2.B) * 0.11, 2);
    }

}
