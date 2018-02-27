import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;


public class PieChartFrame extends JFrame {

    private JPanel chart;
    private EntityContainer<ChartEntity> container;

    public PieChartFrame(String name) {
        super(name);
        container = new EntityContainer<>();
        try {
            new PieChartDataReader().read(container);
        } catch (FileNotFoundException | PieChartDataReader.InputDataFormatException e) {
            JOptionPane.showMessageDialog(null, e.getMessage() + ". Restart app, please");
            return;
        }

        chart = new ChartPanel(createChart());
        setLayout(new BorderLayout());
        add(chart, BorderLayout.CENTER);
    }

    private PieDataset createDataset() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        for (ChartEntity e : container.getEntities()) {
            dataset.setValue(e.getTitle(), e.getValue());
        }
        return dataset;
    }

    private JFreeChart createChart() {
        JFreeChart chart = ChartFactory.createPieChart(
                "Pie Chart",  // chart title
                createDataset(),             // data
                true,               // include legend
                true,
                false
        );

        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setLabelFont(new Font("SansSerif", Font.PLAIN, 12));
        plot.setNoDataMessage("No data available");
        plot.setCircular(false);
        plot.setLabelGap(0.02);

        PieSectionLabelGenerator gen = new StandardPieSectionLabelGenerator(
                "{0}: {1} ({2})", new DecimalFormat("0"), new DecimalFormat("0%"));
        plot.setLabelGenerator(gen);

        return chart;
    }

    public static void main(String[] args) {
        JFrame mainFrame = new PieChartFrame("Pie");
        mainFrame.setBounds(0, 0, 500, 500);
        mainFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        mainFrame.setVisible(true);
    }
}
