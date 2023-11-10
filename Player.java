// contains player object data and methods
public class Player {

    private int x; // x coordinate of character
    private double y; // y coordinate of character
    private final double radius; // radius of character
    private int wins; // player's number of wins
    private double Y_VEL = 0.75; // player velocity
    private boolean hasShot; // whether player has shot or not

    // sets player coordinates, radius, velocity, wins, and hasShoot
    public Player(int x, double y, double radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    // return player x-coordinate
    public int getX() {
        return x;
    }

    // return player y-coordinate
    public double getY() {
        return y;
    }

    // updates player location
    // player moves with constant velocity in y-direction
    public void move() {
        y = y + Y_VEL;
    }

    // returns player velocity
    public double getVY() {
        return Y_VEL;
    }

    // changes player direction
    public void flipVY() {
        Y_VEL = -Y_VEL;
    }
    

    // returns hasShot boolean
    public boolean hasShot() {
        return hasShot;
    }

    // hasShoot updates to true
    public void shoot() {
        hasShot = true;
    }

    // hasShot updates to false
    public void reload() {
        hasShot = false;
    }

    // adds one to total wins
    public void addWin() {
        wins++;
    }


    // returns current number of wins
    public int getWins() {
        return wins;
    }

    // formats print result
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    // tests methods
    public static void main(String[] args) {
        Player player = new Player(1, 1, 1);
        StdOut.println(player); // prints (1, 1)
        StdOut.println(player.getX()); // prints 1
        StdOut.println(player.getY()); // prints 1
        StdOut.println(player.getVY()); // prints 0.75
        player.flipVY();
        StdOut.println(player.getVY()); // prints -0.75
        player.move();
        StdOut.println(player.getY()); // prints 0.25
        StdOut.println(player.hasShot()); // prints false
        player.shoot();
        StdOut.println(player.hasShot()); // prints true
        player.reload();
        StdOut.println(player.hasShot()); // prints false
        player.addWin();
        StdOut.println(player.getWins()); // prints 1
    }
}
