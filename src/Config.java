import java.awt.*;

public class Config {
    public static final Color PREDATOR_COLOR = Color.blue;
    public static final Color PREY_COLOR = Color.gray;
    public static final Color PLANT_COLOR = Color.green;
    public static final int NUM_PREDS = 5;
    public static final int NUM_PREY = 100;
    public static final int NUM_PLANT = 500;

    public static final double INITIAL_HEALTH_ANIMAL = 100;
    public static final double INITIAL_ENERGY_ANIMAL = 100;
    public static final double RADIUS_ANIMAL = 10;
    public static final double REPRODUCE_HEALTH_GIVEN_ANIMAL = 100;
    public static final double ANIMAL_SIGHT = 100;
    public static final int ANIMAL_PREGNANCY = 250;

    public static final double INITIAL_HEALTH_PLANT = 100;
    public static final double MAX_HEALTH_PLANT = 100;
    public static final double RADIUS_PLANT = 3;

    public static final double PREY_EATING_PLANT_RATIO = 0.5;
    public static final double PREDATOR_EATING_PREY_RATIO = 0.5;
    public static final double MUTATION_CHANCE = 0.01;

    public static final double ANIMAL_EATING_SPEED = 10;
    public static final double HEALTH_REGAIN = 1;
    public static final double PLANT_GROWTH = 1;

    public static final double HEALTH_PER_AREA = 0.3;
    public static final double ENERGY_PER_AREA = 0.3;
    public static final double HUNGER_PER_AREA = 0.0003;
    public static final double HUNGER_DAMAGE_PER_AREA = 0.0015;

    public static final double ENERGY_PER_DISTANCE = 0.05;

    public static final double MOVE_DISTANCE_PER_RADIUS = 0.2;
}
