import java.util.*;

public class World {
    public static final int NUM_ANIMAL = 10;
    public static final int NUM_PLANT = 0;
    public static final double RADIUS_ANIMAL = 10;
    public static final double RADIUS_PLANT = 3;
    public static final double ATTRITION_ANIMAL = 0;
    public static final double ATTRITION_PLANT = 1;
    public static final double HEALTH_ANIMAL = 100;
    public static final double HEALTH_PLANT = 10;
    public final Point dimensions = new Point(1080, 1920);

    Collection<Animal> animals;
    Collection<Plant> plants;

    public World () {
        animals = new HashSet<>();
        for (int i = 0; i < NUM_ANIMAL; i++) {
            animals.add(new Animal(Point.random(dimensions), HEALTH_ANIMAL, RADIUS_ANIMAL, ATTRITION_ANIMAL));
        }
        plants = new HashSet<>();
        for (int i = 0; i < NUM_PLANT; i++) {
            plants.add(new Plant(Point.random(dimensions), HEALTH_PLANT, RADIUS_PLANT, ATTRITION_PLANT));
        }
    }

    public void tick() {
        for (Iterator<Animal> iterator = animals.iterator(); iterator.hasNext(); ) {
            Animal animal = iterator.next();
            animal.tick(this, iterator);
        }
        for (Iterator<Plant> iterator = plants.iterator(); iterator.hasNext(); ) {
            Plant plant = iterator.next();
            plant.tick(this, iterator);
        }
    }

}
