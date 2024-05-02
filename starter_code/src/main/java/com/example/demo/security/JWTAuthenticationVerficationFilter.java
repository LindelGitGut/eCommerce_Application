package com.example.demo.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;


//Diese Klasse ist für die Authorisierung verantwortlich (rechte etc), sprich wenn ein Request reinkommt wir dder Header nach dem Secret Gescannt
// (internal Filter)


public class JWTAuthenticationVerficationFilter extends BasicAuthenticationFilter {



    private static final Logger log = LoggerFactory.getLogger(JWTAuthenticationVerficationFilter.class.getSimpleName());

    private static final String USER_NOT_EXIST_ERROR = "User doesn't exist";
    private static final String WRONG_AUTH_TOKEN_ERROR = "Authorization Token is Wrong";

    private static final String NO_TOKEN_ERROR ="No Authorization Token found!";


    public JWTAuthenticationVerficationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        String header = request.getHeader(SecurityConstants.HEADER_STRING);

        if (header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)){
            log.warn(WRONG_AUTH_TOKEN_ERROR);
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authenticationToken = usernamePasswordAuthenticationToken(request);

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken (HttpServletRequest request){
        String token = request.getHeader(SecurityConstants.HEADER_STRING);
        if (token != null){
            String user = JWT.require(Algorithm.HMAC512(SecurityConstants.SECRET.getBytes())).build()
                    .verify(token.replace(SecurityConstants.TOKEN_PREFIX, ""))
                    .getSubject();

            if (user != null){
                log.warn(USER_NOT_EXIST_ERROR);
                return new UsernamePasswordAuthenticationToken(user,null,new ArrayList<>());
            }
            return null;
        }
        log.warn(NO_TOKEN_ERROR);
        return null;
    }
}
