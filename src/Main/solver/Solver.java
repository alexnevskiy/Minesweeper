package Main.solver;

import Main.game.Board;
import Main.game.Cell;
import Main.game.exceptions.GameLoseException;
import Main.game.exceptions.GameWinException;

import java.util.List;

public class Solver {
    public Board board;
    public int width;
    public int height;
    public int moveCounter;
    private int failedMove;
    public GameState state;
    public ChanceBoard chanceBoard;

    public Solver(int width, int height, int mines) {
        this.width = width;
        this.height = height;
        board = new Board(width, height, mines);
        chanceBoard = new ChanceBoard(board.getBoard());
        state = GameState.launching;
        moveCounter = 0;
    }

    public void stepPlay() throws GameLoseException, GameWinException {
        if (state == GameState.launching) {
            play();
        }
    }

    public void play() throws GameLoseException, GameWinException {
        if (moveCounter == 0) {
            randomMove();
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

    public boolean move(int x, int y) throws GameWinException, GameLoseException {
        if (board.board[y][x].check || board.board[y][x].isFlag()) {
            return false;
        }
        List<Cell> list;
        try {
            board.uncover(x, y);
        } catch (GameLoseException e) {
            if (moveCounter == 0) {
                board.board[y][x].removeMine();
                board.minesList.remove(board.board[y][x]);
                boolean isSetMine = false;
                for (int i = 0; i < height; i++) {
                    for (int j = 0; j < width; j++) {
                        if (!board.board[i][j].isMine() && (j != x || i != y)) {
                            board.board[i][j].placeMine();
                            board.minesList.add(board.board[i][j]);
                            isSetMine = true;
                            break;
                        }
                    }
                    if (isSetMine) break;
                }
                board.updateValues();
                board.uncover(x, y);
                if (board.checkCells == board.emptyCells) {
                    board.endGame = true;
                    throw new GameWinException();
                }
            } else {
                board.endGame = true;
                throw new GameLoseException();
            }
        }
        list = chanceBoard.createChanceBoard(board.getBoard());
        for (Cell cell : list) {
            board.board[cell.getY()][cell.getX()].placeFlag();
        }
        moveCounter++;
        return true;
    }

    public boolean randomMove() throws GameLoseException, GameWinException {
        int x = (int) (Math.random() * width);
        int y = (int) (Math.random() * height);
        return move(x, y);
    }
}
