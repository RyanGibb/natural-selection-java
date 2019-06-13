import java.util.*;

public class World {
    public Collection<Animal> animals;
    public Collection<Plant> plants;
    public Point dimensions;

    public Collection<Animal> new_animals;
    public Collection<Plant> new_plants;

    public World (Point dimensions) {
        this.dimensions = dimensions;
        animals = new HashSet<>();
        for (int i = 0; i < Config.NUM_ANIMAL; i++) {
            animals.add(new Animal(this, randomPoint(Config.RADIUS_ANIMAL), Config.RADIUS_ANIMAL,
                    Config.MAX_HEALTH_ANIMAL, Config.MAX_HEALTH_ANIMAL, Config.ANIMAL_COLOR,
                    Config.MAX_ENERGY_ANIMAL, Config.MAX_ENERGY_ANIMAL, Config.HUNGER, Config.HUNGER_DAMAGE,
                    Config.REPRODUCE_HEALTH_GIVEN_ANIMAL,
                    Config.ANIMAL_MOVE_DISTANCE, Config.ANIMAL_SIGHT, Config.ANIMAL_PLANT_EATING));
        }
        plants = new HashSet<>();
        for (int i = 0; i < Config.NUM_PLANT; i++) {
            plants.add(new Plant(this, randomPoint(Config.RADIUS_PLANT), Config.RADIUS_PLANT,
                    Config.MAX_HEALTH_PLANT, Config.MAX_HEALTH_PLANT, Config.PLANT_COLOR));
        }
    }

    public void tick() {
        new_animals = new HashSet<>();
        animals.removeIf(Animal::tick);
        animals.addAll(new_animals);

        new_plants = new HashSet<>();
        plants.removeIf(Plant::tick);
        plants.addAll(new_plants);

        for (int i = 0; i < Config.PLANT_GROWTH; i++) {
            plants.add(new Plant(this, randomPoint(Config.RADIUS_PLANT), Config.RADIUS_PLANT,
                    Config.MAX_HEALTH_PLANT, Config.MAX_HEALTH_PLANT, Config.PLANT_COLOR));
        }
    }

    public Point randomPoint(double radius) {
        return Point.random(new Point(radius, radius), new Point(dimensions.y - radius, dimensions.x - radius));
    }

}
