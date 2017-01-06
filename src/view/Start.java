package view;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import logic.GameController;

import java.io.File;
import java.net.MalformedURLException;

/**
 * Created by cjy970910 on 2016/12/18.
 */
public class Start extends Application {

    @Override
    public void start(Stage primaryStage) {
        //title & background
        primaryStage.setTitle("斗兽棋");
        Image imageBackground = new Image("file:pic/start.png");
        GridPane pane = new GridPane();
        pane.getChildren().add(new ImageView(imageBackground));
        pane.setAlignment(Pos.CENTER);
        //button pane
        GridPane buttonPane = new GridPane();
        buttonPane.setAlignment(Pos.TOP_RIGHT);
        buttonPane.setVgap(16);
        buttonPane.setHgap(45);
        buttonPane.setPadding(new Insets(46, 27, 0, 0));
        //start
        Button startButton = new Button("", new ImageView(new Image("file:pic/startgame.png")));
        buttonPane.add(startButton, 1, 0);
        startButton.setOnAction(event -> {
            pane.setVisible(false);
            pane.setMouseTransparent(true);
            View.player = true;
            View start = new View();
            start.start(primaryStage);
        });
        //load
        load(pane, primaryStage,buttonPane);
        //rule
        rules(pane, buttonPane);
        //exit
        Button exitButton = new Button("", new ImageView(new Image("file:pic/exit.png")));
        buttonPane.add(exitButton, 1, 3);
        exitButton.setOnAction(event -> System.exit(0));
        //bgm
        /* String musicPath = null;
        try {
            musicPath = new File("audio/bgm.mp3").toURI().toURL().toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Media bgm = new Media(musicPath);
        MediaPlayer bgmPlayer = new MediaPlayer(bgm);
        bgmPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        bgmPlayer.play();
        */
        //stage
        pane.add(buttonPane, 0, 0);
        primaryStage.setScene(new Scene(pane, 900, 600));
        primaryStage.setResizable(false);
        primaryStage.show();

    }

    //add rule picture to pane
    private void rules(GridPane pane, GridPane buttonPane) {
        //rules
        Label rule1 = new Label("", new ImageView(new Image("file:pic/rule1.png")));
        rule1.setPadding(new Insets(0, 0, 0, 110));
        GridPane.setConstraints(rule1, 0, 0);
        pane.getChildren().add(rule1);
        rule1.setVisible(false);
        //exit rules
        Label rule2 = new Label("", new ImageView(new Image("file:pic/close.png")));
        buttonPane.add(rule2, 0, 0);
        rule2.setVisible(false);
        rule2.setOnMouseClicked(event -> {
            rule1.setVisible(false);
            rule2.setVisible(false);
        });
        //rule button
        Button ruleButton = new Button("", new ImageView(new Image("file:pic/rule.png")));
        buttonPane.add(ruleButton, 1, 2);
        ruleButton.setOnAction(event -> {
            rule1.setVisible(true);
            rule2.setVisible(true);
        });
    }

    //add load picture to pane and setOnAction
    private void load(GridPane pane, Stage primaryStage, GridPane buttonPane) {
        //读档按钮
        Button load = new Button("", new ImageView(new Image("file:pic/load.png")));
        buttonPane.add(load, 1, 1);

        Label saveBackground = new Label("", new ImageView(new Image("file:pic/savePane.png")));
        pane.add(saveBackground, 0, 0);
        saveBackground.setMouseTransparent(true);
        saveBackground.setPadding(new Insets(10, 0, 0, 200));
        saveBackground.setVisible(false);
        GridPane loadPane = new GridPane();
        loadPane.setAlignment(Pos.CENTER);
        loadPane.setVgap(12);
        saveBackground.setVisible(false);
        loadPane.setVisible(false);
        Label save1 = new Label("", new ImageView(new Image("file:pic/save1.png")));
        save1.setPadding(new Insets(120, 0, 0, 0));
        Label save2 = new Label("", new ImageView(new Image("file:pic/save2.png")));
        Label save3 = new Label("", new ImageView(new Image("file:pic/save3.png")));
        Label back = new Label("", new ImageView(new Image("file:pic/close2.png")));
        GridPane.setConstraints(save1, 0, 0);
        GridPane.setConstraints(save2, 0, 1);
        GridPane.setConstraints(save3, 0, 2);
        GridPane.setConstraints(back, 0, 3);
        loadPane.getChildren().add(save1);
        loadPane.getChildren().add(save2);
        loadPane.getChildren().add(save3);
        loadPane.getChildren().add(back);
        pane.add(loadPane, 0, 0);

        load.setOnMouseClicked(event -> {
            saveBackground.setVisible(true);
            loadPane.setVisible(true);
            buttonPane.setMouseTransparent(true);
        });

        back.setOnMouseClicked(event -> {
            saveBackground.setVisible(false);
            loadPane.setVisible(false);
            buttonPane.setMouseTransparent(false);
        });

        save1.setOnMouseClicked(event -> {
            View.save = 1;
            GameController.load(1);
            pane.setVisible(false);
            pane.setMouseTransparent(true);
            View start = new View();
            start.start(primaryStage);
        });

        save2.setOnMouseClicked(event -> {
            View.save = 2;
            GameController.load(2);
            pane.setVisible(false);
            pane.setMouseTransparent(true);
            View start = new View();
            start.start(primaryStage);
        });

        save3.setOnMouseClicked(event -> {
            View.save = 3;
            GameController.load(3);
            pane.setVisible(false);
            pane.setMouseTransparent(true);
            View start = new View();
            start.start(primaryStage);
        });
    }

}
