package com.pa.refactoring.model;

import java.time.LocalDateTime;

public class Time {
    private int day, year, month, hours, minutes;

    public Time() {
        LocalDateTime ldt = LocalDateTime.now();

        day = ldt.getDayOfMonth();
        month = ldt.getMonthValue();
        year = ldt.getYear();
        hours = ldt.getHour();
        minutes = ldt.getMinute();
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }
}
