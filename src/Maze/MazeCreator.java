package Maze;

import java.util.LinkedList;

/**
 * 使用递归回溯(Recursive backtracker)算法生成迷宫
 */
public class MazeCreator {
    private int col;  // 列（长）x，需为奇数
    private int row;  // 行（宽）y，需为奇数
    private Cell[][] maze;
    private LinkedList<Cell> roadList;
    private LinkedList<Cell> availableList;

    public MazeCreator(int x, int y) {
        col = x;
        row = y;
        maze = new Cell[x][y];
        roadList = new LinkedList<>();
        availableList = new LinkedList<>();
    }

    // 生成0，1间隔的基础地图
    private void initMaze() {
        for (int i = 0; i < col; i++) {
            for (int j = 0; j < row; j++) {
                maze[i][j] = new Cell(i, j);
                availableList.add(maze[i][j]);
            }
        }
        for (int i = 1; i < col; i += 2) {
            for (int j = 1; j < row; j += 2) {
                maze[i][j].status = 1;
            }
        }
    }

    // 随机选择一个1为起始点
    private Cell randomStart() {
        int i = 1 + 2 * (int) (Math.random() * col / 2);
        int j = 1 + 2 * (int) (Math.random() * row / 2);
        return maze[i][j];
    }

    private void generateMaze() {
        initMaze();
        Cell start = randomStart();
    }



    public static void main(String args[]) {
        int x = 19;
        int y = 15;
        MazeCreator mazeCreator = new MazeCreator(x, y);
        mazeCreator.initMaze();
        for (int j = 0; j < y; j++) {
            for (int i = 0; i < x; i++) {
                System.out.print(mazeCreator.maze[i][j].status + " ");
            }
            System.out.println();
        }
        Cell start = mazeCreator.randomStart();
        System.out.println(start.x);
        System.out.println(start.y);
    }
}
