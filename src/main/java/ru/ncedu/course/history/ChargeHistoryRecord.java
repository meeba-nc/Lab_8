package ru.ncedu.course.history;

public class ChargeHistoryRecord extends AbstractHistoryRecord {
    public ChargeHistoryRecord(int balanceBeforeOperation, int charge) {
        super(balanceBeforeOperation, charge);
    }

    @Override
    public String toString() {
        return "Charged " + getDelta() + " at " + getDate();
    }
}
