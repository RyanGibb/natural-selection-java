import java.util.Collection;
import java.util.HashSet;

public class World {
    public Collection<Predator> predators;
    public Collection<Prey> prey;
    public Collection<Plant> plants;
    public Point dimensions;

    public Collection<Predator> new_predators;
    public Collection<Prey> new_prey;
    public Collection<Plant> new_plants;

    double growth = 0;

    public World (Point dimensions) {
        this.dimensions = dimensions;
        predators = new HashSet<>();
        for (int i = 0; i < Config.NUM_PREDS; i++) {
            predators.add(new Predator(this, randomPoint(Config.RADIUS_ANIMAL), Config.RADIUS_ANIMAL,
                    Config.INITIAL_HEALTH_ANIMAL, Config.PREDATOR_COLOR,
                    Config.INITIAL_ENERGY_ANIMAL,
                    Config.REPRODUCE_HEALTH_GIVEN_ANIMAL,
                    Config.ANIMAL_SIGHT, Config.ANIMAL_EATING_SPEED));
        }
        prey = new HashSet<>();
        for (int i = 0; i < Config.NUM_PREY; i++) {
            prey.add(new Prey(this, randomPoint(Config.RADIUS_ANIMAL), Config.RADIUS_ANIMAL,
                    Config.INITIAL_HEALTH_ANIMAL, Config.PREY_COLOR,
                    Config.INITIAL_ENERGY_ANIMAL,
                    Config.REPRODUCE_HEALTH_GIVEN_ANIMAL,
                    Config.ANIMAL_SIGHT, Config.ANIMAL_EATING_SPEED));
        }
        plants = new HashSet<>();
        for (int i = 0; i < Config.NUM_PLANT; i++) {
            plants.add(new Plant(this, randomPoint(Config.RADIUS_PLANT), Config.RADIUS_PLANT,
                    Config.INITIAL_HEALTH_PLANT, Config.MAX_HEALTH_PLANT, Config.PLANT_COLOR));
        }
    }

    public void tick() {
        new_predators = new HashSet<>();
        predators.removeIf(predator -> predator.tick() == TickStatus.Dead);
        predators.addAll(new_predators);

        new_prey = new HashSet<>();
        prey.removeIf(prey -> prey.tick() == TickStatus.Dead);
        prey.addAll(new_prey);

        new_plants = new HashSet<>();
        plants.removeIf(plant -> plant.tick() == TickStatus.Dead);
        plants.addAll(new_plants);

        growth += Config.PLANT_GROWTH;
        for (int i = 1; i <= growth; i++) {
            plants.add(new Plant(this, randomPoint(Config.RADIUS_PLANT), Config.RADIUS_PLANT,
                    Config.INITIAL_HEALTH_PLANT, Config.INITIAL_HEALTH_PLANT, Config.PLANT_COLOR));
        }
        if (growth > 1) {
            growth = growth - (int) growth;
        }
    }

    public Point randomPoint(double radius) {
        return Point.random(new Point(radius, radius), new Point(dimensions.y - radius, dimensions.x - radius));
    }

}
