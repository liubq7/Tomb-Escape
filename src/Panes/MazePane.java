package Panes;

import Maze.Cell;
import Maze.MazeCreator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

class MazeCell extends BorderPane{
    int x;
    int y;  // 保存坐标
    int status;  // 0为墙，1为路
    int props;	//这个地方是否有道具，0:没有，1:有铲子,2:血包,3:隐身衣，4:钥匙
    ImageView blockView;
    public MazeCell(Cell cell, int CellSize) {
    	x = cell.x;
    	y = cell.y;
    	status = cell.status;
    	props = 0;
    	blockView = new ImageView( new Image("file:images/brick-wall.png"));
    	blockView.setFitHeight(CellSize);
    	blockView.setPreserveRatio(true);
    	this.setPrefSize(CellSize, CellSize);
    	if(status == 0) {
    		this.setCenter(blockView);
    	}
    }
}

class CharacterCell extends BorderPane{
	int x,y;	//保存坐标
	int[] itemList;	//拥有道具数目，itemList[0]钥匙 itemList[1]铲子 itemList[2] 隐身衣
	ImageView characterIcon;		//显示角色icon
	public CharacterCell(int x, int y, int CellSize, int characterType) {
		this.x = x;
		this.y = y;
		characterIcon = new ImageView( new Image("file:images/characterPlay/"+String.valueOf(characterType)+".png"));
		characterIcon.setFitHeight(CellSize);
		characterIcon.setPreserveRatio(true);
    	this.setPrefSize(CellSize, CellSize);
    	this.setCenter(characterIcon);
	}
}

public class MazePane extends GridPane{
	MazeCreator mazeCreator;
	MazeCell[][] mazeLayouts;
	CharacterCell player;
	int rows,cols;	//行列数
	int CellSize;
	
	public MazePane(int x, int y, int PaneHeight) {
		rows = y;
		cols = x;
		CellSize = PaneHeight/rows;
		mazeCreator = new MazeCreator(x,y);
		mazeLayouts = new MazeCell[x][y];
		player = new CharacterCell(1,1,CellSize,0);
		initMazeLayouts();
		this.setVgap(2);
		this.setHgap(2);
	}

	private void initMazeLayouts() {
		// TODO Auto-generated method stub
		mazeCreator.generateMaze();
        for (int j = 0; j < rows; j++) {
            for (int i = 0; i < cols; i++) {
				mazeLayouts[i][j] = new MazeCell(mazeCreator.maze[i][j],CellSize);
            	System.out.print(mazeLayouts[i][j].status + " ");
				this.add(mazeLayouts[i][j], i, j);
            }
            System.out.println();
        }
        this.add(player, player.x,player.y );
	}
}
