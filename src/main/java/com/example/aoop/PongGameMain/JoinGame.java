package com.example.aoop.PongGameMain;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

public class JoinGame {

    //	private URL base;
    Image ball,b1;
    String ip_dat;
    boolean player2,player3,player4;
    JoinGame(Image i1,String x)
    {
        ip_dat=x;
//		try {
//			this.base = base;
//		} catch (Exception e) {
//		}
//		Toolkit t=Toolkit.getDefaultToolkit();
//        ball=t.getImage("/Data/Ball.png");
//		ball = getImage(base, "Data/Ball.png");
        b1=i1;
    }

    public void paint(Graphics g) {
        g.drawImage(b1, 100, 100, 240, 30, null);
        g.drawImage(b1, 180, 300, 100, 30, null);
        g.setColor(Color.WHITE);
        g.setFont(new Font("TimesRoman", Font.BOLD, 20));
        g.drawString(ip_dat,110,120);
        g.drawString("START",192,322);
    }
}
