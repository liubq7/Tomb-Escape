package Maze;

public class Cell {
    public int x;
    public int y;  // 保存坐标
    public int status;  // 0为墙，1为路

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        status = 0;
    }
}
