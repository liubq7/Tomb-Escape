package Maze;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * 使用递归回溯(Recursive backtracker)算法生成迷宫
 */
public class MazeCreator {
    private int col;  // 列（长）x，需为奇数
    private int row;  // 行（宽）y，需为奇数
    public Cell[][] maze;
    private LinkedList<Cell> recurseList;
    private LinkedList<Cell> availableList;

    private int shovelNum;
    private int bloodBagNum;
    private int cloakNum;

    public ArrayList<Cell> shovelList;
    public ArrayList<Cell> bloodBagList;
    public ArrayList<Cell> cloakList;


    public MazeCreator(int x, int y) {
        col = x;
        row = y;
        maze = new Cell[x][y];
        recurseList = new LinkedList<>();
        availableList = new LinkedList<>();

        shovelNum = 2;
        bloodBagNum = 3;
        cloakNum = 1;

    }

    // 生成0，1间隔的基础地图
    private void initMaze() {
        for (int i = 0; i < col; i++) {
            for (int j = 0; j < row; j++) {
                maze[i][j] = new Cell(i, j);
            }
        }
        for (int i = 1; i < col; i += 2) {
            for (int j = 1; j < row; j += 2) {
                maze[i][j].status = 1;
                availableList.add(maze[i][j]);
            }
        }
    }

    // 随机选择一个1为起始点
    private Cell randomStart() {
        int i = 1 + 2 * (int) (Math.random() * (col-1) / 2);
        int j = 1 + 2 * (int) (Math.random() * (row-1) / 2);
        recurseList.add(maze[i][j]);
        availableList.remove(maze[i][j]);
        return maze[i][j];
    }

    // 判断一个坐标是否在此maze中
    private boolean isContain(int x, int y) {
        if (x >= 0 && x < col && y >= 0 && y < row) {
            return true;
        } else {
            return false;
        }
    }

    // 获取start点周围没有被mark过的全部的1
    private ArrayList<Cell> getAround(Cell start) {
        ArrayList<Cell> around = new ArrayList<>();
        if (isContain(start.x, start.y-2) && availableList.contains(maze[start.x][start.y-2])) {
            around.add(maze[start.x][start.y-2]);
        }
        if (isContain(start.x, start.y+2) && availableList.contains(maze[start.x][start.y+2])) {
            around.add(maze[start.x][start.y+2]);
        }
        if (isContain(start.x-2, start.y) && availableList.contains(maze[start.x-2][start.y])) {
            around.add(maze[start.x-2][start.y]);
        }
        if (isContain(start.x+2, start.y) && availableList.contains(maze[start.x+2][start.y])) {
            around.add(maze[start.x+2][start.y]);
        }
        return around;
    }

    // 获取start点周围随机的没被mark过的1，若没有则返回null
    private Cell getEnd(Cell start) {
        ArrayList<Cell> around = getAround(start);
        int n = around.size();
        if (n != 0) {
            int random = (int)(Math.random() * n);
            return around.get(random);
        } else {
            return null;
        }
    }

    // 将start与end之间打通，并把end添加进roadList，
    private void generatePath(Cell start, Cell end) {
        int midX = (start.x + end.x) / 2;
        int midY = (start.y + end.y) / 2;
        maze[midX][midY].status = 1;
        recurseList.add(end);
        availableList.remove(end);
    }

    public void generateMaze() {
        initMaze();
        Cell start = randomStart();
        while (!availableList.isEmpty()) {
            if (getEnd(start) != null) {
                Cell end = getEnd(start);
                generatePath(start, end);
                start = recurseList.getLast();
            } else {
                recurseList.removeLast();
                start = recurseList.getLast();
            }
        }


        shovelList = new ArrayList<>();
        while (shovelList.size() < shovelNum) {
            int i = (int) (Math.random() * (col));
            int j = (int) (Math.random() * (row));
            if (maze[i][j].status == 1 && maze[i][j].props == 0) {
                maze[i][j].props = 1;
                shovelList.add(maze[i][j]);
            }
        }

        bloodBagList = new ArrayList<>();
        while (bloodBagList.size() < bloodBagNum) {
            int i = (int) (Math.random() * (col));
            int j = (int) (Math.random() * (row));
            if (maze[i][j].status == 1 && maze[i][j].props == 0) {
                maze[i][j].props = 2;
                bloodBagList.add(maze[i][j]);
            }
        }

        cloakList = new ArrayList<>();
        while (cloakList.size() < cloakNum) {
            int i = (int) (Math.random() * (col));
            int j = (int) (Math.random() * (row));
            if (maze[i][j].status == 1 && maze[i][j].props == 0) {
                maze[i][j].props = 3;
                cloakList.add(maze[i][j]);
            }
        }

        // todo: 生成陷阱
    }



    public static void main(String args[]) {
        int x = 21;
        int y = 13;
        MazeCreator mazeCreator = new MazeCreator(x, y);
        mazeCreator.generateMaze();
        for (int j = 0; j < y; j++) {
            for (int i = 0; i < x; i++) {
                System.out.print(mazeCreator.maze[i][j].status + " ");
            }
            System.out.println();
        }
    }
}
