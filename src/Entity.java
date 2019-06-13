import java.awt.*;

public abstract class Entity<T extends Entity> {
    Point loc;
    double radius;
    double health;
    double max_health;
    double energy;
    double max_energy;
    double attrition;
    double reproduce_health_given;
    Color color;
    World world;

    public Entity(World world, Point loc, double radius, double health, double max_health, double energy, double max_energy,
                  double attrition, double reproduce_health_given, Color color) {
        this.world = world;
        this.loc = loc;
        this.radius = radius;
        this.health = health;
        this.max_health = max_health;
        this.energy = energy;
        this.max_energy = max_energy;
        this.attrition = attrition;
        this.reproduce_health_given = reproduce_health_given;
        this.color = color;
    }

    // return true if dead
    public boolean tick() {
        energy += attrition;
        if (energy < 0) {
            health += attrition;
        }
        else if (energy > max_energy) {
            energy = max_energy;
        }
        if (health < 0) {
            return true;
        }
        else if (health > max_health) {
            health = max_health;
        }
        return false;
    }

    public abstract T reproduce();

    public void check_loc(){
        if (loc.y + radius > world.dimensions.y) {
            loc.y = world.dimensions.y - radius;
        }
        else if (loc.y- radius < 0) {
            loc.y = loc.x + radius;
        }

        if (loc.x + radius > world.dimensions.x) {
            loc.x = world.dimensions.x - radius;
        }
        else if (loc.x - radius < 0) {
            loc.x = loc.x + radius;
        }
    }

}
