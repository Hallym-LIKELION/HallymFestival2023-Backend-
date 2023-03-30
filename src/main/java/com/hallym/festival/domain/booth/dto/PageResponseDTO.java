package com.hallym.festival.domain.booth.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class PageResponseDTO<E> { //다른 도메인에서도 페이징 처리를 할 수 있게 제네릭 처리

    private int page;
    private int size;
    private int total;

    //시작 페이지 번호
    private int start;
    //끝 페이지 번호
    private int end;

    //이전 페이지의 존재 여부
    private boolean prev;
    //다음 페이지의 존재 여부
    private boolean next;

    private List<E> dtoList;

    @Builder(builderMethodName = "withAll")
    public PageResponseDTO(PageRequestDTO pageRequestDTO, List<E> dtoList, int total){ //여러 정보를 생성자를 이용해서 받아서 처리하는 것이 안전

        if(total <= 0){
            return;
        }

        this.page = pageRequestDTO.getPage();
        this.size = pageRequestDTO.getSize();

        this.total = total;
        this.dtoList = dtoList;

        this.end =   (int)(Math.ceil(this.page / 10.0 )) *  10; //마지막 페이지

        this.start = this.end - 9; //시작 페이지

        int last =  (int)(Math.ceil((total/(double)size))); //마지막 페이지 전체 개수

        this.end =  end > last ? last: end; //마지막 페이지가 last 값보다 작은 경우에 last값이 end

        this.prev = this.start > 1; //이전 페이지는 시작 페이지가 1이 아니라면 무조건 true

        this.next =  total > this.end * this.size; //마지막 페이지와 페이지당 개수를 곱한 값보다 전체 개수가 많은가?

    }
}