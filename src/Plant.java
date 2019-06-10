import java.awt.*;
import java.util.Collection;
import java.util.Iterator;

public class Plant extends Entity<Plant> {
    public final Color color = Color.green;

    public Plant(Point loc, double health, double radius, double attrition,
                 double reproduce_health_required, double reproduce_health_given, Color color) {
        super(loc, health, radius, attrition, reproduce_health_required, reproduce_health_given, color);
    }

    @Override
    public void tick(World world, Iterator<Plant> iterator, Collection<Plant> new_plants) {
//        health += attrition;
//        if (health < 0) {
//            iterator.remove();
//            return;
//        }
//        if (health > reproduce_health_required) {
//            new_plants.add(reproduce());
//            health -= reproduce_health_given;
//        }
    }

    @Override
    public Plant reproduce() {
        Point new_loc = Point.random(loc, radius * 20, radius * 20);
        return new Plant(new_loc, reproduce_health_given, radius, attrition,
                reproduce_health_required, reproduce_health_given, color);
    }
}
