package Main.solver;

import Main.game.Cell;

import java.util.ArrayList;
import java.util.List;

public class ChanceBoard {
    public SolverCell[][] chanceBoard;
    int width;
    int height;

    public ChanceBoard(int[][] board) {
        width = board.length;
        height = board[0].length;
        createChanceBoard(board);
    }

    public List<Cell> createChanceBoard(int[][] board) {
        SolverCell[][] result = new SolverCell[width][height];
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
                SolverCell[] neighbours = getNeighbours(j, i);
                cell.resetSurrounding();
                if (cell.getValue() != 0 && cell.isChecked()) {
                    for (SolverCell neighbour : neighbours) {
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
                SolverCell[] neighbours = getNeighbours(j, i);
                if (cell.getValue() > 0 && cell.getValue() == cell.uncheckedNeighbours) {
                    for (SolverCell neighbour : neighbours) {
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
                SolverCell[] neighbours = getNeighbours(j, i);
                if (cell.getValue() != 0 && cell.getValue() == cell.flagsAround) {
                    for (SolverCell neighbour : neighbours) {
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
                SolverCell[] neighbours = getNeighbours(j, i);
                if (cell.getValue() != 0 && cell.isChecked()) {
                    for (SolverCell neighbour : neighbours) {
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

    public SolverCell[] getNeighbours(int x, int y) {  //  Создаём массив из клеток, которые расположены
        SolverCell[] surrounding = new SolverCell[8];  //  в таком порядке относительно ячейки на поле (по часовой стрелке):
        surrounding[0] = new SolverCell(-1 + x, -1 + y, false);  //  Сверху слева
        surrounding[1] = new SolverCell(-1 + x, y, false);  //  Сверху
        surrounding[2] = new SolverCell(-1 + x, 1 + y, false);  //  Сверху справа
        surrounding[3] = new SolverCell(x, 1 + y, false);  //  Справа
        surrounding[4] = new SolverCell(1 + x, 1 + y, false);  //  Справа снизу
        surrounding[5] = new SolverCell(1 + x, y, false);  //  Снизу
        surrounding[6] = new SolverCell(1 + x, -1 + y, false);  //  Снизу слева
        surrounding[7] = new SolverCell(x, -1 + y, false);  //  Слева
        return surrounding;
    }

    public SolverCell getSolverCell(int x, int y) {
        return chanceBoard[x][y];
    }
}