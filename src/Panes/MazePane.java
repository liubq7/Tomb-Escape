package Panes;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import Maze.MazeCreator;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
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
    Ghost[] ghosts;
    Random random;
	int rows,cols;	//行列数

    public ImageView characterIcon;
    private ImageView doorView;


	
    public MazePane(int x, int y, int characterType, int blockType) {
//        this.setPadding(new Insets(0, 10, 0, 10));
		rows = y;
		cols = x;
		mazeCreator = new MazeCreator(x,y);
		mazeCreator.generateMaze();	//迷宫二维数组确定，不然没办法随机生成鬼的位置
		player = new Player(1,1, characterType);

		ghosts = new Ghost[3];
		random = new Random();
		for(int i=0; i<ghosts.length; i++) {
		    // TODO: 鬼的起始位置在（1，1）
			int ghostX = random.nextInt(cols);
			int ghostY = random.nextInt(rows);
			while(mazeCreator.maze[ghostX][ghostY].status != 1) {
				ghostX = random.nextInt(cols);
				ghostY = random.nextInt(rows);
			}
			ghosts[i] = new Ghost(ghostX,ghostY,false);
		}
		ghosts[0].hasKey = true;	//鬼0身上有钥匙
		
		initImg();
		initMazeLayouts(blockType);
		ghostsMove();

        this.setFocusTraversable(true);
	}

    private void ghostsMove() {
		for(int i=0; i<ghosts.length; i++) {
			Ghost ghost = ghosts[i];
			ImageView thisView = ghosts[i].ghostView;
			Timer timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> {
                        ArrayList<Integer> dirList = ghost.checkDir();
                        if (!dirList.isEmpty()) {
                            random = new Random();
                            int dir = dirList.get(random.nextInt(dirList.size()));
                            switch (dir) {
                                case 0:
                                    ghost.x++;
                                    mazeCreator.maze[ghost.x][ghost.y].setCenter(thisView);
                                    break;
                                case 1:
                                    ghost.y++;
                                    mazeCreator.maze[ghost.x][ghost.y].setCenter(thisView);
                                    break;
                                case 2:
                                    ghost.x--;
                                    mazeCreator.maze[ghost.x][ghost.y].setCenter(thisView);
                                    break;
                                case 3:
                                    ghost.y--;
                                    mazeCreator.maze[ghost.x][ghost.y].setCenter(thisView);
                                    break;
                            }
                        }
                    });
                }
            }, 0, 1000);
            if (ghost.x == player.x && ghost.y == player.y) {
                ghost.x = 0;
                ghost.y = 0;
                timer.cancel();
            }
		}
	}

    public class Ghost{
    	int x,y;	//保存坐标
    	boolean hasKey;	//0代表没有钥匙，1代表有钥匙
    	ImageView ghostView;
    	
    	public Ghost(int x, int y, boolean hasKey) {
    		this.hasKey = hasKey;
    		this.x = x;
    		this.y = y;	
    	}

    	// 获取周围能走的方向(没有人的方向）
        private ArrayList<Integer> checkDir() {
            ArrayList<Integer> dirList = new ArrayList<>();
            if (x + 1 < cols && (mazeCreator.maze[x + 1][y]).status == 1 && !(x + 1 == player.x && y == player.y)) {
                dirList.add(0);
            }
            if (y + 1 < rows && (mazeCreator.maze[x][y + 1]).status == 1 && !(x == player.x && y + 1 == player.y)) {
                dirList.add(1);
            }
            if (x - 1 > 0 && (mazeCreator.maze[x - 1][y]).status == 1 && !(x - 1 == player.x && y == player.y)) {
                dirList.add(2);
            }
            if (y - 1 > 0 && (mazeCreator.maze[x][y - 1]).status == 1 && !(x == player.x && y - 1 == player.y)) {
                dirList.add(3);
            }
            return dirList;
        }
    }
    // 通过坐标获取该位置的鬼，该位置没有鬼则返回null
    public Ghost getGhost(int x, int y) {
        for (int i = 0; i < ghosts.length; i++) {
            if (x == ghosts[i].x && y == ghosts[i].y) {
                return ghosts[i];
            }
        }
        return null;
    }



    public class Player {
        int x,y;	//保存坐标
        private int characterType;
        int[] itemList = {0, 0, 0, 2};	//拥有道具数目，itemList[0]钥匙 itemList[1]铲子 itemList[2]隐身衣 itemList[3]血值（初始为2）
        boolean visible = true;		//使用隐身衣后，visible变为false, 将不会触发打鬼游戏

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
            } else if (mazeCreator.bloodBagList.contains(mazeCreator.maze[x][y])) {  // 血包,捡到直接加血
                itemList[3] += 1;
                mazeCreator.bloodBagList.remove(mazeCreator.maze[x][y]);
                return 2;
            } else if (mazeCreator.cloakList.contains(mazeCreator.maze[x][y])) {  // 隐身
                itemList[2] += 1;
                mazeCreator.cloakList.remove(mazeCreator.maze[x][y]);
                return 3;
            } else if (mazeCreator.trapList.contains(mazeCreator.maze[x][y])) {
                mazeCreator.trapList.remove(mazeCreator.maze[x][y]);
                return 4;
            } else {
                return 0;
            }
        }
        
    }

    private void initMazeLayouts(int blockType) {
        this.setVgap(2);
        this.setHgap(2);
		//mazeCreator.generateMaze(); //我在实例化的时候调用了，不然没办法随机生成鬼的位置
        for (int j = 0; j < rows; j++) {
            for (int i = 0; i < cols; i++) {
            	System.out.print(mazeCreator.maze[i][j].status + " ");
                mazeCreator.maze[i][j].setView(blockType);
				this.add(mazeCreator.maze[i][j], i, j);
            }
            System.out.println();
        }

        for(int i=0; i<ghosts.length; i++) {
        	this.mazeCreator.maze[ghosts[i].x][ghosts[i].y].setCenter(ghosts[i].ghostView);
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
        
        for(int i=0; i<ghosts.length; i++) {
        	ghosts[i].ghostView = new ImageView(new Image("file:images/ghost.png"));
        	ghosts[i].ghostView.setFitHeight(CELLSIZE - 1);
        	ghosts[i].ghostView.setPreserveRatio(true);
        }
    }


}
