package com.portoflio.backend.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portoflio.backend.model.UserPortfolio;
import com.portoflio.backend.repository.UserPortfolioRepository;
import com.portoflio.backend.security.util.JwtUtil;
import com.portoflio.backend.security.model.AuthCredential;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
@AllArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtUtil jwtUtil;
    private final UserPortfolioRepository userPortfolioRepository;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        AuthCredential authCredential;
        String username;
        String password;

        try {
            authCredential = new ObjectMapper().readValue(request.getInputStream(), AuthCredential.class);
            username = authCredential.getEmail();
            password = authCredential.getPassword();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
        return getAuthenticationManager().authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        User user = (User) authResult.getPrincipal();
        UserPortfolio userPortfolio = userPortfolioRepository.findByEmail(user.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("No existe un usuario con email " + user.getUsername()));

        Map<String, Object> httpResponse = new HashMap<>();

        if( !userPortfolio.isVerified() ) {
            httpResponse.put("message", "Autenticación fallida, verifique la cuenta");
            httpResponse.put("status", 400);
        }else {
            String token = jwtUtil.generateAccessToken(userPortfolio);

            response.addHeader("Authorization", token);

            httpResponse.put("token", token);
            httpResponse.put("message", "Autenticación correcta");
            httpResponse.put("status", 200);
        }

        response.getWriter().write(new ObjectMapper().writeValueAsString(httpResponse));
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().flush();

        super.successfulAuthentication(request, response, chain, authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {

        Map<String, Object> httpResponse = new HashMap<>();
        httpResponse.put("message", "Autenticación fallida, credenciales invalidas");
        httpResponse.put("status", 400);

        response.getWriter().write(new ObjectMapper().writeValueAsString(httpResponse));
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().flush();
    }
}
