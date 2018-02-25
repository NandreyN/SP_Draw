import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class PaintFrame extends JFrame {
    class PaintPanel extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(currentGraphics, 0, 0, null);
            //g.drawOval(x,y,60,60);
        }
    }

    PaintPanel panel = new PaintPanel();
    static final int TICK = 100;

    int R;
    Timer timer;
    int x, y;
    double angle, anglesPerTick;
    BufferedImage currentGraphics;

    public PaintFrame(String name) {
        super(name);

        panel = new PaintPanel();
        add(panel);
        angle = Math.PI;
        anglesPerTick = 2*Math.PI / (60000 / TICK);

        timer = new Timer(TICK, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                angle -= anglesPerTick;
                updateGraphics();
            }
        });
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                x = getWidth();
                y = getHeight();
                R = Integer.min(x, y) / 2;
                R *= 0.8;

                currentGraphics = new BufferedImage(x, y, BufferedImage.TYPE_INT_RGB);
                updateGraphics();
            }
        });
        timer.start();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                timer.stop();
            }
        });
    }

    private void updateGraphics() {
        Graphics g = currentGraphics.getGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, x, y);
        g.setColor(Color.black);
        if (angle >= 2*Math.PI)
            angle -= 2*Math.PI;

        int dX = (int) (R * Math.sin(angle)), dY = (int) (R * Math.sin(Math.PI / 2 - angle));
        g.drawLine(x / 2, y / 2, x / 2 + dX, y / 2 + dY);
        g.drawOval(x / 2 - R, y / 2 - R, 2 * R, 2 * R);
        g.dispose();
        repaint();
    }

    public static void main(String[] args) {
        PaintFrame mainFrame = new PaintFrame("Clock");
        mainFrame.setBounds(0, 0, 500, 500);
        mainFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        mainFrame.setVisible(true);
    }
}
