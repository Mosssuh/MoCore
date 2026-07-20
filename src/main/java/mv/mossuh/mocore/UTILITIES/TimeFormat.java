package mv.mossuh.mocore.UTILITIES;

public class TimeFormat {
    private String year = "";
    private String month = "";
    private String week = "";
    private String day = "";
    private String hour = "";
    private String minute = "";
    private String second = "";

    public TimeFormat(String year, String month, String week, String day, String hour, String minute, String second) {
        if (year != null) { this.year = UsefulString.get(year).hex().apply(); }
        if (month != null) { this.month = UsefulString.get(month).hex().apply(); }
        if (week != null) { this.week = UsefulString.get(week).hex().apply(); }
        if (day != null) { this.day = UsefulString.get(day).hex().apply(); }
        if (hour != null) { this.hour = UsefulString.get(hour).hex().apply(); }
        if (minute != null) { this.minute = UsefulString.get(minute).hex().apply(); }
        if (second != null) { this.second = UsefulString.get(second).hex().apply(); }
    }

    public String getYear() { return year; }
    public String getMonth() { return month; }
    public String getWeek() { return week; }
    public String getDay() { return day; }
    public String getHour() { return hour; }
    public String getMinute() { return minute; }
    public String getSecond() { return second; }

    public static TimeFormat ignoreFormat() {
        return new TimeFormat(null, null, null, null, null, null, null);
    }
}
