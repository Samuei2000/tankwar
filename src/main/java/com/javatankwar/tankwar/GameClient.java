package com.javatankwar.tankwar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameClient extends JComponent {
    private GameClient(){
        this.playerTank=new Tank(400,100,Direction.DOWN);
        this.setPreferredSize(new Dimension(800,600));
    }
    private Tank playerTank;


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.playerTank.getImage(),this.playerTank.getX(),this.playerTank.getY(),null);
    }

    public static void main(String[] args) {
        JFrame frame=new JFrame();
        frame.setTitle("史上最无聊的坦克大战");
        frame.setIconImage(new ImageIcon("assets/images/icon.png").getImage());
        GameClient client = new GameClient();
        client.repaint();
        frame.add(client);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()){
                    case KeyEvent.VK_UP :
                        client.playerTank.setY(client.playerTank.getY()-5);
                        break;
                    case KeyEvent.VK_DOWN:
                        client.playerTank.setY(client.playerTank.getY()+5);
                        break;
                    case KeyEvent.VK_LEFT:
                        client.playerTank.setX(client.playerTank.getX()-5);
                        break;
                    case KeyEvent.VK_RIGHT:
                        client.playerTank.setX(client.playerTank.getX()+5);
                        break;
                }
                client.repaint();//按下按键后需重新绘制坦克
            }

            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
            }
        });
        frame.setLocationRelativeTo(null);//初始位置为屏幕中央
        frame.setVisible(true);//可视
    }
}
