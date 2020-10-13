package Panes;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.awt.Event;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;

public class SettingPane extends VBox{
	Label settingTitle,characterTitle,hpTitle,proTitle,tileTitle;			
	ImageView[] characterImg,tileImg;					
	Button startBtn;			
	String[] hpInfo = new String[] {"3 points","5 points","3points"};
	String[] proInfo = new String[] {"1 spade","2 more blood points","1 invisible coak"};
	HBox characterBox,tileBox;
	ToggleGroup characterGroup,tileGroup;	//单选按钮组
	RadioButton[] characterBtn,tileBtn;		//单选按钮数组
	int characterPick,tilePick;	//用来保存最终用户选择了哪一个角色/哪一种墙背景 然后传递给mazepane

	/**
	 * 
	 * @param stage 用来切换场景和scene
	 * @param scene
	 */
	public SettingPane(Stage root) {
		
		
		settingTitle = new Label("Settings");
		settingTitle.setFont(new Font("Arial", 30));
		characterTitle = new Label("Character: ");
		hpTitle = new Label("HP: ");
		proTitle = new Label("Pro: ");
		tileTitle = new Label("Tile: ");
		characterBtn = new RadioButton[3];
		tileBtn = new RadioButton[3];
		characterGroup = new ToggleGroup();
		tileGroup = new ToggleGroup();
		characterBox = new HBox();
		tileBox = new HBox();
		characterImg = new ImageView[3];
		tileImg = new ImageView[3];
		startBtn = new Button("Start");
		
		initImgBtn();	//把每个单选按钮加到对应的Toggle Group 里并且加上图片
		initListener(root);	//把每个单选按钮的事件注册
		initLayout();	//把这个Pane的摆放规整
	}

	private void initLayout() {
		// TODO Auto-generated method stub
		for(int i=0; i<3; i++) {
			characterBox.getChildren().add(characterBtn[i]);
			tileBox.getChildren().add(tileBtn[i]);
		}
		this.getChildren().addAll(settingTitle, characterBox, hpTitle, proTitle, tileBox,startBtn);
		characterBox.setAlignment(Pos.CENTER);
		tileBox.setAlignment(Pos.CENTER);
		this.setAlignment(Pos.CENTER);
		this.setSpacing(10);
	}

	private void initListener(Stage root) {
		//如果那个单选按钮被选中，对应的characterPick, tilePick 就等于 0/1/2, 同时更新hp和pro栏的文字
		characterGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
		    public void changed(ObservableValue<? extends Toggle> ov,
		        Toggle old_toggle, Toggle new_toggle) {
		            if (characterGroup.getSelectedToggle() != null) {
		            	int pickInfo = Integer.valueOf(characterGroup.getSelectedToggle().getUserData().toString());
		            	characterPick = pickInfo;
		            	hpTitle.setText("Hp: "+ hpInfo[pickInfo]);
		            	proTitle.setText("Pro: "+proInfo[pickInfo]);
		            }                
		        }
		});
		tileGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
		    public void changed(ObservableValue<? extends Toggle> ov,
		        Toggle old_toggle, Toggle new_toggle) {
		            if (tileGroup.getSelectedToggle() != null) {
		            	tilePick = Integer.valueOf(tileGroup.getSelectedToggle().getUserData().toString());
		            }                
		        }
		});
		startBtn.setOnMouseClicked(e->{
			GamePane newGamePane = new GamePane(35,21,characterPick,tilePick);
			Scene newGameScene = new Scene(newGamePane);
			root.setScene(newGameScene);
			System.out.println("character choose:" + characterPick + "tile choose:" + tilePick);
		});
	}


	private void initImgBtn() {
		// TODO Auto-generated method stub
		for(int i=0;i<3;i++) {
			characterImg[i] = new ImageView(new Image("file:images/characterPlay/"+ String.valueOf(i)+".png"));
			characterImg[i].setFitHeight(80);
			characterImg[i].setPreserveRatio(true);
			characterBtn[i] = new RadioButton();
			characterBtn[i].setGraphic(characterImg[i]);
			characterBtn[i].setToggleGroup(characterGroup);
			characterBtn[i].setUserData(String.valueOf(i));	//保留数据用于选中时分析 0-战士 1-护士 2-卫士
			
			tileImg[i] = new ImageView(new Image("file:images/tileChoose/"+ String.valueOf(i)+".png"));
			tileImg[i].setFitHeight(80);
			tileImg[i].setPreserveRatio(true);
			tileBtn[i] = new RadioButton();
			tileBtn[i].setGraphic(tileImg[i]);
			tileBtn[i].setToggleGroup(tileGroup);
			tileBtn[i].setUserData(String.valueOf(i));
		}
	}
}
