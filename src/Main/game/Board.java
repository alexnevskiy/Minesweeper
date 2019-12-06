package Main.game;

import Main.game.exceptions.GameLoseException;
import Main.game.exceptions.GameWinException;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static javafx.scene.paint.Color.*;

public class Board {
    public Cell[][] board;
    public int width;
    public int height;
    public int emptyCells;
    public int checkCells;
    private List<Cell> minesList = new ArrayList<>();
    public Map<Cell, Label> labels = new HashMap<>();
    public boolean endGame = false;

    public Board(int width, int height, int mines) {
        this.width = width;
        this.height = height;
        this.emptyCells = width * height - mines;
        this.checkCells = 0;
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
            if (!board[randomX][randomY].isMine()) {
                board[randomX][randomY].placeMine();
                minesList.add(board[randomX][randomY]);
            }
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
                            if (!currentNeighbour.isMine() ) {
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
                Label label = new Label();
                if (board[i][j].check && !board[i][j].isFlag()) {
                    int value = board[i][j].getValue();
                    switch (value) {
                        case 0:
                            label.setBackground(new Background(new BackgroundFill(WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
                            break;
                        case 1:
                            label.setText("1");
                            label.setBackground(new Background(new BackgroundFill(WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
                            break;
                        case 2:
                            label.setText("2");
                            label.setBackground(new Background(new BackgroundFill(WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
                            break;
                        case 3:
                            label.setText("3");
                            label.setBackground(new Background(new BackgroundFill(WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
                            break;
                        case 4:
                            label.setText("4");
                            label.setBackground(new Background(new BackgroundFill(WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
                            break;
                        case 5:
                            label.setText("5");
                            label.setBackground(new Background(new BackgroundFill(WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
                            break;
                        case 6:
                            label.setText("6");
                            label.setBackground(new Background(new BackgroundFill(WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
                            break;
                        case 7:
                            label.setText("7");
                            label.setBackground(new Background(new BackgroundFill(WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
                            break;
                        case 8:
                            label.setText("8");
                            label.setBackground(new Background(new BackgroundFill(WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
                            break;
                    }
                } else {
                    if (board[i][j].isFlag()) {
                        label.setBackground(new Background(new BackgroundFill(BLUE, CornerRadii.EMPTY, Insets.EMPTY)));
                    } else {
                        label.setBackground(new Background(new BackgroundFill(GRAY, CornerRadii.EMPTY, Insets.EMPTY)));
                    }
                }
                label.setMinHeight(30);
                label.setMinWidth(30);
                label.setTranslateX(i * 30);
                label.setTranslateY(j * 30);
                labels.put(board[i][j], label);
                pane.getChildren().addAll(label);
            }
        }
        if (endGame) {
            for (Cell mine : minesList) {
                Label label = new Label();
                label.setBackground(new Background(new BackgroundFill(RED, CornerRadii.EMPTY, Insets.EMPTY)));
                label.setMinHeight(30);
                label.setMinWidth(30);
                label.setTranslateX(mine.getY() * 30);
                label.setTranslateY(mine.getX() * 30);
                pane.getChildren().addAll(label);
            }
        }
    }

   public void uncover(int x, int y) throws GameLoseException, GameWinException {
        if (!board[y][x].check) {
            board[y][x].check = true;
            if (board[y][x].isMine()) {
                endGame = true;
                throw new GameLoseException();
            } else {
                checkCells++;
                if (board[y][x].getValue() == 0){
                    Cell[] neighbours = board[y][x].getNeighbours();
                    for (Cell neighbour : neighbours) {
                        if (neighbour.inRange(width, height) &&
                                !board[neighbour.getY()][neighbour.getX()].isMine() &&
                                !board[neighbour.getY()][neighbour.getX()].check) {
                            uncover(neighbour.getX(), neighbour.getY());
                        }
                    }
                }
            }
        }
        if (checkCells == emptyCells) {
            endGame = true;
            throw new GameWinException();
        }
   }
}
