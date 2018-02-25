import javafx.scene.chart.Chart;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class PieChartDataReader {
    public class PieChartInputException extends Exception {
        public PieChartInputException(String message) {
            super(message);
        }
    }

    public class InputFileException extends FileNotFoundException {

        public InputFileException(String message) {
            super(message);
        }
    }

    public class InputDataFormatException extends PieChartInputException {

        public InputDataFormatException(String message) {
            super(message);
        }
    }

    private static final String FILE_NAME = "input.txt";

    public <T extends ChartEntity> void read(EntityContainer<T> readTo) throws FileNotFoundException, InputDataFormatException {
        File in = new File(FILE_NAME);
        if (!in.exists()) {
            throw new InputFileException("Missing file :" + FILE_NAME);
        }

        Scanner sc = new Scanner(in);
        int row = 1;
        while (sc.hasNextLine()) {
            String[] lineContent = sc.nextLine().split(";");
            if (lineContent.length != 2)
                throw new InputDataFormatException("Invalid number of arguments in at row #" + row);

            if (!isDouble(lineContent[1]))
                throw new InputDataFormatException("Second parameter in row " + row + " is not double");

            readTo.add((T) new ChartEntity(lineContent[0], Double.parseDouble(lineContent[1])));
            row++;
        }
    }

    public boolean isDouble(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }

    }
}
