import java.awt.*;
import java.util.Collection;
import java.util.Iterator;

public class Animal extends Entity<Animal> {
    double move_distance, sight, plant_eating;

    public Animal(Point loc, double health, double radius, double attrition,
                  double reproduce_health_required, double reproduce_health_given, Color color,
                  double move_distance, double sight, double plant_eating) {
        super(loc, health, radius, attrition, reproduce_health_required, reproduce_health_given, color);
        this.move_distance = move_distance;
        this.sight = sight;
        this.plant_eating = plant_eating;
    }

    @Override
    public void tick(World world, Iterator<Animal> iterator, Collection<Animal> new_animals) {
        health += attrition;
        if (health < 0) {
            iterator.remove();
            return;
        }
        Plant plant = find_plant(world.plants);
        if (plant != null) {
            world.plants.remove(plant);
//            plant.health -= 5;
            health += plant.health * World.ANIMAL_EATING_PLANT_HEALTH_RATIO;
        }
        if (health > reproduce_health_required) {
            new_animals.add(reproduce());
            health -= reproduce_health_given;
        }
    }

    private Plant find_plant(Collection<Plant> plants) {
        Iterator<Plant> iterator = plants.iterator();
        if (!iterator.hasNext()) {
            wander();
            return null;
        }
        Plant plant = iterator.next();
        Plant closest = plant;
        double min_distance = plant.loc.distance(this.loc);
        while (iterator.hasNext()) {
            plant = iterator.next();
            double distance = plant.loc.distance(this.loc);
            if (distance < min_distance) {
                min_distance = distance;
                closest = plant;
            }
        }
        if (min_distance > sight) {
            wander();
        }
        double edge_distance = min_distance - radius - closest.radius;
        if (edge_distance > move_distance) {
            loc.move_point(closest.loc, move_distance);
        }
        else {
            loc.move_point(closest.loc, edge_distance);
            return closest;
        }
        return null;
    }

    double wander_angle = Math.random() * 2 * Math.PI;
    double wander_angle_delta = Math.PI / 3;
    private void wander() {
        loc.move_angle(wander_angle, move_distance);
        if (loc.getX() == 0 || loc.getY() == Point.dimensions.getX()
                || loc.getY() == 0 || loc.getY() == Point.dimensions.getY()) {
            wander_angle += Math.PI;
        }
        wander_angle += (Math.random() * wander_angle_delta) - wander_angle_delta / 2;
    }

    @Override
    public Animal reproduce() {
        Point new_loc = Point.random(loc, radius * 2, radius * 2);
        return new Animal(new_loc, reproduce_health_given, radius, attrition,
                reproduce_health_required, reproduce_health_given, color,
                move_distance, sight, plant_eating);
    }

}
