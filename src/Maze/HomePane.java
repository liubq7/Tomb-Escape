package Maze;



import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;

public class HomePane extends BorderPane {

	Label homeTitle;		//用于显示界面上的标题
	ImageView gameLogo;		//用于显示游戏logo
	Button newgameBtn;		//开始新游戏按钮
	
	public HomePane() {
		
		//顶端放标题	
		homeTitle = new Label("Tomb Escape");
		homeTitle.setFont(Font.font("Arial",30));
		this.setTop(homeTitle);
		
		//中间放logo
		Image iconImage = new Image("imgs/tomb.jpg");
		gameLogo.setImage(iconImage);
		this.setCenter(gameLogo);
		
		//底部放开始按钮
		newgameBtn = new Button("New Game");
		this.setBottom(newgameBtn);
	}
}
