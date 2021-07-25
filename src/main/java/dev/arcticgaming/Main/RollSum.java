package dev.arcticgaming.Main;

public class RollSum {
    private int rollSum = 0; //established private var rollSum

    public int getRollSum() {
        return rollSum;
    }

    public void setRollSum(int newRollSum, int args) {
        this.rollSum = newRollSum + args;
    }

}