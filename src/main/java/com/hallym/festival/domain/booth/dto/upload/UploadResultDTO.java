package com.hallym.festival.domain.booth.dto.upload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UploadResultDTO { //파일이 여러 개인 경우 여러 정보를 반환해야하기 때문에 별도의 DTO 생성

    private String uuid; //업로드 된 파일의 UUID

    private String fileName; //파일 이름

    private boolean img; //이미지 여부

    public String getLink(){ //JSON 처리 시 첨부 파일 경로 처리를 위한 link 속성으로 자동 처리

        if(img){
            return "s_"+ uuid +"_"+fileName; //이미지인 경우 섬네일
        }else {
            return uuid+"_"+fileName;
        }
    }
}
