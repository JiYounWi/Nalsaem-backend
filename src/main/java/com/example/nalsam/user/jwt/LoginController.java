package com.example.nalsam.user.jwt;

import com.example.nalsam.user.dto.request.LoginRequest;
import com.example.nalsam.user.dto.request.UserRequest;
import com.example.nalsam.user.dto.response.UserResponse;
import com.example.nalsam.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login") // http 서블릿 세션 로그인
    public ResponseEntity<JwtToken> login(@RequestBody LoginRequest loginRequest) {

//        System.out.println("1");
        JwtToken token = loginService.login(loginRequest);

        return ResponseEntity.ok().body(token);

    }

    // < 회원 저장 API >
    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@RequestBody UserRequest userRequest){

        loginService.createUser(userRequest);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/home") // http 서블릿 세션 로그아웃
    public ResponseEntity<UserResponse> home(HttpServletRequest request){

        String accessToken = jwtTokenProvider.getAccessToken(request);

        return ResponseEntity.ok().body(loginService.getUserResponse(accessToken));
    }


    @PostMapping("/mainTest") // http 서블릿 세션 로그아웃
    public ResponseEntity<UserResponse> main(){

        return ResponseEntity.ok().body(loginService.mainTest());
    }



//    @PostMapping("/main2") // http 서블릿 세션 로그아웃
//    public ResponseEntity<UserResponse> main2(HttpServletRequest request){
//
//        request.getHeader("Authorization");
//
//        return ResponseEntity.ok().body(loginService.main2());
//    }
}