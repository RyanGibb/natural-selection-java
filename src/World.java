import java.util.Collection;
import java.util.HashSet;

public class World {
    public Collection<Animal> animals;
    public Collection<Plant> plants;
    public Point dimensions;

    public Collection<Animal> new_animals;
    public Collection<Plant> new_plants;

    double growth = 0;

    public World (Point dimensions) {
        this.dimensions = dimensions;
        animals = new HashSet<>();
        for (int i = 0; i < Config.NUM_ANIMAL; i++) {
            animals.add(new Animal(this, randomPoint(Config.RADIUS_ANIMAL), Config.RADIUS_ANIMAL,
                    Config.MAX_HEALTH_ANIMAL, Config.ANIMAL_COLOR,
                    Config.MAX_ENERGY_ANIMAL,
                    Config.REPRODUCE_HEALTH_GIVEN_ANIMAL,
                    Config.ANIMAL_SIGHT, Config.ANIMAL_PLANT_EATING));
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

        growth += Config.PLANT_GROWTH;
        for (int i = 1; i <= growth; i++) {
            plants.add(new Plant(this, randomPoint(Config.RADIUS_PLANT), Config.RADIUS_PLANT,
                    Config.MAX_HEALTH_PLANT, Config.MAX_HEALTH_PLANT, Config.PLANT_COLOR));
        }
        if (growth > 1) {
            growth = growth - (int) growth;
        }
    }

    public Point randomPoint(double radius) {
        return Point.random(new Point(radius, radius), new Point(dimensions.y - radius, dimensions.x - radius));
    }

}
