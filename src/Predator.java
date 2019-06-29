import java.awt.*;

public class Predator extends Animal {

    Predator(World world, Point loc, double radius, double health, Color color, double energy, double reproduce_health_given, double sight, double plant_eating) {
        super(world, loc, radius, health, color, energy, reproduce_health_given, sight, plant_eating);
    }

    private Predator(World world, Point loc, double radius, double area, double health, Color color,
                 double energy, double reproduce_health_given, double sight, double plant_eating) {
        super(world, loc, radius, area, health, color, energy, reproduce_health_given, sight, plant_eating);
    }

    @Override
    public TickStatus tick() {
        switch (super.tick()) {
            case Dead:
                return TickStatus.Dead;
            case Reproduce:
                world.new_predators.add(reproduce());
                energy = max_energy - reproduce_health_given;
        }
        Prey prey = (Prey) moveTo(findClosest(world.prey.iterator()));
        eatPrey(prey);
        return TickStatus.Nothing;
    }

    private void eatPrey(Prey prey) {
        eat(world.prey, prey, Config.PREDATOR_EATING_PREY_RATIO);
    }

    @Override
    public Predator reproduce() {
        Point new_loc = Point.random(loc, radius * 2, radius * 2);
        boolean mutate = Math.random() < Config.MUTATION_CHANCE;
        double new_area = area;
        if (mutate) {
            new_area += (Math.random() - 0.5) * 2 + new_area; // add -1 to 1
        }
        return new Predator(
                world,
                new_loc,
                radiusFrom(new_area),
                new_area,
                reproduce_health_given,
                color,
                0,
                reproduce_health_given,
                sight,
                eating_speed);
    }

}
