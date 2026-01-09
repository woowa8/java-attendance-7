package attendance.controller;

import attendance.domain.Attendance;
import attendance.domain.User;
import attendance.dto.CalanderDto;
import attendance.dto.ListDto;
import attendance.repository.UserRepository;
import attendance.service.CheckService;
import attendance.service.CreateService;
import attendance.service.ModifiedService;
import attendance.util.InitData;
import attendance.util.InputParser;
import attendance.view.InputView;
import attendance.view.OutputView;

import java.util.List;

public class AttendanceController {
    private InputView inputView;
    private OutputView outputView;

    private CheckService checkService;
    private CreateService createService;
    private ModifiedService modifiedService;

    public AttendanceController() {
        inputView = new InputView(new InputParser());
        outputView = new OutputView();

        UserRepository userRepository = new UserRepository();

        InitData init = new InitData(userRepository);
        init.init();    // 초기 데이터 생성 완료

        checkService = new CheckService(userRepository);
        createService = new CreateService(userRepository);
        modifiedService = new ModifiedService(userRepository);
    }

    public void run() {
        // 2. 숫자에 따라서 service 분기
        while (true) {
            // 1. inputView로 숫자 선택
            String input = inputView.inputMode();

            if ("Q".equals(input)) {
                break;
            }

            if ("1".equals(input)) {
                try {
                    // 1. 닉네임 받기
                    String nick = inputView.inputNick();
                    User user = modifiedService.getUser(nick);
                    // 2. 시간 입력, 출석 생성
                    Attendance attendance = inputView.inputTime();
                    // 3. ModifiedService 연결
                    CalanderDto dto = modifiedService.run(user, attendance);

                    outputView.printCheck(dto);
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                    throw e;
                }
            } else if ("2".equals(input)) {
                try {
                    // 1. 닉네임 받기
                    String nick = inputView.inputModifyNick();
                    // 2. 날짜 받기
                    int day = inputView.inputModifyDay();
                    // 3. 시간으로 새 출석 생성
                    Attendance attendance = inputView.inputModifyTime();
                    // 4. CreateService 연결
                    List<CalanderDto> dtos = createService.run(nick, day, attendance);

                    outputView.updateAttandance(dtos.get(0), dtos.get(1));
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                    throw e;
                }
            } else if ("3".equals(input)) {
                try {
                    String nick = inputView.inputNick();
                    // 2. CheckService로 연결
                    ListDto dto = checkService.run(nick);

                    outputView.printList(dto);
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                    throw e;
                }
            } else {
                System.out.println("아직 기능 미구현");
            }
        }


    }
}
