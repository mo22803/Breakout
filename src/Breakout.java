import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.program.GraphicsProgram;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Breakout extends GraphicsProgram {

    private Ball ball;
    private Paddle paddle;

    public int lives=3;
    public int points;
    public int numBricksInRow;


    private Color[] rowColors = { Color.black, Color.black, Color.darkGray, Color.darkGray,  Color.GRAY, Color.gray, Color.LIGHT_GRAY, Color.lightGray, Color.white, Color.white};


    public GLabel lifeCounter = new GLabel("lives left = "+String.valueOf(lives));
    public GLabel POINTS = new GLabel("points"+points);

    //comment

    @Override
    public void init(){
        ball = new Ball(getWidth()/2, 350, 10, this.getGCanvas());
        add(ball);

        paddle = new Paddle(230, 430, 50 ,10);
        add(paddle);

        numBricksInRow = (int) (getWidth()/ (Brick.WIDTH) + 5.0);

        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < numBricksInRow; col++) {
                Brick brick = new Brick(10 + col * (Brick.WIDTH + 5), 4 * Brick.HEIGHT + row * (Brick.HEIGHT + 5), rowColors[row], row);
                add(brick);
            }
        }

        add(lifeCounter,50,50);
        add(POINTS, 500,50);




    }

    @Override
    public void run(){
        addMouseListeners();
        waitForClick();
        gameLoop();
    }

    @Override
    public void mouseMoved(MouseEvent me){
        // make sure that the paddle doesn't go offscreen
        if((me.getX() < getWidth() - paddle.getWidth()/2)&&(me.getX() > paddle.getWidth() / 2)){
            paddle.setLocation(me.getX() - paddle.getWidth()/2, paddle.getY());
        }
    }

    private void gameLoop(){
        while(true){
            // move the ball
            ball.handleMove();

            // handle collisions
            handleCollisions();

            // handle losing the ball
            if(ball.lost){
                handleLoss();
            }

            pause(5);
        }
    }

    private void handleCollisions(){
        // obj can store what we hit
        GObject obj = null;

        // check to see if the ball is about to hit something

        if(obj == null){
            // check the top right corner
            obj = this.getElementAt(ball.getX()+ball.getWidth(), ball.getY());
        }

        if(obj == null){
            // check the top left corner
            obj = this.getElementAt(ball.getX(), ball.getY());
        }




        if(obj != null){

        if(obj instanceof Paddle){

            if(ball.getX() < (paddle.getX()) + (paddle.getWidth() * .2)){
                ball.bounceleft();
            }else if(ball.getX() > (paddle.getX()) + (paddle.getWidth() * .8)){
                ball.bounceright();
            }else{
               ball.bounce();
            }

        }

        }
        // if by the end of the method obj is still null, we hit nothing

        if(obj instanceof Brick){

            ((Brick) obj).brickLives --;
            if(((Brick) obj).brickLives == 0){
                this.remove(obj);
                points++;
                POINTS.setLabel("points"+points);
            }
            ball.bounce();

        }


    }






    private void handleLoss(){
        ball.lost = false;
        lives--;
        lifeCounter.setLabel("lives left = " + lives);
        if (lives==0){
            pause(500);
            exit();
        }

        reset();
    }


    private boolean reset(){
        ball.setLocation(getWidth()/2, 350);
        paddle.setLocation(230, 430);
        waitForClick();
        return false;



    }




    public static void main(String[] args) {
        new Breakout().start();
    }

}
