package com.hallym.festival.domain.notice.dto;

import com.hallym.festival.domain.notice.entity.Notice;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoticeResponseDto {

    private Long id;
    private String title;
    private String content;
    private boolean is_deleted;

}
