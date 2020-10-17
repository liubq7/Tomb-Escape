package Panes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/* The setting page. */
public class SettingPane extends VBox {
    Label settingTitle, hpTitle, proTitle;
    ImageView[] characterImg, tileImg;
    Button startBtn;
    String[] hpInfo = new String[]{"2 points", "2 points", "3 points"};
    String[] proInfo = new String[]{"1 spade", "1 invisible coak", "1 more blood"};
    HBox characterBox, tileBox;
    ToggleGroup characterGroup, tileGroup;
    RadioButton[] characterBtn, tileBtn;
    int characterPick, tilePick;  // to store the characterType and blockType the user selected, then pass them to mazepane


    public SettingPane(Stage root) {
        this.setPadding(new Insets(20, 20, 20, 20));

        settingTitle = new Label("Settings");
        settingTitle.setFont(new Font("Arial", 30));
        settingTitle.setPadding(new Insets(0, 0, 20, 0));

        hpTitle = new Label("HP: ");
        proTitle = new Label("Pro: ");

        characterBtn = new RadioButton[3];
        tileBtn = new RadioButton[3];
        characterGroup = new ToggleGroup();
        tileGroup = new ToggleGroup();
        characterBox = new HBox();
        tileBox = new HBox();
        characterImg = new ImageView[3];
        tileImg = new ImageView[3];

        startBtn = new Button("Start");

        initImgBtn();
        initListener(root);
        initLayout();
    }

    private void initLayout() {
        for (int i = 0; i < 3; i++) {
            characterBox.getChildren().add(characterBtn[i]);
            tileBox.getChildren().add(tileBtn[i]);
        }
        this.getChildren().addAll(settingTitle, characterBox, hpTitle, proTitle, tileBox, startBtn);
        characterBox.setAlignment(Pos.CENTER);
        tileBox.setAlignment(Pos.CENTER);
        this.setAlignment(Pos.CENTER);
        this.setSpacing(10);
    }

    private void initListener(Stage root) {
        characterGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov,
                                Toggle old_toggle, Toggle new_toggle) {
                if (characterGroup.getSelectedToggle() != null) {
                    int pickInfo = Integer.valueOf(characterGroup.getSelectedToggle().getUserData().toString());
                    characterPick = pickInfo;
                    hpTitle.setText("Hp: " + hpInfo[pickInfo]);
                    proTitle.setText("Pro: " + proInfo[pickInfo]);
                }
            }
        });
        tileGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov,
                                Toggle old_toggle, Toggle new_toggle) {
                if (tileGroup.getSelectedToggle() != null) {
                    tilePick = Integer.valueOf(tileGroup.getSelectedToggle().getUserData().toString());
                }
            }
        });
        startBtn.setOnMouseClicked(e -> {
            GamePane newGamePane = new GamePane(35, 21, characterPick, tilePick, root);
            Scene newGameScene = new Scene(newGamePane);
            root.setScene(newGameScene);
            System.out.println("character choose:" + characterPick + "tile choose:" + tilePick);
        });
    }


    private void initImgBtn() {
        for (int i = 0; i < 3; i++) {
            characterImg[i] = new ImageView(new Image("file:images/characterPlay/" + String.valueOf(i) + ".png"));
            characterImg[i].setFitHeight(80);
            characterImg[i].setPreserveRatio(true);
            characterBtn[i] = new RadioButton();
            characterBtn[i].setGraphic(characterImg[i]);
            characterBtn[i].setPadding(new Insets(10, 10, 5, 10));
            characterBtn[i].setToggleGroup(characterGroup);
            characterBtn[i].setUserData(String.valueOf(i));  // 0: warrior, 1: priest, 2: defender

            tileImg[i] = new ImageView(new Image("file:images/tileChoose/" + String.valueOf(i) + ".png"));
            tileImg[i].setFitHeight(80);
            tileImg[i].setPreserveRatio(true);
            tileBtn[i] = new RadioButton();
            tileBtn[i].setGraphic(tileImg[i]);
            tileBtn[i].setPadding(new Insets(20, 10, 10, 10));
            tileBtn[i].setToggleGroup(tileGroup);
            tileBtn[i].setUserData(String.valueOf(i));
        }
    }
}
