package attendance.domain;

public enum Status {
    양호("양호", 2),
    제적("제적", 3),
    면담("면담", 6),
    위험("위험", Integer.MAX_VALUE);

    private final String displayName;
    private final int downNumber;

    Status(String displayName, int downNumber) {
        this.displayName = displayName;
        this.downNumber = downNumber;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getDownNumber() {
        return downNumber;
    }

    public static Status getInstance(int absenceNum) {
        if(absenceNum < Status.양호.getDownNumber()) {
            return Status.양호;
        }

        if(absenceNum < Status.제적.getDownNumber()) {
            return Status.제적;
        }

        if(absenceNum < Status.면담.getDownNumber()) {
            return Status.면담;
        }

        return Status.위험;
    }
}
