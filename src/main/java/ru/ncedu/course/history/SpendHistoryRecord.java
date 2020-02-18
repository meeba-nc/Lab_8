package ru.ncedu.course.history;

public class SpendHistoryRecord extends AbstractHistoryRecord {
    public SpendHistoryRecord(int balanceBeforeOperation, int spend) {
        super(balanceBeforeOperation, -spend);
    }

    @Override
    public String toString() {
        return "Spent " + (-getDelta()) + " at " + getDate();
    }
}
