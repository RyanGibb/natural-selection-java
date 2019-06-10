import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Ellipse2D;
import java.util.Collection;
import java.util.HashSet;

public class Simulation extends Frame implements Runnable{
    boolean run = true;
    int sleep_ms = 20;
    World world;

    public static void main(String[] args) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Point.dimensions = new Point(screenSize.getHeight(), screenSize.getWidth());
        World world = new World();
        Simulation sim = new Simulation(world);
        sim.run();
    }

    public Simulation(World world) {
        super("Natural Selection Simulator");
        this.world = world;

        setSize((int) Point.dimensions.getX(),(int) Point.dimensions.getY());
        setVisible(true);

        addWindowListener(
                new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        dispose();
                        System.exit(0);
                    }
                }
        );
    }

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        Collection<Animal> animals = new HashSet<>(world.animals);
        Collection<Plant> plants = new HashSet<>(world.plants);
        for (Animal animal : animals) {
            draw_circle(g2d, animal.color, animal.loc, animal.radius);
        }
        for (Plant plant : plants) {
            draw_circle(g2d, plant.color, plant.loc, plant.radius);
        }
    }

    private void draw_circle(Graphics2D g2d, Color color, Point loc, double radius) {
        double diameter = radius * 2;
        Ellipse2D.Double circle = new Ellipse2D.Double(loc.getX() - radius, loc.getY() - radius, diameter, diameter);
        g2d.setColor(color);
        g2d.fill(circle);
        g2d.setColor(Color.black);
        g2d.draw(circle);
    }

    @Override
    public void run() {
        while(run) {
            world.tick();
            repaint();
            try {
                Thread.sleep(sleep_ms);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

}
