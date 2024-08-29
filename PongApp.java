/**
 * File: PongApp.java
 *
 * Description: In the game of pong a ball ricochets off
 * by the up the walls of a square. The paddle on the right wall
 * is controlled and down arrow keys. The ball bounces at a random
 * angle of the paddle and the walls.
 *
 * 
 * @author Aziz Ahmed
 * 
 **/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// implement MouseMotionListener 
public class PongApp extends JFrame implements MouseMotionListener, KeyListener{
	private static final long serialVersionUID = 1L;
	private Thread ball;
    private Paddle paddle;
    private int ComputerScore, UserScore;
    public final int BORDER=50, SIZE=400;
    
 // Current MousePosition
     int mouseX=-1; 
     int mouseY=-1;
    /**
     *  Constructor: creates the ball and paddle objects, and registers as the
     *  KeyListener. It also must requestFocus() so that it will receive
     *  key events.
     */
    public PongApp() {
        ComputerScore=UserScore=0;
        setSize(400,400);
        setLocationRelativeTo(null); // opens at window center
        setBackground(Color.white);
        requestFocus();      // Required to receive key events                
        addKeyListener(this);
        // HINT:
        addMouseMotionListener(this);
        paddle = new Paddle(this);      // Create the paddle     
        ball = new Thread(new Ball(this));    // Create the ball
        //register 'Exit upon closing' as a default close operation
        setDefaultCloseOperation( EXIT_ON_CLOSE );
    } // PongApp
    
    /**
     * startGame(): starts the ball (which causes it to draw) and draws the paddle.
     */
    public void startGame() {
        ball.start();
        repaint();
    }
  
    /**
     * paint() starts the ball (which causes it to draw) and draws the paddle.
     */
    public void paint (Graphics g ) {
        g.setColor(Color.white);
        g.fillRect(0,0,SIZE,SIZE);
        g.setColor(Color.red);     // Draw the bouncing region        
        g.drawRect(BORDER,BORDER,SIZE-2*BORDER,SIZE-2*BORDER);
        g.setColor(Color.blue);

        g.drawString("Score: User: " + UserScore + " Computer: " + ComputerScore, 10,40);
        g.drawString("Press up/down arrow keys or move the mouse to move paddles",10,370);

        if (!ball.isAlive() )
            g.drawString("Ball Died!, You Lose! - Hit Space", 10, 385);

        paddle.draw();       // And draw the paddle               
    } // paint()
  
    /**
     * hitPaddle() is the callback routine called by ball to check whether it
     *  hit the paddle.
     * @param p -- A Point giving the ball's location
     * @return -- a boolean set to true if the paddle and ball are touching
     */
    public boolean hitPaddle(Point p) {
        Point paddleLoc = paddle.getLocation();
        if ( (p.x + Ball.SIZE >= paddleLoc.x)
                && (p.y >= paddleLoc.y) && (p.y <= paddleLoc.y + Paddle.SIZE))

        	return true;
        else
            return false;
    } // hitPaddle()

    /**
     * informGame() is the callback routine called by ball when it dies
     *
     */
    public void informGame() {
    	// ball died - update score 
        repaint();
        return;
    }
  
    /**
     * keyTyped() is invoked every time a KeyEvent occurs. The getKeyChar()
     *  method gets the value of the key that was pressed. Whenever the
     *  spacebar is pressed, a new bouncing ball is introduced into the applet.
     */
    public void keyTyped(KeyEvent e) {
        if (e.getKeyChar() == ' ') {	// If spacebar pressed
            if (!ball.isAlive() ) {  	// and current ball is dead
                ball = new Thread(new Ball(this));
                ball.start();
                repaint();
            }
        }
    }  // keyType()
  
    /**
     *  keyPressed() is invoked every time a KeyEvent occurs. It gets the key's code
     *  and checks whether the up or down arrow keys were pressed. If so it moves
     *  the paddle.
     */
    public void keyPressed( KeyEvent e) {	// Check for arrow keys
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_UP) {    // Up arrow     
            paddle.moveUp();
        } else if (keyCode == KeyEvent.VK_DOWN)  // Up arrow
            paddle.moveDown();
    }  // KeyPressed()


    public void keyReleased( KeyEvent e ) {
    } // keyReleased() - unused
    
//  implement MouseMotionListener Methods:
    public void mouseDragged( MouseEvent e ) {}


    public void mouseMoved( MouseEvent e ) {
    	int x = e.getX();
        int y = e.getY();

        if (y < mouseY)
            paddle.moveUp();
        else if ( y > mouseY)
            paddle.moveDown();

        mouseY = y;
        
        if (x < mouseX) 
        	paddle.moveUp();
        else if (x > mouseX) 
        	paddle.moveDown();
        
        mouseX = x;
    }

    /**
     * getComputerScore() - increases the computer score by 1 
     */
    public void getComputerScore(){
        ComputerScore ++;
    }  // END getComputerScore
    
    /**
     * getUserScore() - increases the user score by 1 
     */
    public void getUserScore(){
        UserScore ++;
    } // END getUserScore

    public static void main(String args[]) {
        PongApp p = new PongApp();
        p.setVisible(true);
        p.startGame();
    } // end main()

}  // end PongApp class
