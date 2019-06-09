import java.util.Iterator;
import java.util.List;

public class Entity {
    static double move_speed = 1;

    Point p;
    int radius = 100;

    public Entity(Point p) {
        this.p = p;
    }

    public void tick(World world, Iterator<Entity> iter) {
        Entity closest = null;
        double min_distance = -1;

        Iterator<Entity> iter2 = world.entities.iterator();
        while (iter2.hasNext()) {
            Entity e = iter2.next();

            if (e == this) continue;
            double distance = e.p.distance(this.p);
            if (distance < (e.radius + radius) / 2) {
                double area1 = 3.142 * e.radius * e.radius;
                double area2 = 3.142 * radius * radius;
                double area = area1 + area2;
                e.radius = (int) Math.sqrt(area/3.142);
                iter.remove();
                iter2.remove();
            }
            if (min_distance == -1 || distance < min_distance) {
                min_distance = distance;
                closest = e;
            }
        }
        if (closest != null) {
            p.move(closest.p, move_speed, world.dimensions);
        }
    }

}
