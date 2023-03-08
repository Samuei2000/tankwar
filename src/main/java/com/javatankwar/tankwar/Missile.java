package com.javatankwar.tankwar;

import java.awt.*;

class Missile {
    private int x,y;
    private final Direction direction;
    private final boolean enemy;//敌方还是我方子弹
    private static final int SPEED=12;

    public Missile(int x, int y, boolean enemy,Direction direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.enemy = enemy;
    }
    Image getImage(){
        return direction.getImage("missile");
    }
    private void move(){
        switch (direction){
            case UP :y-= SPEED;break;
            case DOWN:y+=SPEED;break;
            case LEFT:x-=SPEED;break;
            case RIGHT:x+=SPEED;break;
            case LEFT_UP:y-=SPEED;x-=SPEED;break;
            case RIGHT_UP:y-=SPEED;x+=SPEED;break;
            case RIGHT_DOWN:y+=SPEED;x+=SPEED;break;
            case LEFT_DOWN:y+=SPEED;x-=SPEED;break;
        }
    }
    void draw(Graphics g){
        move();
        if(x<0||x>800||y<0||y>600)
            return;
        g.drawImage(getImage(),x,y,null);
    }
}
