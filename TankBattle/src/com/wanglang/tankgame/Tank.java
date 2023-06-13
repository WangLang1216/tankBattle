package com.wanglang.tankgame;

public class Tank {
    private int x; // 坦克横坐标
    private int y; // 坦克纵坐标

    private int direct; // 方向 0上 1右 2下 3左

    private int speed = 1; // 速度

    private boolean isLive = true; // 是否存活

    public Tank(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // 上下左右移动方法
    // 上
    public void moveUp() {
        y -= speed;
    }
    // 右
    public void moveRight() {
        x += speed;
    }
    // 下
    public void moveDown() {
        y += speed;
    }
    // 左
    public void moveLeft() {
        x -= speed;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDirect() {
        return direct;
    }

    public void setDirect(int direct) {
        this.direct = direct;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
    }
}
