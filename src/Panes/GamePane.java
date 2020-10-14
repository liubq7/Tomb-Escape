package Panes;

import Maze.Cell;
import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
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
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import static Panes.MazePane.CELLSIZE;

import java.util.Timer;
import java.util.TimerTask;

public class GamePane extends BorderPane{
	HBox topBar,btmBar;	//上下分别用两个HBOX放组件
	MazePane mazePane;	//中间是一个gridpane
	Button[] btmBarButton;	//btmBar的按钮组件 0-重启 1-钥匙 2-铲子 3- 隐身衣 4-主页
	ImageView[] btmBarImg;	//btmBar里每一个按钮的图
	int cols,rows;

    private Button key;
    private Button shovel;
    private Button cloak;
    Button restart;
    Button home;

    private Label status;
    private Label blood;

    private EventHandler<KeyEvent> keyboardListener;
    private EventHandler<MouseEvent> dragDetector;
    private EventHandler<DragEvent> dragOverListener;
    private EventHandler<DragEvent> dragEnterListener;
    private EventHandler<DragEvent> dragExitListener;
    private EventHandler<DragEvent> dragDropper;
    private EventHandler<DragEvent> dragDoneListener;

	
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

        status = new Label("Play status:");
        blood = new Label("Blood left: " + mazePane.player.itemList[3]);

		iniTopBar();	//把topBar安置好
		initBtmBar();	//把btmBar安置好
		initCenter();	//把中间的mazepane安置好
		initWholePane();	//把整个Pane上的组件摆上去

        this.setFocusTraversable(true);
        initListener(root,characterType,blockType);
        this.setOnKeyPressed(keyboardListener);
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

		for(int i=0; i<btmBarButton.length; i++) {
			btmBarImg[i] = new ImageView(new Image("file:images/btmBarImg/" + String.valueOf(i) + ".png"));
			btmBarImg[i].setFitHeight(30);
			btmBarImg[i].setPreserveRatio(true);
			btmBarButton[i].setGraphic(btmBarImg[i]);
			btmBarButton[i].setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
			if(i>=1 && i<=3) {
				btmBarButton[i].setText(Integer.toString(mazePane.player.itemList[i - 1]));
			}
			btmBar.getChildren().add(btmBarButton[i]);
		}
		btmBar.setAlignment(Pos.CENTER);
		btmBar.setPadding(new Insets(10,10,10,10));
		btmBar.setSpacing(10);
	}

	private void iniTopBar() {
        topBar.getChildren().addAll(status, blood);
		topBar.setPadding(new Insets(10,10,10,10));
		topBar.setSpacing(50);
		topBar.setAlignment(Pos.CENTER);
		
	}

    private void initListener(Stage root, int characterType, int blockType) {
        keyboardListener = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                // TODO: 不能走的地方给予声音提示？
                switch (keyEvent.getCode()) {
                    case S:
                        System.out.println("S");
                        if (mazePane.mazeCreator.maze[mazePane.player.x][mazePane.player.y + 1].status != 0) {
                            mazePane.player.y += 1;
                            mazePane.mazeCreator.maze[mazePane.player.x][mazePane.player.y].setCenter(mazePane.characterIcon);
                        }else if((mazePane.player.x == mazePane.cols-2)&&(mazePane.player.y+1 == mazePane.rows-1)&&mazePane.player.itemList[0]==1) {
                        	mazePane.player.y += 1;
                        	gameWin();
                        }
                        break;
                    case W:
                        System.out.println("W");
                        if (mazePane.mazeCreator.maze[mazePane.player.x][mazePane.player.y - 1].status != 0) {
                            mazePane.player.y -= 1;
                            mazePane.mazeCreator.maze[mazePane.player.x][mazePane.player.y].setCenter(mazePane.characterIcon);
                        }else if((mazePane.player.x == mazePane.cols-2)&&(mazePane.player.y-1 == mazePane.rows-1)&&mazePane.player.itemList[0]==1) {
                        	mazePane.player.y -= 1;
                        	gameWin();
                        }
                        break;
                    case A:
                        System.out.println("A");
                        if (mazePane.mazeCreator.maze[mazePane.player.x - 1][mazePane.player.y].status != 0) {
                            mazePane.player.x -= 1;
                            mazePane.mazeCreator.maze[mazePane.player.x][mazePane.player.y].setCenter(mazePane.characterIcon);
                        }else if((mazePane.player.x-1 == mazePane.cols-2)&&(mazePane.player.y == mazePane.rows-1)&&mazePane.player.itemList[0]==1) {
                        	mazePane.player.x -= 1;
                        	gameWin();
                        }
                        break;
                    case D:
                        System.out.println("D");
                        if (mazePane.mazeCreator.maze[mazePane.player.x + 1][mazePane.player.y].status != 0) {
                            mazePane.player.x += 1;
                            mazePane.mazeCreator.maze[mazePane.player.x][mazePane.player.y].setCenter(mazePane.characterIcon);
                        }else if((mazePane.player.x+1 == mazePane.cols-2)&&(mazePane.player.y == mazePane.rows-1)&&mazePane.player.itemList[0]==1) {
                        	mazePane.player.x += 1;
                        	gameWin();
                        }
                        break;
                }

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
                        trapGamePane.initTrapGame(trapGameStage, blood, mazePane.player.itemList);
                        break;
                }

                if (mazePane.getGhost(mazePane.player.x, mazePane.player.y) != null) {
                    // 如果遇到了鬼
                    GhostGamePane ghostGamePane = new GhostGamePane();
                    Scene ghostGameScene = new Scene(ghostGamePane);
                    Stage ghostGameStage = new Stage();
                    ghostGameStage.setTitle("Ghost Game");
                    ghostGameStage.initStyle(StageStyle.UNDECORATED);
                    ghostGameStage.setScene(ghostGameScene);
                    MazePane.Ghost ghost = mazePane.getGhost(mazePane.player.x, mazePane.player.y);
                    if (ghost.equals(mazePane.ghosts[0])) {
                        System.out.println("this ghost has key");
                        ghostGamePane.initGhostGame(ghostGameStage, key, blood, mazePane.player.itemList, true);
                    } else {
                        System.out.println("this ghost does not have key");
                        ghostGamePane.initGhostGame(ghostGameStage, key, blood, mazePane.player.itemList, false);
                    }
                    ghost.x = 0;
                    ghost.y = 0;
                }
            }

			private void gameWin() {
				mazePane.mazeCreator.maze[mazePane.player.x][mazePane.player.y].setCenter(mazePane.characterIcon);
				status.setText("Player Status: Win");
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
                    ImageView BlockView = new ImageView();;
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
        
        //隐身衣的监听器
        cloak.setOnMouseClicked(e->{
        	mazePane.player.visible = false;
        	Timer timer = new Timer();
        	timer.schedule(new TimerTask() {
				@Override
				public void run() {
					mazePane.player.visible = true;
				}
        		
        	}, 5000);
            FadeTransition ft = new FadeTransition(Duration.seconds(1), mazePane.characterIcon);
            ft.setFromValue(0.1);
            ft.setToValue(1.0);
            ft.setCycleCount(5);
            ft.setAutoReverse(true);
            ft.play();
        	timer.cancel();
        	timer.purge();
        });
        
        home.setOnMouseClicked(e->{
        	HomePane newHomePane = new HomePane(root);
        	Scene newHomeScene = new Scene(newHomePane);
        	root.setScene(newHomeScene);
        });
        
        restart.setOnMouseClicked(e->{
        	GamePane newGamePane = new GamePane(35,21,characterType,blockType,root);
			Scene newGameScene = new Scene(newGamePane);
			root.setScene(newGameScene);
			System.out.println("character choose:" + characterType+ "tile choose:" + blockType);
        });

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

    }
}
