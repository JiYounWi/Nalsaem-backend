package com.example.nalsam.user.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.stream.Collectors;

/*
토큰을 생성하고 검증하는 컴포넌트를 완성했지만
실제로 이 컴포넌트를 이용하는 것은 인증 작업을 진행하는 Filter 입니다.
이 필터는 검증이 끝난 JWT로부터 유저정보를 받아와서
UsernamePasswordAuthenticationFilter 로 전달해야 할 것입니다.
 */

@Component
@Slf4j
public class JwtTokenProvider {

    private final Key secretKey;

    private final long expirationHours;

    private final String issuer;

//    private final CustomUserDetailService customUserDetailService;

    public JwtTokenProvider(@Value("${jwt-secret-key}") String secretKey,
                            @Value("${jwt-expiration-hours}")long expirationHours,
                            @Value("${jwt-issuer}") String issuer
                            ){
//                            CustomUserDetailService customUserDetailService) {
//        this.secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
        this.expirationHours = expirationHours;
        this.issuer = issuer;
//        this.customUserDetailService = customUserDetailService;
    }

    //jwt 생성
    public JwtToken createToken(Authentication authentication){
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

//        Claims claims = Jwts.claims().setSubject(loginId);

        // Access Token 생성
        String accessToken = Jwts.builder()
                .signWith(secretKey,SignatureAlgorithm.HS256)
                .setSubject(authentication.getName())
                .claim("auth", authorities)  //정보저장
//                .setClaims(claims)
                .setIssuer(issuer)  //토큰 발급자
                .setIssuedAt(Timestamp.valueOf(LocalDateTime.now()))    //발급시각
                .setExpiration(Date.from(Instant.now().plus(expirationHours, ChronoUnit.HOURS)))    //만료 시각
                .compact();

        // Refresh Token 생성
        String refreshToken = Jwts.builder()
                .setExpiration(Date.from(Instant.now().plus(expirationHours*8*30,ChronoUnit.HOURS)))
                .signWith(secretKey,SignatureAlgorithm.HS256)
                .compact();

        return JwtToken.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    // JWT 에서 인증 정보 조회
    public Authentication getAuthentication(String accessToken){
        // 토큰 복호화 하기
        Claims claims = parseClaims(accessToken);

        if(claims.get("auth") == null){
            throw new RuntimeException("권한 정보가 없는 토큰입니다");
        }

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("auth").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        UserDetails principal = new User(claims.getSubject(),"",authorities);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    // 토큰 유효성(access) 및 만료일자 확인
    public boolean validateToken(String jwtToken){
        try{
//            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(jwtToken);
            return true;
        } catch (ExpiredJwtException e){
            log.info("Expired Jwt Token",e);
        } catch (UnsupportedJwtException e){
            log.info("Unsupported Jwt Token",e);
        } catch (IllegalArgumentException e){
            log.info("Jwt claims String is empty",e);
        }
        return false;
    }

    private Claims parseClaims(String accessToken){
        try{
//            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(accessToken).getBody();
            return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e){
            return e.getClaims();
        }
    }

    public String getAccessToken(HttpServletRequest request){
        return request.getHeader("Authorization");
    }

    //토큰에서 회원 추출
    public String getUserLoginId(String token){
        String accessToken = token.split("Bearer ")[1];
//        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(accessToken).getBody().getSubject();
    }

}
