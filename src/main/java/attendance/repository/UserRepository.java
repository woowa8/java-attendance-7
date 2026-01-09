package attendance.repository;

import attendance.domain.Attendance;
import attendance.domain.Rate;
import attendance.domain.Status;
import attendance.domain.User;
import camp.nextstep.edu.missionutils.DateTimes;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private final List<User> users;

    public UserRepository() {
        this.users = new ArrayList<>();
    }

    public List<User> getUsers() {
        return users;
    }

    // 유저의 오늘 출석 기록 가져오는 기능
    public Attendance getTodayAttendance(User user) {
        return user.getAttendances().stream()
                .filter(attendance -> attendance.getAttendanceDate().isEqual(
                        DateTimes.now().toLocalDate()))
                .findFirst().orElse(null);
    }

    // 유저의 해당 날짜 출석 기록 가져오는 기능
    public Attendance getTagetAttendance(User user, LocalDate date) {
        return user.getAttendances().stream()
                .filter(attendance -> attendance.getAttendanceDate().isEqual(date))
                .findFirst().orElse(null);
    }

    // 유저 이름으로 유저 찾는 기능
    public User findUserByName(String name) {
        return users.stream()
                .filter(user -> user.getName().equals(name))
                .findFirst().orElse(null);
    }

    // 유저의 해당 날짜 출석 수정하는 기능
//    public void updateAttendance(User user, LocalDate attendanceDate, LocalTime updateTime) {
//        Attendance target = user.getAttendances().stream()
//                .filter(attendance -> attendance.getAttendanceDate().isEqual(attendanceDate))
//                .findFirst().orElse(null);
//
//        target.setAttendanceTime(updateTime);
//        user.setStatus(sumRate(user, Rate.결석));   // 유저 상태 같이 업데이트
//    }

    public void updateAttendance(User user, Attendance before, Attendance after) {
        before.setAttendanceTime(after.getAttendanceTime());
        user.setStatus(sumRate(user, Rate.결석));   // 유저 상태 같이 업데이트
    }

    // 유저의 출석 기록 전체 조회하는 기능
    public List<Attendance> getAttendanceByUser(User user) {
        return user.getAttendances();
    }

    // 유저 카테고리별 총 합 구하는 기능
    public int sumRate(User user, Rate rate) {
        return (int) user.getAttendances().stream()
                .filter(attendance -> attendance.getAttendanceStatus().equals(rate))
                .count();
    }

    // 상태별 대상자 전체 조회하는 기능
    public List<User> findUsersByStatus(Status status) {
        return users.stream()
                .filter(user -> user.getStatus().equals(status)).toList();
    }

    public void addUser(User user) {
        this.users.add(user);
    }
}
