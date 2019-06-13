import java.awt.*;
import java.util.Iterator;

public class Animal extends Entity<Animal> {
    double energy;
    double max_energy;
    double hunger;
    double hunger_damage;
    double reproduce_health_given;
    double move_distance;
    double sight;
    double plant_eating;

    public Animal(World world, Point loc, double radius, double health, double max_health, Color color,
                  double energy, double max_energy, double hunger, double hunger_damage, double reproduce_health_given,
                  double move_distance, double sight, double plant_eating) {
        super(world, loc, radius, health, max_health, color);
        this.hunger = hunger;
        this.energy = energy;
        this.max_energy = max_energy;
        this.hunger_damage = hunger_damage;
        this.reproduce_health_given = reproduce_health_given;
        this.move_distance = move_distance;
        this.sight = sight;
        this.plant_eating = plant_eating;
    }

    @Override
    public boolean tick() {
        energy -= hunger;
        if (energy < 0) {
            health -= hunger_damage;
        }
        else if (energy > 0 && health < max_health) {
            health += Config.HEALTH_REGAIN;
            energy -= Config.HEALTH_REGAIN;
        }
        if(super.tick()){
            return true;
        }
        eatPlant(find_plant());
        if (health >= max_health) {
            health = max_health;
            if (energy >= max_energy) {
                world.new_animals.add(reproduce());
                energy = max_energy - reproduce_health_given;
            }
        }
        return false;
    }

    private void eatPlant(Plant plant) {
        if (plant != null) {
            double energy_gained = plant.health < plant_eating ? plant.health : plant_eating;
            plant.health -= plant_eating;
            if (plant.health <= 0) {
                world.plants.remove(plant);
            }
            energy += energy_gained * Config.ANIMAL_EATING_PLANT_RATIO;
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
        boolean mutate = Math.random() < Config.MUTATION_CHANCE;
//      (r < World.MUTATION_CHANCE ? Math.random() * 2 - 1: 0) +
        return new Animal(
                world,
                new_loc,
                radius,
                reproduce_health_given,
                max_health,
                color,
//                new Color(
//                        ((((int) (mutate ? Math.random() * 50 - 25: 0) + color.getRed()) % 255) + 255) %255,
//                        ((((int) (mutate ? Math.random() * 50 - 25: 0) + color.getBlue()) % 255) + 255) % 255,
//                        ((((int) (mutate ? Math.random() * 50 - 25: 0) + color.getGreen()) % 255) + 255) %255
//                ),
                max_energy/2,
                max_energy,
                hunger,
                hunger_damage,
                reproduce_health_given,
//                (mutate ? 1 : 0) + move_distance,
                move_distance,
//                (mutate ? 10 : 0) + sight,
                sight,
                plant_eating);
    }



}
