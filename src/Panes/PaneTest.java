package Panes;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PaneTest extends Application {

	@Override
	public void start(Stage root) {

		HomePane homePane = new HomePane();
<<<<<<< HEAD
=======
		SettingPane settingPane = new SettingPane();

		GamePane gamePane = new GamePane(35,21,0);
>>>>>>> branch 'master' of https://github.com/liubq7/Tomb-Escape
		Scene homeScene = new Scene(homePane);
		
		GamePane gamePane = new GamePane(35,21,2);
		Scene mazeScene = new Scene(gamePane);
		
		SettingPane settingPane = new SettingPane(root,mazeScene);
		Scene settingScene = new Scene(settingPane);
		

		root.setScene(homeScene);
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