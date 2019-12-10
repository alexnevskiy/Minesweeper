package Main.game;

import Main.game.exceptions.GameLoseException;
import Main.game.exceptions.GameWinException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
    public List<Cell> minesList = new ArrayList<>();
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

//        board[0][0].placeMine();
//        minesList.add(board[0][0]);
//
//        board[1][4].placeMine();
//        minesList.add(board[1][4]);
//
//        board[5][6].placeMine();
//        minesList.add(board[5][6]);
//
//        board[5][7].placeMine();
//        minesList.add(board[5][7]);
//
//        board[8][8].placeMine();
//        minesList.add(board[8][8]);
//
//        board[3][6].placeMine();
//        minesList.add(board[3][6]);
//
//        board[0][7].placeMine();
//        minesList.add(board[0][7]);
//
//        board[1][7].placeMine();
//        minesList.add(board[1][7]);
//
//        board[2][8].placeMine();
//        minesList.add(board[2][8]);
//
//        board[5][5].placeMine();
//        minesList.add(board[5][5]);


//        board[0][16].placeMine();
//        minesList.add(board[0][16]);
//
//        board[1][0].placeMine();
//        minesList.add(board[1][0]);
//
//        board[1][5].placeMine();
//        minesList.add(board[1][5]);
//
//        board[1][7].placeMine();
//        minesList.add(board[1][7]);
//
//        board[1][10].placeMine();
//        minesList.add(board[1][10]);
//
//        board[2][6].placeMine();
//        minesList.add(board[2][6]);
//
//        board[2][17].placeMine();
//        minesList.add(board[2][17]);
//
//        board[3][5].placeMine();
//        minesList.add(board[3][5]);
//
//        board[3][9].placeMine();
//        minesList.add(board[3][9]);
//
//        board[3][16].placeMine();
//        minesList.add(board[3][16]);
//
//        board[3][17].placeMine();
//        minesList.add(board[3][17]);
//
//        board[4][11].placeMine();
//        minesList.add(board[4][11]);
//
//        board[4][18].placeMine();
//        minesList.add(board[4][18]);
//
//        board[5][10].placeMine();
//        minesList.add(board[5][10]);
//
//        board[5][13].placeMine();
//        minesList.add(board[5][13]);
//
//        board[5][14].placeMine();
//        minesList.add(board[5][14]);
//
//        board[7][15].placeMine();
//        minesList.add(board[7][15]);
//
//        board[7][16].placeMine();
//        minesList.add(board[7][16]);
//
//        board[7][18].placeMine();
//        minesList.add(board[7][18]);
//
//        board[8][3].placeMine();
//        minesList.add(board[8][3]);
//
//        board[8][5].placeMine();
//        minesList.add(board[8][5]);
//
//        board[10][4].placeMine();
//        minesList.add(board[10][4]);
//
//        board[11][17].placeMine();
//        minesList.add(board[11][17]);
//
//        board[13][4].placeMine();
//        minesList.add(board[13][4]);
//
//        board[13][17].placeMine();
//        minesList.add(board[13][17]);
//
//        board[14][2].placeMine();
//        minesList.add(board[14][2]);
//
//        board[14][5].placeMine();
//        minesList.add(board[14][5]);
//
//        board[14][6].placeMine();
//        minesList.add(board[14][6]);
//
//        board[14][7].placeMine();
//        minesList.add(board[14][7]);
//
//        board[14][11].placeMine();
//        minesList.add(board[14][11]);
//
//        board[14][12].placeMine();
//        minesList.add(board[14][12]);
//
//        board[15][0].placeMine();
//        minesList.add(board[15][0]);
//
//        board[15][3].placeMine();
//        minesList.add(board[15][3]);
//
//        board[15][10].placeMine();
//        minesList.add(board[15][10]);
//
//        board[15][14].placeMine();
//        minesList.add(board[15][14]);
//
//        board[15][19].placeMine();
//        minesList.add(board[15][19]);
//
//        board[16][0].placeMine();
//        minesList.add(board[16][0]);
//
//        board[16][13].placeMine();
//        minesList.add(board[16][13]);
//
//        board[16][14].placeMine();
//        minesList.add(board[16][14]);
//
//        board[16][16].placeMine();
//        minesList.add(board[16][16]);
//
//        board[16][17].placeMine();
//        minesList.add(board[16][17]);
//
//        board[17][1].placeMine();
//        minesList.add(board[17][1]);
//
//        board[17][3].placeMine();
//        minesList.add(board[17][3]);
//
//        board[17][13].placeMine();
//        minesList.add(board[17][13]);
//
//        board[17][18].placeMine();
//        minesList.add(board[17][18]);
//
//        board[18][0].placeMine();
//        minesList.add(board[18][0]);
//
//        board[18][6].placeMine();
//        minesList.add(board[18][6]);
//
//        board[18][14].placeMine();
//        minesList.add(board[18][14]);
//
//        board[19][16].placeMine();
//        minesList.add(board[19][16]);
//
//        board[19][17].placeMine();
//        minesList.add(board[19][17]);

        updateValues();
    }

    public void updateValues() {
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