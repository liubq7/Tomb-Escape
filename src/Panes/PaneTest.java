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

		homePane = new HomePane(root);
		homeScene = new Scene(homePane);
		root.setScene(homeScene);
		root.setTitle("Tomb-Escape");
		root.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}