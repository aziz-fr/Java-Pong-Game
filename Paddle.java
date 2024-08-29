/**
 * File: Paddle.java
 *
 * Description: This class represents a paddle for the Pong game.
 * The paddle is a vertical line segment located at the right boundary.
 * Its motion is controlled by the user via up/down arrow keys. Therefore
 * the paddle needs a reference to the app. The Paddle class contains
 * move() and draw() methods.
 *
 */

/** 
 * 
 * @author Aziz Ahmed
 * 
 */

import java.awt.*;

public class Paddle {

    public static final int SIZE = 20; // Size of Paddle
    private static final int DELTA = 10; // Size of each move
//     private static final int BORDER = 50;
    private PongApp app;  // Reference to the applet
    private Point location;
    private Dimension gameArea;

    /**
     * Paddle() constructor is passed a reference to the applet, which it
     *  uses to determine the dimensions of the game area.
     */
    public Paddle (PongApp a) {
        app = a;
        gameArea = app.getSize();
        location = new Point(gameArea.width - SIZE - app.BORDER, gameArea.height/2);
    } // Paddle()
    
    /**
     * getLocation() is returns the paddle's current location
     * @return -- a Point giving the paddle's location
     */
    public Point getLocation() {
        return location;
    }
    
    /**
     * erase() redraws the paddle (line segment) each time it is called.
     *
     */
    public void erase() {
        synchronized(app) {
            Graphics g = app.getGraphics();
            g.setColor(Color.white);
            g.drawLine (location.x, location.y, location.x, location.y + SIZE);
            g.drawLine (location.x+1, location.y, location.x+1, location.y + SIZE);
            g.dispose();
        }
    }   // erase
    
    /**
     * draw() redraws the paddle (line segment) each time it is called.
     *  Note the use of XOR mode, which enables this same method to be used
     *  to draw or erase the paddle.
     */
    public void draw() {
        synchronized(app) {
            Graphics g = app.getGraphics();
            g.setColor(Color.black);
            g.drawLine (location.x, location.y, location.x, location.y + SIZE);
            g.drawLine (location.x+1, location.y, location.x+1, location.y + SIZE);
            g.dispose();
        }
    } // draw()
    
    /**
     * moveUp() moves the paddle up by a fixed DELTA
     */
    public void moveUp () {
        erase();		// Erase present paddle
        if (location.y > app.BORDER )
            location.y -= DELTA;
        draw();			// Draw new paddle
    }  // moveUp()
    
    /**
     * moveDown() moves the paddle down by a fixed DELTA
     */
    public void moveDown() {
        erase();		// Erase current paddle
        if (location.y + SIZE < gameArea.height - app.BORDER)
            location.y += DELTA;
        draw();			 // Draw new paddle
    }	// moveDown()
}	// end Paddle class
