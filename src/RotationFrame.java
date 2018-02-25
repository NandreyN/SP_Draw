import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class RotationFrame extends JFrame {
    static final int V_MIN = 0;
    static final int V_MAX = 5;
    static final int V_INIT = 3;
    static final int TIMER_DELAY = 10;

    private class IconPanel extends JLabel {

    }

    private class SurfacePanel extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(currentGraphics, 0, 0, null);
        }
    }

    private ImageIcon wheel;
    private JPanel speedEditPanel;
    private JPanel surfacePanel;
    private BufferedImage currentGraphics;
    private Point centre;
    private JSlider slider;
    private int width, height;
    private Timer updateTimer;
    private double T, tRatio;
    private double V;

    private double R, pR;
    private double angle;
    private double deltaAnglePerTick;

    private String[] directions = new String[]{"Anticlockwise", "Clockwise"};
    private JComboBox<String> list;

    public RotationFrame(String name) {
        super(name);
        surfacePanel = new SurfacePanel();
        speedEditPanel = new JPanel();
        slider = new JSlider(V_MIN, V_MAX, V_INIT);
        slider.setMajorTickSpacing(V_MAX);
        slider.setMinorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);

        list = new JComboBox<>();
        for (int i = 0; i < directions.length; i++)
            list.addItem(directions[i]);

        list.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkSpeedDirection();
            }
        });

        speedEditPanel.setLayout(new BorderLayout());
        speedEditPanel.add(slider, BorderLayout.CENTER);
        speedEditPanel.add(list, BorderLayout.WEST);
        slider.addChangeListener(e -> {
            V = slider.getValue();
            T = 2 * Math.PI * (R / 100) / V;
            if (T == 0) {
                deltaAnglePerTick = 0;
                currentGraphics = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                centre = new Point(width / 2, height / 2);
                return;
            }
            int ticks = (int) (T * 1000 / TIMER_DELAY);

            deltaAnglePerTick = 2 * Math.PI / ticks;

            System.out.println("SPD : " + V + ", T = " + T + ", deltaAngle = " + deltaAnglePerTick + ", R = " + R / 100 + " ,V = " +
                    2 * Math.PI * (R / 100) / T);

        });
        V = V_INIT;
        tRatio = 1;

        wheel = getScaledImage(new ImageIcon("wheel.jpg"), 80, 80);

        setLayout(new BorderLayout());
        add(surfacePanel, BorderLayout.CENTER);
        add(speedEditPanel, BorderLayout.SOUTH);

        surfacePanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                width = surfacePanel.getWidth();
                height = surfacePanel.getHeight();
                R = Integer.min(width / 2, height / 2) - wheel.getIconHeight() / 2;
                if (R == 0)
                    return;

                if (V == 0) {
                    T = 0;
                    deltaAnglePerTick = 0;
                    return;
                }

                T = 2 * Math.PI * (R / 100) / V;
                if (T == 0) {
                    deltaAnglePerTick = 0;
                    currentGraphics = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                    centre = new Point(width / 2, height / 2);
                    return;
                }
                int ticks = (int) (T * 1000 / TIMER_DELAY);

                deltaAnglePerTick = 2 * Math.PI / ticks;

                System.out.println("SPD : " + V + ", T = " + T + ", deltaAngle = " + deltaAnglePerTick + ", R = " + R / 100 + " ,V = " +
                        2 * Math.PI * (R / 100) / T);
                currentGraphics = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                centre = new Point(width / 2, height / 2);
                //updateImage();
            }
        });

        updateTimer = new Timer(TIMER_DELAY, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkSpeedDirection();
                angle += deltaAnglePerTick;
                updateImage();
            }
        });
        updateTimer.start();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                updateTimer.stop();
            }
        });
    }

    private void checkSpeedDirection()
    {
        int idx = list.getSelectedIndex();
        switch (idx) {
            case 0:
                deltaAnglePerTick = Math.abs(deltaAnglePerTick);
                break;
            case 1:
                deltaAnglePerTick = (deltaAnglePerTick >= 0) ? -deltaAnglePerTick : deltaAnglePerTick;
                break;
        }
    }

    private ImageIcon getScaledImage(ImageIcon imageIcon, int w, int h) {
        Image image = imageIcon.getImage(); // transform it
        Image newimg = image.getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        return new ImageIcon(newimg);
    }

    public static void main(String[] args) {
        RotationFrame mainFrame = new RotationFrame("Rotatin");
        mainFrame.setBounds(0, 0, 500, 500);
        mainFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        mainFrame.setVisible(true);
    }

    private void updateImage() {
        if (currentGraphics == null)
            return;
        Graphics g = currentGraphics.getGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, width, height);
        g.setColor(Color.black);
        int dX = (int) (R * Math.sin(angle)),
                dY = (int) (R * Math.sin(Math.PI / 2 - angle));

        int x = (int) (centre.getX() + dX - wheel.getIconWidth() / 2);
        int y = (int) (centre.getY() + dY - wheel.getIconHeight() / 2);
        g.drawImage(wheel.getImage(), x,
                y, null);
        g.dispose();

        surfacePanel.repaint();
    }
}
