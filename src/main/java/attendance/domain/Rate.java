package attendance.domain;

import java.time.DayOfWeek;
import java.time.LocalTime;

public enum Rate {
    세이프("출석", 5, LocalTime.of(13, 00), LocalTime.of(10, 00)),
    지각("지각", 30, LocalTime.of(13, 00), LocalTime.of(10, 00)),
    결석("결석", Integer.MAX_VALUE, LocalTime.of(13, 00), LocalTime.of(10, 00));

    private final String displayName;
    private final long downTime;
    private final LocalTime monStartTime;
    private final LocalTime exStartTime;

    Rate(String displayName, long downTime, LocalTime monStartTime, LocalTime exStartTime) {
        this.displayName = displayName;
        this.downTime = downTime;
        this.monStartTime = monStartTime;
        this.exStartTime = exStartTime;
    }

    public String getDisplayName() {
        return displayName;
    }

    public long getDownTime() {  // 이거 이하여야 한다. 즉, 시작 시간 이상이되 이거 더했을때 before 이여야 하는 것이다.
        return downTime;
    }

    public LocalTime getMonStartTime() {
        return monStartTime;
    }

    public LocalTime getExStartTime() {
        return exStartTime;
    }

    public static Rate getInstance(DayOfWeek yoil, LocalTime attendanceTime) {
        return calculateRate(yoil, attendanceTime);
    }

    private static Rate calculateRate(DayOfWeek yoil, LocalTime attendanceTime) {
        if(attendanceTime == null){
            return Rate.결석;
        }

        LocalTime startTime = 세이프.exStartTime;
        if(yoil.equals(DayOfWeek.MONDAY)){
            startTime = 세이프.monStartTime;
        }

        LocalTime safeTime = startTime.plusMinutes(Rate.세이프.downTime);
        if(attendanceTime.isBefore(safeTime)) {
            return Rate.세이프;
        }

        LocalTime rateTime = startTime.plusMinutes(Rate.지각.downTime);
        if(attendanceTime.isBefore(rateTime)) {
            return Rate.지각;
        }

        return Rate.결석;
    }


}
