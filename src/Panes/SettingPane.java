package Panes;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SettingPane extends VBox{
	Label settingTitle;			
	ImageView[] characterIcon;	
	Label propsHP,propsPRO;				
	Button startBtn;			
	String[][] characterProps = new String[][] {
		{"3 points","1 spade"},
		{"3 points","2 recovery"},
		{"3 points","1 invisible"}
	};						
	
	ToggleGroup modeGroup;
	RadioButton[] modeBtn;

	public SettingPane() {
		
		
		settingTitle = new Label("Settings");
		this.getChildren().add(settingTitle);
		
		
		HBox iconBox = new HBox();
		iconBox.setSpacing(20);
		Image[] imageRead = new Image[3];
		characterIcon = new ImageView[3];
		for (int i=0; i<imageRead.length; i++) {
			imageRead[i] = new Image("file:images/character"+String.valueOf(i));
			characterIcon[i] = new ImageView(imageRead[i]);
			iconBox.getChildren().add(characterIcon[i]);
		}
		this.getChildren().add(iconBox);
		
		
		propsHP = new Label("HP:");
		propsPRO = new Label("PRO");
		this.getChildren().add(propsHP);
		this.getChildren().add(propsPRO);

		
		HBox modeBtnBox = new HBox();
		modeGroup = new ToggleGroup();
		modeBtn = new RadioButton[3];
		modeBtn[0] = new RadioButton("Easy Mode");
		modeBtn[1] = new RadioButton("Medium Mode");
		modeBtn[2] = new RadioButton("Hard Mode");
		for(int i=0; i<modeBtn.length;i++) {
			modeBtn[i].setToggleGroup(modeGroup);
			modeBtnBox.getChildren().add(modeBtn[i]);
		}
		this.getChildren().add(modeBtnBox);
		
		
		startBtn = new Button("Start");
		this.getChildren().add(startBtn);
		
	}
}
