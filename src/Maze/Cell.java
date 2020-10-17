package Maze;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import static Panes.MazePane.CELLSIZE;

/* The maze cell. */
public class Cell extends BorderPane {
    public int x;
    public int y;  // coordinate
    public int status;  // 0: wall, 1: road
    public int props;	// 0: no property, 1: shovel, 2: blood bag, 3: invisible cloak, 4: trap

    public int blockType;

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

    /**
     * Set cell's view.
     * @param blockType block type player chose in setting
     */
    public void setView(int blockType) {
        this.blockType = blockType;
    	
        blockView = new ImageView(new Image("file:images/tileChoose/"+ blockType+ ".png"));
        blockView.setFitHeight(CELLSIZE);
        blockView.setPreserveRatio(true);

        if(status == 0) {  // if wall
            this.setCenter(blockView);
        } else {
            switch (props) {  // if has property
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
                case 4: // comment this case while playing
                    cloakView = new ImageView(new Image("file:images/tiles.png"));
                    cloakView.setFitHeight(CELLSIZE - 12);
                    cloakView.setPreserveRatio(true);
                    this.setCenter(cloakView);
                    break;
            }
        }
    }
}
