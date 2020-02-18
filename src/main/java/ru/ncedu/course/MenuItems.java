package ru.ncedu.course;

public enum MenuItems {
    CHARGE("Charge loyalty points"),
    SPEND("Spend loyalty points"),
    HISTORY("Show history"),
    MAX_BALANCE_VALUE_EVER("Show maximum balance value ever"),
    AVERAGE_FOR_WEEK("Show average balance for week"),
    SUM_FOR_DAY("Show sum of operations for day"),
    MAX_MIN_DIFFERENCE_FOR_MONTH("Show max-min difference for month"),
    SAVE_AND_EXIT("Save and exit");

    private String description;

    public String getDescription() {
        return description;
    }

    MenuItems(String description) {
        this.description = description;
    }
}
