package Panes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.animation.TranslateTransition;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.shape.Path;
import javafx.scene.shape.Shape;

public class TrapGamePane extends BorderPane{
    int WIDTH = 600 ;	//整个pane的长宽
    int HEIGHT = 600 ;
    int NUM_BULLETS = 5 ;	//子弹数量
    int NUM_HITS = 0;	//被挡下来的次数
    int NUM_SHIELDS = 4;	//盾的数量
    int SHIELD_RADIUS = 60 ;	//盾的半径
    Random RNG = new Random();    //用来随机控制子弹飞的时间
    int[][] shieldCenterOffeset = {{0,-SHIELD_RADIUS},{-SHIELD_RADIUS,0},{0,SHIELD_RADIUS},{SHIELD_RADIUS,0}};
    List<Arc> shield;
    ImageView character;
    Button startBtn,backBtn;
    HBox btmBar;

    
    public TrapGamePane() {
    	this.setPrefWidth(WIDTH);
    	this.setPrefHeight(HEIGHT);
    	character = new ImageView(new Image("file:images/ghost.png"));	//把人的icon放中间
    	startBtn = new Button("start");
    	backBtn = new Button("back");
    	btmBar = new HBox();
    	initCharacter();	//把character图像位置调整好
    	initShield();	//把盾初始化
    }


    void initTrapGame(Stage trapGameStage,int bloodLeft) {
		trapGameStage.show();
    	btmBar.setSpacing(20);
		btmBar.getChildren().addAll(startBtn, backBtn);
		btmBar.setAlignment(Pos.CENTER);
		this.setBottom(btmBar);
		startBtn.setOnMouseClicked(e->{
			System.out.println("bloodLeft:"+ bloodLeft);
			fireBullet(this,shield,bloodLeft);	//让子弹飞
	    	btmBar.getChildren().remove(startBtn);	//只能按一次
		});
		backBtn.setOnMouseClicked(e->{

			trapGameStage.close();	//回到迷宫界面
			System.out.println("bloodLeft:"+ bloodLeft);
		});
	}


	private void initCharacter() {
    	character.setFitWidth(40);
    	character.setPreserveRatio(true);
    	character.setLayoutX(WIDTH/2 - 20);
    	character.setLayoutY(HEIGHT/2 -20);
    	this.getChildren().add(character);
    	shield = new ArrayList<Arc>();
	}



	private void initShield() {
    	for(int i=0; i< NUM_SHIELDS;i++) {
    		Arc aShield = new Arc( WIDTH/2,HEIGHT/2,SHIELD_RADIUS,SHIELD_RADIUS,90*i,90);
            aShield.setType(ArcType.OPEN);
            aShield.setStroke(Color.TRANSPARENT);
            aShield.setFill(Color.TRANSPARENT);
        	setHoverListern(aShield);
        	this.getChildren().add(aShield);
        	shield.add(aShield);
    	}
	}


	private void fireBullet(Pane pane, List<Arc> shield, int bloodLeft) {
		
    	for(int i=0; i<NUM_BULLETS; i++) {
        	Circle bullet = new Circle(2, Color.BLACK);
    		pane.getChildren().add(bullet);
    		TranslateTransition bulletAnimation = new TranslateTransition(Duration.seconds(5),bullet);
    		bulletAnimation.setFromX(RNG.nextInt(WIDTH));
    		bulletAnimation.setFromY(RNG.nextInt(HEIGHT));
    		bulletAnimation.setToX(WIDTH/2);
    		bulletAnimation.setToY(HEIGHT/2);
    		bullet.boundsInParentProperty().addListener(new ChangeListener<Bounds>() {
                @Override
                public void changed(ObservableValue<? extends Bounds> observable,
                        Bounds oldValue, Bounds newValue) {
                    for (final Shape target : new ArrayList<Shape>(shield)) {
                        if (((Path)Shape.intersect(bullet, target)).getElements().size() > 0 && target.isHover()) {
                        	NUM_HITS++;
                        	System.out.println("Hit!" + "NUM_HITS:"+ NUM_HITS);
                            bulletAnimation.stop();
                            pane.getChildren().remove(bullet);
                            target.setStroke(Color.RED);	//判断接到的条件是需要有碰撞且 盾上面有鼠标hover在
                        }
                    }
                }
    		});
    	    bulletAnimation.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    pane.getChildren().remove(bullet);
                }
            }); 
    		bulletAnimation.playFrom(Duration.seconds(RNG.nextInt(1)));;	//让子弹从某一任意时刻开始飞
    	}

    	if(NUM_HITS < NUM_BULLETS) {
    		bloodLeft--;	//如果接到的子弹数小于设置的子弹数，就会让血-1
    	}
	}


	private void setHoverListern(Arc arc) {
		arc.setOnMouseEntered(e->{
			arc.setStroke(Color.GREEN);
		});
		arc.setOnMouseExited(e->{
			arc.setStroke(Color.TRANSPARENT);
		});
	}


    
}