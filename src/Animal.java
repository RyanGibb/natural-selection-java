import java.awt.*;
import java.util.Collection;
import java.util.Iterator;

public class Animal extends Entity<Animal> {
    double move_distance, sight, plant_eating;

    public Animal(World world, Point loc, double health, double radius, double attrition,
                  double reproduce_health_required, double reproduce_health_given, Color color,
                  double move_distance, double sight, double plant_eating) {
        super(world, loc, health, radius, attrition, reproduce_health_required, reproduce_health_given, color);
        this.move_distance = move_distance;
        this.sight = sight;
        this.plant_eating = plant_eating;
        check_loc();
    }

    @Override
    public void tick(Iterator<Animal> iterator, Collection<Animal> new_animals) {
        health += attrition;
        if (health < 0) {
            iterator.remove();
            return;
        }
        Plant plant = find_plant();
        if (plant != null) {
            double health_gained = plant.health < plant_eating ? plant.health : plant_eating;
            plant.health -= plant_eating;
            if (plant.health <= 0) {
                world.plants.remove(plant);
            }
            health += health_gained * World.ANIMAL_EATING_PLANT_HEALTH_RATIO;
        }
        if (health > reproduce_health_required) {
            new_animals.add(reproduce());
            health -= reproduce_health_given;
        }
    }

    private Plant find_plant() {
        Iterator<Plant> iterator = world.plants.iterator();
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
            check_loc();
        }
        else {
            loc.move_point(closest.loc, edge_distance);
            check_loc();
            return closest;
        }
        return null;
    }

    double wander_angle = Math.random() * 2 * Math.PI;
    double wander_angle_delta = Math.PI / 2;
    double wander_ticks = 10;
    double wander_count = wander_ticks;
    private void wander() {
        loc.move_angle(wander_angle, move_distance);
        check_loc();
        if (loc.x == 0 || loc.y == world.dimensions.x
                || loc.y == 0 || loc.y == world.dimensions.y) {
            wander_angle += Math.PI;
        }
        if (wander_count <= 0) {
            wander_angle += (Math.random() * wander_angle_delta) - wander_angle_delta / 2;
            wander_count = wander_ticks;
        }
        wander_count--;
    }

    @Override
    public Animal reproduce() {
        Point new_loc = Point.random(loc, radius * 2, radius * 2);
        double r = Math.random();
        return new Animal(world, new_loc, reproduce_health_given,
                radius,
                attrition,

//                (r < World.MUTATION_CHANCE ? Math.random() * 2 - 1: 0) +
                        reproduce_health_required,
//                (r < World.MUTATION_CHANCE ? Math.random() * 2 - 1: 0) +
                        reproduce_health_given,
                new Color(
                        ((((int) (r < World.MUTATION_CHANCE ? Math.random() * 50 - 25: 0) + color.getRed()) % 255) + 255) %255,
                        ((((int) (r < World.MUTATION_CHANCE ? Math.random() * 50 - 25: 0) + color.getBlue()) % 255) + 255) % 255,
                        ((((int) (r < World.MUTATION_CHANCE ? Math.random() * 50 - 25: 0) + color.getGreen()) % 255) + 255) %255
                        ),
                (r < World.MUTATION_CHANCE ? 1 : 0) + move_distance,
                (r < World.MUTATION_CHANCE ? 10 : 0) + sight,
//                (r < World.MUTATION_CHANCE ? Math.random() * 2 - 1: 0) +
                        plant_eating);
    }



}
