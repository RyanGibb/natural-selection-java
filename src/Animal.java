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
    }

    @Override
    public void tick(Iterator<Animal> iterator, Collection<Animal> new_animals) {
        health += attrition;
        if (health < 0) {
            iterator.remove();
            return;
        }
        Plant plant = find_plant(world.plants, world.dimensions);
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

    private Plant find_plant(Collection<Plant> plants, Point dimensions) {
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
            move_point(closest.loc, move_distance);
        }
        else {
            move_point(closest.loc, edge_distance);
            return closest;
        }
        return null;
    }

    double wander_angle = Math.random() * 2 * Math.PI;
    double wander_angle_delta = Math.PI / 2;
    double wander_ticks = 10;
    double wander_count = wander_ticks;
    private void wander() {
        move_angle(wander_angle, move_distance);
        if (loc.getX() == 0 || loc.getY() == world.dimensions.getX()
                || loc.getY() == 0 || loc.getY() == world.dimensions.getY()) {
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



    public void move_angle(double angle, double distance) {
        loc.move_angle(angle, distance);
        check_loc();
    }

    public void move_point(Point p, double distance) {
        loc.move_point(p, distance);
        check_loc();
    }

    public void move_random(double distance) {
        move_random(distance);
        check_loc();
    }

    public void check_loc(){
        if (loc.getY() >= world.dimensions.getY()) {
            loc.setY(world.dimensions.getY());
        }
        else if (loc.getY() <= 0) {
            loc.setY(0);
        }

        if (loc.getX() >= world.dimensions.getX()) {
            loc.setX(world.dimensions.getX());
        }
        else if (loc.getX() <= 0) {
            loc.setX(0);
        }
    }

}
