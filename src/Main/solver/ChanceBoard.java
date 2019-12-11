package Main.solver;

import Main.game.Cell;
import Main.game.Point;

import java.util.ArrayList;
import java.util.List;

public class ChanceBoard {
    public SolverCell[][] chanceBoard;
    int width;
    int height;

    public ChanceBoard(int[][] board) {
        height = board.length;
        width = board[0].length;
        createChanceBoard(board);
    }

    public List<Cell> createChanceBoard(int[][] board) {
        SolverCell[][] result = new SolverCell[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (board[i][j] == -1) {
                    SolverCell cell = new SolverCell(j, i, false);
                    result[i][j] = cell;
                } else if (board[i][j] >= 0) {
                    SolverCell solverCell = new SolverCell(j, i, true);
                    solverCell.setValue(board[i][j]);
                    result[i][j] = solverCell;
                }
            }
        }
        chanceBoard = result;
        return updateChances();
    }

    public List<Cell> updateChances() {
        List<Cell> list;
        updateSolverCells();
        list = setFlags();
        uncoverNotMinesCells();
        findChance();
        return list;
    }

    private void updateSolverCells() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                SolverCell cell = chanceBoard[i][j];
                Point[] neighbours = Point.getNeighbours(j, i);
                cell.resetSurrounding();
                if (cell.getValue() != 0 && cell.isChecked()) {
                    for (Point neighbour : neighbours) {
                        if (neighbour.inRange(width, height)) {
                            if (chanceBoard[neighbour.getY()][neighbour.getX()].isFlag()) {
                                cell.flagsAround++;
                            }
                            if (!chanceBoard[neighbour.getY()][neighbour.getX()].isChecked()) {
                                cell.uncheckedNeighbours++;
                            }
                        }
                    }
                }
            }
        }
    }

    private List<Cell> setFlags() {
        List<Cell> list = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                SolverCell cell = chanceBoard[i][j];
                Point[] neighbours = Point.getNeighbours(j, i);
                if (cell.getValue() > 0 && cell.getValue() == cell.uncheckedNeighbours) {
                    for (Point neighbour : neighbours) {
                        if (neighbour.inRange(width, height) && !chanceBoard[neighbour.getY()][neighbour.getX()].isChecked()) {
                            chanceBoard[neighbour.getY()][neighbour.getX()].setFlag();
                            list.add(new Cell(neighbour.getX(), neighbour.getY()));
                        }
                    }
                    updateSolverCells();
                }
            }
        }
        return list;
    }

    private void uncoverNotMinesCells() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                SolverCell cell = chanceBoard[i][j];
                Point[] neighbours = Point.getNeighbours(j, i);
                if (cell.getValue() != 0 && cell.getValue() == cell.flagsAround) {
                    for (Point neighbour : neighbours) {
                        if (neighbour.inRange(width, height) && !chanceBoard[neighbour.getY()][neighbour.getX()].isChecked() && !chanceBoard[neighbour.getY()][neighbour.getX()].isFlag()) {
                            chanceBoard[neighbour.getY()][neighbour.getX()].setNotMine();
                        }
                    }
                    updateSolverCells();
                }
            }
        }
    }

    private void findChance() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                SolverCell cell = chanceBoard[i][j];
                Point[] neighbours = Point.getNeighbours(j, i);
                if (cell.getValue() != 0 && cell.isChecked()) {
                    for (Point neighbour : neighbours) {
                        if (neighbour.inRange(width, height) && !chanceBoard[neighbour.getY()][neighbour.getX()].isChecked()) {
                            double previousChance = chanceBoard[neighbour.getY()][neighbour.getX()].getChance();
                            double chance = previousChance + ((double) (cell.getValue() - cell.flagsAround)) / ((double) (cell.uncheckedNeighbours - cell.flagsAround));
                            chanceBoard[neighbour.getY()][neighbour.getX()].setChance(chance);
                        }
                    }
                }
            }
        }
    }
}
