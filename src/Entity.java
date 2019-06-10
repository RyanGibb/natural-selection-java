import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

public abstract class Entity {
    Point p;
    int radius = 10;
    double move_distance = radius / 10;

    public Entity(Point p) {
        this.p = p;
    }

    public void tick(World world, Collection<Entity> removed) {
        Entity closest = null;
        double min_distance = -1;

        Iterator<Entity> iterator = new HashSet<>(world.entities).iterator();
        while (iterator.hasNext()) {
            Entity e = iterator.next();

            if (e == this) continue;
            double distance = e.p.distance(this.p);

            if (distance < e.radius + radius) {
                radius = (int) Math.sqrt(e.radius * e.radius + radius * radius);
                p.move(e.p, (e.radius + radius) / 2, world.dimensions);
                world.entities.remove(e);
                removed.add(e);
                break;
            }
            if (min_distance == -1 || distance < min_distance) {
                min_distance = distance;
                closest = e;
            }
        }
        if (closest != null) {
            p.move(closest.p, move_distance, world.dimensions);
        }
    }

}
