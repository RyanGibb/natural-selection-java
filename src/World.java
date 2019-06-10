import java.awt.*;
import java.util.*;

public class World {
    public static final double ANIMAL_EATING_PLANT_HEALTH_RATIO = 1;
    public static final double RADIUS_ANIMAL = 10;
    public static final double RADIUS_PLANT = 3;
    public static final double ATTRITION_ANIMAL = -1;
    public static final double ATTRITION_PLANT = 1;

    public static final double STARTING_HEALTH_ANIMAL = 100;
    public static final double STARTING_HEALTH_PLANT = 20;
    public static final int NUM_ANIMAL = 10;
    public static final int NUM_PLANT = 100;

    public static final double REPRODUCE_HEALTH_REQUIRED_ANIMAL = 200;
    public static final double REPRODUCE_HEALTH_REQUIRED_PLANT = 200;
    public static final double REPRODUCE_HEALTH_GIVEN_ANIMAL = 100;
    public static final double REPRODUCE_HEALTH_GIVEN_PLANT = 100;

    public static final double ANIMAL_SIGHT = 100;
    public static final double ANIMAL_MOVE_DISTANCE = 5;
    public static final double ANIMAL_PLANT_EATING = 5;

    public static final double PLANT_GROWTH = 2;

    Collection<Animal> animals;
    Collection<Plant> plants;

    public World () {
        animals = new HashSet<>();
        for (int i = 0; i < NUM_ANIMAL; i++) {
            animals.add(new Animal(Point.random(), STARTING_HEALTH_ANIMAL, RADIUS_ANIMAL, ATTRITION_ANIMAL,
                    REPRODUCE_HEALTH_REQUIRED_ANIMAL, REPRODUCE_HEALTH_GIVEN_ANIMAL, Color.blue,
                    ANIMAL_MOVE_DISTANCE, ANIMAL_SIGHT, ANIMAL_PLANT_EATING));
        }
        plants = new HashSet<>();
        for (int i = 0; i < NUM_PLANT; i++) {
            plants.add(new Plant(Point.random(), STARTING_HEALTH_PLANT, RADIUS_PLANT, ATTRITION_PLANT,
                    REPRODUCE_HEALTH_REQUIRED_PLANT, REPRODUCE_HEALTH_GIVEN_PLANT, Color.green));
        }
    }

    public void tick() {
        Collection<Animal> new_animals = new HashSet<>();
        for (Iterator<Animal> iterator = animals.iterator(); iterator.hasNext(); ) {
            Animal animal = iterator.next();
            animal.tick(this, iterator, new_animals);
        }
        animals.addAll(new_animals);

        Collection<Plant> new_plants = new HashSet<>();
        for (Iterator<Plant> iterator = plants.iterator(); iterator.hasNext(); ) {
            Plant plant = iterator.next();
            plant.tick(this, iterator, new_plants);
        }
        plants.addAll(new_plants);

        for (int i = 0; i < PLANT_GROWTH; i++) {
            plants.add(new Plant(Point.random(), STARTING_HEALTH_PLANT, RADIUS_PLANT, ATTRITION_PLANT,
                    REPRODUCE_HEALTH_REQUIRED_PLANT, REPRODUCE_HEALTH_GIVEN_PLANT, Color.green));
        }
    }

}
