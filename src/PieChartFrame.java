import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;

public class PieChartFrame extends JFrame {

    private JPanel chart;
    public PieChartFrame(String name)
    {
        super(name);
        chart = new Chart();
        setLayout(new BorderLayout());
        add(chart,BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        JFrame mainFrame = new PieChartFrame("Pie");
        mainFrame.setBounds(0, 0, 500, 500);
        mainFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        mainFrame.setVisible(true);
    }
}
