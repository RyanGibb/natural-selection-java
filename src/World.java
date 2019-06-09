import java.util.ArrayList;
import java.util.List;

public class World {
    public static final int NUM_ENT = 10;
    public static final Point DIMENSIONS = new Point(1920, 1080);

    static boolean run = true;
    static int sleep_ms = 20;
    Simulation sim;

    List<Entity> entities;
    double i = 10;

    public static void main(String[] args) throws InterruptedException {
        World world = new World();
        while(run) {
            world.tick();
            world.sim.repaint();
            Thread.sleep(sleep_ms);
        }
    }

    public World () {
        sim = new Simulation(this);
        entities = new ArrayList<>();
        for (int i = 0; i < NUM_ENT; i++) {
            double x = Math.random() * DIMENSIONS.x + 1;
            double y = Math.random() * DIMENSIONS.y + 1;
            entities.add(new Entity(new Point(x, y)));
        }
    }

    private void tick() {
        i += 0.1;
        for (Entity entity : entities) {
            entity.p.x += 1.5;
        }
    }

}
