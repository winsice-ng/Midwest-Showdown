// contains obstacle object data and methods
public class Obstacle {

    private double x; // x coordinate of obstacle
    private double y; // y coordinate of obstacle
    private double radius; // radius of obstacle

    // constructs obstacle
    public Obstacle(double x, double y, double radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    // returns x-coordinate of obstacle
    public double getX() {
        return x;
    }

    // returns y-coordinate of obstacle
    public double getY() {
        return y;
    }

    // formats print result
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    // tests methods
    public static void main(String[] args) {
        Obstacle obstacle = new Obstacle(1, 1, 1);
        StdOut.println(obstacle.getX()); // prints 1
        StdOut.println(obstacle.getY()); // prints 1
        StdOut.println(obstacle); // prints (1, 1)
    }
}
