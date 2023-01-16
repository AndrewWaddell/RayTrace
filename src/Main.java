import javax.swing.*;

public class Main {
    public static void main(String[] args) {


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
        SwingUtilities.invokeLater(() -> createAndShowGUI(opticalBench.points2DList));

    }
    private static void createAndShowGUI(int[][] pointsToPlot) {
        System.out.println("Created GUI on EDT? "+
                SwingUtilities.isEventDispatchThread());
        JFrame f = new JFrame("Ray Trace Application");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(new MainPanel(pointsToPlot));
        f.pack();
        f.setVisible(true);
    }
}