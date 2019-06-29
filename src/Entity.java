import java.awt.*;
import java.util.Iterator;

public abstract class Entity<T extends Entity> {
    Point loc;
    double radius;
    double area;
    double health;
    double max_health;
    Color color;
    World world;

    Entity(World world, Point loc, double radius, double area, double health, double max_health, Color color) {
        this.world = world;
        this.loc = loc;
        this.radius = radius;
        this.area = area;
        this.health = health;
        this.max_health = max_health;
        this.color = color;
    }

    // return true if dead
    public TickStatus tick() {
        if(health < 0) {
            return TickStatus.Dead;
        }
        return TickStatus.Nothing;
    }

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

    public abstract T reproduce();

    public static double radiusFrom(double area) {
        return Math.sqrt(area / Math.PI);
    }

    public static double areaFrom(double radius) {
        return Math.PI * radius * radius;
    }

    static class EntityDistance<S extends Entity> {
        S entity;
        double distance;

        EntityDistance(S entity, double distance) {
            this.entity = entity;
            this.distance = distance;
        }
    }
    <S extends Entity, U extends Entity> EntityDistance<S> findClosest(Iterator<S> iterator) {
        if (!iterator.hasNext()) {
            return null;
        }
        S entity = iterator.next();
        S closest = entity;
        double min_distance = closest.loc.distance(this.loc);
        while (iterator.hasNext()) {
            entity = iterator.next();
            double distance = entity.loc.distance(this.loc);
            if (distance < min_distance) {
                min_distance = distance;
                closest = entity;
            }
        }
        return new EntityDistance<>(closest, min_distance);
    }

}
