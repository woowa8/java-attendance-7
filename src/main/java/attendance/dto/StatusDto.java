package attendance.dto;

public record StatusDto(
        int attendance,
        int rate,
        int absence,
        String status
) {
}
