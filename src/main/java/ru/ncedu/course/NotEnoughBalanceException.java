package ru.ncedu.course;

public class NotEnoughBalanceException extends Exception {

    private int requiredPoints;

    public int getRequiredPoints() {
        return requiredPoints;
    }

    public NotEnoughBalanceException(int requiredPoints) {
        this.requiredPoints = requiredPoints;
    }
}
