package attendance;

import attendance.controller.AttendanceController;

public class Application {
    public static void main(String[] args) {
        // TODO: 프로그램 구현
        AttendanceController controller = new AttendanceController();
        controller.run();
    }
}
