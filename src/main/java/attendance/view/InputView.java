package attendance.view;

import attendance.domain.Attendance;
import attendance.util.InputParser;
import camp.nextstep.edu.missionutils.Console;
import camp.nextstep.edu.missionutils.DateTimes;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.function.Supplier;

public class InputView {
    private final InputParser inputParser;

    public InputView(InputParser inputParser) {
        this.inputParser = inputParser;
    }

    public String inputMode() {
        return retryOnError(() -> {
            String month = DateTimes.now().toLocalDate().getMonth().getDisplayName(TextStyle.FULL, Locale.KOREA);
            String day = DateTimes.now().toLocalDate().getDayOfMonth() + "";

            DayOfWeek dayOfWeek = DateTimes.now().toLocalDate().getDayOfWeek();

            String yoil = dayOfWeek.getDisplayName(TextStyle.FULL, Locale.KOREA);

            System.out.println("오늘은 " + month + " " + day + "일" + " " + yoil + "입니다. 기능을 선택해 주세요.");

            System.out.println("1. 출석 확인");
            System.out.println("2. 출석 수정");
            System.out.println("3. 크루별 출석 기록 확인");
            System.out.println("4. 제적 위험자 확인");
            System.out.println("Q. 종료");

            String input = Console.readLine();

            if(!"Q".equals(input) && !"1".equals(input) && !"2".equals(input) && !"3".equals(input) && !"4".equals(input) && !"5".equals(input)){
                throw new IllegalArgumentException("[ERROR] 잘못된 형식을 입력하였습니다.");
            }

            if(dayOfWeek.equals(DayOfWeek.SATURDAY) || dayOfWeek.equals(DayOfWeek.SUNDAY)){
                if(input.equals("1")){
                    throw new IllegalArgumentException("[ERROR] " + month + " " + day + "일" + " " + yoil + "은 등교일이 아닙니다.");
                }
            }

            return input;
        });
    }

    // 1. 출석 확인
    public String inputNick() {
        return retryOnError(() -> {
            System.out.println("닉네임을 입력해 주세요.");
            return Console.readLine();
        });
    }

    // 시간 입력, 출석 생성
    public Attendance inputTime() {
        return retryOnError(() -> {
            System.out.println("등교 시간을 입력해 주세요.");
            List<Integer> words = inputParser.parse(Console.readLine());

            LocalTime attendanceTime = LocalTime.of(words.get(0), words.get(1));

            return new Attendance(DateTimes.now().toLocalDate(), attendanceTime);
        });
    }

    // 2. 출석 수정
    public String inputModifyNick() {
        return retryOnError(() -> {
            System.out.println("출석을 수정하려는 크루의 닉네임을 입력해 주세요.");
            return Console.readLine();
        });
    }

    public int inputModifyDay() {
        return retryOnError(() -> {
            System.out.println("수정하려는 날짜(일)를 입력해 주세요.");
            int day = Integer.parseInt(Console.readLine());

            if(day < 0 || day > 31) {
                throw new IllegalArgumentException("[ERROR] 잘못된 형식을 입력하였습니다.");
            }
            return day;
        });
    }

    public Attendance inputModifyTime() {
        return retryOnError(() -> {
            System.out.println("언제로 변경하겠습니까?");
            List<Integer> words = inputParser.parse(Console.readLine());
            LocalTime attendanceTime = LocalTime.of(words.get(0), words.get(1));

            return new Attendance(DateTimes.now().toLocalDate(), attendanceTime);
        });
    }

    private <T> T retryOnError(Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                throw e;
            }
        }
    }
}
