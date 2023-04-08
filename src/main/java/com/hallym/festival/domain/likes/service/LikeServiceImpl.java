package com.hallym.festival.domain.likes.service;

import com.hallym.festival.domain.booth.dto.PageRequestDTO;
import com.hallym.festival.domain.booth.dto.PageResponseDTO;
import com.hallym.festival.domain.booth.entity.Booth;
import com.hallym.festival.domain.booth.repository.BoothRepository;
import com.hallym.festival.domain.likes.dto.LikesResponseDTO;
import com.hallym.festival.domain.likes.dto.LikesResponseTopDTO;
import com.hallym.festival.domain.likes.entity.Likes;
import com.hallym.festival.domain.likes.repository.LikeRepository;
import com.hallym.festival.global.exception.WrongBoothId;
import com.hallym.festival.global.exception.WrongLikesKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class LikeServiceImpl implements LikeService{

    private final LikeRepository likeRepository;
    private final BoothRepository boothRepository;
    private final ModelMapper modelMapper;


    public String likeTrigger(Long bno, HttpServletRequest request,
                                   HttpServletResponse response) {
        Optional<Cookie> boothCookie = findBoothCookie(request, bno);
        if (boothCookie.isPresent()) { //쿠키가 있을 경우 삭제
            Cookie userCookie = boothCookie.get();
            String CookieKey = userCookie.getValue();
            delete(bno, CookieKey);
            Cookie keyCookie = new Cookie(bno.toString(), null);
            keyCookie.setMaxAge(0);
            keyCookie.setPath("/");
            response.addCookie(keyCookie);

            return "like delete success";
        }
        else { //쿠키가 없을 경우 추가
            LikesResponseDTO likes = createCookie(bno);
            Cookie keyCookie = new Cookie(bno.toString(), likes.getCookieKey());
            keyCookie.setMaxAge(14*60*60*24); // 2주일
            keyCookie.setPath("/");
            response.addCookie(keyCookie);
            return "like create success";
        }
    }

    @Override
    public PageResponseDTO<LikesResponseTopDTO> getTopLikeBoothList(PageRequestDTO pageRequestDTO) {
        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() <= 0? 0:
                        pageRequestDTO.getPage()-1,
                pageRequestDTO.getSize());

        Page<Booth> result = boothRepository.listTopLikeBooth(pageable);
        List<LikesResponseTopDTO> dtoList = result.getContent()
                .stream()
                .map(this::BoothToLikesTopResponseDto)
                .collect(Collectors.toList());

        return PageResponseDTO.<LikesResponseTopDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int)result.getTotalElements())
                .build();
    }

    private LikesResponseTopDTO BoothToLikesTopResponseDto(Booth booth) {
        return LikesResponseTopDTO.builder()
                .bno(booth.getBno())
                .booth_title(booth.getBooth_title())
                .like_cnt(booth.getLikes().size())
                .build();
    }


    private LikesResponseDTO createCookie(Long boothId) {
        Optional<Booth> byId = boothRepository.findById(boothId);
        if (byId.isEmpty()) {
            throw new WrongBoothId();
        }
        String newCookieKey = createCookieKey();
        Likes likes = Likes.builder().booth(byId.get()).cookieKey(newCookieKey).build();
        Likes newLikes = likeRepository.save(likes);
        return modelMapper.map(newLikes, LikesResponseDTO.class);
    }

    private void delete(Long boothId, String cookieKey) {
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

    public int getCount(Long boothId) {
        int LikeCountByBoothId = likeRepository.countLikesByBooth_bno(boothId);
        return LikeCountByBoothId;
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


    private Optional<Cookie> findBoothCookie(HttpServletRequest request, Long id) {
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
}
