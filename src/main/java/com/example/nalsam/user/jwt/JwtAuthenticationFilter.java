package com.example.nalsam.user.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.security.core.Authentication;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/*
Jwt 인증을 위해 생성되는 토큰. 요청과 함께 바로 실행됨.
요청이 들어오면 헤더에서 토큰 추출.
그리고 유효성 검사.
 */

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        System.out.println("a");
        // Header 에서 jwt 받아오기
        String token = resolveToken((HttpServletRequest) request);
//        System.out.println("b");
        // 유효한 토큰인지 검증 (accessToken을 가져와 복호화하고 검증)
        // 검증이 모두 끝나면 토큰을 인증받은 유저인 UsernamePasswordAuthenticationToken을 리턴
        // 과정이 끝나면, 이 유저는 토큰이 유효한 유저임이 증명되고 SecurityContext 에 저장
        if(token != null && jwtTokenProvider.validateToken(token)){
//            System.out.println("c");
            // 유효한 토큰이면 토큰으로부터 유저정보 받아오기.
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
//            System.out.println("d");
            // SecurityContext 에 Authentication 객체 저장.
            SecurityContextHolder.getContext().setAuthentication(authentication);
//            System.out.println("e");
        }
//        System.out.println("f");
        chain.doFilter(request,response);
    }

    //Request의 Header에서 token 값 가져오기.
    public String resolveToken(HttpServletRequest request){

//        System.out.println("A");
        //Authorization 이 Bearer 임을 확인
        String bearerToken = request.getHeader("Authorization");
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")){
//            System.out.println("B");
            return bearerToken.substring(7);
        }
//        System.out.println("C");
        return null;
    }
}
