package attendance.domain;

import java.util.ArrayList;
import java.util.List;

public class User {
    private final String name;
    private final List<Attendance> attendances;
    private Status status;

    public User(String name) {
        this.name = name;
        this.attendances = new ArrayList<>();
        this.status = Status.양호;
    }

    public String getName() {
        return name;
    }

    public List<Attendance> getAttendances() {
        return attendances;
    }

    public void addAttendance(Attendance attendance){
        this.attendances.add(attendance);
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(int absenceNum) {
        this.status = Status.getInstance(absenceNum);
    }
}
