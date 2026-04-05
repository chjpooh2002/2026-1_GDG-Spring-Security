package com.gdghongik.springsecurity.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .formLogin(formLogin -> formLogin.disable() ) // 기본 로그인 페이지 안 씀
                .httpBasic(httpBasic -> httpBasic.disable())   // 브라우저 팝업 로그인 안 씀
                .csrf(csrf->csrf.disable())
                .sessionManagement(session->session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))         // 필요할 때만 세션 생성
                .authorizeHttpRequests(auth->auth
                        .requestMatchers("/auth/signup","/auth/login").permitAll()  // 회원가입/로그인 허용
                        .requestMatchers("/crud/members/**").hasAnyRole("REGULAR")  // ROLE_REGULAR 권한 있어야 접근 가능
                        .anyRequest().authenticated());  // 위에 안 걸린 모든 요청은 로그인 되어 있어야 접근 가능

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
