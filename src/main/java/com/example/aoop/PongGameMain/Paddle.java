package com.example.aoop.PongGameMain;

public class Paddle {

    private int size, pos;
    final int maxSize = 50;
    final int minSize = 20;
    final int maxX, height, minX;
    int speed=0;
    int max_speed;

    Paddle(int s, int p, int x, int min_x, int h, int max_speed) {
        size = s;
        pos = p;
        maxX = x;
        height = h;
        this.max_speed = max_speed;
        minX = min_x;
    }

    public int getSpeed(){
        return speed;
    }

    public void setSpeed(int s){
        speed=s;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        if (size >= minSize && size <= maxSize)
            this.size = size;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        if (pos <= minX + size / 2)
            this.pos = minX + size / 2;
        else if (pos >= maxX - size / 2)
            this.pos = maxX - size / 2;
        else
            this.pos = pos;
    }

}
