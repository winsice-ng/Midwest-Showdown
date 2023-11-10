// contains bullet object data and methods
public class Bullet {
    private double x; // x coordinate of bullet
    private double y; // y coordinate of bullet
    private final double radius; // radius of bullet
    private double X_VEL = 1.5; // x velocity of bullet

    // sets bullet coordinates, radius, and velocity
    public Bullet(double x, double y, double radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    // returns x-coordinate of bullet
    public double getX() {
        return x;
    }

    // returns y-coordinate of bullet
    public double getY() {
        return y;
    }

    // returns bullet velocity
    public double getVx() {
        return X_VEL;
    }

    // flips direction of bullet
    public void flipVx() {
        X_VEL = -X_VEL;
    }

    // updates bullet location
    // bullet only moves in x direction
    public void move() {
        x = x + X_VEL;
    }

    // takes player argument and updates bullet
    // coordinates to player coordinates
    public void setCoord(double x1, double y1) {
        this.x = x1;
        this.y = y1;
    }


    // returns true if bullet collides/hits player or obstacle
    // returns false otherwise
    public boolean inRange(double x1, double y1, double otherR) {
        double distance = Math.sqrt(Math.pow(this.x - x1, 2) + Math.pow(this.y - y1, 2));
        return (distance <= radius + otherR);
    }

    // formats print result
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    // tests methods
    public static void main(String[] args) {
        Bullet bullet = new Bullet(1, 1, 1);
        StdOut.println(bullet); // prints (1, 1)
        StdOut.println(bullet.getX()); // prints 1
        StdOut.println(bullet.getY()); // prints 1
        StdOut.println(bullet.getVx()); // prints 1.5
        bullet.flipVx();
        StdOut.println(bullet.getVx()); // prints -1.5
        bullet.move();
        StdOut.println(bullet); // prints (-0.5, 1)
        bullet.setCoord(0, 0);
        StdOut.println(bullet); // prints (0, 0)
        StdOut.println(bullet.inRange(0, 0, 1)); // prints true
        StdOut.println(bullet.inRange(5, 5, 1)); // prints false
    }
}
