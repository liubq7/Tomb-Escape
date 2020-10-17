package Panes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.shape.Path;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;

public class TrapGamePane extends BorderPane {
    int WIDTH = 600;    //the size of the pane
    int HEIGHT = 600;
    int NUM_BULLETS = 5;    //num of the bullets
    int NUM_HITS = 0;    //num of the user hit the bullets by using the shield
    int NUM_SHIELDS = 4;    //num of shields
    int SHIELD_RADIUS = 60;    //radius or the sheild
    Random RNG = new Random();    //use to control the position of each bullet
    int[][] shieldCenterOffeset = {{0, -SHIELD_RADIUS}, {-SHIELD_RADIUS, 0}, {0, SHIELD_RADIUS}, {SHIELD_RADIUS, 0}};
    List<Arc> shield;
    ImageView character;
    Button startBtn, backBtn;
    HBox btmBar;
    VBox topBar;
    Label gameTitle, subTitle, bulletTitle, hitTitle;

    public TrapGamePane() {
        this.setPrefWidth(WIDTH);
        this.setPrefHeight(HEIGHT);
        this.setPadding(new Insets(20, 20, 20, 20));

        character = new ImageView();
        startBtn = new Button("start");
        backBtn = new Button("back");
        btmBar = new HBox();
        topBar = new VBox();

        gameTitle = new Label("Shield the play from the bullets as many as possible!");
        gameTitle.setFont(new Font("Arial", 21));

        subTitle = new Label("Hover on the 4 direction's shield to hit at least half of the bulltes to win.");
        subTitle.setFont(new Font("Arial", 12));
        subTitle.setPadding(new Insets(5, 0, 20, 0));

        bulletTitle = new Label("Number of bullets: " + NUM_BULLETS);
        bulletTitle.setFont(new Font("Constantia", 20));
        bulletTitle.setTextFill(Color.RED);
        hitTitle = new Label("Number of defend: " + NUM_HITS);
        hitTitle.setFont(new Font("Constantia", 20));
        hitTitle.setTextFill(Color.RED);

        initLayout();    //initialize the whole pane's layout
    }


    private void initLayout() {

        btmBar.setSpacing(20);
        btmBar.getChildren().addAll(startBtn);
        btmBar.setAlignment(Pos.CENTER);
        this.setBottom(btmBar);

        topBar.getChildren().addAll(gameTitle, subTitle, bulletTitle, hitTitle);
        topBar.setPadding(new Insets(0, 0, 20, 0));
        topBar.setAlignment(Pos.CENTER);
        this.setTop(topBar);
    }

    /**
     * @param root:            old windows
     * @param trapGameStage:   window for the trap game
     * @param status:          using to change the player's status, lost if blood=0 after this game
     * @param blood:           the player's blood label
     * @param itemList:        the player's item[3] = blood left
     * @param characterType:   using to change the center image of the pane
     * @param lostMediaPlayer: play the lost music if blood=0 after this game
     */
    void initTrapGame(Stage root, Stage trapGameStage, Label status, Label blood, int[] itemList, int characterType, MediaPlayer lostMediaPlayer) {

        root.hide();    //hide the original game scene
        initCharacter(characterType);    //set the center's player img
        initShield();    //initialize the shield
        trapGameStage.show();
        startBtn.setOnMouseClicked(e -> {
            System.out.println("bloodLeft:" + itemList[3]);
            fireBullet(this, shield);    //play the bullet animation
            btmBar.getChildren().remove(startBtn);    //remove the start button and add the back button
            btmBar.getChildren().add(backBtn);
        });
        backBtn.setOnMouseClicked(e -> {
            EndGame(root, trapGameStage, status, blood, itemList, lostMediaPlayer);
        });
    }


    private void EndGame(Stage root, Stage trapGameStage, Label status, Label blood, int[] itemList, MediaPlayer lostMediaPlayer) {

        if ((NUM_HITS - 2) < NUM_BULLETS) {
            itemList[3]--;    //if hit numbers < bullet numbers, the game lost and blood -1
        }
        System.out.println("bullet nums:" + NUM_BULLETS + " hit nums:" + NUM_HITS);
        System.out.println("bloodLeft:" + itemList[3]);
        blood.setText("Blood left: " + itemList[3]);
        if (itemList[3] == 0) {
            lostMediaPlayer.play();
            root.addEventFilter(KeyEvent.ANY, KeyEvent::consume);    //if blood = 0, then disable the keyboard
            status.setText("Player status: Lost");
        }
        trapGameStage.close();    //close the trap game stage and show main game stage
        root.show();
    }


    private void initCharacter(int characterType) {
        character = new ImageView(new Image("file:images/characterPlay/" + characterType + ".png"));
        character.setFitWidth(40);
        character.setPreserveRatio(true);
        character.setLayoutX(WIDTH / 2 - 20);
        character.setLayoutY(HEIGHT / 2 - 20);
        this.getChildren().add(character);
        shield = new ArrayList<Arc>();
    }

    private void initShield() {
        for (int i = 0; i < NUM_SHIELDS; i++) {
            Arc aShield = new Arc(WIDTH / 2, HEIGHT / 2, SHIELD_RADIUS, SHIELD_RADIUS, 90 * i, 90);
            aShield.setType(ArcType.OPEN);
            aShield.setStroke(Color.TRANSPARENT);
            aShield.setFill(Color.TRANSPARENT);
            setHoverListern(aShield);
            this.getChildren().add(aShield);
            shield.add(aShield);
        }
    }

    private void setHoverListern(Arc arc) {
        arc.setOnMouseEntered(e -> {
            arc.setStroke(Color.GREEN);
        });
        arc.setOnMouseExited(e -> {
            arc.setStroke(Color.TRANSPARENT);    //hovering on a shield will change its color from transparent to green, which means activated
        });
    }

    private void fireBullet(Pane pane, List<Arc> shield) {

        for (int i = 0; i < NUM_BULLETS; i++) {
            Circle bullet = new Circle(2, Color.BLACK);
            pane.getChildren().add(bullet);
            TranslateTransition bulletAnimation = new TranslateTransition(Duration.seconds(3), bullet);
            bulletAnimation.setFromX(RNG.nextInt(WIDTH));
            bulletAnimation.setFromY(RNG.nextInt(HEIGHT));
            bulletAnimation.setToX(WIDTH / 2);
            bulletAnimation.setToY(HEIGHT / 2);
            bullet.boundsInParentProperty().addListener(new ChangeListener<Bounds>() {
                @Override
                public void changed(ObservableValue<? extends Bounds> observable,
                                    Bounds oldValue, Bounds newValue) {
                    for (final Shape target : new ArrayList<Shape>(shield)) {
                        //is the bullet hit the shield and the mouse is hovered on the shield, then the bullet will be stopped
                        if (((Path) Shape.intersect(bullet, target)).getElements().size() > 0 && target.isHover()) {
                            NUM_HITS++;
                            Platform.runLater(() -> hitTitle.setText("Number of defendDD: " + (NUM_HITS)));
                            System.out.println("Hit!" + "NUM_HITS:" + NUM_HITS);
                            bulletAnimation.stop();
                            pane.getChildren().remove(bullet);
                            target.setStroke(Color.RED);
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
            bulletAnimation.playFrom(Duration.seconds(RNG.nextInt(1)));
            ;    //set the bullet play from a random time
        }

        Timer mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (NUM_HITS * 2 < NUM_BULLETS) {
                    Platform.runLater(() -> gameTitle.setText("YOU LOST"));    //after the game finishes, the title result changes
                } else {
                    Platform.runLater(() -> gameTitle.setText("YOU WIN"));
                }
                mTimer.cancel();
                mTimer.purge();
            }

        }, 3500);

    }

}