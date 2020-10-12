package Panes;

import Maze.Cell;
import Maze.MazeCreator;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class MazePane extends GridPane{
	MazeCreator mazeCreator;
	MazeCell[][] mazeLayouts;
    Player player;
	int rows,cols;	//行列数
	int cellSize;

	private ImageView characterIcon;

    private EventHandler<KeyEvent> moveListener;

	
	public MazePane(int x, int y, int PaneHeight) {
		rows = y;
		cols = x;
		cellSize = PaneHeight/rows;
		mazeCreator = new MazeCreator(x,y);
		mazeLayouts = new MazeCell[x][y];
		player = new Player(1,1, 0);
		initImg();
		initMazeLayouts();

        this.setFocusTraversable(true);
        initListener(this);
        this.setOnKeyPressed(moveListener);
	}

    private class MazeCell extends BorderPane{
        private Cell cell;
        private ImageView blockView;

        public MazeCell(Cell cell) {
            this.cell = cell;
            blockView = new ImageView(new Image("file:images/brick-wall.png"));
            blockView.setFitHeight(cellSize);
            blockView.setPreserveRatio(true);
            if(cell.status == 0) {
                this.setCenter(blockView);
            }
        }
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
				mazeLayouts[i][j] = new MazeCell(mazeCreator.maze[i][j]);
                mazeLayouts[i][j].setPrefSize(cellSize, cellSize);
            	System.out.print(mazeLayouts[i][j].cell.status + " ");
				this.add(mazeLayouts[i][j], i, j);
            }
            System.out.println();
        }
        this.mazeLayouts[player.x][player.y].setCenter(characterIcon);
	}

	private void initImg() {
        characterIcon = new ImageView(new Image("file:images/characterPlay/0.png"));
        characterIcon.setFitHeight(cellSize - 1);
        characterIcon.setPreserveRatio(true);
    }


    private void initListener(MazePane mazePane) {
        moveListener = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode()) {
                    case S:
                        System.out.println("S");
                        player.y += 1;
                        mazePane.mazeLayouts[player.x][player.y].setCenter(characterIcon);
                        break;
                    case W:
                        System.out.println("W");
                        player.y -= 1;
                        mazePane.mazeLayouts[player.x][player.y].setCenter(characterIcon);
                        break;
                    case A:
                        System.out.println("A");
                        player.x -= 1;
                        mazePane.mazeLayouts[player.x][player.y].setCenter(characterIcon);
                        break;
                    case D:
                        System.out.println("D");
                        player.x += 1;
                        mazePane.mazeLayouts[player.x][player.y].setCenter(characterIcon);
                        break;
                }

            }
        };
    }


}
