package attendance.view;

import attendance.domain.User;
import attendance.dto.CalanderDto;
import attendance.dto.ListDto;
import attendance.dto.StatusDto;

import java.util.List;

public class OutputView {
    // 1. 출석 확인
    // 12월 13일 금요일 09:59 (출석)
    public void printCheck(CalanderDto dto) {
        System.out.println(dto.month() + " " + dto.day() + "일 " + dto.yoil() + " " + dto.hour() + ":" + dto.minutes() + " " + "(" + dto.status() + ")");
    }

    // 2. 출석 수정
    public void updateAttandance(CalanderDto before, CalanderDto after) {
        // 12월 03일 화요일 10:07 (지각) -> 09:58 (출석) 수정 완료!
        System.out.print(before.month() + " " + before.day() + "일 " + before.yoil() + " " + before.hour() + ":" + before.minutes() + " " + "(" + before.status() + ")");
        System.out.println(" -> " + after.hour() + ":" + after.minutes() + " " + "(" + after.status() + ")" + " 수정 완료!");
    }

    // 3. 출석 기록 보기
    public void printList(ListDto dto) {
        printAttandance(dto.attendances(), dto.user());
        printStatus(dto.status());
    }

    private void printAttandance(List<CalanderDto> attendances, User user) {
        System.out.println("이번 달 " + user.getName() + "의 출석 기록입니다.");
        for (CalanderDto attendance : attendances) {
            printCheck(attendance);
        }
        System.out.println();
    }

    private void printStatus(StatusDto status) {
        System.out.println("출석: " + status.attendance());
        System.out.println("지각: " + status.rate());
        System.out.println("결석: " + status.absence());

        System.out.println();
        // TODO : 멀쩡할 경우는 이게 NULL로 들어와야 한다.
        if (!"양호".equals(status.status())) {
            System.out.println(status.status() + " 대상자입니다.");
        }
        System.out.println();
    }

    // TODO : 이거 복잡해서 나중에 구현
    // 제적 위험자 조회 결과
    //- 빙티: 결석 3회, 지각 2회 (면담)
    //- 이든: 결석 2회, 지각 4회 (면담)
    //- 쿠키: 결석 2회, 지각 2회 (경고)
    //- 빙봉: 결석 1회, 지각 5회 (경고)

    // 4. 제적 위험자 확인

}
