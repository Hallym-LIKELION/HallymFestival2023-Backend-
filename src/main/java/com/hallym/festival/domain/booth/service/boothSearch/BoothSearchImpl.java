package com.hallym.festival.domain.booth.service.boothSearch;

import com.hallym.festival.domain.booth.dto.BoothImageDTO;
import com.hallym.festival.domain.booth.dto.BoothListAllDTO;
import com.hallym.festival.domain.booth.entity.Booth;
import com.hallym.festival.domain.booth.entity.DayNight;
import com.hallym.festival.domain.booth.entity.QBooth;
import com.hallym.festival.domain.comment.entity.QComment;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class BoothSearchImpl extends QuerydslRepositorySupport implements BoothSearch {

    public BoothSearchImpl(){
        super(Booth.class);
    }


    @Override
    public Page<BoothListAllDTO> searchWithAll(String[] types, String keyword, Pageable pageable) {

        QBooth booth = QBooth.booth;
        QComment comment = QComment.comment;

        JPQLQuery<Booth> boothJPQLQuery = from(booth);
        boothJPQLQuery.leftJoin(comment).on(comment.booth.eq(booth)); //left join

        //is_deleted 가 false인것만 가져오는 BooleanBuilder 생성
        BooleanBuilder is_deleted = new BooleanBuilder();
        is_deleted.or(booth.is_deleted.eq(false));
        boothJPQLQuery.where(is_deleted);

        if( (types != null) && keyword != null ){ //검색조건
            String type = String.join("", types);
            log.info(type);
            BooleanBuilder booleanBuilder = new BooleanBuilder();

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
                case "d": //dayNight
                    if(keyword.contains("day"))
                        booleanBuilder.or(booth.dayNight.eq(DayNight.DAY));
                    else
                        booleanBuilder.or(booth.dayNight.eq(DayNight.NIGHT));
                    break;
                case "o": //openDay
                    booleanBuilder.or(booth.openDay.contains(keyword));
                    break;
                case "tod":
                    String[] split = keyword.split(",");
                    booleanBuilder.and(booth.booth_title.contains(split[0]));
                    booleanBuilder.and(booth.openDay.contains(split[1]));
                    if(split[2].equals("d"))
                        booleanBuilder.and(booth.dayNight.eq(DayNight.DAY));
                    else if(split[2].equals("n"))
                        booleanBuilder.and(booth.dayNight.eq(DayNight.NIGHT));
                    break;
                case "od":
                    String[] split2 = keyword.split(",");
                    booleanBuilder.and(booth.openDay.contains(split2[0]));
                    if(split2[1].contains("day"))
                        booleanBuilder.and(booth.dayNight.eq(DayNight.DAY));
                    else
                        booleanBuilder.and(booth.dayNight.eq(DayNight.NIGHT));
                    break;
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
