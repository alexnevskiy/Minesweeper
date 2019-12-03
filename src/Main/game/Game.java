package Main.game;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class Game {

    public static void create(Pane pane) {
        Board board = new Board(10, 10, 10);
        board.createBoard(pane);
    }

    public void start(){
        create(Main.gameLayout);
    }
}
