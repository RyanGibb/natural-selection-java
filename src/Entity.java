import java.awt.*;
import java.util.Collection;
import java.util.Iterator;

public abstract class Entity<T extends Entity> {
    Point loc;
    double radius;
    double attrition;
    double health;
    double reproduce_health_required;
    double reproduce_health_given;
    Color color;
    World world;

    public Entity(World world, Point loc, double health, double radius, double attrition,
                  double reproduce_health_required, double reproduce_health_given, Color color) {
        this.world = world;
        this.loc = loc;
        this.health = health;
        this.radius = radius;
        this.attrition = attrition;
        this.reproduce_health_required = reproduce_health_required;
        this.reproduce_health_given = reproduce_health_given;
        this.color = color;
    }

    public abstract void tick(Iterator<T> iterator, Collection<T> new_entities);

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
