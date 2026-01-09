package attendance.util;

import attendance.domain.Attendance;
import attendance.domain.User;
import attendance.repository.UserRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InitData {
    private final UserRepository userRepository;

    public InitData(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 외부에서는 이것만 사용해서 데이터 초기화
    public void init() {
        try {
            saveUserAndAttendance();
        } catch (IOException e) {
            throw new IllegalStateException("[ERROR] 잘못된 입력입니다. 다시 입력해 주세요.");
        }
    }

    private List<List<String>> readFile(String fileName) throws IOException {
        List<List<String>> mainList = new ArrayList<List<String>>();
        // 1. 파일 읽어들이기
        InputStream inputStream = InitData.class.getClassLoader().getResourceAsStream(fileName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        reader.readLine();    // 첫 줄 스킵

        String line; // 쿠키,2024-12-13 10:08
        while ((line = reader.readLine()) != null) {
            String[] lists = line.split(",");
            List<String> subList = Arrays.asList(lists);
            mainList.add(subList);
        }
        return mainList;
    }

    // 유저 찾거나 없으면 만들어서 내보내기
    private User findUser(String userName){
        User user = userRepository.findUserByName(userName);

        if(user == null){
            return new User(userName);
        }

        return user;
    }

    // 각각 repository에 저장하기
    private void saveUserAndAttendance() throws IOException {
        List<List<String>> mainList = readFile("attendances.csv");

        // nickname,datetime 순서 // 2024-12-13 10:08
        for (List<String> list : mainList) {
            // 1. 우선 user를 가져온다.
            User user = findUser(list.get(0));
            // 2. user의 attandence 저장한다.
            String[] dateAndTime = list.get(1).split(" ");

            LocalDate date = LocalDate.parse(dateAndTime[0]);
            LocalTime time = LocalTime.parse(dateAndTime[1]);

            user.getAttendances().add(new Attendance(date, time));
            userRepository.addUser(user);
        }
    }

//    // 없는 경우 넣기
//    private void saveEmpty(String[] dateAndTime) {
//        LocalDate date = LocalDate.parse(dateAndTime[0]);
//
//        System.out.println("디버깅 : " + date.getMonth().toString() + " " + date.getDayOfMonth() + " " + date.getYear());
//
//        List<User> users = userRepository.getUsers();
//
//        for (User user : users) {
//            if(userRepository.getTagetAttendance(user, date) == null){
//                user.getAttendances().add(new Attendance(date, null));    // 출석 정보 없어도 저장
//            }
//        }
//    }
}