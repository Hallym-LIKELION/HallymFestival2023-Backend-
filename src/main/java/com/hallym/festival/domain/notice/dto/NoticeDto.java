package com.hallym.festival.domain.notice.dto;

import com.hallym.festival.domain.notice.entity.Notice;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NoticeDto {
    private Long id;

    private String title;

    private String content;

    private Boolean is_deleted;

    private LocalDateTime regDate;

    private LocalDateTime modDate;

}
