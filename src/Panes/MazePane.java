package Panes;

import Maze.MazeCreator;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

public class MazePane extends GridPane{
    public static final int CELLSIZE = 28;
    MazeCreator mazeCreator;
    Player player;
	int rows,cols;	//行列数

    public ImageView characterIcon;
    private ImageView doorView;

    private EventHandler<KeyEvent> moveListener;

	
    public MazePane(int x, int y, int characterType) {
//        this.setPadding(new Insets(0, 10, 0, 10));
		rows = y;
		cols = x;
		mazeCreator = new MazeCreator(x,y);
		player = new Player(1,1, characterType);
		initImg();
		initMazeLayouts();

        this.setFocusTraversable(true);
	}

    public class Player {
        int x,y;	//保存坐标
        private int characterType;
        int[] itemList = {0, 0, 0, 2};	//拥有道具数目，itemList[0]钥匙 itemList[1]铲子 itemList[2]隐身衣 itemList[3]血值（初始为2）

        private Player(int x, int y, int characterType) {
            this.x = x;
            this.y = y;
            this.characterType = characterType;
            switch (characterType) {
                case 0:  // warrior
                    itemList[1] += 1;  // 多一把铲子
                    break;
                case 1:  // priest
                    itemList[2] += 1;  // 多一件隐身衣
                    break;
                case 2:  // defender
                    itemList[3] += 1;  // 多一滴血
                    break;
            }
        }

        // 如果走到道具处则itemlist相应+1并return代号
        public int getProp() {
            if (mazeCreator.shovelList.contains(mazeCreator.maze[x][y])) {  // 铲子
                itemList[1] += 1;
                mazeCreator.shovelList.remove(mazeCreator.maze[x][y]);
                return 1;
            } else if (mazeCreator.bloodBagList.contains(mazeCreator.maze[x][y])) {  // 血包
                itemList[3] += 1;
                mazeCreator.bloodBagList.remove(mazeCreator.maze[x][y]);
                return 2;
            } else if (mazeCreator.cloakList.contains(mazeCreator.maze[x][y])) {  // 隐身
                itemList[2] += 1;
                mazeCreator.cloakList.remove(mazeCreator.maze[x][y]);
                return 3;
            } else {
                return 0;
            }
        }
    }

    private void initMazeLayouts() {
        this.setVgap(2);
        this.setHgap(2);
		mazeCreator.generateMaze();
        for (int j = 0; j < rows; j++) {
            for (int i = 0; i < cols; i++) {
            	System.out.print(mazeCreator.maze[i][j].status + " ");
                mazeCreator.maze[i][j].setView();
				this.add(mazeCreator.maze[i][j], i, j);
            }
            System.out.println();
        }
        this.mazeCreator.maze[player.x][player.y].setCenter(characterIcon);
        this.mazeCreator.maze[cols - 2][rows - 1].setCenter(doorView);
    }

    /* 根据不同的charactertype产生不同的icon */
    private void initImg() {
        switch (player.characterType) {
            case 0:
                characterIcon = new ImageView(new Image("file:images/characterPlay/0.png"));
                break;
            case 1:
                characterIcon = new ImageView(new Image("file:images/characterPlay/1.png"));
                break;
            case 2:
                characterIcon = new ImageView(new Image("file:images/characterPlay/2.png"));
                break;
        }
        characterIcon.setFitHeight(CELLSIZE - 1);
        characterIcon.setPreserveRatio(true);

        doorView = new ImageView(new Image("file:images/door.png"));
        doorView.setFitHeight(CELLSIZE - 1);
        doorView.setPreserveRatio(true);
    }


}
