import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class World {
    public static final int NUM_ENT = 3;
    public final Point dimensions = new Point(1920, 1080);

    List<Entity> entities;

    public World () {
        entities = new ArrayList<>();
        for (int i = 0; i < NUM_ENT; i++) {
            double x = Math.random() * dimensions.x + 1;
            double y = Math.random() * dimensions.y + 1;
            entities.add(new Entity(new Point(x, y)));
        }
    }

    public void tick() {
        Iterator<Entity> iter = entities.iterator();
        while (iter.hasNext()) {
            Entity entity = iter.next();
            entity.tick(this, iter);
        }
    }

}
