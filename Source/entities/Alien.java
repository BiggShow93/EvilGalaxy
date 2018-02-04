package entities;

import frames.SpritePattern;

public class Alien extends SpritePattern {

    private final int INITIAL_X = 1024;
    private final int INITIAL_Y = 0;
    
    public Alien(int x, int y) {
        super(x, y);

        initAlien();
    }
    

    private void initAlien() {

        loadImage("images/alien.png");
        getImageDimensions();
    }
    
    
    public void move() {

        if (x < 0) {
            x = INITIAL_X;
        }
        
        if (y > 1200) {
            y = INITIAL_Y;
        }
        
        x -= 2;
        y += 2;
 
    }
    
    public void moveFaster() {

        if (x < 0) {
            x = INITIAL_X;
        }
        
        x -= 1.2;
        
    }
}