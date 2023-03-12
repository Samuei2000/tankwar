package com.javatankwar.tankwar;

import javafx.scene.control.Tab;

import java.awt.*;

class Missile {
    private int x,y;
    private final Direction direction;
    private final boolean enemy;//敌方还是我方子弹
    private static final int SPEED=12;
    private boolean live=true;

    boolean isLive() {
        return live;
    }
    void setLive(boolean live) {
        this.live = live;
    }
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
        x+= direction.xFactor*SPEED;
        y+= direction.yFactor*SPEED;
    }
    void draw(Graphics g){
        move();
        if(x<0||x>800||y<0||y>600){
            this.setLive(false);
            return;
        }
        Rectangle rectangle=this.getRectangle();
        for(Wall wall:GameClient.getInstance().getWalls()){
            if(rectangle.intersects(wall.getRectangle())){
                this.setLive(false);
                return;
            }
        }
        if(enemy){//若这颗是敌方子弹
            Tank playerTank=GameClient.getInstance().getPlayerTank();
            if(rectangle.intersects(playerTank.getRectangle())){//missle和tank发生碰撞的代码
                addExplosion();
                playerTank.setHp(playerTank.getHp()-20);
                if(playerTank.getHp()<=0)
                    playerTank.setLive(false);
                this.setLive(false);
            }
        }
        else {//若这颗是我方子弹，需同时把该子弹和该子弹击中的敌人设为死亡
            for(Tank tank:GameClient.INSTANCE.getEnemyTanks()){
                if(rectangle.intersects(tank.getRectangle())){
                    addExplosion();
                    tank.setLive(false);
                    this.setLive(false);
                    break;
                }
            }
        }
        g.drawImage(getImage(),x,y,null);
    }
    private void addExplosion(){
        GameClient.getInstance().addExplosion(new Explosion(x,y));
        Tools.playAudio("explode.wav");
    }
    Rectangle getRectangle(){
        return new Rectangle(x,y,getImage().getWidth(null),getImage().getHeight(null));
    }
}
