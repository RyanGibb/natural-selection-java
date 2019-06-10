import java.awt.*;
import java.util.Iterator;

public class Animal extends Entity {
    public static final Color COLOR = Color.blue;

    double move_distance = 5;
    double sight = 200;

    Point wander_point = null;

    public Animal(Point p, double health, double radius, double attrition) {
        super(p, health, radius, attrition, COLOR);
    }

    @Override
    public void tick(World world, Iterator<? extends Entity> iterator) {
        super.tick(world, iterator);
        if (health < 0) {
            return;
        }
        Iterator<Plant> plant_iterator = world.plants.iterator();
        if (!plant_iterator.hasNext()) {
            wander(world.dimensions);
            return;
        }
        Plant plant = plant_iterator.next();
        Entity closest = plant;
        double min_distance = plant.loc.distance(this.loc);
        while (plant_iterator.hasNext()) {
            plant = plant_iterator.next();
            double distance = plant.loc.distance(this.loc);
            if (distance < min_distance) {
                min_distance = distance;
                closest = plant;
            }
        }
        if (min_distance - move_distance < plant.radius + radius) {
//            radius = Math.sqrt(e.radius * e.radius + radius * radius);
//            radius += 1;
//            move_distance = radius * MOVE_DISTANCE_RADIUS_RATIO;
//            loc.move_point(closest.loc, (closest.radius + radius) / 2, world.dimensions);
            world.plants.remove(closest);
        }
        if (min_distance > sight) {
            wander(world.dimensions);
        }
        if (min_distance < move_distance) {
            loc.move_point(closest.loc, min_distance, world.dimensions);
        }
        else {
            loc.move_point(closest.loc, move_distance, world.dimensions);
        }
    }

    private void wander(Point dimensions) {
        if (wander_point == null || loc.distance(wander_point) < move_distance) {
            wander_point = Point.random(loc, sight, sight);
        }
        loc.move_point(wander_point, move_distance, dimensions);
    }


}
