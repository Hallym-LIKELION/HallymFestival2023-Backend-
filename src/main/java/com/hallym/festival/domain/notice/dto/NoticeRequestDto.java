package com.hallym.festival.domain.notice.dto;

import com.hallym.festival.domain.notice.entity.Notice;
import lombok.*;

@Getter // get하는 메소드 만들어줌
@Builder
@AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자를 만들어줌
@NoArgsConstructor // 파라미터가 없는 기본 생성자를 만들어줌
public class NoticeRequestDto {

    private Long id;
    private String title;
    private String content;
    private boolean is_deleted;

    public Notice toEntity() {
        Notice build = Notice.builder()
                .id(id)
                .title(title)
                .content(content)
                .is_deleted(is_deleted)
                .build();
        return build;
    }
}
