package com.example.nalsam.user.service;

import com.example.nalsam.user.dto.request.*;
import com.example.nalsam.user.dto.response.UserResponse;
import com.example.nalsam.user.exception.PasswordNotCorrectException;
import com.example.nalsam.user.exception.UserAlreadyExistException;
import com.example.nalsam.user.exception.UserNotFoundException;
import com.example.nalsam.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.nalsam.user.domain.Users;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder encoder;

//    private final BCryptPasswordEncoder encoder;


    //회원 저장 기능 test
    public Users getUserTest(TestRequest request){
        Users users = userRepository.findByLoginId(request.getLoginId()).get();

        return users;
    }

    // 회원 저장 기능
    public void saveUserProfile(UserRequest userRequest){

        if(userRepository.existsByLoginId(userRequest.getLoginId())){
            throw new UserAlreadyExistException();
        }

        String encPwd = encoder.encode(userRequest.getPassword());

        LocalDateTime localDateTime = LocalDateTime.now();

        Integer testOxygen = 90;   //산소포화도 테스트 데이터
        Integer testHeartRate = 80; //심박수 테스트 데이터

        Users users = Users.builder()
                .loginId(userRequest.getLoginId())
                .password(encPwd)
                .name(userRequest.getUserName())
                .birthDate(userRequest.getBirthDate())
                .isMale(userRequest.getIsMale())
                .heartRate(testHeartRate)
                .oxygenSaturation(testOxygen)
                .symptom(userRequest.getSymptom())
                .createDateTime(localDateTime)
                .updateDateTime(localDateTime)
                .build();

        userRepository.save(users);
    }

    // 회원 조회 기능
    //  로그인ID 로 조회하기.
    public UserResponse showUserInfoById(String loginId){

        Users users = userRepository.findByLoginId(loginId).orElseThrow();

        return UserResponse.builder()
                .loginId(users.getLoginId())
                .userName(users.getName())
                .birthDate(users.getBirthDate())
                .isMale(users.getIsMale())
                .symptom(users.getSymptom())
                .build();
    }

    // 회원 조회 기능
    //  모든 회원 조회하기.
    public List<UserResponse> showUserInfoAll(){
        List<Users> getUsers = userRepository.findAll();

        List<UserResponse> getUserInfo = getUsers.stream()
                .map(user -> UserResponse.builder()
                        .loginId(user.getLoginId())
                        .userName(user.getName())
                        .birthDate(user.getBirthDate())
                        .isMale(user.getIsMale())
                        .symptom(user.getSymptom())
                        .build())
                .collect(Collectors.toList());

        return getUserInfo;
    }

    // 회원 정보 업데이트 - password
    @Transactional
    public void updateUserPassword(UserPasswordRequest request){
        if(!userRepository.existsByLoginId(request.getLoginId())){
            throw new UserNotFoundException();
        }

        else if(!request.getPassword().equals(userRepository.findByLoginId(request.getLoginId()).get().getPassword())){
            throw new PasswordNotCorrectException();
        }

        if(request.getNewPassword().equals(request.getAgainNewPassword())){
            Users users = userRepository.findByLoginId(request.getLoginId()).get();
            LocalDateTime now = LocalDateTime.now();
            users.updatePassword(request.getNewPassword(), now);
        }
    }

    // 회원 삭제 기능
    public void deleteUser(UserDeletionRequest request){
        if(!userRepository.existsByLoginId(request.getLoginId())){
            throw new UserNotFoundException();
        }

        else if(!request.getPassword().equals(userRepository.findByLoginId(request.getLoginId()).get().getPassword())){
            throw new PasswordNotCorrectException();
        }

        Users users = userRepository.findByLoginId(request.getLoginId()).get();

        userRepository.delete(users);
    }

    // 로그인 ID 로 회원 찾기.
    public Users findUserByLoginId(String userLoginId) {

        return userRepository.findByLoginId(userLoginId).orElse(null);

    }

    // 아이디 비밀번호 체크. 오류시 exception 발생
    @Transactional
    public void checkUserInfo(LoginRequest request){
        if(!userRepository.existsByLoginId(request.getLoginId())){
            throw new UserNotFoundException();
        }

        Users users = findUserByLoginId(request.getLoginId());

        if(!encoder.matches(request.getPassword(), users.getPassword())){
            throw new PasswordNotCorrectException();
        }

    }
}
