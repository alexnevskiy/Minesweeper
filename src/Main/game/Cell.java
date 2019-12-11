package Main.game;

public class Cell {
    private Point point;
    private int value = 0;
    private boolean mine = false;
    private boolean flag = false;
    public boolean check = false;

    public Cell(int x, int y) {
        point = new Point(x, y);
    }

    public boolean isMine() {
        return mine;
    }

    public void placeMine() {
        mine = true;
        value = -1;
    }

    public void removeMine() {
        mine = false;
        value = 0;
        check = false;
    }

    public boolean isFlag() {
        return flag;
    }

    public void placeFlag() {
        flag = true;
    }

    public void setValue(int val) {
        value = val;
    }

    public int getValue() {
        return value;
    }

    public int getX() {
        return point.getX();
    }

    public int getY() {
        return point.getY();
    }
}
