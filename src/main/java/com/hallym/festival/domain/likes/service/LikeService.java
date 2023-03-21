package com.hallym.festival.domain.likes.service;

import com.hallym.festival.domain.booth.entity.Booth;
import com.hallym.festival.domain.booth.repository.BoothRepository;
import com.hallym.festival.domain.likes.dto.LikesResponseDto;
import com.hallym.festival.domain.likes.entity.Likes;
import com.hallym.festival.domain.likes.repository.LikeRepository;
import com.hallym.festival.global.exception.WrongBoothId;
import com.hallym.festival.global.exception.WrongLikesKey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class LikeService {

    private final LikeRepository likeRepository;
    private final BoothRepository boothRepository;

    public LikesResponseDto create(Long id) {
        Optional<Booth> byId = boothRepository.findById(id);
        if (byId.isEmpty()) {
            throw new WrongBoothId();
        }
        String newCookieKey = createCookieKey();
        Likes likes = Likes.builder().booth(byId.get()).cookieKey(newCookieKey).build();
        Likes newLikes = likeRepository.save(likes);

        return entityToDto(newLikes);
    }

    public void delete(Long boothId, String cookieKey) {
        Optional<Booth> booth = boothRepository.findById(boothId);
        if (booth.isEmpty()) {
            throw new WrongBoothId();
        }
        Optional<Likes> likes = likeRepository.findByCookieKey(cookieKey);
        if (likes.isEmpty()) {
            throw new WrongLikesKey();
        }
        likeRepository.deleteById(likes.get().getId());
    }

    private String createCookieKey(){
        while (true) {
            String cookieKey = createRandomString();
            Optional<Likes> likes = likeRepository.findByCookieKey(cookieKey);
            if (likes.isEmpty()){
                return cookieKey;
            }
        }
    }

    private String createRandomString(){
        int targetStringLength = 10;
        Random random = new Random();
        return random.ints(97, 123)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }


    public Optional<Cookie> findBoothCookie(HttpServletRequest request, Long id) {
        Cookie[] userCookies = request.getCookies();
        if (userCookies == null) {
            return Optional.empty();
        }
        for (Cookie userCookie : userCookies) {
            if (userCookie.getName().equals(id.toString())) {
                return Optional.of(userCookie); //null값이 안들어가게
            }
        }
        return Optional.empty();
    }

    private LikesResponseDto entityToDto(Likes likes) {
        return LikesResponseDto.builder()
                .boothId(likes.getBooth().getBno())
                .cookieKey(likes.getCookieKey())
                .build();
    }
}
