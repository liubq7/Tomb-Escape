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
	int bloodLeft = 2;
	boolean hasKey = true;
	
	@Override
	public void start(Stage root) {
		VBox home = new VBox();
		Button startGhostGame = new Button("startGhostGame");
		Button startTrapGame = new Button("startTrapGame");
		home.getChildren().addAll(startGhostGame,startTrapGame);
		Scene homeScene = new Scene(home,300,300);
		root.setScene(homeScene);
		root.show();
		startGhostGame.setOnMouseClicked(e->{
			GhostGamePane ghostGamePane = new GhostGamePane();
			Scene ghostGameScene = new Scene(ghostGamePane);
			Stage ghostGameStage = new Stage();
			ghostGameStage.setScene(ghostGameScene);
			ghostGamePane.initGhostGame(ghostGameStage, key, hasKey);
		});
		startTrapGame.setOnMouseClicked(e->{
			TrapGamePane trapGamePane = new TrapGamePane();
			Scene trapGameScene = new Scene(trapGamePane);
			Stage trapGameStage = new Stage();
			trapGameStage.setScene(trapGameScene);
			trapGamePane.initTrapGame(trapGameStage, bloodLeft);
		});
		
	}
	
	public static void main(String[] args) {
		launch(args);

	}

}
