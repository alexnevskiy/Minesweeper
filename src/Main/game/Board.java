package Main.game;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;

import static javafx.scene.paint.Color.RED;
import static javafx.scene.paint.Color.WHITE;

public class Board {
    private Cell[][] board;
    private int mines;
    public int width;
    public int height;

    public Board(int width, int height, int mines) {
        this.width = width;
        this.height = height;
        this.mines = mines;
        generateBoard(width, height, mines);
    }

    private void generateBoard(int width, int height, int mines) {
        board = new Cell[width][height];
        if (mines > width * height) throw new IllegalArgumentException("Слишком много мин для данного поля");

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                board[i][j] = new Cell(i, j);
            }
        }

        for (int i = 0; i < mines; i++) {
            int randomX = (int) (Math.random() * width);
            int randomY = (int) (Math.random() * height);
            if (!board[randomX][randomY].isMine()) board[randomX][randomY].placeMine(true);
            else i--;
        }
        updateValues();
    }

    private void updateValues() {
        for (Cell[] row : board) {
            for (Cell cell : row) {
                if (cell.isMine()) {
                    Cell[] neighbours = cell.getNeighbours();
                    for (Cell neighbour : neighbours) {
                        Cell currentNeighbour;
                        if (neighbour.inRange(width, height)) {
                            currentNeighbour = board[neighbour.getY()][neighbour.getX()];
                            if (!currentNeighbour.isMine()) {
                                currentNeighbour.setValue(currentNeighbour.getValue() + 1);
                            }
                        }
                    }
                }
            }
        }
    }

    public void createBoard(Pane pane) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int value = board[i][j].getValue();
                Label label = new Label();
                switch (value) {
                    case -1:
                        label.setBackground(new Background(new BackgroundFill(RED, CornerRadii.EMPTY, Insets.EMPTY)));
                        break;
                    case 0:
                        label.setBackground(new Background(new BackgroundFill(WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
                        break;
                    case 1:
                        label.setText("1");
                        break;
                    case 2:
                        label.setText("2");
                        break;
                    case 3:
                        label.setText("3");
                        break;
                    case 4:
                        label.setText("4");
                        break;
                    case 5:
                        label.setText("5");
                        break;
                    case 6:
                        label.setText("6");
                        break;
                    case 7:
                        label.setText("7");
                        break;
                    case 8:
                        label.setText("8");
                        break;
                }
                label.setMinHeight(30);
                label.setMinWidth(30);
                label.setTranslateX(i * 30);
                label.setTranslateY(j * 30);
                pane.getChildren().addAll(label);
            }
        }
    }
}
