package Panes;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class GamePane extends BorderPane{
	HBox topBar,btmBar;	//上下分别用两个HBOX放组件
	MazePane mazePane;	//中间是一个gridpane
	Button[] btmBarButton;	//btmBar的按钮组件 0-重启 1-钥匙 2-铲子 3- 隐身衣 4-主页
	ImageView[] btmBarImg;	//btmBar里每一个按钮的图

    private Button key;
    private Button shovel;
    private Button cloak;
    private Button restart;
    private Button home;

    private Label status;
    private Label blood;

    private EventHandler<KeyEvent> keyboardListener;

	
	public GamePane(int x, int y, int characterType) {
		topBar = new HBox();
		btmBar = new HBox();
		mazePane = new MazePane(x, y, characterType);
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
        initListener(this);
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

    private void initListener(GamePane gamePane) {
        keyboardListener = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                int propType = gamePane.mazePane.player.getProp();
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
                }
            }
        };
    }
}
