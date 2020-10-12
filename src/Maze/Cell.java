package Maze;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import static Panes.MazePane.CELLSIZE;

public class Cell extends BorderPane {
    public int x;
    public int y;  // 保存坐标
    public int status;  // 0为墙，1为路
    public int props;	//这个地方是否有道具，0:没有，1:有铲子,2:血包,3:隐身衣

    private ImageView blockView;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        status = 0;
        props = 0;
    }

    public void setView() {
        blockView = new ImageView(new Image("file:images/brick-wall.png"));
        blockView.setFitHeight(CELLSIZE);
        blockView.setPreserveRatio(true);
        if(status == 0) {
            this.setCenter(blockView);
        }
    }
}
