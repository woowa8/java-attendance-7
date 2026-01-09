package attendance.dto;

import attendance.domain.User;

import java.util.List;

public record ListDto (
        List<CalanderDto> attendances,
        User user,
        StatusDto status
) {
}
