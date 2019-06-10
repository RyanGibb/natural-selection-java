import java.awt.*;
import java.util.Iterator;

public class Plant extends Entity {
    public static final Color COLOR = Color.green;

    public Plant(Point loc, double health, double radius, double attrition) {
        super(loc, health, radius, attrition, COLOR);
    }

    @Override
    public void tick(World world, Iterator<? extends Entity> iterator) {
        super.tick(world, iterator);
        if (health < 0) {
            return;
        }
    }
}
