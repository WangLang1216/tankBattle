package com.wanglang.tankgame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.Vector;

// 坦克的绘图区域
// 监听，键盘事件
// 为了让Panel不停的重绘子弹，需要将MyPanel实现Runable，当作一个线程使用
public class MyPanel extends JPanel implements KeyListener, Runnable {
    // 定义我的坦克
    Hero hero = null;

    // 定义敌人的坦克，放入在Vector中，考虑线程问题
    Vector<EnemyTank> enemyTanks = new Vector<>();

    // 定义一个存放Node对象的Vector用于恢复敌人坦克的坐标和方向
    Vector<Node> nodes = new Vector<>();

    int enemyTankSize = 8;

    // 定义一个Vector，用于存放炸弹，当子弹击中坦克时就加入一个Bomb对象，加入到bombs集合中
    Vector<Bomb> bombs = new Vector<>();

    // 定义2张图片，爆炸效果
    Image bombImg = null;

    public MyPanel(String key) {
        // 先判断记录文件是否存在
        File file = new File(Recorder.getRecordFile());
        if (file.exists()) nodes = Recorder.getNodesAndEnemyTankRec();  // 恢复上局游戏
        else {
            System.out.println("文件不存在，只能开启新游戏！");
            key = "1";
        }
        // 将MyPanel对象的enemyTanks设置给Recorder的enemyTanks
        Recorder.setEnemyTanks(enemyTanks);

        hero = new Hero(940, 650);
        hero.setSpeed(2);

        switch (key) {
            case "1":  // 开始新游戏
                // 初始化敌人坦克
                for (int i = 0; i < enemyTankSize; i++) {
                    // 创建一个敌人坦克
                    EnemyTank enemyTank = new EnemyTank(100 * (i + 1), 0);
                    // 将enemyTanks设置给enemyTank
                    enemyTank.setEnemyTanks(enemyTanks);
                    // 方向
                    enemyTank.setDirect(2);
                    // 启动敌人线程
                    new Thread(enemyTank).start();
                    // 给该enemyTank加入一颗子弹
                    Shot shot = new Shot(enemyTank.getX() + 20, enemyTank.getY() + 60, enemyTank.getDirect());
                    // 加入enemyTank的Vector成员
                    enemyTank.shots.add(shot);
                    // 启动shot对象
                    new Thread(shot).start();
                    // 添加
                    enemyTanks.add(enemyTank);
                }
                break;
            case "2":  // 继续上局游戏
                // 初始化敌人坦克
                for (int i = 0; i < nodes.size(); i++) {
                    Node node = nodes.get(i);
                    // 创建一个敌人坦克
                    EnemyTank enemyTank = new EnemyTank(node.getX(), node.getY());
                    // 将enemyTanks设置给enemyTank
                    enemyTank.setEnemyTanks(enemyTanks);
                    // 方向
                    enemyTank.setDirect(node.getDirect());
                    // 启动敌人线程
                    new Thread(enemyTank).start();
                    // 给该enemyTank加入一颗子弹
                    Shot shot = new Shot(enemyTank.getX() + 20, enemyTank.getY() + 60, enemyTank.getDirect());
                    // 加入enemyTank的Vector成员
                    enemyTank.shots.add(shot);
                    // 启动shot对象
                    new Thread(shot).start();
                    // 添加
                    enemyTanks.add(enemyTank);
                }
                break;
            default:
                System.out.println("你的输入有误！");
        }
        // 初始化图片对象
        bombImg = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb1.gif"));
    }

    // 显示我方击毁敌方坦克信息
    public void showInfo(Graphics g) {
        // 画出玩家总成绩
        g.setColor(Color.BLACK);
        Font font = new Font("宋体", Font.BOLD, 25);
        g.setFont(font);
        g.drawString("累计击毁敌方坦克", 1020, 30);
        // 画出一个敌方坦克
        drawTank(1020, 60, g, 0, 0);
        g.setColor(Color.BLACK);
        g.drawString(Recorder.getAllEnemyTankNum() + "", 1080, 100);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0, 0, 1000, 750);
        showInfo(g);
        // 画我的坦克-封装方法
        if (hero != null && hero.isLive()) drawTank(hero.getX(), hero.getY(), g, hero.getDirect(), 0);

        // 画出敌人坦克和坦克
        for (int i = 0; i < enemyTanks.size(); i++) {
            EnemyTank enemyTank = enemyTanks.get(i);
            // 判断当前坦克是否存活
            if (enemyTank.isLive()) {
                drawTank(enemyTank.getX(), enemyTank.getY(), g, enemyTank.getDirect(), 1);
                // 画出enemyTank所有子弹
                for (int j = 0; j < enemyTank.shots.size(); j++) {
                    // 取出子弹
                    Shot shot = enemyTank.shots.get(j);
                    // 绘制
                    if (shot.isLive()) {
                        g.fillOval(shot.getX() - 2, shot.getY() - 2, 4, 4);
                    } else {
                        // 从Vector移除
                        enemyTank.shots.remove(shot);
                    }
                }
            }
        }

        // 画出hero射击的子弹，单颗子弹
        if (hero.shot != null && hero.shot.isLive()) g.fillOval(hero.shot.getX() - 2, hero.shot.getY() - 2, 4, 4);
        // 画出hero射击的子弹，遍历取出绘制多颗子弹
//        for (int i = 0; i < hero.shots.size(); i++) {
//            Shot shot = hero.shots.get(i);
//            if (shot != null && shot.isLive()) g.fillOval(shot.getX() - 2, shot.getY() - 2, 4, 4);
//            else hero.shots.remove(shot); // 子弹消亡，删除
//        }

        // 如果bombs集合中有对象就画出
        for (int i = 0; i < bombs.size(); i++) {
            // 取出炸弹
            Bomb bomb = bombs.get(i);
            // 根据当前这个bomb对象的life值去画出对应的图片
            if (bomb.getLife() > 0) g.drawImage(bombImg, bomb.getX() - 25, bomb.getY() - 25, 100, 100, this);
            // 让这个炸弹的生命值减少
            bomb.lifeDown();
            // 如果bomb的life值为0，就从bombs的集合中删除
            if (bomb.getLife() == 0) bombs.remove(bomb);
        }

    }

    // 画坦克方法
    public void drawTank(int x, int y, Graphics g, int direct, int type) {
        switch (type) {
            case 0: // 我们的坦克
                g.setColor(Color.cyan);
                break;
            case 1: // 敌人的坦克
                g.setColor(Color.yellow);
                break;
        }
        // 根据方向，绘制坦克，0上 1右 2下 3左
        switch (direct) {
            case 0: // 向上
                g.fill3DRect(x, y, 10, 60, false); // 左边轮子
                g.fill3DRect(x + 30, y, 10, 60, false); // 右边轮子
                g.fill3DRect(x + 10, y + 10, 20, 40, false); //中间
                g.fillOval(x + 10, y + 20, 20, 20); // 中间圆盖
                g.drawLine(x + 20, y + 30, x + 20, y - 5); // 炮管
                break;
            case 1: // 向右
                g.fill3DRect(x, y, 60, 10, false); // 左边轮子
                g.fill3DRect(x, y + 30, 60, 10, false); // 右边轮子
                g.fill3DRect(x + 10, y + 10, 40, 20, false); //中间
                g.fillOval(x + 20, y + 10, 20, 20); // 中间圆盖
                g.drawLine(x + 30, y + 20, x + 65, y + 20); // 炮管
                break;
            case 2: // 向下
                g.fill3DRect(x, y, 10, 60, false); // 左边轮子
                g.fill3DRect(x + 30, y, 10, 60, false); // 右边轮子
                g.fill3DRect(x + 10, y + 10, 20, 40, false); //中间
                g.fillOval(x + 10, y + 20, 20, 20); // 中间圆盖
                g.drawLine(x + 20, y + 30, x + 20, y + 65); // 炮管
                break;
            case 3: // 向左
                g.fill3DRect(x, y, 60, 10, false); // 左边轮子
                g.fill3DRect(x, y + 30, 60, 10, false); // 右边轮子
                g.fill3DRect(x + 10, y + 10, 40, 20, false); //中间
                g.fillOval(x + 20, y + 10, 20, 20); // 中间圆盖
                g.drawLine(x + 30, y + 20, x - 5, y + 20); // 炮管
                break;
        }
    }

    // 编写方法，判断敌人坦克是否击中我的坦克
    public void hitHero() {
        // 遍历所有敌人坦克
        for (int i = 0; i < enemyTanks.size(); i++) {
            // 取出敌人坦克
            EnemyTank enemyTank = enemyTanks.get(i);
            // 遍历enemyTank对象的所有子弹
            for (int j = 0; j < enemyTank.shots.size(); j++) {
                // 取出子弹
                Shot shot = enemyTank.shots.get(j);
                // 判断shot是否击中我的坦克
                if (hero.isLive() && shot.isLive()) {
                    hitTank(shot, hero);
                }
            }
        }
    }

    // 判断击中敌人坦克
    public void hitEnemTank() {
        // 单颗子弹
        if (hero.shot != null && hero.shot.isLive()) { // 当我方坦克还活着的时候
            // 遍历敌人所有塔克
            for (int i = 0; i < enemyTanks.size(); i++) {
                EnemyTank enemyTank = enemyTanks.get(i);
                hitTank(hero.shot, enemyTank);
            }
        }
        // 多颗子弹
        // 我们坦克可以发射多颗子弹，需要将所有子弹取出和敌人的所有坦克判断
        // 遍历我方子弹
//        for (int j = 0; j < hero.shots.size(); j++) {
//            Shot shot = hero.shots.get(j);
//            // 判断击中敌人坦克
//            if (shot != null && shot.isLive()) { // 当我方坦克还活着的时候
//                // 遍历敌人所有塔克
//                for (int i = 0; i < enemyTanks.size(); i++) {
//                    EnemyTank enemyTank = enemyTanks.get(i);
//                    hitTank(shot, enemyTank);
//                }
//            }
//        }
    }

    // 编写方法，判断子弹是否击中坦克，在run方法中判断哪个坦克被击中了
    public void hitTank(Shot shot, Tank tank) {
        // 判断shot击中坦克
        switch (tank.getDirect()) {
            // 上下相同
            case 0:
            case 2:
                if (shot.getX() > tank.getX() && shot.getX() < tank.getX() + 40
                        && shot.getY() > tank.getY() && shot.getY() < tank.getY() + 60) {
                    shot.setLive(false);
                    tank.setLive(false);
                    // 创建Bomb对象，加入到bombs集合中
                    Bomb bomb = new Bomb(tank.getX(), tank.getY());
                    bombs.add(bomb);
                    // 当我方击毁一个敌人坦克时，就对数据allEnemyTankNum++
                    // 判断是否为敌方坦克
                    if (tank instanceof EnemyTank) Recorder.addAllEnemyTankNum();
                    // 当坦克击中坦克就删除坦克
                    enemyTanks.remove(tank);
                }
                break;
            // 左右相同
            case 1:
            case 3:
                if (shot.getX() > tank.getX() && shot.getX() < tank.getX() + 60
                        && shot.getY() > tank.getY() && shot.getY() < tank.getY() + 40) {
                    shot.setLive(false);
                    tank.setLive(false);
                    // 创建Bomb对象，加入到bombs集合中
                    Bomb bomb = new Bomb(tank.getX(), tank.getY());
                    bombs.add(bomb);
                    // 当我方击毁一个敌人坦克时，就对数据allEnemyTankNum++
                    // 判断是否为敌方坦克
                    if (tank instanceof EnemyTank) Recorder.addAllEnemyTankNum();
                    // 当坦克击中坦克就删除坦克
                    enemyTanks.remove(tank);
                }
                break;
        }
    }

    // 处理“WASD”按键
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {
            // W -> 上
            hero.setDirect(0);
            // 修改坦克坐标
            // 1、可以使用此方法，
//            hero.setX(hero.getY() - 1);
            // 2、可以在Tank类写入方法调用，同时不能超过边界
            if (hero.getY() > 0) hero.moveUp();
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            // D -> 右
            hero.setDirect(1);
            // 修改坦克坐标
            if (hero.getX() + 60 < 1000) hero.moveRight();
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            // S -> 下
            hero.setDirect(2);
            // 修改坦克坐标
            if (hero.getY() + 60 < 750) hero.moveDown();
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            // A -> 左
            hero.setDirect(3);
            // 修改坦克坐标
            if (hero.getX() > 0) hero.moveLeft();
        }


        // 如果用户按下的是空格，就发射子弹
        if(e.getKeyCode() == KeyEvent.VK_SPACE) {
            // 判断hero子弹是否消亡，发射一颗子弹
            if (hero.isLive() && hero.shot == null || !hero.shot.isLive()) {
                hero.shotEnemyTank();
            }
            // 判断hero子弹是否消亡，发射多颗子弹
//            hero.shotEnemyTank();
        }

        // 面板重绘
        this.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void run() {
        while (true) {
            // 每隔50毫秒，重绘区域，刷新
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 判断击中敌人坦克
            hitEnemTank();

            // 判断敌人坦克是否击中我们
            hitHero();

            this.repaint();
        }
    }
}
