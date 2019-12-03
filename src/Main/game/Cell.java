package Main.game;

public class Cell {
    private int x;
    private int y;
    private int value = 0;
    private boolean mine = false;
    public boolean check = false;

    public Cell(int y, int x) {
        this.x = x;
        this.y = y;
    }

    public boolean isMine() {
        return mine;
    }

    public void placeMine(boolean place) {
        mine = place;
        value = -1;
    }

    public void setValue(int val) {
        value = val;
    }

    public int getValue() {
        return value;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean inRange(int width, int height) {
        return 0 <= x && x < width && 0 <= y && y < height;
    }

    public Cell[] getNeighbours() {  //  Создаём массив из клеток, которые расположены
        Cell[] surrounding = new Cell[8];  //  в таком порядке относительно ячейки на поле (по часовой стрелке):
        surrounding[0] = new Cell(-1 + y, -1 + x);  //  Сверху слева
        surrounding[1] = new Cell(-1 + y, x);  //  Сверху
        surrounding[2] = new Cell(-1 + y, 1 + x);  //  Сверху справа
        surrounding[3] = new Cell(y, 1 + x);  //  Справа
        surrounding[4] = new Cell(1 + y, 1 + x);  //  Справа снизу
        surrounding[5] = new Cell(1 + y, x);  //  Снизу
        surrounding[6] = new Cell(1 + y, -1 + x);  //  Снизу слева
        surrounding[7] = new Cell(y, -1 + x);  //  Слева
        return surrounding;
    }


}
