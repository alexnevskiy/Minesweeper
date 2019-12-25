package Main.game;

import Main.solver.Solver;
import javafx.animation.AnimationTimer;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;

import java.util.Map;

class Game {

    private static Board board;
    private Solver solver;
    boolean solve;
    boolean step;
    private AnimationTimer timer;

    private static void create(Pane pane, int width, int height, int mines) {
        board = new Board(width, height, mines);
        board.createBoard(pane);
    }

    void start(int width, int height, int mines){
        create(Main.gameLayout, width, height, mines);
        solver = new Solver(width, height, mines);
        System.out.println();
        System.out.println("Началась новая игра!");

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (solve) {
                    if (step) {
                        Main.stepButton.setOnAction(e -> {
                            if (!solver.board.endGame) {
                                solver.play();
                                if (solver.board.state == GameState.lose) {
                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    alert.setTitle("Вы проиграли");
                                    alert.setHeaderText("Вы попались на мину");
                                    alert.show();
                                } else if (solver.board.state == GameState.win){
                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    alert.setTitle("Вы выиграли");
                                    alert.setHeaderText("Поздравляем, вы нашли все мины!");
                                    alert.show();
                                }
                                solver.board.createBoard(Main.gameLayout);
                            }
                        });
                    } else {
                        if (!solver.board.endGame) {
                            solver.play();
                            if (solver.board.state == GameState.lose) {
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Вы проиграли");
                                alert.setHeaderText("Вы попались на мину");
                                alert.show();
                            } else if (solver.board.state == GameState.win){
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Вы выиграли");
                                alert.setHeaderText("Поздравляем, вы нашли все мины!");
                                alert.show();
                            }
                            solver.board.createBoard(Main.gameLayout);
                        }
                    }
                } else {
                    for (Map.Entry<Cell, Label> entry: board.labels.entrySet()) {
                        if (!board.endGame) {
                            entry.getValue().setOnMouseClicked(event -> {
                                if (event.getButton() == MouseButton.PRIMARY &&
                                        !board.board[entry.getKey().getY()][entry.getKey().getX()].isFlag()) {
                                    board.uncover(entry.getKey().getX(), entry.getKey().getY());
                                    if (board.state == GameState.lose) {
                                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                        alert.setTitle("Вы проиграли");
                                        alert.setHeaderText("Вы попались на мину");
                                        alert.show();
                                    } else if (board.state == GameState.win){
                                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                        alert.setTitle("Вы выиграли");
                                        alert.setHeaderText("Поздравляем, вы нашли все мины!");
                                        alert.show();
                                    }
                                }
                                if (event.getButton() == MouseButton.SECONDARY &&
                                        !board.board[entry.getKey().getY()][entry.getKey().getX()].check) {
                                    if (board.board[entry.getKey().getY()][entry.getKey().getX()].isFlag()) {
                                        board.board[entry.getKey().getY()][entry.getKey().getX()].removeFlag();
                                    } else {
                                        board.board[entry.getKey().getY()][entry.getKey().getX()].placeFlag();
                                    }
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
