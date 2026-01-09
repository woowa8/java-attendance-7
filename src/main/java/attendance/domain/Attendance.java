package attendance.domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public class Attendance {
    private final LocalTime startTime;
    private final LocalTime endTime;
    private final LocalDate attendanceDate;
    private LocalTime attendanceTime;
    private Rate attendanceStatus;

    public Attendance(LocalDate attendanceDate, LocalTime attendanceTime) {
        startTime = LocalTime.of(8, 00);
        endTime = LocalTime.of(23, 00);

        validateAttandanceTime(attendanceTime);
        validateAttandanceYoil(attendanceDate);
        this.attendanceDate = attendanceDate;
        this.attendanceTime = attendanceTime;

        this.attendanceStatus = Rate.getInstance(attendanceDate.getDayOfWeek(), attendanceTime);
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public LocalDate getAttendanceDate() {
        return attendanceDate;
    }

    public LocalTime getAttendanceTime() {
        return attendanceTime;
    }

    public Rate getAttendanceStatus() {
        return attendanceStatus;
    }

    public void setAttendanceTime(LocalTime attendanceTime) {
        this.attendanceTime = attendanceTime;
        this.attendanceStatus = Rate.getInstance(attendanceDate.getDayOfWeek(), attendanceTime);    // 지각인지 뭔지 상태 같이 업데이트
    }

    public void validateAttandanceTime(LocalTime attendanceTime) {
//        System.out.println("디버깅 : " +  attendanceTime.toString());

        if (attendanceTime.isBefore(startTime) || attendanceTime.isAfter(endTime)) {
            throw new IllegalArgumentException("[ERROR] 캠퍼스 운영 시간에만 출석이 가능합니다.");
        }
    }

    // 12월 14일 토요일은 등교일이 아닙니다.
    public void validateAttandanceYoil(LocalDate attendanceDate) {
        if (DayOfWeek.SATURDAY.equals(attendanceDate.getDayOfWeek()) || DayOfWeek.SUNDAY.equals(attendanceDate.getDayOfWeek())) {
            String month = attendanceDate.getMonth().name();
            String day = attendanceDate.getDayOfMonth() + "";
            String yoil = attendanceDate.getDayOfWeek().name();
            // TODO : 이 yoil에 뭐가 담기는지 잘 모르겟어서... 일단 출력해보고 이상하면 if 분기 처리
            throw new IllegalArgumentException("[ERROR] " + month + " 월" + day + "일" + yoil + "요일은 등교일이 아닙니다.");
        }
    }
}
