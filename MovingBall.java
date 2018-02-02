import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.event.KeyListener.*;
import static java.awt.image.ImageObserver.ABORT;


public class MovingBall extends JPanel
    implements KeyListener {

    int x = 0;
    int y = 0;
    int xa = 1;
    int ya = 1;
    
    int r = 0;
    int ra = 0;
   
    public MovingBall() {
       
           

     addKeyListener( this );
    }

    void move() {
        if (x + xa < 0)
            xa = 1;
        if (x + xa > 300- 30)
            xa = -1;
        if (y + ya < 0)
            ya = 1;
        if (y + ya > 400 - 30)
            ya = -1;
        if (y + ya > 330 - 10)
        gameOver();
    
        x = x + xa;
        y = y + ya;
        
        if (r + ra > 0 && r + ra < 300 - 60)
            r = r + ra;
        Rectangle ball= new Rectangle(x,y,30,30);
        Rectangle racquet= new Rectangle(r,330,60,10);
        if( ball.intersects(racquet))
        {  ya = -1;
            //y= 330-10;
        }
    }
      
        public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT)
            ra = -1;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT)
            ra = 1;
    }
        public void keyReleased(KeyEvent e) {
        ra = 0;
    }
        public void keyTyped( KeyEvent e ) { System.out.println("KeyTyped");}
       
    public void paint(Graphics g) {
        super.paint(g);
        g.fillOval(x, y, 30, 30);
        g.fillRect(r, 330, 60, 10);
    }
    
    public void gameOver() {
        JOptionPane.showMessageDialog(this, "Game Over", "Game Over", JOptionPane.YES_NO_OPTION);
        System.exit(ABORT);
    }
 public static void main(String[] args) throws InterruptedException {
     
        JFrame jp1 = new JFrame();
       
        MovingBall a = new MovingBall ();
       
        //your panel needs to be focusable in order to make the Keylistener works
        a.setFocusable(true);
        a.requestFocusInWindow();
        jp1.getContentPane().add(a, BorderLayout.CENTER);
        jp1.setSize(new Dimension(300,400));
        jp1.setVisible(true);
        //Kill the thread
      
        jp1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       
        while (true) {
            a.move();
            a.repaint();
            //Thread.sleep(10);
            Thread.sleep(10);
        }
    }

 
}
