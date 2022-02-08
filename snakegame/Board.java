package snakegame;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Board extends JPanel implements ActionListener{
    private Image apple;
    private Image dot;
    private Image head;

    private final int DOT_SIZE = 10;
    private final int ALL_DOTS = 900;

    private final int RANDOM_POSITION = 29;
    private int apple_x;
    private int apple_y;

    private final int[] X = new int[ALL_DOTS];
    private final int[] Y = new int[ALL_DOTS];

    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;
    private boolean inGame = true;

    private  int dots;
    private Timer timer;

    Board(){
        addKeyListener(new TAdapter());

        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(300, 300));
        setFocusable(true);

        loadImages();
        initGame();
    }

    public void loadImages(){
       
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("snakegame/icons/apple.png"));
       apple = i1.getImage();
       ImageIcon i2 = new ImageIcon(ClassLoader.getSystemResource("snakegame/icons/dot.png"));
       dot = i2.getImage();
       ImageIcon i3 = new ImageIcon(ClassLoader.getSystemResource("snakegame/icons/head.png"));
        head = i3.getImage();
        
    }
    public void initGame(){
        dots = 3;
        for(int z =0;z<dots;z++){
                X[z] = 50 - z * DOT_SIZE;
                Y[z] = 50;
        }
        locateApple();

        timer = new Timer(140,this);
        timer.start();

    }
    public void locateApple(){
        int r = (int)(Math.random() * RANDOM_POSITION);
        apple_x = (r * DOT_SIZE);
        r = (int)(Math.random() * RANDOM_POSITION);
        apple_y = (r * DOT_SIZE);

    }
    public void checkApple(){
        if((X[0] == apple_x) && (Y[0] == apple_y)){
            dots++;
            locateApple();
        }
        
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        if(inGame){
            g.drawImage(apple,apple_x,apple_y,this);
            for(int z =0;z<dots;z++){
                if(z == 0){
                    g.drawImage(head, X[z], Y[z], this);

                }else{
                    g.drawImage(dot, X[z], Y[z], this);
                }   
            }
            Toolkit.getDefaultToolkit().sync();

        }else{
            gameOver(g);
        }
    }
    public void gameOver(Graphics g){
        String msg = "Game Over";
        Font font = new Font("SAN_SERIF", Font.BOLD,14);
        FontMetrics metrices = getFontMetrics(font);
        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString(msg,(300 - metrices.stringWidth(msg))/2 ,300/2);
    }
    public void checkCollision(){
        for(int z =dots;z>0;z--){
            if((z > 4) && (X[0] == X[z]) && (Y[0] == Y[z])){
                inGame = false;
            }
        }
        if(Y[0] >= 300){
            inGame = false;
        }
        if(X[0] >= 300){
            inGame = false;
        }
        if(X[0] < 0){
            inGame = false;
        }
        if(Y[0] < 0){
            inGame = false;

        }
        if(!inGame){
            timer.stop();
        }

    }
    public void move(){
        for(int z = dots; z>0;z--){
            X[z] = X[z-1];
            Y[z] = Y[z-1];
        }
        if(leftDirection){
            X[0] -= DOT_SIZE;
        }
        if(rightDirection){
            X[0] +=  DOT_SIZE;
        }
        if(upDirection){
            Y[0] -= DOT_SIZE;
        }
        if(downDirection){
            Y[0] += DOT_SIZE;                                                                                             
        }                                          
    }                                                                                                                                                                                             
    
    public void actionPerformed(ActionEvent ae) {
        if(inGame){  
            checkApple();
            checkCollision();
            move();
        }
        repaint();

        
    }
    private class TAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            int key = e.getKeyCode();
            if(key == KeyEvent.VK_LEFT && (!rightDirection)){
                leftDirection = true;
                upDirection = false;
                downDirection = false;
            }
            if(key == KeyEvent.VK_RIGHT && (!leftDirection)){
                rightDirection = true;
                upDirection = false;
                downDirection = false;
            }
            if(key == KeyEvent.VK_UP && (!downDirection)){
                leftDirection = false;
                upDirection = true;
                rightDirection = false;
            }
            if(key == KeyEvent.VK_DOWN && (!upDirection)){
                leftDirection = false;
                rightDirection = false;
                downDirection = true;
            }

        }
    }


}
