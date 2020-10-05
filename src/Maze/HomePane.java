package Maze;



import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;

public class HomePane extends BorderPane {

	Label homeTitle;		//������ʾ�����ϵı���
	ImageView gameLogo;		//������ʾ��Ϸlogo
	Button newgameBtn;		//��ʼ����Ϸ��ť
	
	public HomePane() {
		
		//���˷ű���	
		homeTitle = new Label("Tomb Escape");
		homeTitle.setFont(Font.font("Arial",30));
		this.setTop(homeTitle);
		
		//�м��logo
		Image iconImage = new Image("imgs/tomb.jpg");
		gameLogo.setImage(iconImage);
		this.setCenter(gameLogo);
		
		//�ײ��ſ�ʼ��ť
		newgameBtn = new Button("New Game");
		this.setBottom(newgameBtn);
	}
}
