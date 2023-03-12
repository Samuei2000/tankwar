package com.javatankwar.tankwar;

import java.awt.*;

class Explosion {
    private int x,y;
    private int step=0;

    public Explosion(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    public boolean isLive() {
        return live;
    }

    boolean live=true;
    void draw(Graphics g){
        if(step>10){
            this.setLive(false);
            return;
        }
        g.drawImage(Tools.getImage(step++ + ".gif"),x,y,null);
    }
}
