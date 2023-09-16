package com.example.ali.jwt;

import com.example.ali.entity.UserRoleEnum;
import com.example.ali.security.UserDetailsServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j(topic = "JWT 검증 및 인가")
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;

    public JwtAuthorizationFilter(JwtUtil jwtUtil, UserDetailsServiceImpl userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {

        String accessTokenValue = jwtUtil.getTokenFromRequest(req, "Authorization");
        String refreshTokenValue = jwtUtil.getTokenFromRequest(req, "AuthorizationR");

        if (!StringUtils.hasText(accessTokenValue)) {
            if (StringUtils.hasText(refreshTokenValue)) {
                validateRefreshToken(res,refreshTokenValue);
            }
        }else {
            accessTokenValue = jwtUtil.substringToken(accessTokenValue);
            if (!jwtUtil.validateToken(accessTokenValue)) {
                if (StringUtils.hasText(refreshTokenValue)) {
                    validateRefreshToken(res, refreshTokenValue);
                } else {
                    log.error("Token Error");
                    return;
                }
            } else { //토큰 인증
                Claims info = jwtUtil.getUserInfoFromToken(accessTokenValue);
                try {
                    setAuthentication(info.getSubject());
                } catch (Exception e) {
                    log.error(e.getMessage());
                    return;
                }
            }
        }
        filterChain.doFilter(req, res);
    }



    public void validateRefreshToken(HttpServletResponse res, String refreshTokenValue) throws ServletException, IOException {

        refreshTokenValue = jwtUtil.substringToken(refreshTokenValue);
        log.info(refreshTokenValue);
        if (jwtUtil.validateToken(refreshTokenValue)) {
            Claims info = jwtUtil.getUserInfoFromToken(refreshTokenValue);
            String username = info.getSubject();
            String authority = info.get("auth", String.class);
            UserRoleEnum role;
            if (authority.equals("SELLER")) {
                role = UserRoleEnum.SELLER;
            } else {
                role = UserRoleEnum.USER;
            }
            String accessToken = jwtUtil.createToken(username, role, 1);
            jwtUtil.addJwtToCookie(accessToken, res, "Authorization");
        }
        //토큰 인증
        Claims info = jwtUtil.getUserInfoFromToken(refreshTokenValue);
        try {
            setAuthentication(info.getSubject());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    // 인증 처리
    public void setAuthentication(String username) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = createAuthentication(username);
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }

    // 인증 객체 생성
    private Authentication createAuthentication(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

}

