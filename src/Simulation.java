import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
        World world = new World();
        Simulation sim = new Simulation(world);
        sim.run();
    }

    public Simulation(World world) {
        super("Natural Selection Simulator");
        this.world = world;
        setSize((int) world.dimensions.x,(int) world.dimensions.y);
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
        Collection<Entity> entities = new HashSet<>(world.animals);
        entities.addAll(world.plants);
        for (Entity entity : entities) {
            draw_circle(g2d, entity.color, entity.loc, entity.radius);
        }
    }

    private void draw_circle(Graphics2D g2d, Color color, Point loc, double radius) {
        double diameter = radius * 2;
        Ellipse2D.Double circle = new Ellipse2D.Double(loc.x - radius, loc.y - radius, diameter, diameter);
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
