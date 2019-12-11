package Main.game;

public class Point {
    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
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

    public static Point[] getNeighbours(int x, int y) {  //  Создаём массив из клеток, которые расположены
        Point[] surrounding = new Point[8];  //  в таком порядке относительно ячейки на поле (по часовой стрелке):
        surrounding[0] = new Point(-1 + x, -1 + y);  //  Сверху слева
        surrounding[1] = new Point(-1 + x, y);  //  Сверху
        surrounding[2] = new Point(-1 + x, 1 + y);  //  Сверху справа
        surrounding[3] = new Point(x, 1 + y);  //  Справа
        surrounding[4] = new Point(1 + x, 1 + y);  //  Справа снизу
        surrounding[5] = new Point(1 + x, y);  //  Снизу
        surrounding[6] = new Point(1 + x, -1 + y);  //  Снизу слева
        surrounding[7] = new Point(x, -1 + y);  //  Слева
        return surrounding;
    }
}
