package com.javatankwar.tankwar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//--module-path "C:\Program Files\Java\javafx-sdk-19.0.2.1\lib" --add-modules javafx.controls,javafx.fxml,javafx.graphics
public class GameClient extends JComponent {
    static final GameClient INSTANCE=new GameClient();//单例模式
    static GameClient getInstance(){
        return INSTANCE;
    }
    private GameClient(){

        this.playerTank=new Tank(400,100,Direction.DOWN);
        this.setPreferredSize(new Dimension(800,600));
        this.enemyTanks=new ArrayList<>(12);
        this.initEnemyTanks();
        this.walls= Arrays.asList(//初始化墙
                new Wall(200,140,true,15),
                new Wall(200,540,true,15),
                new Wall(100,80,false,15),
                new Wall(700,80,false,15)
        );
        this.missiles=new ArrayList<>();
        this.explosions=new ArrayList<>();
    }

    private void initEnemyTanks() {
        for(int i=0;i<3;i++){//初始化敌方坦克位置和初始方向
            for (int j = 0; j < 4; j++) {
                this.enemyTanks.add(new Tank(200+j*120,400+40*i,true,Direction.UP));
            }
        }
    }

    Tank getPlayerTank() {
        return playerTank;
    }

    private Tank playerTank;
    private List<Tank> enemyTanks;//敌方坦克的数据结构

    public List<Tank> getEnemyTanks() {
        return enemyTanks;
    }

    List<Wall> getWalls() {
        return walls;
    }

    List<Wall> walls;//围墙的数据结构

    private List<Missile> missiles;
    private List<Explosion> explosions;
    void addExplosion(Explosion explosion){
        explosions.add(explosion);
    }

    List<Missile> getMissiles() {
        return missiles;
    }
    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0,0,800,600);//把背景改为黑色
        playerTank.draw(g);
        if(enemyTanks.isEmpty())
            this.initEnemyTanks();//敌人坦克全部死亡后复活
        enemyTanks.removeIf(t->!t.isLive());
        for (Tank tank:enemyTanks) {
            tank.draw(g);
        }
        for(Wall wall:walls){
            wall.draw(g);
        }
        missiles.removeIf(m->!m.isLive());
        for(Missile missile:missiles){
            missile.draw(g);
        }
        explosions.removeIf(m->!m.isLive());
        for(Explosion explosion:explosions){
            explosion.draw(g);
        }
    }

    public static void main(String[] args) {
        com.sun.javafx.application.PlatformImpl.startup(()->{});
        JFrame frame=new JFrame();
        frame.setTitle("史上最无聊的坦克大战");
        frame.setIconImage(new ImageIcon("assets/images/icon.png").getImage());
        //GameClient client = new GameClient();确保只有一个单例，这个client与INSTANCE不同
        final GameClient client=GameClient.getInstance();
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
