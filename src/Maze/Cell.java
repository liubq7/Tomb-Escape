package Maze;

public class Cell {
    int x;
    int y;  // 保存坐标
    boolean isMarked;
    int status;  // 0为墙，1为路

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        isMarked = false;
        status = 0;
    }
}
