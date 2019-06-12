public class Point {
    double x;
    double y;

    public static final Point ORIGIN = new Point(0, 0);

    public static Point random(Point from, Point to) {
        double y = Math.random() * (to.y - from.y) + from.y;
        double x = Math.random() * (to.x - from.x) + from.x;
        return new Point(y, x);
    }

    public static Point random(World world) {
        return random(ORIGIN, world.dimensions);
    }

    public static Point random(Point p, double delta_y, double delta_x) {
        return random(
                new Point(p.y - delta_y, p.x - delta_x),
                new Point(p.y + delta_y, p.x + delta_x)
        );
    }

    public Point(double y, double x) {
        this.y = y;
        this.x = x;
    }

    public double distance(Point p) {
        return Math.hypot(p.x - x, p.y - y);
    }

    public void move_angle(double angle, double distance) {
        double delta_y = distance * Math.sin(angle);
        double delta_x = distance * Math.cos(angle);
        double new_y = y + delta_y;
        double new_x = x + delta_x;
        y = new_y;
        x = new_x;
    }

    public void move_point(Point p, double distance) {
        double distance_y = p.y - y;
        double distance_x = p.x - x;
        double angle = Math.atan2(distance_y, distance_x);
        move_angle(angle, distance);
    }

    public void move_random(double distance) {
        double angle = Math.toRadians(Math.random() * 360);
        move_angle(angle, distance);
    }

}
