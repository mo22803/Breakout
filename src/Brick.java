import acm.graphics.GRect;

import java.awt.*;



public class Brick extends GRect {

    public int brickLives;


        public static final int WIDTH = 44;
        public static final int HEIGHT = 20;


    public Brick( double x, double y, Color color,int row){
        super(x, y, WIDTH, HEIGHT);
        this.setFillColor(color);
        this.setFilled(true);
        switch (row){
            case 0:
            case 1:
                brickLives = 5;
                break;

            case 2:
            case 3:
                brickLives = 4;
                break;

            case 4:
            case 5:
                brickLives = 3;
                break;

            case 6:
            case 7:
                brickLives = 2;
                break;

            case 8:
            case 9:
                brickLives = 1;
                break;
        }
    }

    }
