package attendance.service;

import attendance.domain.Attendance;
import attendance.domain.User;
import attendance.dto.CalanderDto;
import attendance.repository.UserRepository;

public class ModifiedService {
    private final UserRepository userRepository;

    public ModifiedService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser(String nick){
        User user = userRepository.findUserByName(nick);
        if (user == null) {
            throw new IllegalArgumentException("[ERROR] 등록되지 않은 닉네임입니다.");
        }

        return user;
    }

    public CalanderDto run(User user, Attendance attendance) {
        // 4. user 한테 출석 저장
        user.addAttendance(attendance);

        return CalanderDto.of(attendance);
    }
}
