package test.java.pl.ffWorkApp.time;

import main.java.pl.ffWorkApp.time.TimeUtils;

import java.time.LocalDateTime;

public class TimeUtilsTest {
    static void main(String[] args) {
        System.out.println("* Parse Test *");
        LocalDateTime start = TimeUtils.parse("2026-08-10 12:26");
        LocalDateTime end = TimeUtils.parse("2026-08-10 17:31");
        System.out.println("start date: " + start + "\nend date: " + end);

        System.out.println("\n* Formatting Test *");
        try {
            TimeUtils.parse("9.10.2026");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\n* Time Counting Test *");
        System.out.println("Time between start date : " + start.getHour() + ":" + start.getMinute() +
                ", and end date: " + end.getHour() + ":" + end.getMinute() + ", is " + TimeUtils.timeBetween(start, end));

        System.out.println("\n* TimeRange Object Test*");
        TimeUtils.TimeRange timeRange = new TimeUtils.TimeRange(start, end);
        System.out.println("Range start: " + timeRange.start() + "\nRange end: " + end);

        System.out.println("\n* Time Range Validation Test*");
        LocalDateTime beforeStart = TimeUtils.parse("2026-08-10 10:31");
        System.out.println("Start date: " + start.getHour() + ":" + start.getMinute() + "\nEnd date: " +
                beforeStart.getHour() + ":" + beforeStart.getMinute() + ".");
        try {
            TimeUtils.TimeRange timeRangeToValidate = new TimeUtils.TimeRange(start, beforeStart);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\n* Time Range Overlap Test*");
        LocalDateTime start1 = TimeUtils.parse("2026-08-10 10:15");
        LocalDateTime end1 = TimeUtils.parse("2026-08-10 12:15");
        LocalDateTime start2 = TimeUtils.parse("2026-08-10 11:45");
        LocalDateTime end2 = TimeUtils.parse("2026-08-10 13:45");
        TimeUtils.TimeRange timeRange1 = new TimeUtils.TimeRange(start1, end1);
        TimeUtils.TimeRange timeRange2 = new TimeUtils.TimeRange(start2, end2);
        System.out.println("\n1st time range: " + timeRange1.start().getHour() + ":" + timeRange1.start().getMinute() +
                " - " + timeRange1.end().getHour() + ":" + timeRange1.end().getMinute());
        System.out.println("2st time range: " + timeRange2.start().getHour() + ":" + timeRange2.start().getMinute() +
                " - " + timeRange2.end().getHour() + ":" + timeRange2.end().getMinute());
        try {
            System.out.println("Overlap: " + TimeUtils.overlaps(timeRange1, timeRange2));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}