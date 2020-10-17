package Panes;

import Maze.Cell;
import javafx.animation.FadeTransition;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import static Panes.MazePane.CELLSIZE;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

/* The main game page. */
public class GamePane extends BorderPane {
    HBox topBar, btmBar;
    MazePane mazePane;
    Button[] btmBarButton;    // buttons on btmBar, 0: restart, 1: key, 2: shovel, 3: invisible cloak, 4: homepage
    ImageView[] btmBarImg;    // the images of buttons on btmBar
    Media winMedia, lostMedia;
    MediaPlayer winMediaPlayer, lostMediaPlayer;
    int cols, rows;

    private Button key;
    private Button shovel;
    private Button cloak;
    Button restart;
    Button home;

    private Label status;  // show the game status
    private Label blood;  // show the blood left

    private EventHandler<KeyEvent> keyboardListener;
    private EventHandler<MouseEvent> dragDetector;
    private EventHandler<DragEvent> dragOverListener;
    private EventHandler<DragEvent> dragEnterListener;
    private EventHandler<DragEvent> dragExitListener;
    private EventHandler<DragEvent> dragDropper;
    private EventHandler<DragEvent> dragDoneListener;

    /**
     * @param x             width
     * @param y             height
     * @param characterType character type player chose in setting
     * @param blockType     block type player chose in setting
     * @param root          the stage has this gamepane
     */
    public GamePane(int x, int y, int characterType, int blockType, Stage root) {
        cols = x;
        rows = y;
        topBar = new HBox();
        btmBar = new HBox();
        mazePane = new MazePane(x, y, characterType, blockType);
        btmBarButton = new Button[5];
        btmBarImg = new ImageView[5];

        key = new Button();
        shovel = new Button();
        cloak = new Button();
        restart = new Button();
        home = new Button();

        status = new Label("Play status: Playing");
        blood = new Label("Blood left: " + mazePane.player.itemList[3]);

        iniTopBar();
        initBtmBar();
        initCenter();
        initWholePane();
        initMediaPlayer();

        this.setFocusTraversable(true);
        initListener(root, characterType, blockType);
        this.setOnKeyPressed(keyboardListener);
    }

    private void initMediaPlayer() {
        winMedia = new Media(new File("music/win.mp3").toURI().toString());
        winMediaPlayer = new MediaPlayer(winMedia);
        lostMedia = new Media(new File("music/lost.mp3").toURI().toString());
        lostMediaPlayer = new MediaPlayer(lostMedia);
    }

    private void initCenter() {
        mazePane.setAlignment(Pos.CENTER);
    }

    private void initWholePane() {
        this.setTop(topBar);
        this.setCenter(mazePane);
        this.setBottom(btmBar);
    }

    private void initBtmBar() {
        btmBarButton[0] = restart;
        btmBarButton[1] = key;
        btmBarButton[2] = shovel;
        btmBarButton[3] = cloak;
        btmBarButton[4] = home;

        for (int i = 0; i < btmBarButton.length; i++) {
            btmBarImg[i] = new ImageView(new Image("file:images/btmBarImg/" + String.valueOf(i) + ".png"));
            btmBarImg[i].setFitHeight(30);
            btmBarImg[i].setPreserveRatio(true);
            btmBarButton[i].setGraphic(btmBarImg[i]);
            btmBarButton[i].setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
            if (i >= 1 && i <= 3) {
                btmBarButton[i].setText(Integer.toString(mazePane.player.itemList[i - 1]));
            }
            btmBar.getChildren().add(btmBarButton[i]);
        }
        btmBar.setAlignment(Pos.CENTER);
        btmBar.setPadding(new Insets(10, 10, 10, 10));
        btmBar.setSpacing(10);
    }

    private void iniTopBar() {
        topBar.getChildren().addAll(status, blood);
        topBar.setPadding(new Insets(10, 10, 10, 10));
        topBar.setSpacing(50);
        topBar.setAlignment(Pos.CENTER);

    }

    public void playWallSound() {
        Media bs = new Media(new File("music/wall.mp3").toURI().toString());
        MediaPlayer bsPlayer = new MediaPlayer(bs);
        bsPlayer.play();
    }

    private void initListener(Stage root, int characterType, int blockType) {

        keyboardListener = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode()) {
                    case S:
                        System.out.println("S");
                        if (mazePane.mazeCreator.maze[mazePane.player.x][mazePane.player.y + 1].status != 0) {
                            // there's road, can move
                            mazePane.player.y += 1;
                            mazePane.mazeCreator.maze[mazePane.player.x][mazePane.player.y].setCenter(mazePane.characterIcon);
                        } else if ((mazePane.player.x == mazePane.cols - 2) &&
                                (mazePane.player.y + 1 == mazePane.rows - 1) && mazePane.player.itemList[0] == 1) {
                            // to the exit, if has key, game win
                            mazePane.player.y += 1;
                            gameWin();
                        } else if (mazePane.mazeCreator.maze[mazePane.player.x][mazePane.player.y + 1].status == 0) {
                            // there's wall, can't move and play remind sound
                            playWallSound();
                        }
                        break;
                    case W:
                        System.out.println("W");
                        if (mazePane.mazeCreator.maze[mazePane.player.x][mazePane.player.y - 1].status != 0) {
                            mazePane.player.y -= 1;
                            mazePane.mazeCreator.maze[mazePane.player.x][mazePane.player.y].setCenter(mazePane.characterIcon);
                        } else if ((mazePane.player.x == mazePane.cols - 2) &&
                                (mazePane.player.y - 1 == mazePane.rows - 1) &&
                                mazePane.player.itemList[0] == 1) {
                            mazePane.player.y -= 1;
                            gameWin();
                        } else if (mazePane.mazeCreator.maze[mazePane.player.x][mazePane.player.y + 1].status == 0) {
                            playWallSound();
                        }
                        break;
                    case A:
                        System.out.println("A");
                        if (mazePane.mazeCreator.maze[mazePane.player.x - 1][mazePane.player.y].status != 0) {
                            mazePane.player.x -= 1;
                            mazePane.mazeCreator.maze[mazePane.player.x][mazePane.player.y].setCenter(mazePane.characterIcon);
                        } else if ((mazePane.player.x - 1 == mazePane.cols - 2) &&
                                (mazePane.player.y == mazePane.rows - 1) &&
                                mazePane.player.itemList[0] == 1) {
                            mazePane.player.x -= 1;
                            gameWin();
                        } else if (mazePane.mazeCreator.maze[mazePane.player.x][mazePane.player.y + 1].status == 0) {
                            playWallSound();
                        }
                        break;
                    case D:
                        System.out.println("D");
                        if (mazePane.mazeCreator.maze[mazePane.player.x + 1][mazePane.player.y].status != 0) {
                            mazePane.player.x += 1;
                            mazePane.mazeCreator.maze[mazePane.player.x][mazePane.player.y].setCenter(mazePane.characterIcon);
                        } else if ((mazePane.player.x + 1 == mazePane.cols - 2) &&
                                (mazePane.player.y == mazePane.rows - 1) &&
                                mazePane.player.itemList[0] == 1) {
                            mazePane.player.x += 1;
                            gameWin();
                        } else if (mazePane.mazeCreator.maze[mazePane.player.x][mazePane.player.y + 1].status == 0) {
                            playWallSound();
                        }
                        break;
                }

                /* If there's property, get the property and change corresponding label. */
                int propType = mazePane.player.getProp();
                switch (propType) {
                    case 1:
                        shovel.setText(Integer.toString(mazePane.player.itemList[1]));
                        break;
                    case 2:
                        blood.setText("Blood left: " + mazePane.player.itemList[3]);
                        break;
                    case 3:
                        cloak.setText(Integer.toString(mazePane.player.itemList[2]));
                        break;
                    case 4:
                        System.out.println("player's blood left:" + mazePane.player.itemList[3]);
                        TrapGamePane trapGamePane = new TrapGamePane();
                        Scene trapGameScene = new Scene(trapGamePane);
                        Stage trapGameStage = new Stage();
                        trapGameStage.setTitle("Trap Game");
                        trapGameStage.initStyle(StageStyle.UNDECORATED);
                        trapGameStage.setScene(trapGameScene);
                        trapGamePane.initTrapGame(root, trapGameStage, status, blood, mazePane.player.itemList, characterType, lostMediaPlayer);
                        break;
                }

                /* If meet a ghost, start the ghost game and remove the ghost. */
                if (mazePane.getGhost(mazePane.player.x, mazePane.player.y) != null && mazePane.player.visible == true) {
                    GhostGamePane ghostGamePane = new GhostGamePane();
                    Scene ghostGameScene = new Scene(ghostGamePane);
                    Stage ghostGameStage = new Stage();
                    ghostGameStage.setTitle("Ghost Game");
                    ghostGameStage.initStyle(StageStyle.UNDECORATED);
                    ghostGameStage.setScene(ghostGameScene);
                    MazePane.Ghost ghost = mazePane.getGhost(mazePane.player.x, mazePane.player.y);
                    if (ghost.equals(mazePane.ghosts[0])) {
                        System.out.println("this ghost has key");
                        ghostGamePane.initGhostGame(root, ghostGameStage, key, status, blood, mazePane.player.itemList, true, lostMediaPlayer);
                    } else {
                        System.out.println("this ghost does not have key");
                        ghostGamePane.initGhostGame(root, ghostGameStage, key, status, blood, mazePane.player.itemList, false, lostMediaPlayer);
                    }
                    ghost.x = 0;
                    ghost.y = 0;
                }
            }

            private void gameWin() {
                mazePane.mazeCreator.maze[mazePane.player.x][mazePane.player.y].setCenter(mazePane.characterIcon);
                status.setText("Player Status: Win");
                winMediaPlayer.play();
                root.addEventFilter(KeyEvent.ANY, KeyEvent::consume); // disable key event
            }
        };


        dragDetector = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Button source = (Button) event.getSource();
                /* drag was detected, start drag-and-drop gesture*/
                System.out.println("onDragDetected");

                /* allow any transfer mode */
                Dragboard db = source.startDragAndDrop(TransferMode.ANY);

                /* put a string on dragboard */
                ClipboardContent content = new ClipboardContent();
                content.putString(source.getText());
                db.setContent(content);

                event.consume();
            }
        };

        dragOverListener = new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Cell target = (Cell) event.getSource();
                /* data is dragged over the target */
                System.out.println("onDragOver");

                if (!event.getDragboard().getString().equals("0") && target.status == 0) {
                    /* allow for both copying and moving, whatever user chooses */
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }

                event.consume();
            }
        };

        dragEnterListener = new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Cell target = (Cell) event.getSource();
                /* the drag-and-drop gesture entered the target */
                System.out.println("onDragEntered");

                if (!event.getDragboard().getString().equals("0") && target.status == 0) {
                    ImageView noBlockView = new ImageView();
                    switch (target.blockType) {
                        // put a cross on the wall
                        case 0:
                            noBlockView = new ImageView(new Image("file:images/tileChoose/no0.png"));
                            break;
                        case 1:
                            noBlockView = new ImageView(new Image("file:images/tileChoose/no1.png"));
                            target.setCenter(noBlockView);
                            break;
                        case 2:
                            noBlockView = new ImageView(new Image("file:images/tileChoose/no2.png"));
                            target.setCenter(noBlockView);
                            break;
                    }
                    noBlockView.setFitHeight(CELLSIZE);
                    noBlockView.setPreserveRatio(true);
                    target.setCenter(noBlockView);
                }

                event.consume();
            }
        };

        dragExitListener = new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Cell target = (Cell) event.getSource();

                if (!event.getDragboard().getString().equals("0") && target.status == 0) {
                    ImageView BlockView = new ImageView();
                    ;
                    switch (target.blockType) {
                        case 0:
                            BlockView = new ImageView(new Image("file:images/tileChoose/0.png"));
                            break;
                        case 1:
                            BlockView = new ImageView(new Image("file:images/tileChoose/1.png"));
                            break;
                        case 2:
                            BlockView = new ImageView(new Image("file:images/tileChoose/2.png"));
                            break;
                    }
                    BlockView.setFitHeight(CELLSIZE);
                    BlockView.setPreserveRatio(true);
                    target.setCenter(BlockView);
                }

                event.consume();
            }
        };

        dragDropper = new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Cell target = (Cell) event.getSource();
                /* data dropped */
                System.out.println("onDragDropped");
                /* if there is a string data on dragboard, read it and use it */
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (!db.getString().equals("0") && target.status == 0) {
                    // set this wall to road
                    target.status = 1;
                    target.setCenter(null);

                    success = true;
                }
                /* let the source know whether the string was successfully
                 * transferred and used */
                event.setDropCompleted(success);

                event.consume();
            }
        };

        dragDoneListener = new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Button source = (Button) event.getSource();
                /* the drag-and-drop gesture ended */
                System.out.println("onDragDone");
                /* if the data was successfully moved, clear it */
                if (event.getTransferMode() == TransferMode.MOVE) {
                    mazePane.player.itemList[1] -= 1;
                    source.setText(Integer.toString(mazePane.player.itemList[1]));
                }

                event.consume();
            }
        };

        shovel.setOnDragDetected(dragDetector);
        shovel.setOnDragDone(dragDoneListener);

        for (int i = 0; i < mazePane.cols; i++) {
            for (int j = 0; j < mazePane.rows; j++) {
                if (mazePane.mazeCreator.maze[i][j].status == 0) {
                    mazePane.mazeCreator.maze[i][j].setOnDragOver(dragOverListener);
                    mazePane.mazeCreator.maze[i][j].setOnDragEntered(dragEnterListener);
                    mazePane.mazeCreator.maze[i][j].setOnDragExited(dragExitListener);
                    mazePane.mazeCreator.maze[i][j].setOnDragDropped(dragDropper);
                }
            }
        }


        cloak.setOnMouseClicked(e -> {
            mazePane.player.itemList[2] -= 1;
            cloak.setText(String.valueOf(mazePane.player.itemList[2]));
            mazePane.player.visible = false;
            System.out.println("player visible status:" + mazePane.player.visible);
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    mazePane.player.visible = true;
                    System.out.println("player visible status:" + mazePane.player.visible);
                    timer.cancel();
                    timer.purge();
                }

            }, 5000);
            FadeTransition ft = new FadeTransition(Duration.seconds(1), mazePane.characterIcon);
            ft.setFromValue(0.1);
            ft.setToValue(1.0);
            ft.setCycleCount(5);
            ft.setAutoReverse(true);
            ft.play();
        });

        home.setOnMouseClicked(e -> {
            root.close();
            Stage newStage = new Stage();
            newStage.setTitle("Tomb-Escape");
            HomePane newHomePane = new HomePane(newStage);
            Scene newHomeScene = new Scene(newHomePane);
            newStage.setScene(newHomeScene);
            newStage.show();
        });

        restart.setOnMouseClicked(e -> {
            root.close();
            Stage newStage = new Stage();
            newStage.setTitle("Tomb-Escape");
            GamePane newGamePane = new GamePane(35, 21, characterType, blockType, newStage);
            Scene newGameScene = new Scene(newGamePane);
            newStage.setScene(newGameScene);
            newStage.show();
            System.out.println("character choose:" + characterType + "tile choose:" + blockType);
        });


    }
}
