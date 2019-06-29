import java.awt.*;

public class Plant extends Entity<Plant> {

    public Plant(World world, Point loc, double radius, double health, double max_health, Color color) {
        super(world, loc, radius, areaFrom(radius), health, max_health, color);
    }

    @Override
    public TickStatus tick() {
//        health += hunger;
//        if (health < 0) {
//            iterator.remove();
//            return;
//        }
//        if (health > reproduce_health_required) {
//            world.new_plants.add(reproduce());
//            health -= reproduce_health_given;
//        }
        return null;
    }

    @Override
    public Plant reproduce() {
        Point new_loc = Point.random(loc, radius * 2, radius * 2);
        return null;
//        return new Plant(world, new_loc, radius, reproduce_health_given, color);
    }

}
