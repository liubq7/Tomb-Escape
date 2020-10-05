package Maze;

public class Cell {
    private int x;
    private int y;  // 保存坐标
    boolean isMarked;
    int status;  // 0为墙，1为路

    public void Cell() {
        isMarked = false;
    }

}
