import java.awt.*;

public class Plant extends Entity<Plant> {

    public Plant(World world, Point loc, double radius, double health, double max_health, double energy, double max_energy,
                 double hunger, double reproduce_health_given, Color color) {
        super(world, loc, radius, health, max_health, energy, max_energy, hunger, reproduce_health_given, color);
        check_loc();
    }

    @Override
    public boolean tick() {
//        health += hunger;
//        if (health < 0) {
//            iterator.remove();
//            return;
//        }
//        if (health > reproduce_health_required) {
//            world.new_plants.add(reproduce());
//            health -= reproduce_health_given;
//        }
        return false;
    }

    @Override
    public Plant reproduce() {
        Point new_loc = Point.random(loc, radius * 2, radius * 2);
        return new Plant(world, new_loc, radius, reproduce_health_given, max_health, energy, max_energy, attrition, reproduce_health_given, color);
    }
}
