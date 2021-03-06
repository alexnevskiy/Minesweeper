package Main.solver;

import Main.game.Board;
import Main.game.Cell;

import java.util.List;

public class Solver {
    public Board board;
    private int width;
    private int height;
    private int moveCounter;
    private int failedMove;
    private ChanceBoard chanceBoard;

    public Solver(int width, int height, int mines) {
        this.width = width;
        this.height = height;
        board = new Board(width, height, mines);
        chanceBoard = new ChanceBoard(board.getBoard());
        moveCounter = 0;
    }

    public void play() {
        if (moveCounter == 0) {
            randomMove();
            return;
        }
        Cell cell = bestMove();
        System.out.println("Сейчас решатель на координатах x: " + cell.getX() + ", y: " + cell.getY());
        if (failedMove > 2) {
            while (!randomMove()) {
                failedMove = 0;
            }
        }
        if (!move(cell.getX(), cell.getY())) {
            failedMove++;
        }
    }

    private Cell bestMove() {
        chanceBoard.createChanceBoard(board.getBoard());
        double minimumChance = Double.MAX_VALUE;
        Cell cell = new Cell(0, 0);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                SolverCell solverCell = chanceBoard.chanceBoard[i][j];
                if (solverCell.getChance() > 0 && solverCell.getChance() < minimumChance && !solverCell.isFlag()) {
                    cell = new Cell(solverCell.getX(), solverCell.getY());
                    minimumChance = solverCell.getChance();
                }
            }
        }
        return cell;
    }

    private boolean move(int x, int y) {
        if (board.board[y][x].check || board.board[y][x].isFlag()) {
            return false;
        }
        List<Cell> list;
        board.uncover(x, y);
        list = chanceBoard.createChanceBoard(board.getBoard());
        for (Cell cell : list) {
            board.board[cell.getY()][cell.getX()].placeFlag();
        }
        moveCounter++;
        return true;
    }

    private boolean randomMove() {
        int x = (int) (Math.random() * width);
        int y = (int) (Math.random() * height);
        return move(x, y);
    }
}
