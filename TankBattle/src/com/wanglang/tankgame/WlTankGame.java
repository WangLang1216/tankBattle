package com.wanglang.tankgame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Scanner;

public class WlTankGame extends JFrame {
    // 定义MyPanel
    MyPanel myPanel = null;
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        WlTankGame wlTankGame = new WlTankGame();
    }

    public WlTankGame() {
        System.out.println("请输入选择（1：新游戏；2：继续上局）：");
        String key = scanner.next();

        myPanel = new MyPanel(key);
        // 将myPanel放入到Thread，并启动
        Thread thread = new Thread(myPanel);
        thread.start();

        this.add(myPanel);
        this.setSize(1300, 950);
        this.addKeyListener(myPanel); // 让JFrame监听myPanel的键盘事件
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        // 在JFrame中增加相应关闭窗口的处理
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("监听到关闭窗口！");
                Recorder.keepRecord();
                System.exit(0);
            }
        });
    }
}
