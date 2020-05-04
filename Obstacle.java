import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.*;

/**
 * Obstacle class
 * 
 * The obstacle blocks bullets from both the player and enemies but it takes damage and shrinks when hit
 */

public class Obstacle extends Entity
{
    public Obstacle(int x, int y)
    {
        super(x, y, 70, 35, false);
    }
    
    public void render (Graphics g) {
        //if the block is nit invisible, then...
        if (getInvis() == false) {
            g.fillRect(getX() - getWidth()/2, getY() - getHeight()/2, getWidth(), getHeight());
        }
    }
}
