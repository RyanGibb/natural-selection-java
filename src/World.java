import java.awt.*;
import java.util.*;

public class World {
    public static final double ANIMAL_EATING_PLANT_HEALTH_RATIO = 1;
    public static final double RADIUS_ANIMAL = 10;
    public static final double RADIUS_PLANT = 3;
    public static final double ATTRITION_ANIMAL = -1;
    public static final double ATTRITION_PLANT = 1;

    public static final double PLANT_GROWTH = 2;
    public static final double MUTATION_CHANCE = 0.1;

    public static final double MAX_HEALTH_ANIMAL = 100;
    public static final double MAX_HEALTH_PLANT = 20;
    public static final double MAX_ENERGY_ANIMAL = 0;
    public static final double MAX_ENERGY_PLANT = 0;
    public static final int NUM_ANIMAL = 100;
    public static final int NUM_PLANT = 1000;

    public static final double REPRODUCE_HEALTH_GIVEN_PLANT = 10;

    public static final double REPRODUCE_HEALTH_GIVEN_ANIMAL = 50;
    public static final double ANIMAL_SIGHT = 100;
    public static final double ANIMAL_MOVE_DISTANCE = 5;
    public static final double ANIMAL_PLANT_EATING = 5;

    Collection<Animal> animals;
    Collection<Plant> plants;
    Collection<Animal> new_animals;
    Collection<Plant> new_plants;
    public Point dimensions;

    public World (Point dimensions) {
        this.dimensions = dimensions;
        animals = new HashSet<>();
        for (int i = 0; i < NUM_ANIMAL; i++) {
            animals.add(new Animal(this, randomPoint(), RADIUS_ANIMAL, MAX_HEALTH_ANIMAL, MAX_HEALTH_ANIMAL,
                    MAX_HEALTH_ANIMAL, MAX_ENERGY_ANIMAL, ATTRITION_ANIMAL,
                    REPRODUCE_HEALTH_GIVEN_ANIMAL, new Color(255/2,255/2,255/2),
                    ANIMAL_MOVE_DISTANCE, ANIMAL_SIGHT, ANIMAL_PLANT_EATING));
        }
        plants = new HashSet<>();
        for (int i = 0; i < NUM_PLANT; i++) {
            plants.add(new Plant(this, randomPoint(), RADIUS_PLANT, MAX_HEALTH_PLANT, MAX_HEALTH_PLANT,
                    MAX_HEALTH_PLANT, MAX_ENERGY_PLANT, ATTRITION_PLANT,
                    REPRODUCE_HEALTH_GIVEN_PLANT, Color.blue));
        }
    }

    public void tick() {
        new_animals = new HashSet<>();
        for (Iterator<Animal> iterator = animals.iterator(); iterator.hasNext(); ) {
            Animal animal = iterator.next();
            if(animal.tick()){
                iterator.remove();
            }
        }
        animals.addAll(new_animals);

        new_plants = new HashSet<>();
        for (Iterator<Plant> iterator = plants.iterator(); iterator.hasNext(); ) {
            Plant plant = iterator.next();
            if(plant.tick()) {
                iterator.remove();
            }
        }
        plants.addAll(new_plants);

        for (int i = 0; i < PLANT_GROWTH; i++) {
            plants.add(new Plant(this, randomPoint(), RADIUS_PLANT, MAX_HEALTH_PLANT, MAX_HEALTH_PLANT,
                    MAX_HEALTH_PLANT, MAX_ENERGY_PLANT, ATTRITION_PLANT,
                    REPRODUCE_HEALTH_GIVEN_PLANT, Color.blue));
        }
    }

    public Point randomPoint() {
        return Point.random(this);
    }

}
