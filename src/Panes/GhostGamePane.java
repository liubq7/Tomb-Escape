package Panes;

import java.util.Timer;
import java.util.TimerTask;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GhostGamePane extends BorderPane {
	Label gameTitle, timeTitle,clickTitle, monsterPic;
	Button startBtn, backBtn;
	ImageView monsterImg;
	VBox topBar;
	HBox btmBar;
	int count=0;	//计算点按次
	int timeLeft = 5;
	Timer mTimer;
	/*
	 * 传参传进来的stage 和 homeScene 是为了游戏结束返回， key是来自homePane 的参数
	 */
	public GhostGamePane(Stage stage,int key, Scene homeScene){
		this.setPrefSize(300, 300);
		gameTitle = new Label("Try to click the Ghost as quick as possible!");
		timeTitle = new Label("5s");
		clickTitle = new Label("Click times:");
		topBar = new VBox();
		btmBar = new HBox();
		startBtn = new Button("Start");
		backBtn = new Button("Back");
		monsterImg = new ImageView(new Image("file:images/ghost.png"));
		monsterImg.setFitWidth(100);
		monsterImg.setPreserveRatio(true);
		monsterImg.setDisable(true);
		initLayout();
		initGhostGame(stage, key, homeScene);
	}

	private void initGhostGame(Stage stage, int key, Scene homeScene) {
		
		monsterImg.setOnMouseClicked(e->{
			count++;
			clickTitle.setText("Click times:" + String.valueOf(count));
		});
		//点击返回按钮将会回到原来的界面
		backBtn.setOnMouseClicked(e->{
			EndGame(stage,key,homeScene);
		});
		
		//按钮点击后中间的小鬼可以点击，并且计算点击次数
		startBtn.setOnAction(e -> {
			btmBar.getChildren().remove(startBtn);
			monsterImg.setDisable(false);
			mTimer = new Timer();
			mTimer.scheduleAtFixedRate(new TimerTask() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					timeLeft--;
					if(timeLeft<0) {
						monsterImg.setDisable(true);
						mTimer.cancel();
						mTimer.purge();
					}else {
						Platform.runLater(() -> timeTitle.setText("time left: "+String.valueOf(timeLeft)));
					}
				}
				
			}, 100, 1000);

		});

	}
	
	//打鬼游戏结束，回到原来的scene
	private void EndGame(Stage stage, int key, Scene homeScene) {
		// TODO Auto-generated method stub
		//先判断游戏赢了还是输了
		if(count>25) {
			key = 1;
			System.out.println("you win "+ key);
		}else {
			System.out.println("you lost "+ key);
			key = 0;
		}
		stage.setScene(homeScene);
	}

	private void initLayout() {
		// TODO Auto-generated method stub
		topBar.getChildren().addAll(gameTitle,timeTitle,clickTitle);
		btmBar.getChildren().addAll(startBtn, backBtn);
		this.setTop(topBar);
		this.setCenter(monsterImg);
		this.setBottom(btmBar);
		btmBar.setAlignment(Pos.CENTER);
	}
	
}