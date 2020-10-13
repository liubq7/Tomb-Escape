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
    private ImageView shovelView;
    private ImageView bloodBagView;
    private ImageView cloakView;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        status = 0;
        props = 0;
        this.setPrefSize(CELLSIZE, CELLSIZE);
    }

    public void setView(int blockType) {
    	
        blockView = new ImageView(new Image("file:images/tileChoose/"+ blockType+ ".png"));	//k值来自settingPane 的tileChoose
        blockView.setFitHeight(CELLSIZE);
        blockView.setPreserveRatio(true);
        if(status == 0) {
            this.setCenter(blockView);
        } else {
            switch (props) {
                case 1:
                    shovelView = new ImageView(new Image("file:images/btmBarImg/2.png"));
                    shovelView.setFitHeight(CELLSIZE - 12);
                    shovelView.setPreserveRatio(true);
                    this.setCenter(shovelView);
                    break;
                case 2:
                    bloodBagView = new ImageView(new Image("file:images/btmBarImg/blood.png"));
                    bloodBagView.setFitHeight(CELLSIZE- 12);
                    bloodBagView.setPreserveRatio(true);
                    this.setCenter(bloodBagView);
                    break;
                case 3:
                    cloakView = new ImageView(new Image("file:images/btmBarImg/3.png"));
                    cloakView.setFitHeight(CELLSIZE - 12);
                    cloakView.setPreserveRatio(true);
                    this.setCenter(cloakView);
                    break;
            }
        }
    }
}
