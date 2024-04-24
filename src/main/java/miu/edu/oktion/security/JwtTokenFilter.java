package miu.edu.oktion.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import miu.edu.oktion.service.security.JwtUserDetailService;
import miu.edu.oktion.util.JwtTokenUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenUtil tokenUtil;
    //private final UserDetailsService userDetailsService;
    private final JwtUserDetailService jwtUserDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.split(" ")[1];
        boolean result = tokenUtil.validateToken(token);
        if (!result) {
            filterChain.doFilter(request, response);
            return;
        }

        Claims claims = tokenUtil.parseClaims(token);
        String subject = claims.getSubject(); //1234,test@miu.edu
        String[] info = subject.split(",");
        String email = info[1];
        UserDetails userDetails = jwtUserDetailService.loadUserByUsername(email);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);

        filterChain.doFilter(request, response);
    }

}
