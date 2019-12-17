package Main.game;

import Main.solver.GameState;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {
    public Cell[][] board;
    public int width;
    public int height;
    public int emptyCells;
    public int checkCells;
    public List<Cell> minesList = new ArrayList<>();
    public Map<Cell, Label> labels = new HashMap<>();
    public boolean endGame = false;
    public GameState state;

    public Board(int width, int height, int mines) {
        this.width = width;
        this.height = height;
        this.emptyCells = width * height - mines;
        this.checkCells = 0;
        this.state = GameState.launching;
        generateBoard(width, height, mines);
    }

    private void generateBoard(int width, int height, int mines) {
        board = new Cell[height][width];
        if (mines > width * height) throw new IllegalArgumentException("Слишком много мин для данного поля");

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                board[i][j] = new Cell(j, i);
            }
        }

        for (int i = 0; i < mines; i++) {
            int randomX = (int) (Math.random() * width);
            int randomY = (int) (Math.random() * height);
            if (!board[randomY][randomX].isMine()) {
                board[randomY][randomX].placeMine();
                minesList.add(board[randomY][randomX]);
            }
            else i--;
        }
        updateValues();
    }

    public void updateValues() {
        for (Cell[] row : board) {
            for (Cell cell : row) {
                if (cell.isMine()) {
                    Point[] neighbours = Point.getNeighbours(cell.getX(), cell.getY());
                    for (Point neighbour : neighbours) {
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

    public void resetValues() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j].getValue() > 0) {
                    board[i][j].setValue(0);
                }
            }
        }
    }

    public void createBoard(Pane pane) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Label label = new Label();
                if (board[i][j].check && !board[i][j].isFlag()) {
                    int value = board[i][j].getValue();
                    switch (value) {
                        case 0:
                            label.setStyle("-fx-background-color: white; -fx-border-color: black;");
                            break;
                        case 1:
                            label.setText("1");
                            label.setAlignment(Pos.CENTER);
                            label.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-text-fill: blue;" +
                                    " -fx-font-size: 1.5em; -fx-font-weight: bold;");
                            break;
                        case 2:
                            label.setText("2");
                            label.setAlignment(Pos.CENTER);
                            label.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-text-fill: green;" +
                                    " -fx-font-size: 1.5em; -fx-font-weight: bold;");
                            break;
                        case 3:
                            label.setText("3");
                            label.setAlignment(Pos.CENTER);
                            label.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-text-fill: red;" +
                                    " -fx-font-size: 1.5em; -fx-font-weight: bold;");
                            break;
                        case 4:
                            label.setText("4");
                            label.setAlignment(Pos.CENTER);
                            label.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-text-fill: navy;" +
                                    " -fx-font-size: 1.5em; -fx-font-weight: bold;");
                            break;
                        case 5:
                            label.setText("5");
                            label.setAlignment(Pos.CENTER);
                            label.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-text-fill: maroon;" +
                                    " -fx-font-size: 1.5em; -fx-font-weight: bold;");
                            break;
                        case 6:
                            label.setText("6");
                            label.setAlignment(Pos.CENTER);
                            label.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-text-fill: aqua;" +
                                    " -fx-font-size: 1.5em; -fx-font-weight: bold;");
                            break;
                        case 7:
                            label.setText("7");
                            label.setAlignment(Pos.CENTER);
                            label.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-text-fill: fuchsia;" +
                                    " -fx-font-size: 1.5em; -fx-font-weight: bold;");
                            break;
                        case 8:
                            label.setText("8");
                            label.setAlignment(Pos.CENTER);
                            label.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-text-fill: black;" +
                                    " -fx-font-size: 1.5em; -fx-font-weight: bold;");
                            break;
                    }
                } else {
                    if (board[i][j].isFlag()) {
                        label.setStyle("-fx-background-color: blue; -fx-border-color: black;");
                    } else {
                        label.setStyle("-fx-background-color: gray; -fx-border-color: black;");
                    }
                }
                label.setMinHeight(30);
                label.setMinWidth(30);
                label.setTranslateX(j * 30);
                label.setTranslateY(i * 30);
                labels.put(board[i][j], label);
                pane.getChildren().addAll(label);
            }
        }
        if (endGame) {
            for (Cell mine : minesList) {
                Label label = new Label();
                if (mine.isFlag()) {
                    label.setStyle("-fx-background-color: purple; -fx-border-color: black;");
                } else {
                    label.setStyle("-fx-background-color: red; -fx-border-color: black;");
                }
                label.setMinHeight(30);
                label.setMinWidth(30);
                label.setTranslateX(mine.getX() * 30);
                label.setTranslateY(mine.getY() * 30);
                pane.getChildren().addAll(label);
            }
        }
    }

    public void uncover(int x, int y) {
        if (!board[y][x].check) {
            board[y][x].check = true;
            if (board[y][x].isMine() && checkCells != 0) {
                endGame = true;
                state = GameState.lose;
                return;
            } else if (board[y][x].isMine() && checkCells == 0) {
                System.out.println("Перегенерировалось поле на клетке с координатами х: " + x + ", y: " + y);
                board[y][x].removeMine();
                minesList.remove(board[y][x]);
                boolean isSetMine = false;
                for (int i = 0; i < height; i++) {
                    for (int j = 0; j < width; j++) {
                        if (!board[i][j].isMine() && (j != x || i != y)) {
                            board[i][j].placeMine();
                            minesList.add(board[i][j]);
                            isSetMine = true;
                            break;
                        }
                    }
                    if (isSetMine) break;
                }
                resetValues();
                updateValues();
                uncover(x, y);
            } else {
                checkCells++;
                if (board[y][x].getValue() == 0){
                    Point[] neighbours = Point.getNeighbours(x, y);
                    for (Point neighbour : neighbours) {
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
            state = GameState.win;
        }
    }

    public int[][] getBoard() {
        int[][] gettingBoard = new int[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j].check) {
                    gettingBoard[i][j] = board[i][j].getValue();
                } else {
                    gettingBoard[i][j] = -1;
                }
            }
        }
        return gettingBoard;
    }
}