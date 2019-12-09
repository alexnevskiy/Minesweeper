package Main.game;

public class Cell {
    private int x;
    private int y;
    private int value = 0;
    private boolean mine = false;
    private boolean flag = false;
    public boolean check = false;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean isMine() {
        return mine;
    }

    public void placeMine() {
        mine = true;
        value = -1;
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
        surrounding[0] = new Cell(-1 + x, -1 + y);  //  Сверху слева
        surrounding[1] = new Cell(-1 + x, y);  //  Сверху
        surrounding[2] = new Cell(-1 + x, 1 + y);  //  Сверху справа
        surrounding[3] = new Cell(x, 1 + y);  //  Справа
        surrounding[4] = new Cell(1 + x, 1 + y);  //  Справа снизу
        surrounding[5] = new Cell(1 + x, y);  //  Снизу
        surrounding[6] = new Cell(1 + x, -1 + y);  //  Снизу слева
        surrounding[7] = new Cell(x, -1 + y);  //  Слева
        return surrounding;
    }


}
