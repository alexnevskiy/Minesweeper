package Main.solver;

class SolverCell {
    private int x;
    private int y;
    private int value;
    int uncheckedNeighbours;
    int flagsAround;
    private double chance;
    private double notMineChance;
    private boolean notMine;
    private boolean flag = false;
    private boolean check;

    SolverCell(int x, int y, boolean check) {
        this.x = x;
        this.y = y;
        this.check = check;
        value = 0;
        uncheckedNeighbours = 0;
        flagsAround = 0;
        chance = 0;
        notMineChance = 0;
        notMine = false;
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    void setValue(int val) {
        value = val;
    }

    int getValue() {
        return value;
    }

    void setChance(double chan) {
        chance = chan;
    }

    double getChance() {
        if (notMine) return notMineChance;
        return chance;
    }

    boolean isChecked() {
        return check;
    }

    void setFlag() {
        chance = 100;
        flag = true;
    }

    boolean isFlag() {
        return flag;
    }


    void resetSurrounding() {
        uncheckedNeighbours = 0;
        flagsAround = 0;
    }


    void setNotMine() {
        notMineChance = 0.01;
        notMine = true;
    }
}
