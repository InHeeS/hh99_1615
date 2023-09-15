package com.example.ali.config;

import com.example.ali.jwt.JwtAuthenticationFilter;
import com.example.ali.jwt.JwtAuthorizationFilter;
import com.example.ali.jwt.JwtUtil;
import com.example.ali.security.UserDetailsServiceImpl;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity // Spring Security 지원을 가능하게 함
//@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;
    private final AuthenticationConfiguration authenticationConfiguration;

    public WebSecurityConfig(JwtUtil jwtUtil, UserDetailsServiceImpl userDetailsService, AuthenticationConfiguration authenticationConfiguration) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.authenticationConfiguration = authenticationConfiguration;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtUtil);
        filter.setAuthenticationManager(authenticationManager(authenticationConfiguration));
        return filter;
    }

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() {
        return new JwtAuthorizationFilter(jwtUtil, userDetailsService);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // CSRF 설정
        http.csrf((csrf) -> csrf.disable());

        // 기본 설정인 Session 방식은 사용하지 않고 JWT 방식을 사용하기 위한 설정
        http.sessionManagement((sessionManagement) ->
            sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        http.authorizeHttpRequests((authorizeHttpRequests) ->
                                              authorizeHttpRequests
                                                  .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll() // resources 패키지 접근 허용 설정
                                                  .requestMatchers(
                                                      "/api/auth/**",
                                                      /* swagger v3 */
                                                      "/v3/api-docs/**",
                                                      "/swagger-ui/**").permitAll() // '/api/user/'로 시작하는 요청 모두 접근 허가
                                                  .anyRequest().authenticated()

        );
//        http.exceptionHandling(a-> a.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))); //예외처리


//        http.formLogin((formLogin) ->
//            formLogin
//                .loginPage("/api/user/login-page").permitAll()
//        );

        // 폼로그인 비활성화
        http.formLogin(AbstractHttpConfigurer::disable);

        // 필터 관리
        http.addFilterBefore(jwtAuthorizationFilter(), JwtAuthenticationFilter.class);
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

//        // 접근 불가 페이지
//        http.exceptionHandling((exceptionHandling) ->
//                exceptionHandling
//                        // "접근 불가" 페이지 URL 설정
//                        .accessDeniedPage("/forbidden.html")
//        );

        return http.build();
    }
}