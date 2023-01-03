import java.awt.*;
// import java.io.*;
// import javax.imageio.*;
import javax.swing.*;

public class main_screen {
    public void GUI() {
        // Frame and panels setup
        JFrame frame = new JFrame();

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JPanel buttonsPanel = new JPanel();
        JPanel graphicsPanel = new JPanel();

        // Set up of the panel containing the buttons
        buttonsPanel.setLayout(new FlowLayout());
        buttonsPanel.add(new JButton("Inputs"));
        buttonsPanel.add(new JButton("Source"));
        buttonsPanel.add(new JButton("Shapes"));
        buttonsPanel.add(new JButton("Sensors"));
        buttonsPanel.add(new JButton("Outputs"));

        // Set up of the panel containing the graphics
        graphicsPanel.setBorder(BorderFactory.createLineBorder(Color.black));

        graphicsPanel.setLayout(new BoxLayout(graphicsPanel, BoxLayout.PAGE_AXIS));

        ImageIcon testPlot = new ImageIcon("img/ColorWaveDemo.png");
        JLabel picLabel = new JLabel(testPlot);

        graphicsPanel.add(picLabel);

        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        mainPanel.add(buttonsPanel);
        mainPanel.add(graphicsPanel);

        frame.add(mainPanel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Ray Trace Application");
        frame.pack();
        frame.setVisible(true);

    }
}
