package Panes;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GhostAndTrapPaneTest extends Application {
	int key = 0;
	int result = 0;
	
	@Override
	public void start(Stage root) {
		// TODO Auto-generated method stub
		VBox home = new VBox();
		Button startGhostGame = new Button("startGhostGame");
		Button startTrapGame = new Button("startTrapGame");
		home.getChildren().addAll(startGhostGame,startTrapGame);
		Scene homeScene = new Scene(home,300,300);
		root.setScene(homeScene);
		root.show();
		startGhostGame.setOnMouseClicked(e->{
			GhostGamePane ghostGamePane = new GhostGamePane(root,key,homeScene);
			Scene ghostGameScene = new Scene(ghostGamePane);
			root.setScene(ghostGameScene);
		});
		startTrapGame.setOnMouseClicked(e->{
			TrapGamePane trapGamePane = new TrapGamePane(root,homeScene,result);
			Scene trapGameScene = new Scene(trapGamePane);
			root.setScene(trapGameScene);
		});
		
	}
	
	public static void main(String[] args) {
		launch(args);

	}

}
