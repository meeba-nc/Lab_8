package ru.ncedu.course.history.analysis;

import ru.ncedu.course.LoyaltyBalance;
import ru.ncedu.course.history.AbstractHistoryRecord;

import java.util.List;

public interface AbstractDataAnalysisOperation<ResultType> {

    default ResultType apply(LoyaltyBalance balance) {
        return apply(balance.getHistoryRecords());
    }

    ResultType apply(List<AbstractHistoryRecord> historyRecords);

}
