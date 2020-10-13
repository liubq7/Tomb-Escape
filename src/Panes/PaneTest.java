package Panes;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PaneTest extends Application {

	HomePane homePane;
	Scene homeScene;
	SettingPane settingPane;
	Scene settingScene;
	
	@Override
	public void start(Stage root) {

		homePane = new HomePane();
		homeScene = new Scene(homePane);
				
		settingPane = new SettingPane(root);
		settingScene = new Scene(settingPane);
		

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