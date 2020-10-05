package Maze;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SettingPane extends VBox{
	Label settingTitle;			//主标题
	ImageView[] characterIcon;	//每一个角色的icon
	Label propsHP,propsPRO;				//角色属性
	Button startBtn;			//游戏开始按钮
	String[][] characterProps = new String[][] {
		{"3 points","1 spade"},
		{"3 points","2 recovery"},
		{"3 points","1 invisible"}
	};							//每一个角色的属性表
	
	ToggleGroup modeGroup;
	RadioButton[] modeBtn;

	public SettingPane() {
		
		//放在头部的标题
		settingTitle = new Label("Settings");
		this.getChildren().add(settingTitle);
		
		//读取图片资源放在一个HBox里,然后把HBox放在title下
		HBox iconBox = new HBox();
		iconBox.setSpacing(20);
		Image[] imageRead = new Image[3];		
		for (int i=0; i<imageRead.length; i++) {
			imageRead[i] = new Image("character"+String.valueOf(i));
			characterIcon[i].setImage(imageRead[i]);
			iconBox.getChildren().add(characterIcon[i]);
		}
		this.getChildren().add(iconBox);
		
		//把HP和PRO两个标签加入,悬停时更新
		propsHP = new Label("HP:");
		propsPRO = new Label("PRO");
		this.getChildren().add(propsHP);
		this.getChildren().add(propsPRO);

		//加入模式选择单选按钮,togglegroup 没办法直接加所以只能定义一个HBOX在这里
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
		
		//加入游戏开始按钮
		startBtn = new Button("Start");
		this.getChildren().add(startBtn);
		
	}
}
