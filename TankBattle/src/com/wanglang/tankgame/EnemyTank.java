package com.wanglang.tankgame;

import java.util.Vector;

// 敌人的坦克
public class EnemyTank extends Tank implements Runnable {
    // 在敌人坦克类，使用Vector保持多个Shot
    Vector<Shot> shots = new Vector<>();

    // 增加成员，EnemyTank可以得到敌人的Vector
    Vector<EnemyTank> enemyTanks = new Vector<>();
    // 提供一个方法，将MyPanel的成员Vector<EnemyTank> enemyTanks = new Vector<>();
    // 设置到EnemyTank的成员enemyTanks
    public void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        this.enemyTanks = enemyTanks;
    }
    // 编写方法，判断当前这个敌人坦克，是否和enemyTanks中的其他坦克发生重叠或碰撞
    public boolean isTouchEnemyTank() {
        // 判断当前敌人坦克（this）方向
        switch (this.getDirect()) {
            case 0: // 上
                // 让当前敌人坦克和其他所有坦克比较
                for (int i = 0; i < enemyTanks.size(); i++) {
                    // vector中取出一个敌人的坦克
                    EnemyTank enemyTank = enemyTanks.get(i);
                    // 不和自己比较
                    if (enemyTank != this) {
                        // 如果敌人坦克是上/下
                        // x的范围就是[enemyTank.getX(), enemyTank.getX() + 40]
                        // y的范围就是[enemyTank.getY(), enemyTank.getY() + 60]
                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2) {
                            // 当前坦克的左上角的坐标[this.getX(), this.getY()]
                            if (this.getX() >= enemyTank.getX() && this.getX() <= enemyTank.getX() + 40
                                    && this.getY() >= enemyTank.getY() && this.getY() <= enemyTank.getX() + 60) {
                                    return true;
                            }
                            // 当前坦克的右上角的坐标[this.getX() + 40, this.getY()]
                            if (this.getX() + 40 >= enemyTank.getX() && this.getX() + 40 <= enemyTank.getX() + 40
                                    && this.getY() >= enemyTank.getY() && this.getY() <= enemyTank.getX() + 60) {
                                return true;
                            }
                        } else {
                            // 敌人坦克是左/右
                            // x的范围就是[enemyTank.getX(), enemyTank.getX() + 60]
                            // y的范围就是[enemyTank.getY(), enemyTank.getY() + 40]
                            // 当前坦克的左上角的坐标[this.getX(), this.getY()]
                            if (this.getX() >= enemyTank.getX() && this.getX() <= enemyTank.getX() + 60
                                    && this.getY() >= enemyTank.getY() && this.getY() <= enemyTank.getX() + 40) {
                                return true;
                            }
                            // 当前坦克的右上角的坐标[this.getX() + 40, this.getY()]
                            if (this.getX() + 40 >= enemyTank.getX() && this.getX() + 40 <= enemyTank.getX() + 60
                                    && this.getY() >= enemyTank.getY() && this.getY() <= enemyTank.getX() + 40) {
                                return true;
                            }
                        }
                    }
                }
                break;
            case 1: // 右
                // 让当前敌人坦克和其他所有坦克比较
                for (int i = 0; i < enemyTanks.size(); i++) {
                    // vector中取出一个敌人的坦克
                    EnemyTank enemyTank = enemyTanks.get(i);
                    // 不和自己比较
                    if (enemyTank != this) {
                        // 如果敌人坦克是上/下
                        // x的范围就是[enemyTank.getX(), enemyTank.getX() + 60]
                        // y的范围就是[enemyTank.getY(), enemyTank.getY() + 40]
                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2) {
                            // 当前坦克的右上角的坐标[this.getX() + 60, this.getY()]
                            if (this.getX() + 60 >= enemyTank.getX() && this.getX() + 60 <= enemyTank.getX() + 40
                                    && this.getY() >= enemyTank.getY() && this.getY() <= enemyTank.getX() + 60) {
                                return true;
                            }
                            // 当前坦克的右下角的坐标[this.getX() + 60, this.getY() + 40]
                            if (this.getX() + 60 >= enemyTank.getX() && this.getX() + 60 <= enemyTank.getX() + 40
                                    && this.getY() + 40 >= enemyTank.getY() && this.getY() + 40 <= enemyTank.getX() + 60) {
                                return true;
                            }
                        } else {
                            // 敌人坦克是左/右
                            // x的范围就是[enemyTank.getX(), enemyTank.getX() + 60]
                            // y的范围就是[enemyTank.getY(), enemyTank.getY() + 40]
                            // 当前坦克的右上角的坐标[this.getX() + 60, this.getY()]
                            if (this.getX() + 60 >= enemyTank.getX() && this.getX() + 60 <= enemyTank.getX() + 60
                                    && this.getY() >= enemyTank.getY() && this.getY() <= enemyTank.getX() + 40) {
                                return true;
                            }
                            // 当前坦克的右下角的坐标[this.getX() + 60, this.getY() + 40]
                            if (this.getX() + 60 >= enemyTank.getX() && this.getX() + 60 <= enemyTank.getX() + 60
                                    && this.getY() + 40 >= enemyTank.getY() && this.getY() + 40 <= enemyTank.getX() + 40) {
                                return true;
                            }
                        }
                    }
                }
                break;
            case 2: // 下
                // 让当前敌人坦克和其他所有坦克比较
                for (int i = 0; i < enemyTanks.size(); i++) {
                    // vector中取出一个敌人的坦克
                    EnemyTank enemyTank = enemyTanks.get(i);
                    // 不和自己比较
                    if (enemyTank != this) {
                        // 如果敌人坦克是上/下
                        // x的范围就是[enemyTank.getX(), enemyTank.getX() + 40]
                        // y的范围就是[enemyTank.getY(), enemyTank.getY() + 60]
                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2) {
                            // 当前坦克的左下角的坐标[this.getX(), this.getY() + 60]
                            if (this.getX() >= enemyTank.getX() && this.getX() <= enemyTank.getX() + 40
                                    && this.getY() + 60 >= enemyTank.getY() && this.getY() + 60 <= enemyTank.getX() + 60) {
                                return true;
                            }
                            // 当前坦克的右下角的坐标[this.getX() + 40, this.getY() + 60]
                            if (this.getX() + 40 >= enemyTank.getX() && this.getX() + 40 <= enemyTank.getX() + 40
                                    && this.getY() + 60 >= enemyTank.getY() && this.getY() + 60 <= enemyTank.getX() + 60) {
                                return true;
                            }
                        } else {
                            // 敌人坦克是左/右
                            // x的范围就是[enemyTank.getX(), enemyTank.getX() + 40]
                            // y的范围就是[enemyTank.getY(), enemyTank.getY() + 60]
                            // 当前坦克的右上角的坐标[this.getX(), this.getY() + 60]
                            if (this.getX() >= enemyTank.getX() && this.getX() <= enemyTank.getX() + 60
                                    && this.getY() + 60 >= enemyTank.getY() && this.getY() + 60 <= enemyTank.getX() + 40) {
                                return true;
                            }
                            // 当前坦克的右下角的坐标[this.getX() + 40, this.getY() + 60]
                            if (this.getX() + 40 >= enemyTank.getX() && this.getX() + 40 <= enemyTank.getX() + 60
                                    && this.getY() + 60 >= enemyTank.getY() && this.getY() + 60 <= enemyTank.getX() + 40) {
                                return true;
                            }
                        }
                    }
                }
                break;
            case 3: // 左
                // 让当前敌人坦克和其他所有坦克比较
                for (int i = 0; i < enemyTanks.size(); i++) {
                    // vector中取出一个敌人的坦克
                    EnemyTank enemyTank = enemyTanks.get(i);
                    // 不和自己比较
                    if (enemyTank != this) {
                        // 如果敌人坦克是上/下
                        // x的范围就是[enemyTank.getX(), enemyTank.getX()]
                        // y的范围就是[enemyTank.getY(), enemyTank.getY() + 40]
                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2) {
                            // 当前坦克的左上角的坐标[this.getX(), this.getY()]
                            if (this.getX() >= enemyTank.getX() && this.getX() <= enemyTank.getX() + 40
                                    && this.getY() >= enemyTank.getY() && this.getY() <= enemyTank.getX() + 60) {
                                return true;
                            }
                            // 当前坦克的左下角的坐标[this.getX(), this.getY() + 40]
                            if (this.getX() >= enemyTank.getX() && this.getX() <= enemyTank.getX() + 40
                                    && this.getY() + 40 >= enemyTank.getY() && this.getY() + 40 <= enemyTank.getX() + 60) {
                                return true;
                            }
                        } else {
                            // 敌人坦克是左/右
                            // x的范围就是[enemyTank.getX(), enemyTank.getX() + 40]
                            // y的范围就是[enemyTank.getY(), enemyTank.getY() + 60]
                            // 当前坦克的左上角的坐标[this.getX(), this.getY()]
                            if (this.getX() >= enemyTank.getX() && this.getX() <= enemyTank.getX() + 60
                                    && this.getY() >= enemyTank.getY() && this.getY() <= enemyTank.getX() + 40) {
                                return true;
                            }
                            // 当前坦克的左下角的坐标[this.getX(), this.getY() + 40]
                            if (this.getX() >= enemyTank.getX() && this.getX() <= enemyTank.getX() + 60
                                    && this.getY() + 40 >= enemyTank.getY() && this.getY() + 40 <= enemyTank.getX() + 40) {
                                return true;
                            }
                        }
                    }
                }
                break;
        }
        return false;
    }


    private boolean isLive = true; // 生命状态
    public EnemyTank(int x, int y) {
        super(x, y);
    }

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
    }

    @Override
    public void run() {
        while (true) {
            // 判断活着同时是否还有子弹，没有则创建一颗子弹放入到集合，并启动
            if (isLive && shots.size() < 2) { // 可控制是否可多颗子弹
                Shot shot = null;
                // 判断坦克方向，对应子弹
                switch (getDirect()) {
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
                shots.add(shot);
                // 启动
                new Thread(shot).start();
            }

            // 根据坦克的方向继续移动
            switch (getDirect()) {
                case 0: // 上
                    // 让坦克一个方向走30步
                    for (int i = 0; i < 30; i++) {
                        if (getY() > 0 && !isTouchEnemyTank()) moveUp();  // 不超出边界
                        // 休眠50毫秒
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                case 1: // 右
                    // 让坦克一个方向走30步
                    for (int i = 0; i < 30; i++) {
                        if (getX() + 60 < 1000 && !isTouchEnemyTank()) moveRight();
                        // 休眠50毫秒
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                case 2: // 下
                    // 让坦克一个方向走30步
                    for (int i = 0; i < 30; i++) {
                        if (getY() + 60 < 750 && !isTouchEnemyTank()) moveDown();
                        // 休眠50毫秒
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                case 3: // 左
                    // 让坦克一个方向走30步
                    for (int i = 0; i < 30; i++) {
                        if (getX() > 0 && !isTouchEnemyTank()) moveLeft();
                        // 休眠50毫秒
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
            }
            // 随机改变方向
            setDirect((int)(Math.random() * 4));
            // （多线程）判断该线程什么时候结束
            if (!isLive) break;
        }
    }
}
