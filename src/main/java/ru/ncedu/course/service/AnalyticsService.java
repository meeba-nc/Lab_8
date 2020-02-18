package ru.ncedu.course.service;

import ru.ncedu.course.LoyaltyBalance;
import ru.ncedu.course.history.AbstractHistoryRecord;
import ru.ncedu.course.service.properties.Property;

import java.util.Calendar;
import java.util.Date;
import java.util.stream.IntStream;

public class AnalyticsService {

    @Property("precision")
    public long precision;

    public void averageBalanceForWeek(LoyaltyBalance balance) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.WEEK_OF_MONTH, -1);
        Date weekAgo = calendar.getTime();

        double averageBalance = balance.provideDataAnalysis(historyRecords -> {
            IntStream balanceValues = IntStream.concat(
                    historyRecords.stream().
                            filter(record -> record.getDate().after(weekAgo)).
                            mapToInt(AbstractHistoryRecord::getBalanceBeforeOperation),
                    IntStream.of(balance.getBalance())
            );
            return balanceValues.average().orElse(balance.getBalance());
        });
        System.out.printf("Your average balance for week is %." + precision + "f points", averageBalance);
    }

}
