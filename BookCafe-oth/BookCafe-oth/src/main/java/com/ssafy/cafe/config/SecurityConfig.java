package com.ssafy.cafe.config;


import com.ssafy.cafe.config.jwt.JwtAuthenticationFilter;
import com.ssafy.cafe.config.jwt.JwtAuthorizationFilter;
import com.ssafy.cafe.model.dao.UserDao;
import lombok.RequiredArgsConstructor;




import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor //@RequiredArgsConstructor는 Lombok에서 제공하는 어노테이션으로,
//클래스의 final 필드 또는 @NonNull이 붙은 필드를 매개변수로 받는 생성자를 자동으로 생성해 줍니다.
public class SecurityConfig  {
    private final AuthenticationConfiguration authenticationConfiguration;
    private final UserDao userRepository;

    private final CorsConfig corsConfig;
//    private final MyFilterBefor myFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,AuthenticationManager authenticationManager) throws Exception {

        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager(authenticationConfiguration));
        jwtAuthenticationFilter.setFilterProcessesUrl("/rest/user/login");  //
        return http
                .csrf(csrf -> csrf.disable()) // CSRF 비활성화 (JWT 사용 시 필요)
//                .addFilterBefore(myFilter, SecurityContextHolderFilter.class) // 내가 제작한 필터가 BasicAuthenticationFilter 가등장 전에 적용된다라는 의미 이런걸 수정확인 위해서는
                //시큐리티 필터들을 순서를 확인해야한다 위처럼 사용해도 되고 Bean 을 만들어 사용할수 있다 FilterConfig 에 있음
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 사용 안 함
                .addFilter(corsConfig.corsFilter()) //필터 적용
                .addFilterAt(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAt(new JwtAuthorizationFilter(authenticationManager(authenticationConfiguration),userRepository),JwtAuthenticationFilter.class)
                .formLogin(form -> form.disable()) // 폼 로그인 비활성화
                .httpBasic(basic -> basic.disable()) // HTTP Basic 인증 비활성화
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers("/idcheck", "/rest/user/login", "/rest/user/**","/rest/payment/**").permitAll()
//                                .requestMatchers("/rest/user/**").hasAnyRole("USER", "MANAGER", "ADMIN")
                                .requestMatchers("/rest/product/**","/rest/order/**","/rest/book/**","/rest/gpt/**").hasAnyRole("USER", "MANAGER")
                                .requestMatchers("/rest/manager/**").hasRole("MANAGER")
                                .anyRequest().permitAll() // 그 외 경로는 인증 없이 접근
                        // 가능
                )
                .build();




    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }




    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {

        return configuration.getAuthenticationManager();
    }


}