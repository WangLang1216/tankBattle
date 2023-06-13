package com.wanglang.tankgame;


import java.io.*;
import java.util.Vector;

/**
 * 该类用于记录相关信息的和文件交互
 */
public class Recorder {
    // 定义变量，记录我方击毁敌人坦克数
    private static int allEnemyTankNum = 0;
    // 定义IO对象，准备写数据到文件中
    private static BufferedReader br = null;
    private static BufferedWriter bw = null;
    // 定义文件路径
    private static String recordFile = "src\\myRecord.txt";
    // 定义Vector，指向MyPanel对象的敌人坦克Vector
    private static Vector<EnemyTank> enemyTanks = null;
    // 定义一个Node的Vector用于保存敌人的信息node
    private static Vector<Node> nodes = new Vector<>();

    // 当游戏退出时，我们将allEnemyTankNum值保存到recordFile中
    // 保存敌人坦克的坐标和方向
    public static void keepRecord() {
        try {
            bw = new BufferedWriter(new FileWriter(recordFile));
            bw.write(allEnemyTankNum + "\n");
            // 遍历敌人坦克的Vector，然后根据情况保存
            for (int i = 0; i < enemyTanks.size(); i++) {
                // 取出
                EnemyTank enemyTank = enemyTanks.get(i);
                if (enemyTank.isLive()) {
                    // 保存该enemyTank信息
                    String record = enemyTank.getX() + " " + enemyTank.getY() + " " + enemyTank.getDirect();
                    // 写入
                    bw.write(record);
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 用于读取recordFile，恢复相关信息
    // 该方法用于继续上局游戏的时候
    public static Vector<Node> getNodesAndEnemyTankRec() {
        try {
            br = new BufferedReader(new FileReader(recordFile));
            allEnemyTankNum = Integer.parseInt(br.readLine());
            // 循环读取文件，生成nodes集合
            String line = "";
            while ((line = br.readLine()) != null) {
                String[] xyd = line.split(" ");
                Node node = new Node(Integer.parseInt(xyd[0]), Integer.parseInt(xyd[1]), Integer.parseInt(xyd[2]));
                // 放入nodes Vector
                nodes.add(node);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return nodes;
    }

    // 当我方坦克击毁一个敌人坦克，就应该allEnemyTankNum++
    public static void addAllEnemyTankNum() {
        Recorder.allEnemyTankNum++;
    }

    public static int getAllEnemyTankNum() {
        return allEnemyTankNum;
    }

    public static void setAllEnemyTankNum(int allEnemyTankNum) {
        Recorder.allEnemyTankNum = allEnemyTankNum;
    }

    public static Vector<EnemyTank> getEnemyTanks() {
        return enemyTanks;
    }

    public static void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        Recorder.enemyTanks = enemyTanks;
    }

    public static String getRecordFile() {
        return recordFile;
    }

    public static void setRecordFile(String recordFile) {
        Recorder.recordFile = recordFile;
    }
}
