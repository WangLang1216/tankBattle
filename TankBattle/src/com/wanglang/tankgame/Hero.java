package com.wanglang.tankgame;

import java.util.Vector;

// 自己的坦克
public class Hero extends Tank {
    // 定义一个Shot对象，表示一个射击（线程）
    Shot shot = null;

    // 可以发射多颗子弹
//    Vector<Shot> shots = new Vector<>();
    public Hero(int x, int y) {
        super(x, y);
    }

    // 射击
    public void shotEnemyTank() {
        // 最多只能有3颗子弹
//        if (shots.size() == 3) return;
        // 创建Shot对象，要根据当前Hero对象的位置和方向来创建Shot
        switch (getDirect()) { // Hero对象方向
            case 0: // 上
                shot = new Shot(getX() + 20, getY() - 5, 0);
                break;
            case 1: // 右
                shot = new Shot(getX() + 65, getY() + 20, 1);
                break;
            case 2: // 下
                shot = new Shot(getX() + 20, getY() + 65, 2);
                break;
            case 3: // 左
                shot = new Shot(getX() - 5, getY() + 20, 3);
                break;
        }
        // 把新创建的shot放入待shots中
//        shots.add(shot);
        // 启动我们的Shot线程
        new Thread(shot).start();
    }
}
