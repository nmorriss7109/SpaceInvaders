/**
 * Anything that appears on screen and has an x and y position is an entity
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.*;

public abstract class Entity extends JPanel
{
    private int width;
    private int height;
    private int XPos;
    private int YPos;
    private boolean invis;

    public Entity(int x, int y, int w, int h, boolean i) {
        XPos = x;
        YPos = y;
        width = w;
        height = h;
        invis = i;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getX() {
        return XPos;
    }

    public int getY() {
        return YPos;
    }
    
    public boolean getInvis() {
        return invis;
    }

    public void setWidth(int newWidth) {
        width = newWidth;
    }

    public void setHeight(int newHeight) {
        height = newHeight;
    }
    
    public void setX(int newXPos) {
        XPos = newXPos;
    }
    
    public void setY(int newYPos) {
        YPos = newYPos;
    }
    
    public void setInvis(boolean newInvis) {
        invis = newInvis;
    }
}
