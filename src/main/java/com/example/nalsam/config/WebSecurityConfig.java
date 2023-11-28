package com.example.nalsam.config;

import com.example.nalsam.user.jwt.JwtAuthenticationFilter;
import com.example.nalsam.user.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/*
아무 설정도 되지 않은 기본 상태에서는 서버의 어디로 접속하더라도 위의 임시 로그인창에서 로그인을 진행해야 한다.
그런데 이 프로젝트에서는 권한이 없는 사용자가 API를 사용하지 못하도록 막기만 하면 되기 때문에
스웨거 문서에 접속할 때는 굳이 로그인을 할 필요가 없다.
따라서 이 부분에 대한 예외 처리가 필요하다.
 */

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private JwtTokenProvider jwtTokenProvider;

    public WebSecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

//    @Bean
//    public BCryptPasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                // 쿠키 기반이 아닌 jwt 기반이므로 사용x : disable 이유는 jwt를 쿠키에 저장하지 않기 때문에.
                .csrf().disable()
                // spring security 세션 정책 : 세션을 생성 하거나 사용 x
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .httpBasic(Customizer.withDefaults()) // HTTP 기본 인증 사용
                // 조건별 요청 허용,제한 설정
                .authorizeRequests(authorizeRequests ->
                                authorizeRequests
//                                          모두승인
                                        .antMatchers("/**").permitAll()
                                        .anyRequest().authenticated()
                )
                // JwtAuthenticationFilter를 UsernamePasswordAuthenticationFilter 전에 넣기
                // -> UsernamePasswordAuthenticationFilter 로 가기 전에 내가 직접 만든 JwtAuthenticationFilter를 실행하겠다.
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}