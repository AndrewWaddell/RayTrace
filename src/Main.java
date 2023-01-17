import javax.swing.*;
import java.awt.*;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class Main extends JPanel{
    static int width = 1000;
    static int height = 1000;

    public void paintComponent(Graphics g) {

        Scene opticalBench = new Scene();
        opticalBench.sources.add(new Source());
        opticalBench.shapes.add(new Shape());
        opticalBench.trace(1);

        // testing:
        System.out.println("ray points");
        for (int i=0;i<opticalBench.rays.pointsAcc.size();i++){
//            System.out.println(i);
//            opticalBench.rays.unitAcc.get(i).print();
        }
        opticalBench.plot();

//        // Obtain Graphics Panel Width and Height
//        int width = getWidth();
//        int height = getHeight();

        // Set colour of paint
        super.paintComponent(g);
        g.setColor(Color.BLACK);

        // Scale points to fit Panel
        int[][] pointsToPlot = opticalBench.points2DList;

        int[] xValues = new int[10];
        int[] yValues = new int[10];
        for (int i=0;i<pointsToPlot.length;i++){
            xValues[2*i] = pointsToPlot[i][0];;
            yValues[2*i] = pointsToPlot[i][1];
            xValues[2*i+1] = pointsToPlot[i][2];
            yValues[2*i+1] = pointsToPlot[i][3];
        }
        Arrays.sort(xValues);
        Arrays.sort(yValues);
        int xScale = width/(xValues[xValues.length - 1] - xValues[0]);
        int yScale = height/(yValues[yValues.length - 1] - yValues[0]);
        int scaleFactor = Math.max(xScale, yScale);

        for (int i=0;i<pointsToPlot.length;i++){
            pointsToPlot[i][0] = (scaleFactor * pointsToPlot[i][0]) + width/2;
            pointsToPlot[i][1] = (scaleFactor * pointsToPlot[i][1]) + height/2;
            pointsToPlot[i][2] = (scaleFactor * pointsToPlot[i][2]) + width/2;
            pointsToPlot[i][3] = (scaleFactor * pointsToPlot[i][3]) + height/2;
        }
        System.out.println("New Points to Plot");
        System.out.println(Arrays.toString(pointsToPlot[0]));
        // Scale Factor Testing
        System.out.println("Graphics Width is:");
        System.out.println(width);
        System.out.println("Graphics Height is:");
        System.out.println(height);
        System.out.println("X and Y Values");
        System.out.println(Arrays.toString(xValues));
        System.out.println(Arrays.toString(yValues));
        System.out.println("Scale Factor is:");
        System.out.println(scaleFactor);


        for (int i=0;i<5;i++){
            g.drawLine(pointsToPlot[i][0], pointsToPlot[i][1], pointsToPlot[i][2], pointsToPlot[i][3]);
        }
    }
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new Main());
        frame.setVisible(true);
    }
}