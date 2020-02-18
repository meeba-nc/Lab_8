package ru.ncedu.course;

import ru.ncedu.course.history.AbstractHistoryRecord;
import ru.ncedu.course.history.ChargeHistoryRecord;
import ru.ncedu.course.history.SpendHistoryRecord;
import ru.ncedu.course.history.analysis.AbstractDataAnalysisOperation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class LoyaltyBalance implements Serializable {

    private static final long serialVersionUID = 499010244810219539L;

    private int balance;
    private List<AbstractHistoryRecord> historyRecords = new LinkedList<>();

    public int getBalance() {
        return this.balance;
    }

    public List<AbstractHistoryRecord> getHistoryRecords() {
        return new ArrayList<>(historyRecords);
    }

    public void charge(int charge) {
        this.historyRecords.add(new ChargeHistoryRecord(this.balance, charge));
        this.balance += charge;
    }

    public void spend(int spend) throws NotEnoughBalanceException {
        int result = this.balance - spend;
        if(result < 0) {
            throw new NotEnoughBalanceException(-result);
        }
        this.historyRecords.add(new SpendHistoryRecord(this.balance, spend));
        this.balance = result;
    }

    public <T> T provideDataAnalysis(AbstractDataAnalysisOperation<T> operation) {
        return operation.apply(this);
    }

}
