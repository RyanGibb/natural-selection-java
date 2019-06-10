public class Point {
    double x;
    double y;

    public static final Point ORIGIN = new Point(0, 0);

    public static Point random(Point from, Point to) {
        double y = Math.random() * (to.y - from.y) + from.y;
        double x = Math.random() * (to.x - from.x) + from.x;
        return new Point(y, x);
    }

    public static Point random(Point dimensions) {
        return random(ORIGIN, dimensions);
    }

    public static Point random(Point p, double delta_y, double delta_x) {
        return random(new Point(p.y - delta_y, p.x - delta_x), new Point(p.y + delta_y, p.x + delta_x));
    }


    public Point(double y, double x) {
        this.y = y;
        this.x = x;
    }

    public double distance(Point p) {
        return Math.hypot(p.x - x, p.y - y);
    }

    public void move_angle(double angle, double distance, Point dimensions) {
        double delta_y = distance * Math.sin(angle);
        double delta_x = distance * Math.cos(angle);
        double new_y = y + delta_y;
        double new_x = x + delta_x;
        if (new_y >= dimensions.y) {
            y = dimensions.y;
        }
        else if (new_y <= 0) {
            y = 0;
        }
        else {
            y = new_y;
        }

        if (new_x >= dimensions.x) {
            x = dimensions.x;
        }
        else if (new_x <= 0) {
            x = 0;
        }
        else {
            x = new_x;
        }
    }

    public void move_point(Point p, double distance, Point dimensions) {
        double distance_y = p.y - y;
        double distance_x = p.x - x;
        double angle = Math.atan2(distance_y, distance_x);
        move_angle(angle, distance, dimensions);
    }

    public void move_random(double distance, Point dimensions) {
        double angle = Math.toRadians(Math.random() * 360);
        move_angle(angle, distance, dimensions);
    }
}
