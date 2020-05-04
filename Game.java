//imports for the key listener
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JTextField;
//swing for the GUI
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.*;
//for arraylists
import java.util.ArrayList;

public class Game
{
    //The player
    Player player;
    //keeps track of the missiles
    ArrayList<Missile> missiles;
    //keeps track of the enemy lasers
    //ArrayList<Entity> lasers;
    //an arraylist of obstacles
    ArrayList<Obstacle> obstacles;
    //an arraylist of invaders
    ArrayList<Invader> invaders;

    //this is the panel which will house all the graphics
    gamePanel gamePanel = new gamePanel();

    public Game() throws InterruptedException {
        player = new Player(500, 900, 0);
        missiles = new ArrayList<Missile>();
        //lasers = new ArrayList<Entity>();
        obstacles = new ArrayList<Obstacle>();
        invaders = new ArrayList<Invader>();

        for (int i = 1; i < 9; i++) {
            obstacles.add(new Obstacle((i*110), 800));
        }

        for (int i = 1; i < 16; i++) {
            for (int j = 1; j < 5; j++) {
                invaders.add(new Invader(i*50, j*50));
            }
        }

        Timer gameClock = new Timer(17, new TimeListener());
        gameClock.start();

        JTextField component = new JTextField();
        component.addKeyListener(new KeyListener());

        JFrame frame = new JFrame();

        frame.add(component);
        frame.add(gamePanel);

        frame.setSize(1000, 1000);
        frame.setVisible(true);
    }
    
    //method to find the extreme x-positions in an array of entities (used for the Invaders in this case, to ensure that they change directions when they hit one side of the screen)
    public int getExtremeX(ArrayList<Invader> invaders, boolean max) {
        //set default extreme to first value
        int extreme = invaders.get(0).getX();
        for (Invader i: invaders) {
            //dont count invisible entities
            if (i.getInvis() == false) {
                //if we want to find the max...
                if (max) {
                    //replace the default extreme accordingly, and repeat
                    if (i.getX() > extreme) {
                        extreme = i.getX();
                    }
                } else {
                    //replace the default extreme accordingly, and repeat
                    if (i.getX() < extreme) {
                        extreme = i.getX();
                    }
                }
            }
        }
        //return the extreme
        return extreme;
    }

    //method that returns the number of visible entities in a entity array
    public int getVisibleSize(ArrayList<Invader> invaders) {
        int visCount = 0;
        for (Invader i: invaders) {
            if (i.getInvis() == false) {
                visCount++;
            }
        }
        return visCount;
    }

    int x = 0;
    class TimeListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //repaint to refresh screen (gamePanel)
            gamePanel.repaint();
            //Player movement put into action here:
            player.move();
            //fire has to be true and 20 game cycles must pass
            if (player.getFire() == true) {
                if (player.getFireDelay() <= 0) {
                    //make some missiles
                    missiles.add(new Missile(player.getX(), player.getY()));
                    //set fire back to false
                    player.setFire(false);
                    //reset the cooldown
                    player.setFireDelay(20);
                } else {
                    //decrement the cooldown so that you can fire again
                    player.decrementFireDelay();
                }
            } else {
                //same thing
                player.decrementFireDelay();
            }

            //moves invaders every 50 frames
            if (x > (5 + getVisibleSize(invaders))) {
                x = 0;
                if (getExtremeX(invaders, true) > 920 || getExtremeX(invaders, false) < 40) {
                    for (Invader i: invaders) {
                        //change direction of travel if they get to one end of the screen or the other
                        i.invertXTrans();
                        //move all invaders down by 50 pixels
                        i.moveY(50);
                    }
                }
                //add xTransI to all x coordinates to move them from side to side
                for (Invader i: invaders) {
                    i.moveX();
                }
            }
            x++;

            for (Missile m: missiles) {
                //makes the missile go up
                m.moveY(-15);
                //check to see if the missile is in contact with the obstacle
                if (m.getY() < 800) {
                    for (Obstacle o: obstacles) {
                        //obstacle hitboxes
                        if (o.getX() - o.getWidth()/2 < m.getX() + 15 && m.getX() + 15 < o.getX() + o.getWidth()/2) {
                            if (!m.getInvis()) {
                                //decrease the width of the barrier
                                o.setWidth((int)(o.getWidth()-5));
                            }
                            //make the missile invisible(and impotent);
                            m.setInvis(true);
                        }
                    }
                }
                for (Invader i: invaders) {
                    //both have to be visible
                    if (m.getInvis() == false && i.getInvis() == false) {
                        if (i.getX() - i.getWidth()/2 < m.getX() + 15 && m.getX() + 15 < i.getX() + i.getWidth()/2 && i.getY() < m.getY() && m.getY() < i.getY() + i.getHeight()) {
                            //make the missile invisible(and impotent);
                            m.setInvis(true);
                            //make the invader invisible
                            i.setInvis(true);
                        }
                        //end the game if the invaders reach the bottom
                        if (i.getY() > 850) {
                            System.exit(0);
                        }
                    }
                }
            }
        }
    }

    class KeyListener extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            //when you press the d key (right)

            if (e.getKeyChar() == 'd') {
                //makes player go right
                player.setXTrans(3);
            }

            //when you press the a key (left)

            if (e.getKeyChar() == 'a') {
                //makes player go left
                player.setXTrans(-3);
            }

            //when you press w (fire)
            //KNOWN BUG: If you move after you start firing, the missiles will stop launching (but you can fire after you start moving)
            if (e.getKeyChar() == 'w') {
                //fires missile
                player.setFire(true);
            }
        }

        public void keyReleased(KeyEvent e) {
            //when you release the left key
            if (e.getKeyChar() == 'd' && player.getXTrans() > 0) {
                player.setXTrans(0);
            }
            //when you release the right key
            if (e.getKeyChar() == 'a' && player.getXTrans() < 0) {
                player.setXTrans(0);
            }
        }
    }   

    class gamePanel extends JPanel
    {
        @Override
        public void paintComponent (Graphics g) {
            //make backgroung rectangle
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
            //set the paddle color (green)
            g.setColor(new Color(0, 255, 0)); 
            
            //the rectangles for the obstacles
            for (Obstacle o: obstacles) {
                o.render(g);
            }
            //the rectangles for the missiles
            for (Missile m: missiles)   {
                m.render(g);
            }
            //the rectangles for the invaders
            for (Invader i: invaders) {
                i.render(g);
            }
            
            //Draw the player
            player.render(g);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new Game();
    }
}