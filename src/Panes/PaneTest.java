package Panes;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PaneTest extends Application {

	@Override
	public void start(Stage root) {

		HomePane homePane = new HomePane();
		SettingPane settingPane = new SettingPane();

		GamePane gamePane = new GamePane(35,21,0);
		Scene homeScene = new Scene(homePane);

		Scene settingScene = new Scene(settingPane);
		Scene mazeScene = new Scene(gamePane);

		root.setScene(mazeScene);
		root.setTitle("Tomb-Escape");
		root.show();
		homePane.newgameBtn.setOnMouseClicked(e->{
			root.setScene(settingScene);
		});
	}

	
	public static void main(String[] args) {
		launch(args);
	}

}