package com.example.nalsam.user.controller;

import com.example.nalsam.user.domain.Users;
import com.example.nalsam.user.dto.request.TestRequest;
import com.example.nalsam.user.dto.request.UserDeletionRequest;
import com.example.nalsam.user.dto.request.UserPasswordRequest;
import com.example.nalsam.user.dto.request.UserRequest;
import com.example.nalsam.user.dto.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.nalsam.user.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    // < 회원 저장 TEST >
    @PostMapping("/test")
    public ResponseEntity<Users> createUserTest(@RequestBody TestRequest testRequest){

        return ResponseEntity.ok().body(userService.getUserTest(testRequest));
    }

    // < 회원 저장 API >
    @PostMapping("/save")
    public ResponseEntity<Void> createUser(@RequestBody UserRequest userRequest){

        userService.saveUserProfile(userRequest);

        return ResponseEntity.noContent().build();
    }

    // < 회원 조회 API >
    // 회원 로그인ID 로 조회
    @GetMapping("/show")
    public ResponseEntity<UserResponse> showUserInfo(@RequestParam("id") String loginId){
        return ResponseEntity.ok(userService.showUserInfoById(loginId));
    }

    // 회원 전체 조회
    @GetMapping("/show/all")
    public ResponseEntity<List<UserResponse>> showUserInfoAll(){
        return ResponseEntity.ok(userService.showUserInfoAll());
    }

    // < 회원 수정 API >
    // password 수정
    @PutMapping("/update/password")
    public ResponseEntity<Void> updateUserPassword(@RequestBody UserPasswordRequest request){

        userService.updateUserPassword(request);

        return ResponseEntity.noContent().build();
    }

    // < 회원 삭제 API >
    // 아이디 패스워드 일치시.
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteUser(@RequestBody UserDeletionRequest request){

        userService.deleteUser(request);

        return ResponseEntity.noContent().build();
    }
}
