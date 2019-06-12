public class Point {
    private double x;
    private double y;

    public static final Point ORIGIN = new Point(0, 0);

    public static Point random(Point from, Point to) {
        double y = Math.random() * (to.getY() - from.getY()) + from.getY();
        double x = Math.random() * (to.getX() - from.getX()) + from.getX();
        return new Point(y, x);
    }

    public static Point random(World world) {
        return random(ORIGIN, world.dimensions);
    }

    public static Point random(Point p, double delta_y, double delta_x) {
        return random(
                new Point(p.getY() - delta_y, p.getX() - delta_x),
                new Point(p.getY() + delta_y, p.getX() + delta_x)
        );
    }

    public Point(double y, double x) {
        this.setY(y);
        this.setX(x);
    }

    public double distance(Point p) {
        return Math.hypot(p.getX() - getX(), p.getY() - getY());
    }

    public void move_angle(double angle, double distance) {
        double delta_y = distance * Math.sin(angle);
        double delta_x = distance * Math.cos(angle);
        double new_y = getY() + delta_y;
        double new_x = getX() + delta_x;
        setY(new_y);
        setX(new_x);
    }

    public void move_point(Point p, double distance) {
        double distance_y = p.getY() - getY();
        double distance_x = p.getX() - getX();
        double angle = Math.atan2(distance_y, distance_x);
        move_angle(angle, distance);
    }

    public void move_random(double distance) {
        double angle = Math.toRadians(Math.random() * 360);
        move_angle(angle, distance);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
