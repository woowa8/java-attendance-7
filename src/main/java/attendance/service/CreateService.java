package attendance.service;

import attendance.domain.Attendance;
import attendance.domain.User;
import attendance.dto.CalanderDto;
import attendance.repository.UserRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CreateService {
    private final UserRepository userRepository;

    public CreateService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<CalanderDto> run(String nick, int day, Attendance after) {
        User user = userRepository.findUserByName(nick);
        if (user == null) {
            throw new IllegalArgumentException("[ERROR] 등록되지 않은 닉네임입니다.");
        }
        // 2. 해당하는 날짜 출석 찾기
        LocalDate target = LocalDate.of(2024,12,day);
        Attendance beforeA = userRepository.getTagetAttendance(user, target);

        // 1. before dto 하나 생성하기
        CalanderDto before = CalanderDto.of(beforeA);

        // 3. 출석 수저앟기
        userRepository.updateAttendance(user, beforeA, after);
        // 4. after 생성하기
        CalanderDto afterA = CalanderDto.of(after);

        List<CalanderDto> lists = new ArrayList<>();
        lists.add(before);
        lists.add(afterA);

        return lists;
    }
}
