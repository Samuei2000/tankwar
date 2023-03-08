package com.javatankwar.tankwar;


import java.awt.*;

 class Wall {
    private int x,y;//墙的起始点
    private boolean horizontal;//墙的延申方向
    private int bricks;//墙的砖块数
    private final Image brickImage;//把brickImage提取出来

    Wall(int x, int y, boolean horizontal, int bricks) {
        this.x = x;
        this.y = y;
        this.horizontal = horizontal;
        this.bricks = bricks;
        this.brickImage=Tools.getImage("brick.png");
    }
    Rectangle getRectangle(){
        return horizontal?new Rectangle(x,y,bricks*brickImage.getWidth(null),brickImage.getHeight(null)):
                new Rectangle(x,y,brickImage.getWidth(null),bricks*brickImage.getHeight(null));
    }

    void draw(Graphics g){
        if(horizontal){
            for (int i = 0; i < bricks; i++) {
                g.drawImage(brickImage,x+i*brickImage.getWidth(null),y,null);
            }
        }else{
            for (int i = 0; i < bricks; i++) {
                g.drawImage(brickImage,x,y+i*brickImage.getHeight(null),null);
            }
        }
    }
}
