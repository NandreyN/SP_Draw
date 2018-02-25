import javax.swing.*;
import java.io.FileNotFoundException;

public class Chart extends JPanel {
    private EntityContainer<ChartEntity> container;
    public Chart()
    {
        container = new EntityContainer<>();
        try {
            new PieChartDataReader().read(container);
        } catch (FileNotFoundException | PieChartDataReader.InputDataFormatException e) {
            JOptionPane.showMessageDialog(null,e.getMessage() + ". Restart app, please");
            return;
        }
    }
}
