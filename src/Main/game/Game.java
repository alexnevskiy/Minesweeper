package Main.game;

import Main.game.exceptions.GameLoseException;
import Main.game.exceptions.GameWinException;
import Main.solver.GameState;
import Main.solver.Solver;
import javafx.animation.AnimationTimer;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;

import java.util.Map;

public class Game {

    private static Board board;
    private Solver solver;
    public boolean solve = true;
    AnimationTimer timer;

    public static void create(Pane pane, int width, int height, int mines) {
        board = new Board(width, height, mines);
        board.createBoard(pane);
    }

    public void start(int width, int height, int mines){
        //create(Main.gameLayout);
        solver = new Solver(width, height, mines);

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (solve) {
                    if (!solver.board.endGame) {
                        try {
                            solver.play();
                            //solver.stepPlay();
                        } catch (GameLoseException e) {
                            if (solver.moveCounter == 0) {
                                solver = new Solver(width, height, mines);
                            } else {
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Вы проиграли");
                                alert.setHeaderText("Вы попались на мину");
                                alert.show();
                                solver.state = GameState.lose;
                            }
                        } catch (GameWinException e) {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Вы выиграли");
                            alert.setHeaderText("Поздравляем, вы нашли все мины!");
                            alert.show();
                            solver.state = GameState.win;
                        }
                        solver.board.createBoard(Main.gameLayout);
                    }
                } else {
                    for (Map.Entry<Cell, Label> entry: board.labels.entrySet()) {
                        if (!board.endGame) {
                            entry.getValue().setOnMouseClicked(event -> {
                                if (event.getButton() == MouseButton.PRIMARY) {
                                    try {
                                        board.uncover(entry.getKey().getX(), entry.getKey().getY());
                                    } catch (GameLoseException e) {
                                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                        alert.setTitle("Вы проиграли");
                                        alert.setHeaderText("Вы попались на мину");
                                        alert.showAndWait();
                                    } catch (GameWinException e) {
                                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                        alert.setTitle("Вы выиграли");
                                        alert.setHeaderText("Поздравляем, вы нашли все мины!");
                                        alert.showAndWait();
                                    }
                                }
                                if (event.getButton() == MouseButton.SECONDARY &&
                                        !board.board[entry.getKey().getY()][entry.getKey().getX()].check) {
                                    board.board[entry.getKey().getY()][entry.getKey().getX()].placeFlag();
                                }
                                board.createBoard(Main.gameLayout);
                            });
                        }
                    }
                }
            }
        };
        timer.start();
    }
}
