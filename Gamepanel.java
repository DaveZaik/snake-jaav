import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.Random;

public class Gamepanel extends JPanel implements ActionListener{
    static final int SCREEN_WIDTH=600;
    static final int SCREEN_HEIGHT=600;
    static final int UNIT_SIZE=25;
    static final int GAME_UNIT = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
    static final int DELAY = 75;
    final int x[] = new int [GAME_UNIT];
    final int y[] = new int [GAME_UNIT];
    int bodypart=6;
    int appleEaten;
    int applesX;
    int applesy;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;

    Gamepanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startgame();
    }
    public void startgame(){
        newApple();
        running=true;
        timer = new Timer( DELAY,this);
        timer.start();
       
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        for (int i = 0; i < SCREEN_HEIGHT/UNIT_SIZE; i++) {
            g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
            g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
        }
        g.setColor(Color.red);
        g.fillOval(applesX, applesy, UNIT_SIZE, UNIT_SIZE);

        //body of the snake.
        for (int i = 0; i < bodypart; i++) {
            if (i ==0 ) {
                g.setColor(Color.green);
                g.fillRect(x[i],y[i], UNIT_SIZE,UNIT_SIZE);
            }
            else{
                g.setColor(Color.blue);
                g.fillRect(x[i],y[i], UNIT_SIZE,UNIT_SIZE);

            }
        }

    }
    public void newApple(){
        applesX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        applesy = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;

    }
    public void move(){
        for (int i = bodypart; i > 0; i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }

        switch(direction){
            case 'U':
                y[0]=y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0]=y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0]=x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0]=x[0] + UNIT_SIZE;
                break;
        }
    }
    public void checkApple(){

    }
    public void checkCollision(){
        //check if head collides with body
        for (int i = bodypart; i > 0; i--) {
            if((x[0]==x[i] && y[0]==y[i])){
                running = false;
            }
        }
        // check  if head touches left border
        if (x[0]  < 0 ) {
            running = false;
        }
        // check  if head touches right border
        if (x[0]  > SCREEN_WIDTH ) {
            running = false;
        }
        //check if head touches top boarder
        if (y[0] < 0) {
            running = false;
        }
        //check if head toucher bottom border
        if (y[0] > SCREEN_HEIGHT) {
            running = false;
        }
        if (!running) {
            timer.stop();
        }
    }
    public void gameOver(Graphics g){

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkApple();
            checkCollision();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            switch(e.getKeyCode()){
            case KeyEvent.VK_A:
                if(direction != 'R'){
                    direction = 'L';
                }
                break;
                case KeyEvent.VK_D:
                if(direction != 'L'){
                    direction = 'R';
                }
                break;
                case KeyEvent.VK_W:
                if(direction != 'D'){
                    direction = 'U';
                }
                break;
                case KeyEvent.VK_S:
                if(direction != 'U'){
                    direction = 'D';
                }
                break;
                
            }
        }
    }
    
}
