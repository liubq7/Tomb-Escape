package Panes;

import Maze.Cell;
import Maze.MazeCreator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class MazePane extends GridPane{
	MazeCreator mazeCreator;
	MazeCell[][] mazeLayouts;
	CharacterCell player;
	int rows,cols;	//行列数
	int cellSize;
	
	public MazePane(int x, int y, int PaneHeight) {
		rows = y;
		cols = x;
		cellSize = PaneHeight/rows;
		mazeCreator = new MazeCreator(x,y);
		mazeLayouts = new MazeCell[x][y];
		player = new CharacterCell(1,1, 0);
		initMazeLayouts();

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

    private class CharacterCell extends BorderPane{
        int x,y;	//保存坐标
        int[] itemList;	//拥有道具数目，itemList[0]钥匙 itemList[1]铲子 itemList[2] 隐身衣
        ImageView characterIcon;		//显示角色icon
        public CharacterCell(int x, int y, int characterType) {
            this.x = x;
            this.y = y;
            characterIcon = new ImageView( new Image("file:images/characterPlay/"+String.valueOf(characterType)+".png"));
            characterIcon.setFitHeight(cellSize);
            characterIcon.setPreserveRatio(true);
            this.setCenter(characterIcon);
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
        this.add(player, player.x,player.y );
	}
}
