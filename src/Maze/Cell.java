package Maze;

import javafx.scene.layout.BorderPane;

public class Cell extends BorderPane {
    public int x;
    public int y;  // 保存坐标
    public int status;  // 0为墙，1为路
    public int props;	//这个地方是否有道具，0:没有，1:有铲子,2:血包,3:隐身衣

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        status = 0;
        props = 0;
    }
}
