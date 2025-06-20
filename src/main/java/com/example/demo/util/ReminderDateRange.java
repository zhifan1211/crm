package com.example.demo.util;

import java.time.LocalDate;

public class ReminderDateRange {
	public static LocalDate[] getSearchRange(LocalDate today) {
        int day = today.getDayOfMonth();

        if (day == 5) {
            // ex: today = 2025-05-05
            LocalDate nextMonth = today.plusMonths(1);
            LocalDate start = LocalDate.of(nextMonth.getYear(), nextMonth.getMonth(), 5);
            LocalDate end = LocalDate.of(nextMonth.getYear(), nextMonth.getMonth(), 19);
            return new LocalDate[]{start, end};
        } else if (day == 20) {
            // ex: today = 2025-05-20
            LocalDate nextMonth = today.plusMonths(1);
            LocalDate start = LocalDate.of(nextMonth.getYear(), nextMonth.getMonth(), 20);

            // 下下個月的4號
            LocalDate nextNextMonth = today.plusMonths(2);
            LocalDate end = LocalDate.of(nextNextMonth.getYear(), nextNextMonth.getMonth(), 4);
            return new LocalDate[]{start, end};
        }
        return null; // 其它日子不執行
    }
}
