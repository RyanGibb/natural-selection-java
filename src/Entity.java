import java.awt.*;

public abstract class Entity<T extends Entity> {
    Point loc;
    double radius;
    double health;
    double max_health;
    Color color;
    World world;

    public Entity(World world, Point loc, double radius, double health, double max_health, Color color) {
        this.world = world;
        this.loc = loc;
        this.radius = radius;
        this.health = health;
        this.max_health = max_health;
        this.color = color;
    }

    // return true if dead
    public boolean tick() {
        return health < 0;
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
