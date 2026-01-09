package attendance.dto;

import java.util.List;

/*
제적 위험자를 담는 dto
 */
public record DanDto (
        List<DanUserDto> users
) {

}
