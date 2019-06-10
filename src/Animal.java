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
        Plant plant = find_plant(world);
        if (plant != null) {
            world.plants.remove(plant);
            plant.health -= 5;
            health += plant.health;
        }
        if (health > reproduce_health_required) {
            new_animals.add(reproduce());
            health -= reproduce_health_given;
        }
    }

    private Plant find_plant(World world) {
        Iterator<Plant> iterator = world.plants.iterator();
        if (!iterator.hasNext()) {
            wander(world.dimensions);
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
            wander(world.dimensions);
        }
        double edge_distance = min_distance - radius - closest.radius;
        if (edge_distance > move_distance) {
            loc.move_point(closest.loc, move_distance, world.dimensions);
        }
        else {
            loc.move_point(closest.loc, edge_distance, world.dimensions);
            return closest;
        }
        return null;
    }

    double wander_angle = Math.random() * 2 * Math.PI;
    double wander_angle_delta = Math.PI / 3;
    private void wander(Point dimensions) {
        loc.move_angle(wander_angle, move_distance, dimensions);
        if (loc.x == 0 || loc.x == dimensions.x || loc.y == 0 || loc.y == dimensions.y) {
            wander_angle += Math.PI;
        }
        wander_angle += (Math.random() * wander_angle_delta) - wander_angle_delta / 2;
    }

    @Override
    public Animal reproduce() {
        return new Animal(Point.random(loc, radius * 2, radius * 2), reproduce_health_given, radius, attrition,
                reproduce_health_required, reproduce_health_given, color,
                move_distance, sight, plant_eating);
    }

}
