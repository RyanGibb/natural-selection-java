public class Point {
    double x;
    double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double distance(Point p) {
        return Math.hypot(p.x - x, p.y - y);
    }

    public void move(Point p, double move_speed, Point dimensions) {
        double distance_y = p.y - y;
        double distance_x = p.x - x;
        double angle = Math.atan2(distance_y, distance_x);
        double delta_y = move_speed * Math.sin(angle);
        double delta_x = move_speed * Math.cos(angle);
        double new_y = y + delta_y;
        double new_x = x + delta_x;
        if (new_y < dimensions.y && new_y > 0) {
            y = new_y;
        }
        if (new_x < dimensions.x && new_x > 0) {
            x = new_x;
        }
    }
}
