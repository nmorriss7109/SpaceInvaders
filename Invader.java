import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.*;

/**
 * Enemy class
 */

public class Invader extends Entity
{
    //declare X-coordinate translation for player
    private int xTrans;
    
    public Invader (int x, int y) {
        super(x, y, 20, 20, false);
        xTrans = 10;
    }
    
    public void render (Graphics g) {
        if (getInvis() == false) {
            g.fillRect(getX() - getWidth()/2, getY() - getHeight()/2, getWidth(), getHeight());
        }
    }
    
    public void moveX () {
        //update player location
        setX(getX() + xTrans);
    } 
    
    public void moveY (int amt) {
        //update invader location by amt
        setY(getY() + amt);
    } 
    
    public void invertXTrans() {
        xTrans *= -1;
    }
}
