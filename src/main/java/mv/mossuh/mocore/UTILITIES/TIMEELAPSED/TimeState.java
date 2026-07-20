package mv.mossuh.mocore.UTILITIES.TIMEELAPSED;

import mv.mossuh.mocore.UTILITIES.TimeFormat;
import mv.mossuh.mocore.UTILITIES.UsefulMethods;
import mv.mossuh.mocore.UTILITIES.UsefulString;

public class TimeState {
    long startTime = 0;
    private TimeType timeType = TimeType.MILLISECONDS;

    public TimeState(Long startTime, TimeType timeType) {
        if (startTime != null) { this.startTime = startTime; }
        if (timeType != null) { this.timeType = timeType; }
    }

    public void start() {
        this.startTime = System.currentTimeMillis();
    }

    public boolean isActive() {
        return startTime > 0;
    }

    public void setTimeType(TimeType timeType) {
        if (timeType != null) { this.timeType = timeType; }
    }
    public TimeType getTimeType() { return timeType; }

    public long getElapsedTime() {
        if (timeType == TimeType.MILLISECONDS) {
            if (startTime > 0) {
                return (System.currentTimeMillis() - startTime) / 1000;
            }
        } else if (timeType == TimeType.SECONDS) {
            return startTime;
        }
        return 0;
    }

    public String getElapsedTime(boolean formatted, TimeFormat format) {
        if (formatted) {
            if (timeType == TimeType.MILLISECONDS) {
                if (startTime > 0) {
                    return UsefulMethods.showTimeElapsedFormatted(getElapsedTime(), format);
                }
                return "0" + UsefulString.get(format.getSecond()).hex().apply();
            } else if (timeType == TimeType.SECONDS) {
                return UsefulMethods.showTimeElapsedFormatted(startTime, format);
            }
            return "0" + UsefulString.get(format.getSecond()).hex().apply();
        } else {
            return String.valueOf(getElapsedTime());
        }
    }

    public void reset() {
        startTime = 0;
    }
}
