package Panes;



import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;

public class HomePane extends BorderPane {

	Label homeTitle;	
	ImageView gameLogo;		
	Button newgameBtn;	
	
	public HomePane() {
		
		
		homeTitle = new Label("Tomb Escape");
		homeTitle.setFont(Font.font("Arial",30));
		this.setTop(homeTitle);
		
		
		Image iconImage = new Image("file:images/graveyard.png");
		gameLogo = new ImageView(iconImage);
		this.setCenter(gameLogo);
		
		
		newgameBtn = new Button("New Game");
		this.setBottom(newgameBtn);
		
		this.setAlignment(homeTitle, Pos.CENTER);
		this.setAlignment(newgameBtn, Pos.CENTER);
	}
}
