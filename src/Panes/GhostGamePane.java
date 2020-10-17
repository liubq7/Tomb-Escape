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
import javafx.geometry.Insets;
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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GhostGamePane extends BorderPane {
	Label gameTitle, subTitle, timeTitle, clickTitle;
	Button startBtn, backBtn;
	ImageView monsterImg;
	VBox topBar;
	HBox btmBar;
	int count=0;	// the count of click times
	int timeLeft = 5;	//the whole game's time
	Timer mTimer;


	public GhostGamePane(){
		this.setPrefSize(400, 350);
		this.setPadding(new Insets(20, 20, 20, 20));

		gameTitle = new Label("Click the Ghost as quick as possible!");
		gameTitle.setFont(new Font("Arial", 21));

		subTitle = new Label("Click more than 25 times in 5s or you will lost a blood.");
		subTitle.setFont(new Font("Arial", 12));
		subTitle.setPadding(new Insets(5, 0, 20, 0));

		timeTitle = new Label("Time left: 0s");
		timeTitle.setFont(new Font("Constantia", 20));
		timeTitle.setTextFill(Color.RED);
		clickTitle = new Label("Click times: 0");
		clickTitle.setFont(new Font("Constantia", 20));
		clickTitle.setTextFill(Color.RED);

		topBar = new VBox();
		btmBar = new HBox();
		startBtn = new Button("Start");
		backBtn = new Button("Back");

		monsterImg = new ImageView(new Image("file:images/ghost.png"));
		monsterImg.setFitWidth(100);
		monsterImg.setPreserveRatio(true);
		monsterImg.setDisable(true);	//when initial the ghost image, disable the clicking unless the user click the start button

		initLayout();
	}
	/**
	 * 
	 * @param root: main game's stage
	 * @param ghostGameStage: ghost game's stage
	 * @param key: the key label of the player
	 * @param status: the status of the player lost/going
	 * @param blood: the blood label of the player
	 * @param itemList: itemList[0] indicates the key and itemList[3] indicates the blood
	 * @param hasKey: whether the ghost has a key
	 * @param lostMediaPlayer: if player's blood = 0 after this game, play lost music
	 */

	public void initGhostGame(Stage root, Stage ghostGameStage, Button key, Label status, Label blood, int[] itemList, boolean hasKey, MediaPlayer lostMediaPlayer) {
		
		root.hide();
		ghostGameStage.show();
		monsterImg.setOnMouseClicked(e->{
			count++;
			clickTitle.setText("Click times: " + String.valueOf(count));
		});
		backBtn.setOnMouseClicked(e->{
			EndGame(root,ghostGameStage, key, status, blood, itemList, hasKey,lostMediaPlayer);
		});
		
		//after clicking the start btn, the user can click the ghost and start counting click times
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
						if(count>25) {
							Platform.runLater(()->gameTitle.setText("YOU WIN"));
						}else {
							Platform.runLater(()->gameTitle.setText("YOU LOST"));
						}
						mTimer.cancel();
						mTimer.purge();
					}else {
						Platform.runLater(() -> timeTitle.setText("Time left: " + String.valueOf(timeLeft) + "s"));
					}
				}
				
			}, 100, 1000);

		});

	}
	
	//ghost game ends and get back to the main game
	public void EndGame(Stage root, Stage ghostGameStage, Button key, Label status, Label blood, int[] itemList, boolean hasKey, MediaPlayer lostMediaPlayer) {
		//judge game result. if the ghost has key, player's key + 1
		if(count>25) {
			if(hasKey) {
				itemList[0] = 1;
			}
		} else {
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
		topBar.getChildren().addAll(gameTitle, subTitle, timeTitle,clickTitle);
		topBar.setPadding(new Insets(0, 0, 20, 0));
		topBar.setAlignment(Pos.CENTER);

		btmBar.getChildren().addAll(startBtn);
		btmBar.setAlignment(Pos.CENTER);
		this.setTop(topBar);
		this.setCenter(monsterImg);
		this.setBottom(btmBar);
	}

	
}
