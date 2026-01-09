package attendance.service;

import attendance.domain.Attendance;
import attendance.domain.Rate;
import attendance.domain.User;
import attendance.dto.CalanderDto;
import attendance.dto.ListDto;
import attendance.dto.StatusDto;
import attendance.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class CheckService {
    private final UserRepository userRepository;

    public CheckService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ListDto run(String nick){
        User user = userRepository.findUserByName(nick);
        if (user == null) {
            throw new IllegalArgumentException("[ERROR] 등록되지 않은 닉네임입니다.");
        }

        List<CalanderDto> a = getCalander(user);
        StatusDto b = getStatus(user);

        return new ListDto(a,user,b);
    }

    public List<CalanderDto> getCalander(User user) {
        // 1. 전체 출석기록 가져오기
        List<Attendance> attendances = userRepository.getAttendanceByUser(user);
        // 2. dto 변환
        List<CalanderDto> calanders = new ArrayList<>();

        for (Attendance attendance : attendances) {
            CalanderDto dto = CalanderDto.of(attendance);
            calanders.add(dto);
        }

        return calanders;
    }

    public StatusDto getStatus(User user) {
        int attendance = userRepository.sumRate(user, Rate.세이프);
        int rate = userRepository.sumRate(user, Rate.지각);
        int absence = userRepository.sumRate(user, Rate.결석);

        String status = user.getStatus().toString();

        return new StatusDto(attendance, rate, absence, status);
    }
}
