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

    public Entity(Point loc, double health, double radius, double attrition,
                  double reproduce_health_required, double reproduce_health_given, Color color) {
        this.loc = loc;
        this.health = health;
        this.radius = radius;
        this.attrition = attrition;
        this.reproduce_health_required = reproduce_health_required;
        this.reproduce_health_given = reproduce_health_given;
        this.color = color;
    }

    public abstract void tick(World world, Iterator<T> iterator, Collection<T> new_entities);

    public abstract T reproduce();

}
