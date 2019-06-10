import java.awt.*;
import java.util.Iterator;

public abstract class Entity {
    Point loc;
    double radius;
    double attrition;
    double health;
    Color color;

    public Entity(Point loc, double health, double radius, double attrition, Color color) {
        this.loc = loc;
        this.health = health;
        this.radius = radius;
        this.attrition = attrition;
        this.color = color;
    }

    public void tick(World world, Iterator<? extends Entity> iterator) {
        health += attrition;
        if (health < 0) {
            iterator.remove();
        }
    }

}
