package Main.solver;

public class SolverCell {
    private int x;
    private int y;
    private int value;
    public int uncheckedNeighbours;
    public int flagsAround;
    public double chance;
    public double notMineChance;
    public boolean notMine;
    private boolean flag = false;
    public boolean check;

    public SolverCell(int x, int y, boolean check) {
        this.x = x;
        this.y = y;
        this.check = check;
        value = 0;
        //neighbours = 0;
        uncheckedNeighbours = 0;
        flagsAround = 0;
        chance = 0;
        notMineChance = 0;
        notMine = false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setValue(int val) {
        value = val;
    }

    public int getValue() {
        return value;
    }

    public void setChance(double chan) {
        chance = chan;
    }

    public double getChance() {
        if (notMine) return notMineChance;
        return chance;
    }

    public boolean isChecked() {
        return check;
    }

    public void setFlag() {
        chance = 100;
        flag = true;
    }

    public boolean isFlag() {
        return flag;
    }


    public void resetSurrounding() {
        uncheckedNeighbours = 0;
        flagsAround = 0;
    }


    public void setNotMine() {
        notMineChance = 0.01;
        notMine = true;
    }
}
