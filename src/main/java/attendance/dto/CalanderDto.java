package attendance.dto;

import attendance.domain.Attendance;
import attendance.domain.User;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.Locale;

// // 12월 13일 금요일 09:59 (출석)
// TODO : 여기서 HOUR 같은게 0으로 들어오면 --로 내보내는거 있어야 하다.
public record CalanderDto(
        String month,
        String day,
        String yoil,
        String hour,
        String minutes,
        String status
) {
    private static String toMinute(int day){
        if (day < 10){
            return "0" + day;
        }
        return String.valueOf(day);
    }

    public static CalanderDto of(Attendance attendance) {
        LocalTime localTime = attendance.getAttendanceTime();

        String hour = "";
        String minute = "";

        if (localTime == null) {
            hour = "--";
            minute = "--";
        } else{
            hour = toMinute(localTime.getHour());
            minute = toMinute(localTime.getMinute());
        }

        return new CalanderDto(
                attendance.getAttendanceDate().getMonth().getDisplayName(TextStyle.FULL, Locale.KOREA),
                String.valueOf(attendance.getAttendanceDate().getDayOfMonth()),
                attendance.getAttendanceDate().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA),
                hour,
                minute,
                attendance.getAttendanceStatus().getDisplayName()
        );
    }
}
