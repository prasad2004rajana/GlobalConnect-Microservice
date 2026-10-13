package follow_service.follow_service.dto;

import org.springframework.data.domain.Page;

import java.util.List;

public record PageResponse<T>(List<T> content, int page, int size, long totalElements, boolean last) {

    public static <T> PageResponse<T> from(Page<?> page, List<T> content) {
        return new PageResponse<>(content, page.getNumber(), page.getSize(), page.getTotalElements(), page.isLast());
    }
}