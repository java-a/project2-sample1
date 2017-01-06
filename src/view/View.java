package view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javafx.scene.control.*;
import javafx.util.Duration;
import logic.Board;
import logic.GameController;

/**
 * Created by cjy970910 on 2016/12/13.
 */
public class View extends Application {
    public static boolean player = true;
    public static int save = 0;
    private static BorderPane timer = new BorderPane();
    private Text timeWholeGame = new Text();
    private int secondsAll = 0;
    private KeyFrame keyFrame = new KeyFrame(Duration.millis(1000), e -> {
        secondsAll++;
        timeAll(timeWholeGame, secondsAll);
    });
    protected Timeline timeMeter = new Timeline(keyFrame);

    @Override
    public void start(Stage primaryStage) {
        GameController gameController;
        if (save == 0) {
            gameController = new GameController();
        } else {
            gameController = new GameController(save, this);
            save = 0;
        }
        //title&background
        primaryStage.setTitle("斗兽棋");
        Image imageBackground = new Image("file:pic/Map3.png");
        GridPane pane = new GridPane();
        //border
        BorderPane borderPane = new BorderPane();
        borderPane.getChildren().add(new ImageView(imageBackground));
        //animal pane
        GridPane animalPane = new GridPane();
        animalPane.setAlignment(Pos.CENTER_LEFT);
        animalPane.setHgap(0);
        animalPane.setVgap(0);
        animalPane.setPadding(new Insets(12, 0, 15, 130));
        //pane
        GridPane functionPane = new GridPane();
        GridPane picturePane = new GridPane();
        GridPane buttonPane = new GridPane();
        GridPane attentionPane = new GridPane();
        attentionPane.setAlignment(Pos.BOTTOM_LEFT);
        functionPane.setAlignment(Pos.TOP_CENTER);
        picturePane.setAlignment(Pos.CENTER);
        buttonPane.setAlignment(Pos.TOP_CENTER);
        buttonPane.setVgap(160);
        buttonPane.setPadding(new Insets(14, 0, 0, 24));
        picturePane.setPadding(new Insets(0, 0, 0, 25));
        functionPane.setPadding(new Insets(15, 0, 0, 10));
        functionPane.setHgap(30);
        //timer
        timer.setTop(timeWholeGame);
        timer.setPadding(new Insets(0, 15, 0, 15));
        GridPane timerPicture = new GridPane();
        ImageView sandClockLeft = new ImageView(new Image("file:pic/timer.gif"));
        ImageView sandClockRight = new ImageView(new Image("file:pic/timer.gif"));
        sandClock(timerPicture, sandClockLeft, sandClockRight);
        if (player) {
            sandClockLeft.setVisible(true);
        } else {
            sandClockRight.setVisible(true);
        }
        //attention
        Label attentionLeft = new Label("", new ImageView(new Image("file:pic/attentionleft.png")));
        Label attentionRight = new Label("", new ImageView(new Image("file:pic/attentionright.png")));
        attention(attentionPane, attentionLeft, attentionRight);
        if (gameController.attention()) {
            if (player) {
                attentionLeft.setVisible(true);
            } else {
                attentionRight.setVisible(true);
            }
        }
        //gameover
        BorderPane gameOver = setGameOver(primaryStage);
        gameOver.setPadding(new Insets(150, 0, 0, 240));
        gameOver.setMouseTransparent(true);
        gameOver.setVisible(false);
        //TIME METER FOR ONE SIDE
        Text timeLeft = new Text("");
        Text timeRight = new Text("");
        final int[] second = {0};
        time(timeLeft, timeRight, timer, second[0]);
        KeyFrame keyFrame2 = new KeyFrame(Duration.millis(1000), e -> {
            second[0]++;
            time(timeLeft, timeRight, timer, second[0]);
        });
        Timeline timeMeterOne = new Timeline(keyFrame2);
        timeMeterOne.setCycleCount(15);
        timeMeterOne.play();
        //overtime
        KeyFrame keyFrame = new KeyFrame(Duration.millis(15000), e -> {
            gameController.play();
            start(primaryStage);
        });
        Timeline timeline = new Timeline(keyFrame);
        timeline.setCycleCount(1);
        timeline.setOnFinished(event -> timeMeterOne.stop());
        timeline.play();
        // total time
        timeMeter.setCycleCount(Timeline.INDEFINITE);
        timeMeter.play();
        //set visible and available
        attentionPane.setMouseTransparent(true);
        picturePane.setMouseTransparent(true);
        buttonPane.setVisible(false);
        buttonPane.setMouseTransparent(true);
        //menu
        Label menu = new Label("", new ImageView(new Image("file:pic/pauseShow.png")));
        menu(picturePane, menu);
        //set labels
        restart(buttonPane, primaryStage, timeline, timeMeterOne);
        exit(buttonPane, animalPane, functionPane, primaryStage, timeline, timeMeterOne);
        pauseAndContinue(buttonPane, animalPane, functionPane, timeline, timeMeterOne, menu);
        help(picturePane, buttonPane);
        save(functionPane, picturePane, animalPane, timeline, timeMeterOne);
        undo(functionPane, picturePane, animalPane, timeline, timeMeterOne, primaryStage);
        //set animals
        showAnimals(animalPane, gameController, primaryStage, gameOver, timeline, timeMeterOne);
        //set the final stage
        borderPane.setTop(functionPane);
        borderPane.setCenter(animalPane);
        pane.add(borderPane, 0, 0);
        pane.add(timer, 0, 0);
        pane.add(picturePane, 0, 0);
        pane.add(buttonPane, 0, 0);
        pane.add(timerPicture, 0, 0);
        pane.add(gameOver, 0, 0);
        pane.add(attentionPane, 0, 0);
        primaryStage.setScene(new Scene(pane, 900, 600));
        primaryStage.show();
    }

    private void sandClock(GridPane timerPicture, ImageView timerLeft, ImageView timerRight) {
        timerLeft.setVisible(false);
        timerRight.setVisible(false);
        timerPicture.setAlignment(Pos.TOP_LEFT);
        timerPicture.setMouseTransparent(true);
        timerPicture.setHgap(710);
        timerPicture.setPadding(new Insets(100, 0, 0, 30));
        GridPane.setConstraints(timerLeft, 0, 0);
        timerPicture.getChildren().add(timerLeft);
        GridPane.setConstraints(timerRight, 1, 0);
        timerPicture.getChildren().add(timerRight);
    }

    private void timeAll(Text timeWholeGame, int seconds) {
        BorderPane.setAlignment(timer.getTop(), Pos.CENTER);
        int minute = 0;
        int second;
        while (true) {
            if (seconds >= 60) {
                seconds = seconds - 60;
                minute++;
            } else {
                second = seconds;
                break;
            }
        }
        if (minute >= 10 && second < 10) {
            timeWholeGame.setText("  " + minute + ":0" + second);
        } else if (minute >= 10 && second >= 10) {
            timeWholeGame.setText("  " + minute + ":" + second);
        } else if (minute < 10 && second < 10) {
            timeWholeGame.setText("  0" + minute + ":0" + second);
        } else {
            timeWholeGame.setText("  0" + minute + ":" + second);
        }
    }

    private void time(Text timeLeft, Text timeRight, BorderPane timer, int second) {
        timeLeft.setVisible(false);
        timeRight.setVisible(false);
        timer.setMouseTransparent(true);
        timer.setLeft(timeLeft);
        timer.setRight(timeRight);
        timeLeft.setFont(Font.font(60));
        timeRight.setFont(Font.font(60));
        if (player) {
            if (second > 5) {
                timeLeft.setText("0" + (15 - second));
            } else {
                timeLeft.setText("" + (15 - second));
            }
            timeLeft.setVisible(true);
        } else {
            if (second > 5) {
                timeRight.setText("0" + (15 - second));
            } else {
                timeRight.setText("" + (15 - second));
            }
            timeRight.setVisible(true);
        }

    }

    private void help(GridPane picturePane, GridPane buttonPane) {
        Label help = new Label("", new ImageView(new Image("file:pic/help.png")));
        buttonPane.add(help, 1, 1);
        ImageView rules = new ImageView(new Image("file:pic/rule3.png"));
        rules.setVisible(false);
        rules.setOnMouseClicked(event -> {
            rules.setVisible(false);
            buttonPane.setVisible(true);
            buttonPane.setMouseTransparent(false);
            picturePane.setMouseTransparent(true);
        });
        GridPane.setConstraints(rules, 0, 0);
        picturePane.getChildren().add(rules);

        help.setOnMouseClicked(event -> {
            rules.setVisible(true);
            buttonPane.setVisible(false);
            buttonPane.setMouseTransparent(true);
            picturePane.setMouseTransparent(false);
        });
    }

    private void menu(GridPane picturePane, Label menu) {
        menu.setPadding(new Insets(0, 0, 0, 103));
        GridPane.setConstraints(menu, 0, 0);
        picturePane.getChildren().add(menu);
        menu.setMouseTransparent(true);
        menu.setVisible(false);
    }

    protected void restart(GridPane buttonPane, Stage primaryStage, Timeline timeline, Timeline timeline2) {
        Label restart = new Label("", new ImageView(new Image("file:pic/restart.png")));
        buttonPane.add(restart, 0, 1);
        restart.setOnMouseClicked(event -> {
            stopTimeline(timeline, timeline2);
            timeMeter.stop();
            new Board();
            GameController.clear();
            player = true;
            View start = new View();
            start.start(primaryStage);
        });
    }

    private void exit(GridPane buttonPane, GridPane animalsPane, GridPane functionPane, Stage primaryStage, Timeline timeline, Timeline timeline2) {
        Label exit = new Label("", new ImageView(new Image("file:pic/exitbutton.png")));
        buttonPane.add(exit, 2, 1);
        exit.setOnMouseClicked(event -> {
            stopTimeline(timeline, timeline2);
            timeMeter.stop();
            GameController.clear();
            animalsPane.setVisible(false);
            animalsPane.setMouseTransparent(true);
            functionPane.setVisible(false);
            functionPane.setMouseTransparent(true);
            new Board();
            Start start = new Start();
            start.start(primaryStage);
        });
    }

    private void pauseAndContinue(GridPane buttonPane, GridPane animalsPane, GridPane functionPane, Timeline timeline, Timeline timeline2, Label menu) {
        Label pause = new Label("", new ImageView(new Image("file:pic/pause.png")));
        functionPane.add(pause, 1, 0);
        Label returnButton = new Label("", new ImageView(new Image("file:pic/return.png")));
        buttonPane.add(returnButton, 1, 0);
        returnButton.setVisible(false);
        returnButton.setMouseTransparent(true);
        returnButton.setOnMouseClicked(event -> {
            playTimeline(timeline, timeline2);
            timeMeter.play();
            menu.setVisible(false);
            buttonPane.setVisible(false);
            returnButton.setVisible(false);
            returnButton.setMouseTransparent(true);
            pause.setVisible(true);
            buttonPane.setMouseTransparent(true);
            animalsPane.setMouseTransparent(false);
        });
        pause.setOnMouseClicked(event -> {
            pauseTimeline(timeline, timeline2);
            timeMeter.pause();
            menu.setVisible(true);
            buttonPane.setVisible(true);
            returnButton.setVisible(true);
            returnButton.setMouseTransparent(false);
            pause.setVisible(false);
            buttonPane.setMouseTransparent(false);
            animalsPane.setMouseTransparent(true);
        });
    }

    private void undo(GridPane functionPane, GridPane picturePane, GridPane animalPane, Timeline timeline, Timeline timeline2, Stage primaryStage) {
        Label undo = new Label("", new ImageView(new Image("file:pic/undo.png")));
        functionPane.add(undo, 0, 0);
        //undo request
        VBox undoRequest = new VBox();
        undoRequest.setPadding(new Insets(50, 0, 0, 50));
        Label ask = new Label("", new ImageView(new Image("file:pic/ask.png")));
        HBox choose = new HBox();
        choose.setPadding(new Insets(0, 0, 0, 30));
        choose.setSpacing(180);
        Label yes = new Label("", new ImageView(new Image("file:pic/yes.png")));
        Label no = new Label("", new ImageView(new Image("file:pic/no.png")));
        choose.getChildren().add(0, yes);
        choose.getChildren().add(1, no);
        undoRequest.getChildren().add(0, ask);
        undoRequest.getChildren().add(1, choose);
        picturePane.add(undoRequest, 0, 0);
        undoRequest.setVisible(false);

        undo.setOnMouseClicked(event -> {
            pauseTimeline(timeline, timeline2);
            timeMeter.pause();
            picturePane.setMouseTransparent(false);
            animalPane.setMouseTransparent(true);
            undoRequest.setVisible(true);
        });

        yes.setOnMouseClicked(event -> {
            stopTimeline(timeline, timeline2);
            timeMeter.play();
            undoRequest.setVisible(false);
            picturePane.setMouseTransparent(true);
            animalPane.setMouseTransparent(false);
            GameController.undo();
            start(primaryStage);
        });

        no.setOnMouseClicked(event -> {
            playTimeline(timeline, timeline2);
            timeMeter.play();
            animalPane.setMouseTransparent(false);
            picturePane.setMouseTransparent(true);
            undoRequest.setVisible(false);
        });
    }

    private void save(GridPane functionPane, GridPane picturePane, GridPane animalPane, Timeline timeline, Timeline timeline2) {
        Label save = new Label("", new ImageView(new Image("file:pic/save.png")));
        functionPane.add(save, 2, 0);
        Label saveBackground = new Label("", new ImageView(new Image("file:pic/savePane.png")));
        picturePane.add(saveBackground, 0, 0);
        saveBackground.setVisible(false);
        GridPane savePane = new GridPane();
        savePane.setAlignment(Pos.CENTER);
        savePane.setVgap(12);
        savePane.setVisible(false);
        Label save1 = new Label("", new ImageView(new Image("file:pic/save1.png")));
        save1.setPadding(new Insets(120, 0, 0, 0));
        Label save2 = new Label("", new ImageView(new Image("file:pic/save2.png")));
        Label save3 = new Label("", new ImageView(new Image("file:pic/save3.png")));
        Label back = new Label("", new ImageView(new Image("file:pic/close2.png")));
        GridPane.setConstraints(save1, 0, 0);
        GridPane.setConstraints(save2, 0, 1);
        GridPane.setConstraints(save3, 0, 2);
        GridPane.setConstraints(back, 0, 3);
        savePane.getChildren().add(save1);
        savePane.getChildren().add(save2);
        savePane.getChildren().add(save3);
        savePane.getChildren().add(back);
        picturePane.add(savePane, 0, 0);

        save.setOnMouseClicked(event -> {
            pauseTimeline(timeline, timeline2);
            timeMeter.pause();
            animalPane.setMouseTransparent(true);
            picturePane.setMouseTransparent(false);
            saveBackground.setVisible(true);
            savePane.setVisible(true);
        });
        back.setOnMouseClicked(event -> {
            playTimeline(timeline, timeline2);
            timeMeter.play();
            animalPane.setMouseTransparent(false);
            picturePane.setMouseTransparent(true);
            saveBackground.setVisible(false);
            savePane.setVisible(false);
        });
        save1.setOnMouseClicked(event -> {
            GameController.save(1, this);
            playTimeline(timeline, timeline2);
            timeMeter.play();
            animalPane.setMouseTransparent(false);
            picturePane.setMouseTransparent(true);
            saveBackground.setVisible(false);
            savePane.setVisible(false);
        });
        save2.setOnMouseClicked(event -> {
            GameController.save(2, this);
            playTimeline(timeline, timeline2);
            timeMeter.play();
            animalPane.setMouseTransparent(false);
            picturePane.setMouseTransparent(true);
            saveBackground.setVisible(false);
            savePane.setVisible(false);
        });
        save3.setOnMouseClicked(event -> {
            GameController.save(3, this);
            playTimeline(timeline, timeline2);
            timeMeter.play();
            animalPane.setMouseTransparent(false);
            picturePane.setMouseTransparent(true);
            saveBackground.setVisible(false);
            savePane.setVisible(false);
        });
    }

    protected BorderPane setGameOver(Stage primaryStage) {
        BorderPane end = new BorderPane();
        GridPane buttons = new GridPane();
        Label leftWin = new Label("", new ImageView(new Image("file:pic/leftwin.png")));
        Label rightWin = new Label("", new ImageView(new Image("file:pic/rightwin.png")));
        Label newGame = new Label("", new ImageView(new Image("file:pic/newgame.png")));
        Label back = new Label("", new ImageView(new Image("file:pic/back.png")));

        newGame.setOnMouseClicked(event -> {
            new Board();
            player = true;
            View newView = new View();
            newView.start(primaryStage);
        });

        back.setOnMouseClicked(event -> {
            new Board();
            player = true;
            Start start = new Start();
            start.start(primaryStage);
        });

        GridPane.setConstraints(newGame, 0, 0);
        buttons.getChildren().add(newGame);
        GridPane.setConstraints(back, 1, 0);
        buttons.getChildren().add(back);

        if (player) {
            end.setTop(leftWin);
            end.setCenter(buttons);
        } else {
            end.setTop(rightWin);
            end.setCenter(buttons);
        }
        return end;
    }

    protected void showAnimals(GridPane animalPane, GameController gameController, Stage primaryStage, BorderPane gameOver, Timeline timeline, Timeline timeline2) {
        //animals label
        Label[] labelLeft = new Label[9];
        Label[] labelRight = new Label[9];
        Label[][] blue = new Label[7][9];
        Label[][] red = new Label[7][9];
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 9; j++) {
                blue[i][j] = new Label("", new ImageView(new Image("file:pic/animals/left/0.png")));
                blue[i][j].setVisible(false);
                blue[i][j].setMouseTransparent(true);
                red[i][j] = new Label("", new ImageView(new Image("file:pic/animals/left/red.png")));
                red[i][j].setVisible(false);
                red[i][j].setMouseTransparent(true);
            }
        }
        for (int i = 1; i < 9; i++) {
            Image imageAnimalLeft = new Image("file:pic/animals/left/" + i + ".png");
            Image imageAnimalRight = new Image("file:pic/animals/right/" + i + ".png");
            labelLeft[i] = new Label("", new ImageView(imageAnimalLeft));
            labelRight[i] = new Label("", new ImageView(imageAnimalRight));
            if (player) {
                animalAction(labelLeft[i], gameController, labelLeft, labelRight, blue, red, gameOver, primaryStage, timeline, timeline2);
            } else {
                animalAction(labelRight[i], gameController, labelLeft, labelRight, blue, red, gameOver, primaryStage, timeline, timeline2);
            }
        }
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 9; j++) {
                GridPane.setConstraints(blue[i][j], j, i);
                animalPane.getChildren().add(blue[i][j]);
                GridPane.setConstraints(red[i][j], j, i);
                animalPane.getChildren().add(red[i][j]);
                if (Board.animalBoard[i][j].getCamp() == -1) {
                    GridPane.setConstraints(labelLeft[Board.animalBoard[i][j].getValue()], j, i);
                    animalPane.getChildren().add(labelLeft[Board.animalBoard[i][j].getValue()]);
                } else if (Board.animalBoard[i][j].getCamp() == 1) {
                    GridPane.setConstraints(labelRight[Board.animalBoard[i][j].getValue()], j, i);
                    animalPane.getChildren().add(labelRight[Board.animalBoard[i][j].getValue()]);
                }
            }
        }
    }

    protected void animalAction(Label label, GameController gameController, Label[] labelLeft, Label[] labelRight, Label[][] blue, Label[][] red, BorderPane gameOver, Stage primaryStage, Timeline timeline, Timeline timeline2) {
        label.setOnMouseEntered(event -> {
            int x = (int) ((label.getLayoutY() - 12) / 72);
            int y = (int) ((label.getLayoutX() - 130) / 72);
            red[x][y].setVisible(true);
        });

        label.setOnMouseExited(event -> {
            int x = (int) ((label.getLayoutY() - 12) / 72);
            int y = (int) ((label.getLayoutX() - 130) / 72);
            red[x][y].setVisible(false);
        });

        label.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int x = (int) ((label.getLayoutY() - 12) / 72);
                int y = (int) ((label.getLayoutX() - 130) / 72);
                red[x][y].setVisible(true);
                label.setOnMouseExited(event12 -> {
                });
                gameController.setAnimal(x, y);
                for (int i = 1; i < 9; i++) {
                    labelLeft[i].setMouseTransparent(true);
                    labelRight[i].setMouseTransparent(true);
                }
                label.setMouseTransparent(false);
                //direction
                try {
                    showPossibleMove(x, y);
                } catch (Exception e) {
                }
                //cancel the selection
                label.setOnMouseClicked(event1 -> {
                    red[x][y].setVisible(false);
                    for (int i = 1; i < 9; i++) {
                        labelLeft[i].setMouseTransparent(false);
                        labelRight[i].setMouseTransparent(false);
                    }
                    for (int i = 0; i < 7; i++) {
                        for (int j = 0; j < 9; j++) {
                            blue[i][j].setMouseTransparent(true);
                            blue[i][j].setVisible(false);
                        }
                    }
                    label.setOnMouseExited(event2 -> red[x][y].setVisible(false));
                    label.setOnMouseClicked(event11 -> animalAction(label, gameController, labelLeft, labelRight, blue, red, gameOver, primaryStage, timeline, timeline2));
                });
            }

            private void showPossibleMove(int x, int y) throws Exception {
                int[] directionUp = {-1, 0};
                int[] directionDown = {1, 0};
                int[] directionLeft = {0, -1};
                int[] directionRight = {0, 1};
                if (Board.animalBoard[x][y].move(directionUp)) {
                    setClicks(blue[x + directionUp[0]][y + directionUp[1]], -1, 0);
                    blue[x + directionUp[0]][y + directionUp[1]].setVisible(true);
                }
                if (Board.animalBoard[x][y].move(directionDown)) {
                    setClicks(blue[x + directionDown[0]][y + directionDown[1]], 1, 0);
                    blue[x + directionDown[0]][y + directionDown[1]].setVisible(true);
                }
                if (Board.animalBoard[x][y].move(directionLeft)) {
                    setClicks(blue[x + directionLeft[0]][y + directionLeft[1]], 0, -1);
                    blue[x + directionLeft[0]][y + directionLeft[1]].setVisible(true);
                }
                if (Board.animalBoard[x][y].move(directionRight)) {
                    setClicks(blue[x + directionRight[0]][y + directionRight[1]], 0, 1);
                    blue[x + directionRight[0]][y + directionRight[1]].setVisible(true);
                }
            }

            private void setClicks(Label label, int x, int y) {
                label.setMouseTransparent(false);
                label.setOnMouseClicked(event -> {
                    gameController.setDirection(x, y);
                    gameController.play();
                    stopTimeline(timeline, timeline2);
                    if (gameController.ifEnd()) {
                        GameController.clear();
                        timeMeter.stop();
                        gameOver.setVisible(true);
                        gameOver.setMouseTransparent(false);
                    } else {
                        start(primaryStage);
                    }
                });

            }
        });
    }

    protected void attention(GridPane attentionPane, Label attentionLeft, Label attentionRight) {
        GridPane.setConstraints(attentionLeft, 0, 0);
        GridPane.setConstraints(attentionRight, 1, 0);
        attentionPane.setHgap(640);
        attentionPane.getChildren().add(attentionLeft);
        attentionPane.getChildren().add(attentionRight);
        attentionLeft.setVisible(false);
        attentionRight.setVisible(false);
    }

    protected void stopTimeline(Timeline timeline1, Timeline timeline2) {
        timeline1.stop();
        timeline2.stop();
    }

    private void pauseTimeline(Timeline timeline1, Timeline timeline2) {
        timeline1.pause();
        timeline2.pause();
    }

    private void playTimeline(Timeline timeline1, Timeline timeline2) {
        timeline1.play();
        timeline2.play();
    }

    public int getSecondsAll() {
        return secondsAll;
    }

    public void setSecondsAll(int secondsAll) {
        this.secondsAll = secondsAll;
    }
}
