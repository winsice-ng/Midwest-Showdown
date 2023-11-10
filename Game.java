import java.awt.Font;

// main game file that runs the game
// creates game's frames and mechanics
public class Game {
    private Player playerOne, playerTwo; // Two players
    private final int MAX_BOUND = 32; // Radius of StdDraw window

    // Player and Bullet radius
    private final double PLAYER_RADIUS = 3.5, BULLET_RADIUS = 1.5, OBSTACLE_RADIUS = 3.0;

    // Character, background, "bullet", and obstacle pictures
    private final String rSquirrel = "rightsquirrel.png", lSquirrel = "leftSquirrel.gif",
            acorn = "acorn.png", backdrop = "img.png", cactus = "cactus.png";
    private Obstacles obstacles; // arraylist of obstacles
    private boolean canShoot1, canShoot2; // determines if player has shot
    private Bullet oneBullet, twoBullet; // player's bullets
    private double xBullet, yBullet; // coordinates of bullet off screen

    public Game() {
        // sets random coordinates for players
        // flips direction of movement for second player
        int x1 = -MAX_BOUND + 6, x2 = MAX_BOUND - 6;
        double y1 = (StdRandom.uniformDouble() * (MAX_BOUND * 1.5) - (MAX_BOUND - 8));
        double y2 = (StdRandom.uniformDouble() * (MAX_BOUND * 1.5) - (MAX_BOUND - 8));
        playerOne = new Player(x1, y1, PLAYER_RADIUS);
        playerTwo = new Player(x2, y2, PLAYER_RADIUS);
        playerTwo.flipVY();

        // creates two bullet objects and sets them to off-screen coordinates
        xBullet = -33;
        yBullet = -33;
        oneBullet = new Bullet(xBullet, yBullet,
                               BULLET_RADIUS);
        twoBullet = new Bullet(xBullet, yBullet,
                               BULLET_RADIUS);
        // changes direction of second player's bullet
        twoBullet.flipVx();

        // sets up random number of obstacles (between 5 and 8)
        // at random locations
        int n = StdRandom.uniformInt(5, 8);
        obstacles = new Obstacles(MAX_BOUND, n, OBSTACLE_RADIUS);

        // initialize standard drawing
        StdDraw.setXscale(-MAX_BOUND, MAX_BOUND);
        StdDraw.setYscale(-MAX_BOUND, MAX_BOUND);
        StdDraw.enableDoubleBuffering();
    }

    // runs the game as long as a player has not reached 5 points
    public void run() {
        while (playerOne.getWins() < 5 && playerTwo.getWins() < 5) {
            // change player direction up/down when hitting boundaries
            if (hitBound(playerOne))
                playerOne.flipVY();
            if (hitBound(playerTwo))
                playerTwo.flipVY();
            // reload weapon when both players shoot and have not hit each other
            if (playerOne.hasShot() && playerTwo.hasShot() && !(inScreen(oneBullet)) && !inScreen(
                    twoBullet)) {
                reset();
            }
            // updates coordinates of players and bullet and draws frame
            update();
        }
        // upon winning, display who wins
        if (playerOne.getWins() == 5)
            StdDraw.picture(0, 0, "player1Wins.png");
        else if (playerTwo.getWins() == 5)
            StdDraw.picture(0, 0, "player2Wins.png");
        // prompts user to play again
        StdDraw.text(0, -20, "press 'f' to play again");
        StdDraw.show();
    }


    // updates player movement and draws
    public void update() {
        // update position - constant velocity

        playerOne.move();
        playerTwo.move();

        // check if the user has played a key; if so, process it
        if (StdDraw.hasNextKeyTyped()) {
            // shoots bullet from player one coord
            // updates shoot status when 's' is pressed
            if (StdDraw.isKeyPressed(83))
                if (!playerOne.hasShot()) {
                    canShoot1 = true;
                    playerOne.shoot();
                    oneBullet.setCoord(playerOne.getX(), playerOne.getY());
                }


            // shoots bullet from player two coord
            // updates shoot status when 'k' is pressed
            if (StdDraw.isKeyPressed(75))
                if (!playerTwo.hasShot()) {
                    canShoot2 = true;
                    playerTwo.shoot();
                    twoBullet.setCoord(playerTwo.getX(), playerTwo.getY());
                }
        }

        // takes player's bullet and list of obstacles
        // determines if bullet collides with obstacle
        if (hitObstacle(oneBullet, obstacles)) {
            canShoot1 = false;
            oneBullet.setCoord(xBullet, yBullet);
        }
        if (hitObstacle(twoBullet, obstacles)) {
            canShoot2 = false;
            twoBullet.setCoord(xBullet, yBullet);
        }

        // takes player's bullet and opponent
        // determines if bullet collides with opponent
        // resets round
        if (hitPlayer(oneBullet, playerTwo)) {
            reset();
            playerOne.addWin();
            canShoot1 = false;
            oneBullet.setCoord(xBullet, yBullet);
        }
        if (hitPlayer(twoBullet, playerOne)) {
            reset();
            playerTwo.addWin();
            canShoot2 = false;
            twoBullet.setCoord(xBullet, yBullet);
        }

        // updates bullet coordinates
        if (canShoot1)
            oneBullet.move();
        if (canShoot2)
            twoBullet.move();


        // set background to clear
        StdDraw.clear(StdDraw.LIGHT_GRAY);

        // draw player characters and background and bullets
        StdDraw.picture(0, 0, backdrop);
        StdDraw.picture(playerOne.getX(), playerOne.getY(), rSquirrel);
        StdDraw.picture(playerTwo.getX(), playerTwo.getY(), lSquirrel);
        for (int i = 0; i < obstacles.size(); i++) {
            StdDraw.picture(obstacles.get(i).getX(),
                            obstacles.get(i).getY(), cactus);
        }
        StdDraw.text(0, -30, playerOne.getWins() + "         " + playerTwo.getWins());
        if (canShoot1)
            StdDraw.picture(oneBullet.getX(), oneBullet.getY(), acorn);
        if (canShoot2)
            StdDraw.picture(twoBullet.getX(), twoBullet.getY(), acorn);


        // display and pause for 20 ms
        StdDraw.show();
        StdDraw.pause(20);
    }


    // takes player argument and determines whether
    // the player hits the edges of the StdDraw windoe
    public boolean hitBound(Player player) {
        return (Math.abs(player.getY() + player.getVY()) + PLAYER_RADIUS > MAX_BOUND);
    }

    // takes bullet argument and determines whether
    // bullet is in the StdDraw window
    public boolean inScreen(Bullet bullet) {
        return (Math.abs(bullet.getX() + bullet.getVx()) + BULLET_RADIUS < MAX_BOUND);
    }

    // takes bullet and player argument and determines whether
    // bullet collides with the player
    public boolean hitPlayer(Bullet bullet, Player player) {
        return bullet.inRange(player.getX(), player.getY(), PLAYER_RADIUS);
    }

    // takes bullet and obstacles argument and determines whether
    // bullet collides with any obstacle objects
    // removes obstacle object from list if true
    public boolean hitObstacle(Bullet bullet, Obstacles obstacless) {
        // checks to see if bullet collides with any of the obstacles
        // if it does, remove the obstacle
        for (int i = 0; i < obstacless.size(); i++) {
            if (bullet.inRange(obstacless.get(i).getX(), obstacless.get(i).getY(),
                               OBSTACLE_RADIUS)) {
                obstacless.remove(i);
                return true;
            }
        }
        return false;
    }


    // sets both player's hasShot boolean to false
    public void reset() {
        playerOne.reload();
        playerTwo.reload();
    }

    // simulates and runs the game
    // when game ends, the game restarts if user presses 'f' key
    public static void main(String[] args) {
        // sets up fonts for title and body of start screen
        Font title = new Font("ARIAL", Font.ITALIC, 30);
        Font body = new Font("ARIAL", Font.PLAIN, 16);

        // sets pen color to dark blue
        StdDraw.setPenColor(33, 46, 82);
        String music = "";
        StdDraw.enableDoubleBuffering();

        // Start screen
        // Prompts user to select music to play during game
        // Pressing key corresponding with song starts the game
        // plays the song in background
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                if (key == '1') {
                    music = "weeknd.wav";
                    break;
                }
                if (key == '2') {
                    music = "beberhexa.wav";
                    break;
                }
                if (key == '3') {
                    music = "lilnasx.wav";
                    break;
                }
            }
            // displays start screen text
            StdDraw.clear();
            StdDraw.picture(0.5, 0.5, "img.png");
            StdDraw.setFont(title);
            StdDraw.text(0.5, 0.8, "MIDWEST SHOWDOWN");
            StdDraw.setFont(body);
            StdDraw.text(0.5, 0.30, "Select Music to Start Game:");
            StdDraw.text(0.5, 0.25,
                         "Press '1' for the weeknd, '2' for bebe rhexa, '3' for lil nas x");
            StdDraw.text(0.5, 0.20, "-----------------------------------");
            StdDraw.text(0.5, 0.15, "Controls:");
            StdDraw.text(0.5, 0.10, "Player 1 Press 's' To Shoot & Player 2 Press 'k' To Shoot");

            StdDraw.show();
            StdDraw.pause(20);
        }
        // loops running the game
        while (true) {
            StdAudio.playInBackground(music);
            Game game = new Game();
            game.run();
            StdAudio.stopInBackground();
            while (true) {
                if (StdDraw.hasNextKeyTyped()) {
                    // pressing 'f' restarts the game
                    if (StdDraw.isKeyPressed(70))
                        break;
                }
            }
        }
    }
}








