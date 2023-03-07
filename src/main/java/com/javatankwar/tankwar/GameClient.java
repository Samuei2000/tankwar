package com.javatankwar.tankwar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class GameClient extends JComponent {
    private GameClient(){
        this.playerTank=new Tank(400,100,Direction.DOWN);
        this.setPreferredSize(new Dimension(800,600));
        this.enemyTanks=new ArrayList<>(12);
        for(int i=0;i<3;i++){//初始化敌方坦克位置和初始方向
            for (int j = 0; j < 4; j++) {
                this.enemyTanks.add(new Tank(200+j*60,400+40*i,true,Direction.UP));
            }

        }
    }
    private Tank playerTank;
    private List<Tank> enemyTanks;//敌方坦克的数据结构
    private List<Wall> walls;//围墙的数据结构

    @Override
    protected void paintComponent(Graphics g) {
        playerTank.draw(g);
        for (Tank tank:enemyTanks) {
            tank.draw(g);
        }
    }

    public static void main(String[] args) {
        JFrame frame=new JFrame();
        frame.setTitle("史上最无聊的坦克大战");
        frame.setIconImage(new ImageIcon("assets/images/icon.png").getImage());
        GameClient client = new GameClient();
        frame.add(client);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                client.playerTank.keyPressed(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                client.playerTank.keyReleased(e);
            }
        });
        frame.setLocationRelativeTo(null);//初始位置为屏幕中央
        frame.setVisible(true);//可视
        while (true){//游戏循环，否则只会画一次
            client.repaint();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
