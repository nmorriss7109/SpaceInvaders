import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.*;

/**
 * Missile class
 */

public class Missile extends Entity
{
    public Missile (int x, int y) {
        super(x, y, 4, 6, false);
    }
    
    public void render(Graphics g){
        if (getInvis() == false) {
            g.fillRect(getX() + 13, getY(), getWidth(), getHeight());
        }
    }
    
    public void moveY (int amt) {
        //update missile location by amt
        setY(getY() + amt);
    } 
}
