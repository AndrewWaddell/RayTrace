import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {
    int[][] pointsToPlot;
    int width = 1000;
    int height = 1000;

    public MainPanel() {

        setBorder(BorderFactory.createLineBorder(Color.black));

    }


    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i=0;i<5;i++){
            g.drawLine(pointsToPlot[i][0], pointsToPlot[i][1], pointsToPlot[i][2], pointsToPlot[i][3]);
        }
    }
}
