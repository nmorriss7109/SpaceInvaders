/**
 * The player
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.*;

public class Player extends Entity
{
    //counter for player lives left
    private int livesLeft;
    //declare X-coordinate translation for player
    private int xTrans;
    //boolean to allow/disallow firing missiles
    private boolean fire;
    //delay between missiles
    private int fireDelay;
    
    public Player (int x, int y, int xt) {
        super(x, y, 30, 20, false);
        xTrans = xt;
        fire = true;
        fireDelay = 0;
    }
    
    public void render (Graphics g) {
        //the rectangles for the player
        g.fillRect(getX(), getY(), getWidth(), getHeight());
        g.fillRect(getX() + 11, getY() - 10, 8, 10);
    }
    
    public void move () {
        //update player location
        super.setX(super.getX() + xTrans);
    } 
    
    public void setXTrans(int xt) {
        xTrans = xt;
    }
    public int getXTrans() {
        return xTrans;
    }
    
    public void setFire (boolean f) {
        fire = f;
    }
    public boolean getFire () {
        return fire;
    }
    
    public void setFireDelay (int fd) {
        fireDelay = fd;
    }
    public int getFireDelay () {
        return fireDelay;
    }
    
    public void decrementFireDelay () {
        fireDelay -= 1;
    }
}
