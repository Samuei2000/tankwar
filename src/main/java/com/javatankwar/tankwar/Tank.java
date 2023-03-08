package com.javatankwar.tankwar;


import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Random;

public class Tank {
    private static final int MOVESPEED = 5;
    private int x;
    private int y;
    private Direction direction;
    private boolean stopped;
    private boolean enemy;

    private void move(){
        if(stopped) return;//若没有输入，则不动
        x+= direction.xFactor*MOVESPEED;
        y+= direction.yFactor*MOVESPEED;
    }
    Tank(int x, int y, Direction direction) {
        this(x,y,false,direction);
    }

     Tank(int x, int y,boolean enemy,Direction direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.enemy = enemy;
    }

    void draw(Graphics g){
        int oldX=x,oldY=y;
        this.determineDirection();
        this.move();

        //限制在画面内
        if(x<0) x=0;
        else if(x>800- getImage().getWidth(null)) x=800- getImage().getWidth(null);
        if(y<0) y=0;
        else if(y>600- getImage().getHeight(null)) y=600- getImage().getHeight(null);

        Rectangle rec=this.getRectangle();
        for (Wall wall:GameClient.getInstance().getWalls()){//坦克与墙的碰撞检测,使用GameClient的单例
            if(rec.intersects(wall.getRectangle())) {//若发生碰撞，x和y不变
                x = oldX;
                y = oldY;
                break;
            }
        }
        for(Tank tank:GameClient.getInstance().getEnemyTanks()){
            if(rec.intersects(tank.getRectangle())){
                x = oldX;
                y = oldY;
                break;
            }
        }

        g.drawImage(this.getImage(),x,y,null);
    }
    private Rectangle getRectangle(){
        return new Rectangle(x,y,getImage().getWidth(null),getImage().getHeight(null));
    }
    Image getImage(){
        String prefix=enemy ? "e" : "";
        return direction.getImage(prefix+"tank");
    }
    private boolean up=false,down=false,left=false,right=false;
    private void determineDirection(){
        if (!up&&!left&&!down&&!right) this.stopped=true;
        else {
            if (up && left && !down && !right) this.direction = Direction.LEFT_UP;
            else if (up && !left && !down && right) this.direction = Direction.RIGHT_UP;
            else if (up && !left && !down && !right) this.direction = Direction.UP;
            else if (!up && !left && down && !right) this.direction = Direction.DOWN;
            else if (!up && left && down && !right) this.direction = Direction.LEFT_DOWN;
            else if (!up && !left && down && right) this.direction = Direction.RIGHT_DOWN;
            else if (!up && left && !down && !right) this.direction = Direction.LEFT;
            else if (!up && !left && !down && right) this.direction = Direction.RIGHT;
            this.stopped=false;
        }
    }
    void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_UP :up=true;break;
            case KeyEvent.VK_DOWN :down=true;break;
            case KeyEvent.VK_LEFT: left=true;break;
            case KeyEvent.VK_RIGHT: right=true;break;
            case KeyEvent.VK_CONTROL:fire();break;//ctrl开火
            case KeyEvent.VK_A: superFire();break;//超级开火，复用fire()

        }
        this.determineDirection();
    }

    private void fire() {
        Missile missile=new Missile(x+ getImage().getWidth(null)/2-6,y+ getImage().getHeight(null)/2-6,
                enemy,direction);
        GameClient.getInstance().getMissiles().add(missile);
        playAudio("shoot.wav");
    }
    private void superFire() {
        for(Direction direction:Direction.values()){
            Missile missile=new Missile(x+ getImage().getWidth(null)/2-6,y+ getImage().getHeight(null)/2-6,
                    enemy,direction);
            GameClient.getInstance().getMissiles().add(missile);
        }
        String audioFile=new Random().nextBoolean()?"supershoot.aiff":"supershoot.wav";
        playAudio(audioFile);
    }

    private static void playAudio(String fileName) {
        Media sound=new Media(new File("assets/audios/"+ fileName).toURI().toString());
        MediaPlayer mediaPlayer=new MediaPlayer(sound);
        mediaPlayer.play();
    }

    void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_UP :up=false;break;
            case KeyEvent.VK_DOWN :down=false;break;
            case KeyEvent.VK_LEFT: left=false;break;
            case KeyEvent.VK_RIGHT: right=false;break;
        }
    }
}
