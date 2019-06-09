import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Simulation extends Frame {
    World world;

    public Simulation(World world) {
        super("Natural Selection Simulator");
        this.world = world;
        setSize(1920,1080);
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
        g.drawLine(0,0,1000,(int) (10 * world.i));
        for (Entity entity : world.entities) {
            g.drawRect((int) entity.p.x,(int) entity.p.y,50,50);
        }
        g.setColor(Color.red);
        Graphics2D g2d = (Graphics2D)g;
        g2d.setColor(Color.blue);
        g2d.drawRect(75,75,300,200);
    }
}
