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
    private static final Color HEALTH_COLOR = Color.red;
    private static final Color ENERGY_COLOR = Color.green;

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
        frame.setSize((int) sim.world.dimensions.x,(int) sim.world.dimensions.y);
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        frame.setUndecorated(false);

        frame.add(getPanel());
        frame.pack();
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
        btnPause.setToolTipText("Shortcut: a");
        panel.add(btnPause);

        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0), PAUSE_ACTION_MAP_KEY);
        panel.getActionMap().put(PAUSE_ACTION_MAP_KEY, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                new Thread(() -> sim.pause()).run();
            }
        });

        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_0, 0), CHANGE_SPEED_ACTION_MAP_KEY + 0);
        panel.getActionMap().put(CHANGE_SPEED_ACTION_MAP_KEY + 0, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                sim.sleep_ms = 0;
            }
        });

        for (int speedModifierInverse = 8, shortcut = 5; speedModifierInverse > 1; speedModifierInverse /= 2, shortcut++) {
            double speedModifier = 1.0 / speedModifierInverse;
            int new_sleep_ms = Simulation.SLEEP_MS_BASE * speedModifierInverse;
            addSpeedModifier(panel, new_sleep_ms, Double.toString(speedModifier), shortcut);
        }

        for (int speedModifier = 1, shortcut = 1; speedModifier <= 8; speedModifier *= 2, shortcut++) {
            int new_sleep_ms = Simulation.SLEEP_MS_BASE / speedModifier;
            addSpeedModifier(panel, new_sleep_ms, Integer.toString(speedModifier), shortcut);
        }

        JButton btnSpeedMax = new JButton("Speed: Max");
        btnSpeedMax.addActionListener(e -> sim.sleep_ms = 0 );
        btnSpeedMax.setToolTipText("Shortcut: 0");
        panel.add(btnSpeedMax);

        return panel;
    }

    private void addSpeedModifier(JPanel panel, int new_sleep_ms, String label, int shortcut) {
        JButton btnSpeed = new JButton("x" + label);
        btnSpeed.addActionListener(e -> {
            sim.sleep_ms = new_sleep_ms;
            System.out.println(new_sleep_ms);
        });
        btnSpeed.setToolTipText("Shortcut: " + shortcut);
        panel.add(btnSpeed);

        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_0 + shortcut, 0),
                        CHANGE_SPEED_ACTION_MAP_KEY + label);
        panel.getActionMap().put(CHANGE_SPEED_ACTION_MAP_KEY + label, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                sim.sleep_ms = new_sleep_ms;
            }
        });
    }

    private void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setBackground(Color.WHITE);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for (Animal animal : animals) {
            drawAnimal(g2d, animal.color, animal, animal.radius);
        }
        for (Plant plant : plants) {
            drawPlant(g2d, plant.color, plant, plant.radius);
        }
    }

    private void drawPlant(Graphics2D g2d, Color color, Plant plant, double radius) {
        Point topLeft = new Point(plant.loc.y - radius, plant.loc.x - radius);
        double diameter = radius * 2;
        drawCircle(g2d, plant.color, topLeft, diameter);
    }

    private void drawAnimal(Graphics2D g2d, Color color, Animal animal, double radius) {
        Point topLeft = new Point(animal.loc.y - radius, animal.loc.x - radius);
        Point centre = animal.loc;
        double diameter = radius * 2;
        drawCircle(g2d, animal.color, topLeft, diameter);

        double statusBarsSizeRatio = 0.2;
        double statusBarSize = statusBarsSizeRatio * diameter;
        g2d.setColor(HEALTH_COLOR);
        Rectangle.Double health = new Rectangle.Double(
                topLeft.x,
                centre.y - statusBarSize,
                diameter * animal.health / animal.max_health,
                statusBarSize);
        Rectangle.Double energy = new Rectangle.Double(
                topLeft.x,
                centre.y,
                diameter * animal.energy / animal.max_energy,
                statusBarSize);
        g2d.setColor(Color.red);
        g2d.fill(health);
        g2d.setColor(Color.green);
        g2d.fill(energy);
    }

    private void drawCircle(Graphics2D g2d, Color color, Point p, double diameter) {
        Ellipse2D.Double circle = new Ellipse2D.Double(p.x, p.y, diameter, diameter);
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
