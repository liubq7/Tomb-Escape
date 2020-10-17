package Panes;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import Maze.MazeCreator;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class MazePane extends GridPane {
    public static final int CELLSIZE = 28;
    MazeCreator mazeCreator;
    Player player;
    Ghost[] ghosts;
    Random random;
    int rows, cols;

    public ImageView characterIcon;
    private ImageView doorView;


    /**
     * @param x             width
     * @param y             height
     * @param characterType character type player chose in setting
     * @param blockType     block type player chose in setting
     */
    public MazePane(int x, int y, int characterType, int blockType) {

        rows = y;
        cols = x;
        mazeCreator = new MazeCreator(x, y);
        mazeCreator.generateMaze();
        player = new Player(1, 1, characterType);

        /* init 3 ghosts and their start position randomly. */
        ghosts = new Ghost[3];
        random = new Random();
        for (int i = 0; i < ghosts.length; i++) {
            int ghostX = random.nextInt(cols);
            int ghostY = random.nextInt(rows);
            while (mazeCreator.maze[ghostX][ghostY].status != 1 || (ghostX == 1 && ghostY == 1)) {
                ghostX = random.nextInt(cols);
                ghostY = random.nextInt(rows);
            }
            ghosts[i] = new Ghost(ghostX, ghostY, false);
        }
        ghosts[0].hasKey = true;

        initImg();
        initMazeLayouts(blockType);
        ghostsMove();

        this.setFocusTraversable(true);
    }

    /* Ghosts move randomly in the maze's road. */
    private void ghostsMove() {
        for (int i = 0; i < ghosts.length; i++) {
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
                                    break;
                                case 1:
                                    ghost.y++;
                                    break;
                                case 2:
                                    ghost.x--;
                                    break;
                                case 3:
                                    ghost.y--;
                                    break;
                            }
                            ghostRefresh(ghost.x, ghost.y, thisView);
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

    /* Refresh the cell when a ghost move to there. */
    protected void ghostRefresh(int x, int y, ImageView thisView) {
        mazeCreator.maze[x][y].setCenter(thisView);
        mazeCreator.maze[x][y].props = 0;
    }

    public class Ghost {
        int x, y;    // the position of this ghost
        boolean hasKey;    // whether the ghost has a key
        ImageView ghostView;

        public Ghost(int x, int y, boolean hasKey) {
            this.hasKey = hasKey;
            this.x = x;
            this.y = y;
        }

        /* Get all directions can move. */
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

    /* Get the ghost at the location by coordinates, return null if there is no ghost at the location. */
    public Ghost getGhost(int x, int y) {
        for (int i = 0; i < ghosts.length; i++) {
            if (x == ghosts[i].x && y == ghosts[i].y) {
                return ghosts[i];
            }
        }
        return null;
    }


    public class Player {
        int x, y;  // the position of the player
        private int characterType;
        int[] itemList = {0, 0, 0, 2};    // the number of prop he has. [0]key, [1]shovel, [2]cloak, [3]blood(init 2)
        boolean visible = true;  // whether the player is visible by the ghost.

        private Player(int x, int y, int characterType) {
            this.x = x;
            this.y = y;
            this.characterType = characterType;
            switch (characterType) {
                case 0:  // warrior
                    itemList[1] += 1;  // An extra shovel
                    break;
                case 1:  // priest
                    itemList[2] += 1;  // An extra invisibility cloak
                    break;
                case 2:  // defender
                    itemList[3] += 1;  // An extra blood
                    break;
            }
        }

        /* If go to the cell has prop, corresponding itemList +1 and return code. */
        public int getProp() {
            if (mazeCreator.shovelList.contains(mazeCreator.maze[x][y])) {  // shovel
                itemList[1] += 1;
                mazeCreator.shovelList.remove(mazeCreator.maze[x][y]);
                return 1;
            } else if (mazeCreator.bloodBagList.contains(mazeCreator.maze[x][y])) {  // blood bag
                itemList[3] += 1;
                mazeCreator.bloodBagList.remove(mazeCreator.maze[x][y]);
                return 2;
            } else if (mazeCreator.cloakList.contains(mazeCreator.maze[x][y])) {  // cloak
                itemList[2] += 1;
                mazeCreator.cloakList.remove(mazeCreator.maze[x][y]);
                return 3;
            } else if (mazeCreator.trapList.contains(mazeCreator.maze[x][y])) {  // trap
                mazeCreator.trapList.remove(mazeCreator.maze[x][y]);
                return 4;
            } else {  // none
                return 0;
            }
        }

    }

    private void initMazeLayouts(int blockType) {
        this.setVgap(2);
        this.setHgap(2);

        for (int j = 0; j < rows; j++) {
            for (int i = 0; i < cols; i++) {
                System.out.print(mazeCreator.maze[i][j].status + " ");
                mazeCreator.maze[i][j].setView(blockType);
                this.add(mazeCreator.maze[i][j], i, j);
            }
            System.out.println();
        }

        for (int i = 0; i < ghosts.length; i++) {
            this.mazeCreator.maze[ghosts[i].x][ghosts[i].y].setCenter(ghosts[i].ghostView);
        }

        this.mazeCreator.maze[player.x][player.y].setCenter(characterIcon);  // set the init player's position (1,1)
        this.mazeCreator.maze[cols - 2][rows - 1].setCenter(doorView);  // set the exit's position
    }

    /* Generate different icons according to different charactertypes. */
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

        for (int i = 0; i < ghosts.length; i++) {
            ghosts[i].ghostView = new ImageView(new Image("file:images/ghost.png"));
            ghosts[i].ghostView.setFitHeight(CELLSIZE - 1);
            ghosts[i].ghostView.setPreserveRatio(true);
        }
    }


}
