package view;

import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import logic.Board;
import logic.GameController;

/**
 * Created by cjy970910 on 2017/1/3.
 */
public class AIView extends View {


    @Override
    protected void restart(GridPane buttonPane, Stage primaryStage, Timeline timeline, Timeline timeline2) {
        Label restart = new Label("", new ImageView(new Image("file:pic/restart.png")));
        buttonPane.add(restart, 0, 1);
        restart.setOnMouseClicked(event -> {
            stopTimeline(timeline, timeline2);
            timeMeter.stop();
            new Board();
            GameController.clear();
            player = true;
            AIView start = new AIView();
            start.start(primaryStage);
        });
    }

    @Override
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
            AIView newView = new AIView();
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
            end.setTop(rightWin);
            end.setCenter(buttons);
        } else {
            end.setTop(leftWin);
            end.setCenter(buttons);
        }
        return end;
    }

    @Override
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
            animalAction(labelLeft[i], gameController, labelLeft, labelRight, blue, red, gameOver, primaryStage, timeline, timeline2);

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

    @Override
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
                    stopTimeline(timeline, timeline2);
                        gameController.play();
                    if (gameController.ifEnd()) {
                        GameController.clear();
                        timeMeter.stop();
                        setGameOver(primaryStage);
                        gameOver.setVisible(true);
                        gameOver.setMouseTransparent(false);
                    }
                    gameController.AI();
                    if (gameController.ifEnd()) {
                        GameController.clear();
                        timeMeter.stop();
                        setGameOver(primaryStage);
                        gameOver.setVisible(true);
                        gameOver.setMouseTransparent(false);
                    } else {
                        start(primaryStage);
                    }
                });

            }
        });
    }

    @Override
    protected void attention(GridPane attentionPane, Label attentionLeft, Label attentionRight) {
        GridPane.setConstraints(attentionLeft, 0, 0);
        attentionPane.getChildren().add(attentionLeft);
        attentionLeft.setVisible(false);
    }


}
