package Panes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class HomePane extends BorderPane {

	ImageView gameLogo;		
	Button newgameBtn;	
	
	public HomePane(Stage root) {
		this.setPadding(new Insets(0, 0, 20, 0));
		
		Image iconImage = new Image("file:images/graveyard.png");
		gameLogo = new ImageView(iconImage);
		gameLogo.setFitWidth(600);
		gameLogo.setPreserveRatio(true);
		this.setCenter(gameLogo);

		newgameBtn = new Button("New Game");
		this.setBottom(newgameBtn);
		newgameBtn.setOnMouseClicked(e->{
			SettingPane settingPane = new SettingPane(root);
			Scene settingScene = new Scene(settingPane);
			root.setScene(settingScene);
		});

		this.setAlignment(newgameBtn, Pos.CENTER);
	}
}
