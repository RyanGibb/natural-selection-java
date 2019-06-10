import java.util.*;

public class World {
    public static final int NUM_ENT = 100;
    public final Point dimensions = new Point(1920, 1080);

    Collection<Entity> entities;

    public World () {
        entities = new HashSet<>();
        for (int i = 0; i < NUM_ENT; i++) {
            double x = Math.random() * dimensions.x + 1;
            double y = Math.random() * dimensions.y + 1;
            entities.add(new Entity(new Point(x, y)));
        }
    }

    public void tick() {
        List<Entity> to_process = new LinkedList<>(entities);
        while (to_process.size() > 0) {
            Entity entity = to_process.get(0);
            Collection<Entity> removed = new ArrayList<>();
            removed.add(entity);
            entity.tick(this, removed);
            to_process.removeAll(removed);
        }
    }

}
