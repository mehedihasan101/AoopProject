package com.example.aoop.PongGameMain;

import com.example.aoop.PongGameMainNetwork.PlayGame;
import com.example.aoop.PongGameMainNetwork.StartGame;
import com.example.aoop.PongGameMainNetwork.UserType;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@SuppressWarnings("serial")
public class StartingClass extends JPanel implements Runnable, MouseListener,
        MouseMotionListener, KeyListener {

    File ff1=null;
    File ff2=null;
    File ff3=null;

    Paddle paddle, comp_paddle, left_paddle, right_paddle; // the four paddles
    // in the game
    int n_balls = 1; // number of balls in the game
    public int init_life = 10; // number of lives for each player in the
    // beginning
    Ball ball[] = new Ball[n_balls]; // ball objects for the n balls
    private Image Ball, Paddle, image, Life; // ball -> ball image; paddle ->
    // paddle image; image TODO
    // (donno)
    private Graphics second;
    int paddle_life, comp_life, left_life, right_life; // paddle lives for the
    int paddle_life_rec, comp_life_rec, left_life_rec, right_life_rec; // paddle lives for the
    // four paddles

    int disp_comp,disp_left,disp_right;

    int anim_time = 5; // anim_time -> time to
    public static boolean level_select, started, clicked_ip_box, player2, player3,
            player4, to_start, // started => main game started; clicked_ip_box
    // => ipbox clicked; playerx -> human;
    create_not_join, allJoined; // to_start -> create_not_join -> game
    // created

    String ip_text = "Your IP Goes Here";
    final int barrier_height = 50;

    public int level = 1;

    final int border_top = 0, border_bottom = 480, border_left = 0,
            border_right = 480;
    public int playerNum = 1;

    long t_old;
    public static boolean broadcastBall = false;

    public StartingClass() {
        paddle_life = init_life;
        comp_life = init_life;
        left_life = init_life;
        right_life = init_life;
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);
        setSize(border_right + 200, border_bottom);
        setBackground(Color.BLACK);
        setFocusable(true);
//		Frame frame = (Frame) this.getParent().getParent();
//		frame.setTitle("Network Pong");
//		try {
//			base = getDocumentBase();
//		} catch (Exception e) {
//		}
        BufferedImage i1=null,i2=null,i3=null;
        try {
            ff1=new File("src/main/java/com/example/aoop/PongGameMain/Paddle.png");
            i1 = new BufferedImage(120, 120, BufferedImage.TYPE_INT_ARGB);
            i1=ImageIO.read(ff1);

            ff2=new File("src/main/java/com/example/aoop/PongGameMain/Ball.png");
            i2 = new BufferedImage(30, 30, BufferedImage.TYPE_INT_ARGB);
            i2=ImageIO.read(ff2);

            ff3=new File("src/main/java/com/example/aoop/PongGameMain/Life.png");
            i3 = new BufferedImage(80, 80, BufferedImage.TYPE_INT_ARGB);
            i3=ImageIO.read(ff2);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Paddle = i1;
        Ball = i2;
        Life = i3;

    }

    public void start() {
        allJoined = false;
        started = false;
        clicked_ip_box = false;
        player2 = false;
        player3 = false;
        player4 = false;
        to_start = false;
        create_not_join = false;
        paddle = new Paddle(80, border_right / 2,
                border_right - barrier_height, barrier_height, 20, -1);
        comp_paddle = new Paddle(80, border_right / 2, border_right
                - barrier_height, barrier_height, 20, -1);
        left_paddle = new Paddle(80, border_right / 2, border_right
                - barrier_height, barrier_height, 20, -1);
        right_paddle = new Paddle(80, border_right / 2, border_right
                - barrier_height, barrier_height, 20, -1);
        int radius = 10;
        for (int b = 0; b < n_balls; b++)
            ball[b] = new Ball(paddle.getPos()
                    + (2 * paddle.getSize() * (2 * b - n_balls))
                    / (5 * n_balls),
                    (int) (border_bottom - paddle.height - radius), radius);
        gameNotStarted();
        Thread thread = new Thread(this);
        thread.start();

    }

    private void gameNotStarted() {
        Thread thread = new Thread() {
            public void run() {
                while (!started)
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
            }
        };
        thread.start();
    }

    public void paint1(Graphics g) {
//		System.out.println("EGERGEG#### "
//				+ (System.currentTimeMillis() - t_old) + "  "+
//				ball[0].getSpeedX() + "  " + ball[0].getSpeedY() + "  "+
//				(ball[0].getSpeedX()*ball[0].getSpeedX() + ball[0].getSpeedY()*ball[0].getSpeedY())
//				+"  " + ball[0].getX() + "   " + ball[0].getY());
//		t_old = System.currentTimeMillis();
        if(!broadcastBall) {
            switch(playerNum)
            {
                case 2:
                    ball[0].setY(border_right-PlayGame.getBallXPos());
                    ball[0].setX(PlayGame.getBallYPos());
                    break;
                case 3:
                    ball[0].setX(border_right-PlayGame.getBallXPos());
                    ball[0].setY(border_bottom-PlayGame.getBallYPos());
                    break;
                case 4:
                    ball[0].setY(PlayGame.getBallXPos());
                    ball[0].setX(border_bottom-PlayGame.getBallYPos());
                    break;
                default:
                    ball[0].setX(PlayGame.getBallXPos());
                    ball[0].setY(PlayGame.getBallYPos());
            }
            paddle_life_rec = PlayGame.getLife(1);
            comp_life_rec = PlayGame.getLife(3);
            left_life_rec = PlayGame.getLife(2);
            right_life_rec = PlayGame.getLife(4);

            disp_comp = PlayGame.getPos(3);
            disp_left = PlayGame.getPos(2);
            disp_right = PlayGame.getPos(4);
        }
        else
        {
            if(player3)
                disp_comp = PlayGame.getPos(3);
            else
                disp_comp = comp_paddle.getPos();
            if(player2)
                disp_left = PlayGame.getPos(2);
            else
                disp_left = left_paddle.getPos();
            if(player4)
                disp_right = PlayGame.getPos(4);
            else
                disp_right = right_paddle.getPos();

            paddle_life_rec=paddle_life;
            comp_life_rec=comp_life;
            left_life_rec=left_life;
            right_life_rec=right_life;
        }
        comp_paddle.setPos(disp_comp);
        left_paddle.setPos(disp_left);
        right_paddle.setPos(disp_right);
        disp_comp = comp_paddle.getPos();
        disp_left = left_paddle.getPos();
        disp_right = right_paddle.getPos();
        if(left_life <= 0 && comp_life<=0 && right_life<=0)
        {
            new WinningScreen().paint(g, 1);
            return;
        }
        if(paddle_life <= 0 && comp_life<=0 && right_life<=0)
        {
            new WinningScreen().paint(g, 2);
            return;
        }
        if(left_life <= 0 && paddle_life<=0 && right_life<=0)
        {
            new WinningScreen().paint(g, 3);
            return;
        }
        if(left_life <= 0 && comp_life<=0 && paddle_life<=0)
        {
            new WinningScreen().paint(g, 4);
            return;
        }

        for (int i = 0; i < n_balls; i++)
            g.drawImage(Ball, (int) ball[i].getX() - ball[i].getSize(),
                    (int) ball[i].getY() - ball[i].getSize(),
                    2 * ball[i].getSize(), 2 * ball[i].getSize(), this);

        if (paddle_life_rec > 0)
            g.drawImage(Paddle, paddle.getPos() - paddle.getSize() / 2,
                    border_bottom - paddle.height, paddle.getSize(),
                    paddle.height, this);
        if (comp_life_rec > 0)
            g.drawImage(Paddle, border_right - disp_comp
                            - comp_paddle.getSize() / 2, border_top,
                    comp_paddle.getSize(), comp_paddle.height, this);
        if (left_life_rec > 0)
            g.drawImage(Paddle, border_left,
                    disp_left - left_paddle.getSize() / 2,
                    left_paddle.height, left_paddle.getSize(), this);
        if (right_life_rec > 0)
            g.drawImage(
                    Paddle,
                    border_right - right_paddle.height,
                    border_bottom - disp_right
                            - right_paddle.getSize() / 2, right_paddle.height,
                    right_paddle.getSize(), this);
        // barriers
        g.drawImage(Paddle, border_left, border_top, barrier_height,
                barrier_height, this);
        g.drawImage(Paddle, border_right - barrier_height, border_top,
                barrier_height, barrier_height, this);
        g.drawImage(Paddle, border_left, border_bottom - barrier_height,
                barrier_height, barrier_height, this);
        g.drawImage(Paddle, border_right - barrier_height, border_bottom
                - barrier_height, barrier_height, barrier_height, this);
        g.setColor(Color.WHITE);
        g.setFont(new Font("TimesRoman", Font.BOLD, 20));
        g.drawString(paddle_life + "\n" + comp_life + "\n" + left_life + "\n"
                + right_life, 500, 100);
        switch (paddle_life_rec) {
            case 10:
                g.drawImage(Life, border_right + 5, border_top + 40, 15, 15, this);
            case 9:
                g.drawImage(Life, border_right + 25, border_top + 40, 15, 15, this);
            case 8:
                g.drawImage(Life, border_right + 45, border_top + 40, 15, 15, this);
            case 7:
                g.drawImage(Life, border_right + 65, border_top + 40, 15, 15, this);
            case 6:
                g.drawImage(Life, border_right + 85, border_top + 40, 15, 15, this);
            case 5:
                g.drawImage(Life, border_right + 105, border_top + 40, 15, 15, this);
            case 4:
                g.drawImage(Life, border_right + 125, border_top + 40, 15, 15, this);
            case 3:
                g.drawImage(Life, border_right + 145, border_top + 40, 15, 15, this);
            case 2:
                g.drawImage(Life, border_right + 165, border_top + 40, 15, 15, this);
            case 1:
                g.drawImage(Life, border_right + 185, border_top + 40, 15, 15, this);
                break;
            case 0:
                g.setColor(Color.WHITE);
                g.setFont(new Font("TimesRoman", Font.BOLD, 15));
                g.drawString("Player 1 Dead",border_right + 5, border_top+40);
        }
        switch (left_life_rec) {
            case 10:
                g.drawImage(Life, border_right + 5, border_top + 80, 15, 15, this);
            case 9:
                g.drawImage(Life, border_right + 25, border_top + 80, 15, 15, this);
            case 8:
                g.drawImage(Life, border_right + 45, border_top + 80, 15, 15, this);
            case 7:
                g.drawImage(Life, border_right + 65, border_top + 80, 15, 15, this);
            case 6:
                g.drawImage(Life, border_right + 85, border_top + 80, 15, 15, this);
            case 5:
                g.drawImage(Life, border_right + 105, border_top + 80, 15, 15, this);
            case 4:
                g.drawImage(Life, border_right + 125, border_top + 80, 15, 15, this);
            case 3:
                g.drawImage(Life, border_right + 145, border_top + 80, 15, 15, this);
            case 2:
                g.drawImage(Life, border_right + 165, border_top + 80, 15, 15, this);
            case 1:
                g.drawImage(Life, border_right + 185, border_top + 80, 15, 15, this);
                break;
            case 0:
                g.setColor(Color.WHITE);
                g.setFont(new Font("TimesRoman", Font.BOLD, 15));
                g.drawString("Player 2 Dead",border_right + 5, border_top+80);
        }
        switch (comp_life_rec) {
            case 10:
                g.drawImage(Life, border_right + 5, border_top + 120, 15, 15, this);
            case 9:
                g.drawImage(Life, border_right + 25, border_top + 120, 15, 15, this);
            case 8:
                g.drawImage(Life, border_right + 45, border_top + 120, 15, 15, this);
            case 7:
                g.drawImage(Life, border_right + 65, border_top + 120, 15, 15, this);
            case 6:
                g.drawImage(Life, border_right + 85, border_top + 120, 15, 15, this);
            case 5:
                g.drawImage(Life, border_right + 105, border_top + 120, 15, 15,
                        this);
            case 4:
                g.drawImage(Life, border_right + 125, border_top + 120, 15, 15,
                        this);
            case 3:
                g.drawImage(Life, border_right + 145, border_top + 120, 15, 15,
                        this);
            case 2:
                g.drawImage(Life, border_right + 165, border_top + 120, 15, 15,
                        this);
            case 1:
                g.drawImage(Life, border_right + 185, border_top + 120, 15, 15,
                        this);
                break;
            case 0:
                g.setColor(Color.WHITE);
                g.setFont(new Font("TimesRoman", Font.BOLD, 15));
                g.drawString("Player 3 Dead",border_right + 5, border_top+120);
        }
        switch (right_life_rec) {
            case 10:
                g.drawImage(Life, border_right + 5, border_top + 160, 15, 15, this);
            case 9:
                g.drawImage(Life, border_right + 25, border_top + 160, 15, 15, this);
            case 8:
                g.drawImage(Life, border_right + 45, border_top + 160, 15, 15, this);
            case 7:
                g.drawImage(Life, border_right + 65, border_top + 160, 15, 15, this);
            case 6:
                g.drawImage(Life, border_right + 85, border_top + 160, 15, 15, this);
            case 5:
                g.drawImage(Life, border_right + 105, border_top + 160, 15, 15,
                        this);
            case 4:
                g.drawImage(Life, border_right + 125, border_top + 160, 15, 15,
                        this);
            case 3:
                g.drawImage(Life, border_right + 145, border_top + 160, 15, 15,
                        this);
            case 2:
                g.drawImage(Life, border_right + 165, border_top + 160, 15, 15,
                        this);
            case 1:
                g.drawImage(Life, border_right + 185, border_top + 160, 15, 15,
                        this);
                break;
            case 0:
                g.setColor(Color.WHITE);
                g.setFont(new Font("TimesRoman", Font.BOLD, 15));
                g.drawString("Player 4 Dead",border_right + 5, border_top+160);
        }
    }


    public void paintComponent(Graphics g) {
        if (image == null) {
            image = createImage(this.getWidth(), this.getHeight());
            second = image.getGraphics();
        }

        second.setColor(getBackground());
        second.fillRect(0, 0, getWidth(), getHeight());
        second.setColor(getForeground());

        if (started) {
            paint1(second);
            Image arr[] = { Paddle };
            // System.out.println("FfefwefPPPP" + level);
            switch (level) {
                case 0:
                    new Level0().display(second, arr);
                    break;
                case 1:
                    new Level1().display(second, arr);
                    break;
                case 2:
                    new Level2().display(second, arr);
                    break;
            }
        } else if (to_start && create_not_join && level_select)
            new ChooseLevel(Paddle).paint(second);
        else if (to_start && create_not_join)
            new OpeningScreen(Paddle, ip_text, player2, player3, player4)
                    .paint(second);
        else if (to_start && !create_not_join)
            new JoinGame(Paddle, ip_text).paint(second);
        else
            new Choose_Game_Type(Paddle).paint(second);
        g.drawImage(image, 0, 0, this);

    }

    @Override
    public void run() {
        float x, y, radius = ball[0].getSize(), paddle_pos, paddle_size, comp_paddle_pos, comp_paddle_size, right_paddle_pos, right_paddle_size, left_paddle_size, left_paddle_pos;
        boolean flag = true;
        int paddle_life_old,comp_life_old,left_life_old,right_life_old;
        long t1, t2, t3 = 0, t4;
        while (true) {
            t1 = System.currentTimeMillis();
            if (flag)
                repaint();
            paddle_life_old = paddle_life;
            comp_life_old = comp_life;
            left_life_old = left_life;
            right_life_old = right_life;
            for (int b = 0; b < n_balls; b++) {
                x = ball[b].getX();
                y = ball[b].getY();
                // System.out.println(b + "  " + x + "  " + y);
                paddle_pos = paddle.getPos();
                paddle_size = paddle.getSize();

                // setOnPaddle() calls
                // if (ball[b].getSpeedX() == 0 && ball[b].getSpeedY() == 0
                // && y + radius + paddle.height >= border_bottom)
                // ball[b].setOnPaddle(true);
                // else
                // ball[b].setOnPaddle(false);

                // paddle reflections
                if (paddle_life_rec>0 && y + paddle.height + radius >= border_bottom
                        && (Math.abs(x - paddle_pos) <= paddle_size / 2)) {
                    float sx = ball[b].getSpeedX(), sy = ball[b].getSpeedY();
                    float speed = (float) Math.sqrt(sx * sx + sy * sy);
                    double angle = 5 * Math.PI / 6 * (x - paddle_pos)
                            / paddle_size;
                    ball[b].setSpeedY((float) (-speed * Math.cos(angle)));
                    ball[b].setSpeedX((float) (speed * Math.sin(angle)));
                }

                // comp_paddle
                comp_paddle_pos = border_right - comp_paddle.getPos();
                comp_paddle_size = comp_paddle.getSize();

                // comp_paddle reflections
                if (comp_life_rec>0 && y - comp_paddle.height - radius <= border_top
                        && (Math.abs(x - comp_paddle_pos) <= comp_paddle_size / 2)) {
                    float sx = ball[b].getSpeedX(), sy = ball[b].getSpeedY();
                    float speed = (float) Math.sqrt(sx * sx + sy * sy);
                    double angle = 5 * Math.PI / 6 * (x - comp_paddle_pos)
                            / comp_paddle_size;
                    ball[b].setSpeedY((float) (speed * Math.cos(angle)));
                    ball[b].setSpeedX((float) (speed * Math.sin(angle)));
                }

                // left_paddle
                left_paddle_pos = left_paddle.getPos();
                left_paddle_size = left_paddle.getSize();

                // left_paddle reflections
                if (left_life_rec>0 && x - left_paddle.height - radius <= border_left
                        && (Math.abs(y - left_paddle_pos) <= left_paddle_size / 2)) {
                    float sx = ball[b].getSpeedX(), sy = ball[b].getSpeedY();
                    float speed = (float) Math.sqrt(sx * sx + sy * sy);
                    double angle = 5 * Math.PI / 6 * (y - left_paddle_pos)
                            / left_paddle_size;
                    ball[b].setSpeedX((float) (speed * Math.cos(angle)));
                    ball[b].setSpeedY((float) (speed * Math.sin(angle)));
                    // System.out.println(b+" "+comp_paddle_pos + " " + x + " "
                    // +
                    // comp_paddle.getSize() + " " + ball[b].getSpeedX() + " "+
                    // ball[b].getSpeedY());
                }
                // right_paddle
                right_paddle_pos = border_bottom - right_paddle.getPos();
                right_paddle_size = right_paddle.getSize();

                // left_paddle reflections
                if (right_life_rec>0 && x + right_paddle.height + radius >= border_right
                        && (Math.abs(y - right_paddle_pos) <= right_paddle_size / 2)) {
                    float sx = ball[b].getSpeedX(), sy = ball[b].getSpeedY();
                    float speed = (float) Math.sqrt(sx * sx + sy * sy);
                    double angle = 5 * Math.PI / 6 * (y - right_paddle_pos)
                            / right_paddle_size;
                    ball[b].setSpeedX((float) (-speed * Math.cos(angle)));
                    ball[b].setSpeedY((float) (speed * Math.sin(angle)));
                    // System.out.println(b+" "+comp_paddle_pos + " " + x + " "
                    // +
                    // comp_paddle.getSize() + " " + ball[b].getSpeedX() + " "+
                    // ball[b].getSpeedY());
                }

                // border reflections
                if (x - radius <= border_left) {
                    ball[b].setSpeedX(Math.abs(ball[b].getSpeedX()));
                    ball[b].setX(border_left + radius);
                    left_life--;
                }
                if ((border_top + barrier_height >= y - radius || border_bottom
                        - barrier_height <= y + radius)
                        && x - radius <= border_left + barrier_height
                        && x - radius - ball[b].getSpeedX() > border_left
                        + barrier_height) {
                    ball[b].setSpeedX(Math.abs(ball[b].getSpeedX()));
                    ball[b].setX(border_left + barrier_height + radius);
                }
                if (x + radius >= border_right) {
                    ball[b].setSpeedX(-Math.abs(ball[b].getSpeedX()));
                    ball[b].setX(border_right - radius);
                    right_life--;
                }
                if ((border_top + barrier_height >= y - radius || border_bottom
                        - barrier_height <= y + radius)
                        && x + radius >= border_right - barrier_height
                        && x + radius - ball[b].getSpeedX() < border_right
                        - barrier_height) {
                    ball[b].setSpeedX(-Math.abs(ball[b].getSpeedX()));
                    ball[b].setX(border_right - barrier_height - radius);
                }
                if (y - radius <= border_top) {
                    ball[b].setSpeedY(Math.abs(ball[b].getSpeedY()));
                    ball[b].setY(border_top + radius);
                    comp_life--;
                }
                if ((x - radius <= border_left + barrier_height || x + radius >= border_right
                        - barrier_height)
                        && border_top + barrier_height >= y - radius
                        && border_top + barrier_height < y - radius
                        - ball[b].getSpeedY()) {
                    ball[b].setSpeedY(Math.abs(ball[b].getSpeedY()));
                    ball[b].setY(border_top + barrier_height + radius);
                }
                if (y + radius >= border_bottom) {
                    ball[b].setSpeedY(-Math.abs(ball[b].getSpeedY()));
                    ball[b].setY(border_bottom - radius);
                    paddle_life--;
                }
                if ((x - radius < border_left + barrier_height || x + radius > border_right
                        - barrier_height)
                        && border_bottom - barrier_height <= y + radius
                        && border_bottom - barrier_height > y + radius
                        - ball[b].getSpeedY()) {
                    ball[b].setSpeedY(-Math.abs(ball[b].getSpeedY()));
                    ball[b].setY(border_bottom - barrier_height - radius);
                }
                // update ball position
                ball[b].update();
                // System.out.println(b + " " + ball[b].getX() + " " +
                // ball[b].getY());
                switch (level) {
                    case 0:
                        new Level0().reflect(ball[b]);
                        break;
                    case 1:
                        new Level1().reflect(ball[b]);
                        break;
                    case 2:
                        new Level2().reflect(ball[b]);
                        break;
                }

                // System.out.println("FWEFF+_+_+" +
                // ball[b].isOnPaddle()+"   "+ball[b].getSpeedX()+"  "+ball[b].getSpeedY()+" "+(ball[b].getSpeedX()==0));
                if (ball[b].isOnPaddle()) {
                    // ball[b].setOnPaddle(false);
                    switch (playerNum) {
                        case 1:
                            ball[b].setX(paddle.getPos()
                                    + (2 * paddle.height * (2 * b - n_balls))
                                    / (5 * n_balls));
                            ball[b].setY(border_bottom - paddle.height
                                    - ball[b].getSize());
                            break;

                        case 2:
                            ball[b].setY(border_bottom
                                    - (right_paddle.getPos() + (2 * right_paddle.height * (2 * b - n_balls))
                                    / (5 * n_balls)));
                            ball[b].setX(border_right - right_paddle.height
                                    - ball[b].getSize());
                            break;

                        case 3:
                            ball[b].setX(border_right
                                    - (comp_paddle.getPos() + (2 * comp_paddle.height * (2 * b - n_balls))
                                    / (5 * n_balls)));
                            ball[b].setY(comp_paddle.height + ball[b].getSize());
                            break;

                        case 4:
                            ball[b].setY(left_paddle.getPos()
                                    + (2 * left_paddle.height * (2 * b - n_balls))
                                    / (5 * n_balls));
                            ball[b].setX(left_paddle.height + ball[b].getSize());
                            break;
                    }
                    // System.out.println("FEWFWEFWEf___ "+playerNum+"   "+ball[b].getX()+"  "+ball[b].getY());
                }
                // System.out.println("GEGEGEGER+++++");
            }
            // <<<<<<< HEAD
            // =======

            // <<<<<<< HEAD

            // comp_paddle.setPos(10);
            // if (ball[0].getX() != border_right - comp_paddle.getPos())
            // comp_paddle
            // .setPos((int) (border_right - ball[0].getX() + (ball[0]
            // .getX() - border_right - comp_paddle.getPos() >= 0 ? 10
            // : -10)));

            // if (ball[0].getY() != border_bottom - comp_paddle.getPos())
            // left_paddle
            // .setPos((int) (ball[0].getY() + (ball[0].getY()
            // - border_right - left_paddle.getPos() >= 0 ? 10
            // : -10)));

            // if (ball[0].getY() != border_bottom - comp_paddle.getPos())
            // right_paddle
            // .setPos((int) (border_bottom - ball[0].getY() - (ball[0]
            // .getY() - border_right - right_paddle.getPos() >= 0 ? 10
            // : -10)));

            // =======
            // >>>>>>> 03b9831ff851f6d9e43eb4799eb5981451c76b8d
            Ball bal = ball[0];
            float ballSpeedX = bal.getSpeedX(), ballSpeedY = bal.getSpeedY();
            if (!player3) {
                // AI logic for COMP_PADDLE

                // Steps:
                // step 1: If the ball is moving away stop paddle
                if (ballSpeedY >= 0) {
                    // comp_paddle.setPos(comp_paddle.getPos());
                    comp_paddle.setSpeed(0);
                }
                // step 2: calculate ball's final position on the paddle wall if
                // its approaching
                else {
                    // number of steps to reach top y
                    float predictX;
                    int numSteps = 0;
                    float tmpY = bal.getY();
                    while (tmpY > 0) {
                        tmpY += ballSpeedY;
                        numSteps++;
                    }
                    // predict X in infinite plane
                    predictX = (numSteps * ballSpeedX); // distance ball travels
                    // relative to original
                    // position
                    if (predictX < 0) {
                        predictX = -predictX;
                        predictX = (predictX % 960);
                        if (predictX > bal.getX()) {
                            predictX = predictX - bal.getX();
                            if (predictX > 480) {
                                predictX = 480 - (predictX - 480);
                            }
                        } else {
                            predictX = bal.getX() - predictX;
                        }
                    } else { // dist travelled > 0
                        predictX = predictX % 960;
                        if (predictX > (480 - bal.getX())) {
                            predictX = (predictX - (480 - bal.getX()));
                            if (predictX > 480) {
                                predictX = predictX - 480;
                            } else {
                                predictX = 480 - predictX;
                            }
                        } else {
                            predictX = 480 - predictX;
                        }
                    }
                    // comp_paddle.setSpeed(((480-(int)(predictX))/numSteps));
                    float finalPos = 480 - (int) (predictX);
                    if (comp_paddle.getPos() > finalPos) {
                        comp_paddle.setPos(comp_paddle.getPos() - 1);
                    } else {
                        comp_paddle.setPos(comp_paddle.getPos() + 1);
                    }

                }
                // comp_paddle.setPos(comp_paddle.getPos()+comp_paddle.getSpeed());

                // if (ball[0].getX() != border_right - comp_paddle.getPos())
                // comp_paddle
                // .setPos((int) (border_right - ball[0].getX() + (ball[0]
                // .getX() - border_right - comp_paddle.getPos() >= 0 ? 10
                // : -10)));
            } else if (started && allJoined) {
                comp_paddle.setPos(PlayGame.getPos(3));
            }
            if (!player2) {
                // AI for LEFT PADDLE
                // step 1: If the ball is moving away stop paddle
                if (ballSpeedX >= 0) {
                    // comp_paddle.setPos(comp_paddle.getPos());
                    left_paddle.setSpeed(0);
                }
                // step 2: calculate ball's final position on the paddle wall if
                // its approaching
                else {
                    // number of steps to reach top y
                    float predictY = 0;
                    int numSteps = 0;
                    float tmpX = bal.getX();
                    while (tmpX > 0) {
                        tmpX += ballSpeedX;
                        numSteps++;
                    }
                    // predict Y in infinite plane
                    predictY = (numSteps * ballSpeedY); // distance ball travels
                    // relative to original
                    // position
                    if (predictY < 0) {
                        predictY = -predictY;
                        predictY = (predictY % 960);
                        if (predictY > bal.getY()) {
                            predictY = predictY - bal.getY();
                            if (predictY > 480) {
                                predictY = 480 - (predictY - 480);
                            }
                        } else {
                            predictY = bal.getY() - predictY;
                        }
                    } else { // dist travelled > 0
                        predictY = predictY % 960;
                        if (predictY > (480 - bal.getY())) {
                            predictY = (predictY - (480 - bal.getY()));
                            if (predictY > 480) {
                                predictY = predictY - 480;
                            } else {
                                predictY = 480 - predictY;
                            }
                        } else {
                            predictY = 480 - predictY;
                        }
                    }
                    // comp_paddle.setSpeed(((480-(int)(predictX))/numSteps));
                    float finalPos = (int) (predictY);
                    if (left_paddle.getPos() > finalPos) {
                        left_paddle.setPos(left_paddle.getPos() - 1);
                    } else {
                        left_paddle.setPos(left_paddle.getPos() + 1);
                    }

                }

                // if (ball[0].getY() != border_bottom - comp_paddle.getPos())
                // left_paddle
                // .setPos((int) (ball[0].getY() + (ball[0].getY()
                // - border_right - left_paddle.getPos() >= 0 ? 10
                // : -10)));
            } else if (started && allJoined) {
                left_paddle.setPos(PlayGame.getPos(2));
            }
            if (!player4) {
                // AI for RIGhT PADDLE
                // step 1: If the ball is moving away stop paddle
                if (ballSpeedX <= 0) {
                    // comp_paddle.setPos(comp_paddle.getPos());
                    right_paddle.setSpeed(0);
                }
                // step 2: calculate ball's final position on the paddle wall if
                // its approaching
                else {
                    // number of steps to reach top y
                    float predictY = 0;
                    int numSteps = 0;
                    float tmpX = bal.getX();
                    while (tmpX < 480) {
                        tmpX += ballSpeedX;
                        numSteps++;
                    }
                    // predict X in infinite plane
                    predictY = (numSteps * ballSpeedY); // distance ball travels
                    // relative to original
                    // position
                    if (predictY < 0) {
                        predictY = -predictY;
                        predictY = (predictY % 960);
                        if (predictY > bal.getY()) {
                            predictY = predictY - bal.getY();
                            if (predictY > 480) {
                                predictY = 480 - (predictY - 480);
                            }
                        } else {
                            predictY = bal.getY() - predictY;
                        }
                    } else { // dist travelled > 0
                        predictY = predictY % 960;
                        if (predictY > (480 - bal.getY())) {
                            predictY = (predictY - (480 - bal.getY()));
                            if (predictY > 480) {
                                predictY = predictY - 480;
                            } else {
                                predictY = 480 - predictY;
                            }
                        } else {
                            predictY = 480 - predictY;
                        }
                    }
                    // comp_paddle.setSpeed(((480-(int)(predictX))/numSteps));
                    float finalPos = 480 - (int) (predictY);
                    if (right_paddle.getPos() > finalPos) {
                        right_paddle.setPos(right_paddle.getPos() - 1);
                    } else {
                        right_paddle.setPos(right_paddle.getPos() + 1);
                    }

                }

                // if (ball[0].getY() != border_bottom - comp_paddle.getPos())
                // right_paddle
                // .setPos((int) (border_bottom - ball[0].getY() - (ball[0]
                // .getY() - border_right - right_paddle.getPos() >= 0 ? 10
                // : -10)));
            } else if (started && allJoined) {









                right_paddle.setPos(PlayGame.getPos(4));
            }

            if(broadcastBall) {
                try {
                    int bx = 0,by=0;
                    switch(playerNum)
                    {
                        case 2:
                            bx = (int) (border_right-ball[0].getY());
                            by = (int) (ball[0].getX());
                            break;
                        case 3:
                            bx = (int) (border_right-ball[0].getX());
                            by = (int) (border_bottom-ball[0].getY());
                            break;
                        case 4:
                            bx = (int) (ball[0].getY());
                            by = (int) (border_bottom-ball[0].getX());
                            break;
                        case 1:
                            bx = (int) ball[0].getX();
                            by = (int) ball[0].getY();
                    }
                    PlayGame.sendBallPos(bx, by);
                    if(!player2)
                        PlayGame.sendPos(left_paddle.getPos(),2);
                    if(!player3)
                        PlayGame.sendPos(comp_paddle.getPos(),3);
                    if(!player4)
                        PlayGame.sendPos(right_paddle.getPos(),4);
                    if(!(paddle_life==paddle_life_old && comp_life == comp_life_old && left_life == left_life_old && right_life == right_life_old))
                        PlayGame.sendLife(paddle_life,left_life,comp_life,right_life);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }

            // >>>>>>> 78941520b3ea2854daae094c14a4649325f3cf34
            t2 = System.currentTimeMillis();
            t3 += t2 - t1;
            if (t3 <= anim_time) {
                flag = true;
                t4 = t3;
                t3 = 0;
                try {
                    Thread.sleep(17);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Missed Frame");
                flag = false;
                t3 -= anim_time;
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent arg0) {
        // TODO Auto-generated method stub
        if (to_start && !level_select && !started && arg0.getX() > 80
                && arg0.getX() < 340 && arg0.getY() > 100 && arg0.getY() < 130) {
            clicked_ip_box = true;
            ip_text = "";
        } else if (to_start && create_not_join && !level_select && !started
                && arg0.getX() > 80 && arg0.getX() < 400 && arg0.getY() > 140
                && arg0.getY() < 170) {
            player2 = !player2;
        } else if (to_start && create_not_join && !level_select && !started
                && arg0.getX() > 80 && arg0.getX() < 400 && arg0.getY() > 180
                && arg0.getY() < 210) {
            player3 = !player3;
        } else if (to_start && create_not_join && !level_select && !started
                && arg0.getX() > 80 && arg0.getX() < 400 && arg0.getY() > 220
                && arg0.getY() < 250) {
            player4 = !player4;
        } else if (to_start
                && create_not_join
                && level_select
                && !started
                && (arg0.getX() > 100 && arg0.getX() < 340 && arg0.getY() > 60 && arg0
                .getY() < 90)) {
            level = 0;
            System.out.println("FWEEFWEFWWEWEFWFWEFWE  " + level);
            started = true;
            clicked_ip_box = false;
            System.out.println("Starting game3");
            if (create_not_join) {
                StartGame sg = new StartGame(UserType.START, "ServerPlayer",
                        "", 0, this);
            } else {
                StartGame sg = new StartGame(UserType.JOIN, "JoiningPlayer",
                        ip_text, 1, this);
            }
            allJoined = true;
            PlayGame.startGettingData(this);

        } else if (to_start
                && create_not_join
                && level_select
                && !started
                && (arg0.getX() > 100 && arg0.getX() < 340 && arg0.getY() > 100 && arg0
                .getY() < 130)) {
            System.out.println("FWEEFWEFWWEWEFWFWEFWE  " + level);
            level = 1;
            started = true;
            clicked_ip_box = false;
            System.out.println("Starting game2");
            if (create_not_join) {
                StartGame sg = new StartGame(UserType.START, "ServerPlayer",
                        "", 0, this);
            } else {
                StartGame sg = new StartGame(UserType.JOIN, "JoiningPlayer",
                        ip_text, 1, this);
            }
            allJoined = true;
            PlayGame.startGettingData(this);
        } else if (to_start
                && create_not_join
                && level_select
                && !started
                && (arg0.getX() > 100 && arg0.getX() < 340 && arg0.getY() > 140 && arg0
                .getY() < 170)) {
            System.out.println("FWEEFWEFWWEWEFWFWEFWE  " + level);
            level = 2;
            started = true;
            clicked_ip_box = false;
            System.out.println("Starting game1");
            if (create_not_join) {
                StartGame sg = new StartGame(UserType.START, "ServerPlayer",
                        "", 0, this);
            } else {
                StartGame sg = new StartGame(UserType.JOIN, "JoiningPlayer",
                        ip_text, 1, this);
            }
            allJoined = true;
            PlayGame.startGettingData(this);
        } else if (to_start
                && create_not_join
                && !level_select
                && !started
                && !(arg0.getX() > 80 && arg0.getX() < 400 && arg0.getY() > 80 && arg0
                .getY() < 280)) {
            level_select = true;
            clicked_ip_box = false;
        } else if (to_start
                && !create_not_join
                && !started
                && !(arg0.getX() > 80 && arg0.getX() < 400 && arg0.getY() > 80 && arg0
                .getY() < 280)) {
            started = true;
            clicked_ip_box = false;
            System.out.println("Joingin game");
            if (create_not_join) {
                StartGame sg = new StartGame(UserType.START, "ServerPlayer",
                        "", 0, this);
            } else {
                StartGame sg = new StartGame(UserType.JOIN, "JoiningPlayer",
                        ip_text, 1, this);
            }
            allJoined = true;
            PlayGame.startGettingData(this);
        } else if (!to_start
                && (arg0.getX() > 100 && arg0.getX() < 340 && arg0.getY() > 100 && arg0
                .getY() < 130)) {
            to_start = true;
            create_not_join = true;
        } else if (!to_start
                && (arg0.getX() > 100 && arg0.getX() < 340 && arg0.getY() > 140 && arg0
                .getY() < 170)) {
            to_start = true;
            create_not_join = false;
        }
    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mousePressed(MouseEvent arg0) {
        System.out.println("f the world");
        if (started && ball[0].isOnPaddle() && playerNum == 1) {
            System.out.println("f the world again");
            t_old = System.currentTimeMillis();
            System.out.println(System.currentTimeMillis());
            try {
                PlayGame.triggerStart();
            } catch (Exception e) {
                e.printStackTrace();
            }
            for (int b = 0; b < n_balls; b++) {
                if (ball[b].isOnPaddle()) {
                    ball[b].setSpeedX(2.5f * ((ball[b].getX() - paddle
                            .getPos()) / ((float) paddle.getSize()) * 2));
                    ball[b].setSpeedY(-(float) Math.sqrt(6.25
                            - ball[b].getSpeedX() * ball[b].getSpeedX()));
                    ball[b].setOnPaddle(false);
                }
            }
        }
    }

    public void hostMousePressed() {
        if (started && ball[0].isOnPaddle()) {
            t_old = System.currentTimeMillis();
            // System.out.println(System.currentTimeMillis());
            for (int b = 0; b < n_balls; b++) {
                if (ball[b].isOnPaddle()) {
                    ball[b].setOnPaddle(false);
                    switch (playerNum) {
                        case 1:
                            ball[b].setSpeedX(2.5f * ((ball[b].getX() - paddle
                                    .getPos()) / ((float) paddle.getSize()) * 2));
                            ball[b].setSpeedY(-(float) Math.sqrt(6.25
                                    - ball[b].getSpeedX() * ball[b].getSpeedX()));
                            break;

                        case 2:
                            ball[b].setSpeedY(((ball[b].getY() - (border_bottom - right_paddle
                                    .getPos())) / ((float) right_paddle.getSize()) * 2));
                            ball[b].setSpeedX(-(float) Math.sqrt(6.25
                                    - ball[b].getSpeedY() * ball[b].getSpeedY()));

                            break;

                        case 3:
                            ball[b].setSpeedX(2.5f * ((ball[b].getX() - (border_right - comp_paddle
                                    .getPos())) / ((float) comp_paddle.getSize()) * 2));
                            ball[b].setSpeedY((float) Math.sqrt(6.25
                                    - ball[b].getSpeedX() * ball[b].getSpeedX()));

                            break;

                        case 4:
                            ball[b].setSpeedY(2.5f * ((ball[b].getY() - left_paddle
                                    .getPos()) / ((float) left_paddle.getSize()) * 2));
                            ball[b].setSpeedX((float) Math.sqrt(6.25
                                    - ball[b].getSpeedY() * ball[b].getSpeedY()));

                            break;
                    }
                }
            }

        }
    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseDragged(MouseEvent arg0) {
        // paddle.setPos(arg0.getX());
        // for (int b = 0; b < n_balls; b++) {
        // if (ball[b].isOnPaddle())
        // ball[b].setX(paddle.getPos()
        // + (2 * paddle.getSize() * (2 * b - n_balls))
        // / (5 * n_balls));
        // }
    }

    @Override
    public void mouseMoved(MouseEvent arg0) {
        paddle.setPos(arg0.getX());

        if (allJoined) {
            try {
                PlayGame.sendPos(arg0.getX(), playerNum);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent arg0) {
        if (clicked_ip_box) {
            switch (arg0.getKeyCode()) {
                case KeyEvent.VK_ENTER:
                    started = true;
                    break;
                case KeyEvent.VK_BACK_SPACE:
                    ip_text = (ip_text.length() == 0) ? "" : ip_text.substring(0,
                            ip_text.length() - 1);
                    break;
                default:
                    ip_text = ip_text.length() == 15 ? ip_text : ip_text
                            + arg0.getKeyChar();
            }
        } else if (arg0.getKeyCode() == KeyEvent.VK_LEFT) {
            paddle.setPos(paddle.getPos() - 10);
        } else if (arg0.getKeyCode() == KeyEvent.VK_RIGHT) {
            paddle.setPos(paddle.getPos() + 10);
        }
    }

    @Override
    public void keyReleased(KeyEvent arg0) {
    }

    @Override
    public void keyTyped(KeyEvent arg0) {
    }

    public static void main(String args[])
    {
        StartingClass sc = new StartingClass();
        sc.start();
        JFrame jf = new JFrame();
        Container c =jf.getContentPane();
        c.add(sc);
        jf.setBounds(0,0,700,520);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
