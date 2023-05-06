package com.hallym.festival.domain.booth.service.boothSearch;

import com.hallym.festival.domain.booth.dto.BoothImageDTO;
import com.hallym.festival.domain.booth.dto.BoothListAllDTO;
import com.hallym.festival.domain.booth.entity.Booth;
import com.hallym.festival.domain.booth.entity.QBooth;
import com.hallym.festival.domain.comment.entity.QComment;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.stream.Collectors;

public class BoothSearchImpl extends QuerydslRepositorySupport implements BoothSearch {

    public BoothSearchImpl(){
        super(Booth.class);
    }


    @Override
    public Page<Booth> searchAll(String[] types, String keyword, Pageable pageable) {

        QBooth booth = QBooth.booth;
        JPQLQuery<Booth> query = from(booth);

        BooleanBuilder is_deleted = new BooleanBuilder();
        is_deleted.or(booth.is_deleted.eq(false));
        query.where(is_deleted);

        if( (types != null && types.length > 0) && keyword != null ){ //검색 조건과 키워드가 있다면

            BooleanBuilder booleanBuilder = new BooleanBuilder(); // (

            for(String type: types){

                switch (type){
                    case "t":
                        booleanBuilder.or(booth.booth_title.contains(keyword));
                        break;
                    case "c":
                        booleanBuilder.or(booth.booth_content.contains(keyword));
                        break;
                    case "w":
                        booleanBuilder.or(booth.writer.contains(keyword));
                        break;
                }
            }//end for
            query.where(booleanBuilder);
        }//end if

        //bno > 0
        query.where(booth.bno.gt(0L));

        //paging
        this.getQuerydsl().applyPagination(pageable, query);

        List<Booth> list = query.fetch();

        long count = query.fetchCount();

        return new PageImpl<>(list, pageable, count);
        //페이징의 최종 처리는 Page<T>타입을 반환합니다. (실제 목록 데이터, 페이지 정보를 가진 객체, 전체개수)

    }

    @Override
    public Page<BoothListAllDTO> searchWithAll(String[] types, String keyword, Pageable pageable) {

        QBooth booth = QBooth.booth;
        QComment comment = QComment.comment;

        JPQLQuery<Booth> boothJPQLQuery = from(booth);
        boothJPQLQuery.leftJoin(comment).on(comment.booth.eq(booth)); //left join

        if( (types != null && types.length > 0) && keyword != null ){ //검색조건

            BooleanBuilder booleanBuilder = new BooleanBuilder();

            for(String type: types){

                switch (type){
                    case "t":
                        booleanBuilder.or(booth.booth_title.contains(keyword));
                        break;
                    case "c":
                        booleanBuilder.or(booth.booth_content.contains(keyword));
                        break;
                    case "w":
                        booleanBuilder.or(booth.writer.contains(keyword));
                        break;
                }
            }//end for
            boothJPQLQuery.where(booleanBuilder);
        }


        boothJPQLQuery.groupBy(booth);

        getQuerydsl().applyPagination(pageable, boothJPQLQuery); //paging

        JPQLQuery<Tuple> tupleJPQLQuery = boothJPQLQuery.select(booth, comment.countDistinct());

        List<Tuple> tupleList = tupleJPQLQuery.fetch();

        List<BoothListAllDTO> dtoList = tupleList.stream().map(tuple -> {

            Booth booth1 = (Booth) tuple.get(booth);
            long CommentCount = tuple.get(1,Long.class);

            BoothListAllDTO dto = BoothListAllDTO.builder()
                    .bno(booth1.getBno())
                    .booth_title(booth1.getBooth_title())
                    .booth_content(booth1.getBooth_content())
                    .writer(booth1.getWriter())
                    .booth_type(booth1.getBooth_type())
                    .dayNight(booth1.getDayNight())
                    .openDay(booth1.getOpenDay())
                    .like_cnt(booth1.getLikes().size())
                    .comment_cnt(booth1.getComments().size())
                    .regDate(booth1.getRegDate())
                    .build();

            //BoothImage를 BoothImageDTO 처리할 부분
            List<BoothImageDTO> imageDTOS = booth1.getImageSet().stream().sorted()
                    .map(boothImage -> BoothImageDTO.builder()
                            .uuid(boothImage.getUuid())
                            .fileName(boothImage.getFileName())
                            .ord(boothImage.getOrd())
                            .build()
                    ).collect(Collectors.toList());

            dto.setBoothImages(imageDTOS);

            return dto;
        }).collect(Collectors.toList());

        long totalCount = boothJPQLQuery.fetchCount();


        return new PageImpl<>(dtoList, pageable, totalCount);
    }
}
