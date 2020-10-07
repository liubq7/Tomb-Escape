package Panes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class GamePane extends BorderPane{
	HBox topBar,btmBar;	//上下分别用两个HBOX放组件
	MazePane mazePane;	//中间是一个gridpane
	Label[] topBarLabel;	//topBar的组件 0-"status:" 2-"blood" 1,3为状态
	Button[] btmBarButton;	//btmBar的按钮组件 0-重启 1-钥匙 2-铲子 3- 隐身衣 4-主页
	ImageView[] btmBarImg;	//btmBar里每一个按钮的图
	
	public GamePane(int x, int y, int cellsize) {
		topBar = new HBox();
		btmBar = new HBox();
		mazePane = new MazePane(x,y,cellsize);
		topBarLabel = new Label[4];
		btmBarButton = new Button[5];
		btmBarImg = new ImageView[5];
		
		iniTopBar();	//把topBar安置好
		initBtmBar();	//把btmBar安置好
		initCenter();	//把中间的mazepane安置好
		initWholePane();	//把整个Pane上的组件摆上去
	}

	private void initCenter() {
		// TODO Auto-generated method stub
		mazePane.setAlignment(Pos.CENTER);
	}

	private void initWholePane() {
		// TODO Auto-generated method stub
		this.setTop(topBar);
		this.setCenter(mazePane);
		this.setBottom(btmBar);
	}

	private void initBtmBar() {
		// TODO Auto-generated method stub
		for(int i=0; i<btmBarButton.length; i++) {
			btmBarImg[i] = new ImageView(new Image("file:images/btmBarImg/" + String.valueOf(i) + ".png"));
			btmBarImg[i].setFitHeight(30);
			btmBarImg[i].setPreserveRatio(true);
			btmBarButton[i] = new Button("", btmBarImg[i]);
			btmBarButton[i].setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
			if(i>=1 && i<=3) {
				btmBarButton[i].setText("0");
			}
			btmBar.getChildren().add(btmBarButton[i]);
		}
		btmBar.setAlignment(Pos.CENTER);
		btmBar.setPadding(new Insets(10,10,10,10));
		btmBar.setSpacing(10);
	}

	private void iniTopBar() {
		// TODO Auto-generated method stub
		topBarLabel[0] = new Label("Play status:");
		topBarLabel[1] = new Label("");
		topBarLabel[2] = new Label("Blood left:");
		topBarLabel[3] = new Label("");
		topBar.setAlignment(Pos.CENTER);
		for(int i=0; i<topBarLabel.length; i++) {
			topBar.getChildren().add(topBarLabel[i]);
		}
		topBar.setPadding(new Insets(10,10,10,10));
		topBar.setSpacing(50);
		topBar.setAlignment(Pos.CENTER);
		
	}
	
	
}
