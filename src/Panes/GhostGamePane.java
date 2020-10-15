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
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
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
	 * 
	 */
	public GhostGamePane(){
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
		//initGhostGame(ghostGameStage, itemKey, hasKey);
	}

	public void initGhostGame(Stage root, Stage ghostGameStage, Button key, Label status, Label blood, int[] itemList, boolean hasKey, MediaPlayer lostMediaPlayer) {
		
		root.hide();
		ghostGameStage.show();
		monsterImg.setOnMouseClicked(e->{
			count++;
			clickTitle.setText("Click times:" + String.valueOf(count));
		});
		//点击返回按钮将会回到原来的界面
		backBtn.setOnMouseClicked(e->{
			EndGame(root,ghostGameStage, key, status, blood, itemList, hasKey,lostMediaPlayer);
		});
		
		//按钮点击后中间的小鬼可以点击，并且计算点击次数
		startBtn.setOnAction(e -> {
			System.out.println("key owns:"+ itemList[0]);
			btmBar.getChildren().remove(startBtn);
			btmBar.getChildren().add(backBtn);
			monsterImg.setDisable(false);
			mTimer = new Timer();
			mTimer.scheduleAtFixedRate(new TimerTask() {
				@Override
				public void run() {
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
	public void EndGame(Stage root, Stage ghostGameStage, Button key, Label status, Label blood, int[] itemList, boolean hasKey, MediaPlayer lostMediaPlayer) {
		//先判断游戏赢了还是输了,再判断有无钥匙
		if(count>25) {
			if(hasKey) {
				itemList[0] = 1;
			}else {
				itemList[0] = 0;
			}
		}else {
			itemList[3]--;
			blood.setText("Blood left:"+ String.valueOf(itemList[3]));
			System.out.println("you lost, key owns: "+ itemList[0] + "Blood left: "+ itemList[3]);
		}

		key.setText(String.valueOf(itemList[0]));
		if(itemList[3] == 0) {
			lostMediaPlayer.play();
			root.addEventFilter(KeyEvent.ANY, KeyEvent::consume);	//游戏输了不能再走了
			status.setText("Player status: lost");
		}
		ghostGameStage.close();
		root.show();
	}

	private void initLayout() {
		topBar.getChildren().addAll(gameTitle,timeTitle,clickTitle);
		btmBar.getChildren().addAll(startBtn);
		this.setTop(topBar);
		this.setCenter(monsterImg);
		this.setBottom(btmBar);
		btmBar.setAlignment(Pos.CENTER);
	}

	
}
