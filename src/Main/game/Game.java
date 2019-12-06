package Main.game;

import Main.game.exceptions.GameLoseException;
import Main.game.exceptions.GameWinException;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;

import java.util.Map;

import static javafx.scene.paint.Color.BLUE;
import static javafx.scene.paint.Color.GRAY;

public class Game {

    private static Board board;
    AnimationTimer timer;

    public static void create(Pane pane) {
        board = new Board(100, 100, 10);
        board.createBoard(pane);
    }

    public void start(){
        create(Main.gameLayout);

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
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
        };
        timer.start();
    }
}
