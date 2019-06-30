import java.awt.*;
import java.util.Collection;

public abstract class Animal<T extends Animal> extends Entity<T> {
    double energy;
    double max_energy;
    double hunger;
    double hunger_damage;
    double reproduce_health_given;
    double move_distance;
    double sight;
    double eating_speed;
    int pregnancy = -1;

    Animal(World world, Point loc, double radius, double health, Color color,
           double energy, double reproduce_health_given, double sight, double eating_speed) {
        this(world, loc, radius, areaFrom(radius), health, color, energy, reproduce_health_given, sight, eating_speed);
    }

    Animal(World world, Point loc, double radius, double area, double health, Color color,
                  double energy, double reproduce_health_given, double sight, double eating_speed) {
        super(world, loc, radius, area, health, area * Config.HEALTH_PER_AREA, color);
        this.hunger = area * Config.HUNGER_PER_AREA;
        this.energy = energy;
        this.max_energy = area * Config.ENERGY_PER_AREA;
        this.hunger_damage = area * Config.HUNGER_DAMAGE_PER_AREA;
        this.reproduce_health_given = reproduce_health_given;
        this.move_distance = radius * Config.MOVE_DISTANCE_PER_RADIUS;
        this.sight = sight;
        this.eating_speed = eating_speed;
    }

    @Override
    public TickStatus tick() {
        energy -= hunger;
        if (energy < 0) {
            health -= hunger_damage;
        }
        else if (energy > 0 && health < max_health) {
            double health_difference = max_health - health;
            double health_gain = health_difference > Config.HEALTH_REGAIN ? Config.HEALTH_REGAIN : health_difference;
            health += health_gain;
            energy -= health_gain;
        }
        if(super.tick() == TickStatus.Dead){
            return TickStatus.Dead;
        }
        if (pregnancy < 0) { // If not pregnant,
            if (health >= max_health && energy >= max_energy) { // healthy, and have energy
                pregnancy = Config.ANIMAL_PREGNANCY; // get pregnant.
            }
        }
        else if (pregnancy == 0){ // If due,
            pregnancy = -1; // be not pregnant
            return TickStatus.Reproduce; // Have baby
        }
        else {
            pregnancy--; // Become more pregnant
        }
        return TickStatus.Nothing;
    }

    private double wander_angle = Math.random() * 2 * Math.PI;
    private static final double wander_angle_delta = Math.PI / 2;
    private double wander_ticks = 10;
    private double wander_count = wander_ticks;
    void wander() {
        loc.move_angle(wander_angle, move_distance);
        energy -= Config.ENERGY_PER_DISTANCE * move_distance;
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

    <S extends Entity> S moveTo(EntityDistance<S> entityDistance) {
        if (entityDistance == null) {
            wander();
            return null;
        }
        S animal = entityDistance.entity;
        double distance = entityDistance.distance;
        if (distance > sight) {
            wander();
            return null;
        }
        double edge_distance = distance - radius - animal.radius;
        if (edge_distance > move_distance) {
            loc.move_point(animal.loc, move_distance);
            energy -= Config.ENERGY_PER_DISTANCE * move_distance;
            check_loc();
            return null;
        }
        else {
            loc.move_point(animal.loc, edge_distance);
            energy -= Config.ENERGY_PER_DISTANCE * edge_distance;
            check_loc();
            return animal;
        }
    }

    <U extends Entity> void eat(Collection<U> collection, U entity, double ratio) {
        if (entity != null) {
            double energy_gained = entity.health < eating_speed ? entity.health : eating_speed;
            entity.health -= eating_speed;
            if (entity.health <= 0) {
                collection.remove(entity);
            }
            energy += energy_gained * ratio;
        }
    }

    public abstract T reproduce();

}
