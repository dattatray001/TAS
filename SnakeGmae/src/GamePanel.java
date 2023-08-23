import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GamePanel extends JPanel implements ActionListener {
    static final int SCEERN_WIDTH =600;
    static final int SCEERN_HEIGHT=600;
    static final int UNIT_SIZE=25;

    static final int GAME_SIZE=(SCEERN_WIDTH *SCEERN_HEIGHT)/UNIT_SIZE;
    static final int DELAY=150;
    final int[] x=new int[UNIT_SIZE];
    final int[] y=new int[UNIT_SIZE];
    int bodyParts=6;
    int appleEaten;
    int appleX;
    int appleY;
    char diraction='R';
    boolean runing=false;
    Timer timer;
    Random random;


    GamePanel(){
        random=new Random();
        this.setPreferredSize(new Dimension(SCEERN_WIDTH,SCEERN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }

    public void startGame(){
       newApple();
       runing=true;
       timer=new Timer(DELAY,this);
       timer.start();
    }
   public void paintComponent(Graphics g){
     super.paintComponent(g);
     draw(g);
   }
   public void draw(Graphics g){
     if(runing)
     {   //to draw the greed
//         for (int i = 0; i < SCEERN_HEIGHT/UNIT_SIZE; i++) {
//             g.drawLine(i*UNIT_SIZE,0,i*UNIT_SIZE,SCEERN_HEIGHT);
//             g.drawLine(0,i*UNIT_SIZE, SCEERN_WIDTH,i*UNIT_SIZE);
//         }
         g.setColor(Color.red);
         g.fillOval(appleX,appleY,UNIT_SIZE,UNIT_SIZE);

         for (int i = 0; i < bodyParts; i++) {
             if(i == 0)
             {
                 g.setColor(Color.green);
                 g.fillRect(x[i],y[i],UNIT_SIZE,UNIT_SIZE);
             }else {
//                 g.setColor(new Color(45,180,0));
                 g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)) );
                 g.fillRect(x[i],y[i],UNIT_SIZE,UNIT_SIZE);
             }
         }
         //Dispaly the score
         g.setColor(Color.red);
         g.setFont(new Font("Ink Free",Font.BOLD,40));
         FontMetrics metrics=getFontMetrics(g.getFont());
         g.drawString("Score:-"+appleEaten,(SCEERN_WIDTH-metrics.stringWidth("Score:-"+appleEaten))/2,g.getFont().getSize());

     }else
     {
         gameOver(g);
     }
   }
   public void newApple(){
       appleX=random.nextInt((int)(SCEERN_WIDTH /UNIT_SIZE))*UNIT_SIZE;
       appleY=random.nextInt((int)(SCEERN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
   }
   public void move(){
       for(int i=bodyParts;i>0;i--){
          x[i]=x[i-1];
          y[i]=y[i-1];
       }
       switch(diraction){
           case 'U':
               y[0]=y[0]-UNIT_SIZE;
               break;
           case 'D':
               y[0]=y[0]+UNIT_SIZE;
               break;
           case 'L':
               x[0]=x[0]-UNIT_SIZE;
               break;
           case 'R':
               x[0]=x[0]+UNIT_SIZE;
               break;
       }
   }
   public void checkApple(){
        if((x[0]==appleX)&&(y[0]==appleY))
        {
            bodyParts++;
            appleEaten++;
            newApple();
        }

   }
   public void checkCollisions(){
//        check if head collides with body
       for (int i = bodyParts; i >0 ; i--) {
           if((x[0]==x[i])&&(y[0]==y[i])){
               runing=false;
           }
       }
       //    cheack if head touches left border
       if(x[0]<0){
           runing=false;
       }
       //    cheack if head touches right border
       if(x[0] > SCEERN_WIDTH){
           runing=false;
       }
       //    cheack if head touches top border
       if(y[0]<0){
           runing=false;
       }
       //    cheack if head touches bottom border
       if(x[0] > SCEERN_HEIGHT){
           runing=false;
       }
       if(!runing){
           timer.stop();
       }
   }


   public void gameOver(Graphics g){
       //Dispaly the score
       g.setColor(Color.red);
       g.setFont(new Font("Ink Free",Font.BOLD,40));
       FontMetrics metrics=getFontMetrics(g.getFont());
       g.drawString("Score:-"+appleEaten,(SCEERN_WIDTH-metrics.stringWidth("Score:-"+appleEaten))/2,g.getFont().getSize());

//        game over text
       g.setColor(Color.red);
       g.setFont(new Font("Ink Free",Font.BOLD,75));
       FontMetrics metrics1=getFontMetrics(g.getFont());
       g.drawString("GAME OVER",(SCEERN_WIDTH-metrics.stringWidth("GAME OVER"))/3,SCEERN_HEIGHT/2);

   }
    @Override
    public void actionPerformed(ActionEvent e) {
       if(runing){
           move();
           checkApple();
           checkCollisions();
       }
       repaint();
    }

    public class MyKeyAdapter extends KeyAdapter{
        public void keyPressed(KeyEvent e){
           switch(e.getKeyCode())
           {
               case KeyEvent.VK_LEFT:
                   if(diraction!='R')
                   {
                       diraction='L';
                   }
                   break;
               case KeyEvent.VK_RIGHT:
                   if(diraction!='L')
                   {
                       diraction='R';
                   }
                   break;
               case KeyEvent.VK_UP:
                   if(diraction!='D')
                   {
                       diraction='U';
                   }
                   break;
               case KeyEvent.VK_DOWN:
                   if(diraction!='U')
                   {
                       diraction='D';
                   }
                   break;
           }
        }
    }
}
