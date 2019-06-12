import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.geom.Ellipse2D;
import java.util.Collection;
import java.util.HashSet;

public class SimGUI {
    private static final GraphicsDevice DEVICE = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];
    private static final String TOGGLE_FULLSCREEN_ACTION_MAP_KEY = "toggleFullscreen";
    private static final String PAUSE_ACTION_MAP_KEY = "pause";
    private static final String CHANGE_SPEED_ACTION_MAP_KEY = "changeSpeed";

    private static final String TITLE = "Natural Selection Simulation";

    Simulation sim;
    JFrame frame;

    public Collection<Animal> animals = new HashSet<>();
    public Collection<Plant> plants = new HashSet<>();

    public SimGUI(Simulation sim) {
        this.sim = sim;
        frame = getFrame();
        toggleFullscreen();
        frame.setVisible(true);
    }

    public JFrame getFrame() {
        JFrame frame = new JFrame(TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize((int) sim.world.dimensions.getX(),(int) sim.world.dimensions.getY());
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        frame.setUndecorated(false);

        frame.add(getPanel());
        frame.pack();
//        this.createBufferStrategy(2);
//        sim.strategy = this.getBufferStrategy();
        return frame;
    }

    private JPanel getPanel() {
        JPanel panel = new JPanel() {
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                render(g);
            }
        };

        JButton btnFullScreen = new JButton("Toggle Fullscreen");
        btnFullScreen.addActionListener(e -> toggleFullscreen());
        btnFullScreen.setToolTipText("Shortcut: F11");
        panel.add(btnFullScreen);

        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F11, 0), TOGGLE_FULLSCREEN_ACTION_MAP_KEY);
        panel.getActionMap().put(TOGGLE_FULLSCREEN_ACTION_MAP_KEY, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                toggleFullscreen();
            }
        });

        JButton btnPause = new JButton("Pause");
        btnPause.addActionListener(e -> sim.pause());
        btnPause.setToolTipText("Shortcut: Space");
        panel.add(btnPause);

        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), PAUSE_ACTION_MAP_KEY);
        panel.getActionMap().put(PAUSE_ACTION_MAP_KEY, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                new Thread(() -> sim.pause()).run();
            }
        });

        JButton btnSpeedMax = new JButton("Speed: Max");
        btnSpeedMax.addActionListener(e -> { sim.sleep_ms = 0; });
        btnSpeedMax.setToolTipText("Shortcut: 0");
        panel.add(btnSpeedMax);

        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_0, 0), CHANGE_SPEED_ACTION_MAP_KEY + 0);
        panel.getActionMap().put(CHANGE_SPEED_ACTION_MAP_KEY + 0, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                sim.sleep_ms = 0;
            }
        });

        for (int i = 1; i <= 8; i *= 2) {
            JButton btnSpeed = new JButton(i + "x");
            int new_sleep_ms = sim.SLEEP_MS_BASE / i;
            btnSpeed.addActionListener(e -> {
                sim.sleep_ms = new_sleep_ms;
                System.out.println(new_sleep_ms);
            });
            btnSpeed.setToolTipText("Shortcut: " + i);
            panel.add(btnSpeed);

            panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_0 + i, 0), CHANGE_SPEED_ACTION_MAP_KEY + i);
            panel.getActionMap().put(CHANGE_SPEED_ACTION_MAP_KEY + i, new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    sim.sleep_ms = new_sleep_ms;
                }
            });
        }
        return panel;
    }

    //    public void renderingLoop() {
//        while (render) {
////            Graphics g = this.getGraphics();
//            Graphics g;
//            try {
//                g = strategy.getDrawGraphics();
//                render(g);
//            } finally {
//                g.dispose();
//            }
//            strategy.show();
//        }
//    }

    private void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setBackground(Color.WHITE);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for (Animal animal : animals) {
            drawCircle(g2d, animal.color, animal.loc, animal.radius);
        }
        for (Plant plant : plants) {
            drawCircle(g2d, plant.color, plant.loc, plant.radius);
        }
    }

    private void drawCircle(Graphics2D g2d, Color color, Point loc, double radius) {
        double diameter = radius * 2;
        Ellipse2D.Double circle = new Ellipse2D.Double(loc.getX() - radius, loc.getY() - radius, diameter, diameter);
        g2d.setColor(color);
        g2d.fill(circle);
        g2d.setColor(Color.black);
        g2d.draw(circle);
    }

    private void toggleFullscreen() {
        if(!DEVICE.isFullScreenSupported()) {
            JOptionPane.showMessageDialog(frame, "Fullscreen is not supported for this display device.");
            return;
        }
        if (DEVICE.getFullScreenWindow() == null) {
            DEVICE.setFullScreenWindow(frame);
            frame.setIgnoreRepaint(true);
        }
        else {
            DEVICE.setFullScreenWindow(null);
            frame.setIgnoreRepaint(false);
        }
    }

    public void display() {
        animals = new HashSet<>(sim.world.animals);
        plants = new HashSet<>(sim.world.plants);
        frame.repaint();
    }

}
