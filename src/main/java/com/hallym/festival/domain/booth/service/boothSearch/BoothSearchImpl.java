package com.hallym.festival.domain.booth.service.boothSearch;

import com.hallym.festival.domain.booth.entity.Booth;
import com.hallym.festival.domain.booth.entity.QBooth;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class BoothSearchImpl extends QuerydslRepositorySupport implements BoothSearch {

    public BoothSearchImpl(){
        super(Booth.class);
    }

    @Override
    public Page<Booth> search1(Pageable pageable) {

        QBooth booth = QBooth.booth;

        JPQLQuery<Booth> query = from(booth); //@Query로 생성했던 JPQL을 코드로 생성

        BooleanBuilder booleanBuilder = new BooleanBuilder(); // (

        booleanBuilder.or(booth.booth_title.contains("11")); // title like ...

        booleanBuilder.or(booth.booth_content.contains("11")); // content like ....

        query.where(booleanBuilder);
        query.where(booth.bno.gt(0L)); //bno가 0보다 큰 조건


        //paging
        this.getQuerydsl().applyPagination(pageable, query);

        List<Booth> list = query.fetch(); //JPQLQuery 실행

        long count = query.fetchCount(); //fetchCount()로 count 쿼리 실행


        return null;

    }

    @Override
    public Page<Booth> searchAll(String[] types, String keyword, Pageable pageable) {

        QBooth booth = QBooth.booth;
        JPQLQuery<Booth> query = from(booth);

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
}
