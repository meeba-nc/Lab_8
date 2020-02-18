package ru.ncedu.course;

import ru.ncedu.course.history.AbstractHistoryRecord;
import ru.ncedu.course.service.AnalyticsService;
import ru.ncedu.course.service.execution.ThreadPool;
import ru.ncedu.course.service.import_export.ImportExportService;
import ru.ncedu.course.service.properties.PropertiesService;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class Main {

    private final static Scanner SCANNER = new Scanner(System.in);

    public static MenuItems printMenuAndGetSelectedItem(LoyaltyBalance balance) {
        MenuItems result = null;
        do {
            System.out.println("You have " + balance.getBalance() + " loyalty points");
            for(MenuItems item: MenuItems.values()) {
                System.out.println((item.ordinal() + 1) + ". " + item.getDescription());
            }

            int selectedItemNumber = SCANNER.nextInt();
            if(selectedItemNumber > 0 && selectedItemNumber <= MenuItems.values().length) {
                result = MenuItems.values()[selectedItemNumber - 1];
            }
        } while (result == null);
        return result;
    }

    public static void chargeAction(LoyaltyBalance balance) {
        System.out.println("Enter value to charge");
        int valueToCharge = SCANNER.nextInt();
        balance.charge(valueToCharge);
    }

    public static void spendAction(LoyaltyBalance balance) {
        System.out.println("Enter value to spend");
        int valueToSpend = SCANNER.nextInt();
        try {
            balance.spend(valueToSpend);
        } catch (NotEnoughBalanceException e) {
            System.out.println("You need " + e.getRequiredPoints() + " more loyalty points to continue operation");
        }
    }

    public static void historyAction(LoyaltyBalance balance) {
        if(balance.getHistoryRecords().isEmpty()) {
            System.out.println("No history records");
        } else {
            for (AbstractHistoryRecord historyRecord : balance.getHistoryRecords()) {
                System.out.println(historyRecord);
            }
        }
    }

    public static void maxBalanceValueEverAction(LoyaltyBalance balance) {
        int maximumBalanceEverValue = balance.provideDataAnalysis(historyRecords -> {
            IntStream balanceValues = IntStream.concat(
                    historyRecords.stream().
                            mapToInt(AbstractHistoryRecord::getBalanceBeforeOperation),
                    IntStream.of(balance.getBalance())
            );
            return balanceValues.max().orElse(balance.getBalance());
        });
        System.out.println("Your maximum balance record was " + maximumBalanceEverValue + " points");
    }

    public static void sumOfOperationsForDay(LoyaltyBalance balance) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_WEEK, -1);
        Date dayAgo = calendar.getTime();

        int sumOfOperations = balance.provideDataAnalysis(historyRecords ->
                historyRecords.stream().
                    filter(record -> record.getDate().after(dayAgo)).
                    mapToInt(record -> Math.abs(record.getDelta())).
                    sum());
        System.out.println("Your sum of operations for day is " + sumOfOperations + " points");
    }

    public static void maxMinBalanceDifferenceForMonth(LoyaltyBalance balance) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        Date monthAgo = calendar.getTime();

        IntSummaryStatistics summary = balance.provideDataAnalysis(historyRecords ->
                IntStream.concat(
                    historyRecords.stream().
                        filter(record -> record.getDate().after(monthAgo)).
                        mapToInt(AbstractHistoryRecord::getBalanceBeforeOperation),
                    IntStream.of(balance.getBalance())
                ).summaryStatistics()
        );
        System.out.println("Your max-min difference for month is " + (summary.getMax() - summary.getMin()) + " points");
    }

    public static void main(String[] args) throws IllegalAccessException {
        ThreadPool threadPool = new ThreadPool(10);

        LoyaltyBalance balance = new LoyaltyBalance();

        for(int i = 0; i < 10; i++) {
            CountDownLatch counter = new CountDownLatch(0);
            if(i%2 == 0) {
                threadPool.execute(() -> {
                    balance.charge(100);
                    counter.countDown();
                    System.out.println("Charge " + Thread.currentThread());
                });
            } else {
                threadPool.execute(() -> {
                    try {
                        System.out.println("Inside spend " + Thread.currentThread());
                        counter.await();
                        System.out.println("Spend " + Thread.currentThread());
                        balance.spend(100);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        }

        //threadPool.deactivate();

        /*PropertiesService propertiesService = new PropertiesService();

        ImportExportService importExportService = new ImportExportService();
        propertiesService.injectProperties(importExportService);

        AnalyticsService analyticsService = new AnalyticsService();
        propertiesService.injectProperties(analyticsService);

        LoyaltyBalance balance = importExportService
                .readLoyaltyBalance()
                .orElse(new LoyaltyBalance());

        MenuItems action = null;
        while (action != MenuItems.SAVE_AND_EXIT) {
            action = printMenuAndGetSelectedItem(balance);
            switch (action) {
                case CHARGE:
                    chargeAction(balance);
                    break;
                case SPEND:
                    spendAction(balance);
                    break;
                case HISTORY:
                    historyAction(balance);
                    break;
                case MAX_BALANCE_VALUE_EVER:
                    maxBalanceValueEverAction(balance);
                    break;
                case AVERAGE_FOR_WEEK:
                    analyticsService.averageBalanceForWeek(balance);
                    break;
                case SUM_FOR_DAY:
                    sumOfOperationsForDay(balance);
                    break;
                case MAX_MIN_DIFFERENCE_FOR_MONTH:
                    maxMinBalanceDifferenceForMonth(balance);
                    break;
            }
            System.out.println();
        }

        importExportService.writeLoyaltyBalance(balance);*/
    }

}
