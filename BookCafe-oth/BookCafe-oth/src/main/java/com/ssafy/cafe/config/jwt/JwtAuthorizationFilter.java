package com.ssafy.cafe.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.ssafy.cafe.config.auth.PrincipalDetails;
import com.ssafy.cafe.model.dao.UserDao;
import com.ssafy.cafe.model.dto.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;

//인증 필터
// 시큐리티 필터 중에 basicAuthenticationFilter 것이 있음
// 이 필터는 권한이나 인증이 필요한 특정 주소를 요청했을때 위 필터를 거쳐야한다
//인증이 필요한 주소가 아니면 이 필터를 통과하지 않는다.
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final UserDao userRepository;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserDao userRepository) {
        super(authenticationManager);
        this.userRepository = userRepository;

    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {


//        if (request.getRequestURI().startsWith("/rest/user/login")||request.getRequestURI().startsWith("/rest/user")||request.getRequestURI().startsWith("/login")) {
//
//            chain.doFilter(request, response);
//            return;
//        }

        System.out.println("인증 권한 요구된 요청입니다.");
//        super.doFilterInternal(request, response, chain);

        String jwtHeader = request.getHeader("Authorization"); //토큰
        System.out.println(jwtHeader + ": jwt 헤더이다");


        //헤더가 있는지 확인
        if (jwtHeader == null || !jwtHeader.startsWith("Bearer")) {
            chain.doFilter(request, response);
            return;
        }

        //받은 토큰으로 정상적인 사용자 인지 확인한다.
        String jwtToken = request.getHeader("Authorization").replace("Bearer ", ""); //토큰만 꺼내기

        String username = JWT.require(Algorithm.HMAC512("code")).build().verify(jwtToken)
                .getClaim("id").asString();
        System.out.println(username+" 유저 이름");
        if (username != null) {
            User user = userRepository.selectById(username);

            // 인증은 토큰 검증시 끝. 인증을 하기 위해서가 아닌 스프링 시큐리티가 수행해주는 권한 처리를 위해
            // 아래와 같이 토큰을 만들어서 Authentication 객체를 강제로 만들고 그걸 세션에 저장!
            PrincipalDetails principalDetails = new PrincipalDetails(user);
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    principalDetails, // 나중에 컨트롤러에서 DI해서 쓸 때 사용하기 편함.
                    null, // 패스워드는 모르니까 null 처리, 어차피 지금 인증하는게 아니니까!!
                    principalDetails.getAuthorities());

            // 강제로 시큐리티의 세션에 접근하여 값 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(request, response);
    }
}