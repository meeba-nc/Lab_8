package ru.ncedu.course.history;

import java.io.Serializable;
import java.util.Date;

public abstract class AbstractHistoryRecord implements Serializable {

    private static final long serialVersionUID = -7478824510985751404L;

    private final Date date = new Date();
    private final int balanceBeforeOperation;
    private final int delta;

    public AbstractHistoryRecord(int balanceBeforeOperation, int delta) {
        this.balanceBeforeOperation = balanceBeforeOperation;
        this.delta = delta;
    }

    public Date getDate() {
        return date;
    }

    public int getBalanceBeforeOperation() {
        return balanceBeforeOperation;
    }

    public int getDelta() {
        return delta;
    }
}
