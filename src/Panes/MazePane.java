package Panes;

import Maze.Cell;
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
    Cell[][] mazeLayouts;
    Player player;
	int rows,cols;	//行列数

    private ImageView characterIcon;
    private ImageView doorView;

    private EventHandler<KeyEvent> moveListener;

	
    public MazePane(int x, int y, int characterType) {
//        this.setPadding(new Insets(0, 10, 0, 10));
		rows = y;
		cols = x;
		mazeCreator = new MazeCreator(x,y);
		mazeLayouts = mazeCreator.maze;
		player = new Player(1,1, characterType);
		initImg();
		initMazeLayouts();

        this.setFocusTraversable(true);
        initListener(this);
        this.setOnKeyPressed(moveListener);
	}

    private class Player {
        int x,y;	//保存坐标
        private int characterType;
        int[] itemList;	//拥有道具数目，itemList[0]钥匙 itemList[1]铲子 itemList[2] 隐身衣

        private Player(int x, int y, int characterType) {
            this.x = x;
            this.y = y;
            this.characterType = characterType;
        }
    }

    private void initMazeLayouts() {
        this.setVgap(2);
        this.setHgap(2);
		mazeCreator.generateMaze();
        for (int j = 0; j < rows; j++) {
            for (int i = 0; i < cols; i++) {
            	System.out.print(mazeLayouts[i][j].status + " ");
                mazeLayouts[i][j].setView();
				this.add(mazeLayouts[i][j], i, j);
            }
            System.out.println();
        }
        this.mazeLayouts[player.x][player.y].setCenter(characterIcon);
        this.mazeLayouts[cols - 2][rows - 1].setCenter(doorView);
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


    // TODO: 走到有道具的地方label+1
    private void initListener(MazePane mazePane) {
        moveListener = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                // TODO: 不能走的地方给予声音提示？
                switch (keyEvent.getCode()) {
                    case S:
                        System.out.println("S");
                        if (mazePane.mazeLayouts[player.x][player.y + 1].status != 0) {
                            player.y += 1;
                            mazePane.mazeLayouts[player.x][player.y].setCenter(characterIcon);
                        }
                        break;
                    case W:
                        System.out.println("W");
                        if (mazePane.mazeLayouts[player.x][player.y - 1].status != 0) {
                            player.y -= 1;
                            mazePane.mazeLayouts[player.x][player.y].setCenter(characterIcon);
                        }
                        break;
                    case A:
                        System.out.println("A");
                        if (mazePane.mazeLayouts[player.x - 1][player.y].status != 0) {
                            player.x -= 1;
                            mazePane.mazeLayouts[player.x][player.y].setCenter(characterIcon);
                        }
                        break;
                    case D:
                        System.out.println("D");
                        if (mazePane.mazeLayouts[player.x + 1][player.y].status != 0) {
                            player.x += 1;
                            mazePane.mazeLayouts[player.x][player.y].setCenter(characterIcon);
                        }
                        break;
                }

            }
        };
    }


}
