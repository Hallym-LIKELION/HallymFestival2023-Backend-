//package com.hallym.festival.domain.Users.controller;
//
//import com.hallym.festival.domain.Users.dto.APIUserDTO;
//import com.hallym.festival.global.security.APIUserDetailsService;
//import com.hallym.festival.global.security.util.JWTUtil;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api/auth")
//@Log4j2
//public class APIUserController {
//
//    private final APIUserDetailsService userDetailsService;
//    private final AuthenticationManager authenticationManager;
//    private final JWTUtil jwtUtil;
//
//
//    @PostMapping("/login")
//    public ResponseEntity<Map<String, Object>> login(@RequestBody APIUserDTO userDTO) {
//
//        try {
//            authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(userDTO.getMid(), userDTO.getPassword())
//            );
//
//            UserDetails userDetails = userDetailsService.loadUserByUsername(userDTO.getMid());
//
//            Map<String, Object> valueMap = new HashMap<>(); //payload로 지정된 값
//            valueMap.put("username", userDetails.getUsername());
//            valueMap.put("role", userDetails.getAuthorities().stream().findFirst().orElseThrow().getAuthority());
//
//            String token = jwtUtil.generateToken(valueMap, 1);
//
//            HttpHeaders headers = new HttpHeaders();
//            headers.setBearerAuth(token);
//
//            return ResponseEntity.ok()
//                    .headers(headers)
//                    .body(valueMap);
//
//        } catch (Exception e) {
//            log.error("login error: {}", e.getMessage());
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }
//    }
//
//
//    @GetMapping("/hello")
//    @PreAuthorize("hasRole('ROLE_USER')")
//    public ResponseEntity<String> hello(Authentication authentication) {
//        return ResponseEntity.ok("Hello, " + authentication.getName());
//    }
//
//}