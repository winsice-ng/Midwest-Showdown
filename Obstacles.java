import java.util.ArrayList;

// contains obstacles object methods using obstacle data type
// imitates arraylist of obstacle objects
public class Obstacles {

    private ArrayList<Obstacle> obstacles; // list of obstacles

    // adds n number of obstacle objects with random coordinates into list
    public Obstacles(int maxbound, int n, double radius) {
        obstacles = new ArrayList<Obstacle>();
        for (int i = 0; i <= n; i++) {
            // random x coordinate between ~ -20 and 20
            double x = (StdRandom.uniformDouble() * (maxbound * 1.1) - (maxbound - 12));
            // random y coordinate between ~ -26 and 26
            double y = (StdRandom.uniformDouble() * (maxbound * 1.625) - (maxbound - 6));
            obstacles.add(new Obstacle(x, y, radius));
        }

    }

    // removes obstacle object from list
    public void remove(int i) {
        obstacles.remove(i);
    }


    // returns size of list
    public int size() {
        return obstacles.size();
    }

    // returns obstacle object at index i
    public Obstacle get(int i) {
        return obstacles.get(i);
    }

    // formats print result
    public String toString() {
        String result = "";
        for (int i = 0; i < size(); i++)
            result += obstacles.get(i);
        return result;
    }

    // tests methods
    public static void main(String[] args) {
        Obstacles obstacles = new Obstacles(32, 3, 3.5);
        StdOut.println(obstacles); // prints 4 sets of coordinates
        StdOut.println("size: " + obstacles.size()); // prints 4
        obstacles.remove(2);
        StdOut.println(obstacles); // prints 1st, 2nd, and 4th set of coordinates
        StdOut.println("size: " + obstacles.size()); // prints 3
        StdOut.println("first obstacle: " + obstacles.get(0));  // prints first set of coordinates
    }
}
