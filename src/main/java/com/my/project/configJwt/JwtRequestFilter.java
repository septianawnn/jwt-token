package com.my.project.configJwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author pi
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    // class ini merupakan filter yang dijalankan sekali per permintaan (extend 'OncePerRequestFilter')
    // menangkap setiap permintaan HTTP, mengambil token JWT dari header, dan mengautentikasi pengguna jika token valid.
    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";

    @Autowired
    private JwtTokenService jwtTokenService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // fungsi ini mengambil token dari request, memvalidasi token, dan mengatur 'SecurityContext' jika token valid.

        final Optional<String> jwt = getJwtFromRequest(request);
        jwt.ifPresent(token -> {
            try {
                if (jwtTokenService.validateToken(token)) {
                    setSecurityContext(new WebAuthenticationDetailsSource().buildDetails(request), token);
                }
            } catch (IllegalArgumentException | MalformedJwtException | ExpiredJwtException e) {
                logger.error("Unable to get JWT Token or JWT Token has expired");
                //UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken("anonymous", "anonymous", null);
                //SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        });

        filterChain.doFilter(request, response);
    }

    private void setSecurityContext(WebAuthenticationDetails authDetails, String token) {
        final String username = jwtTokenService.getUsernameFromToken(token);
        final List<String> roles = jwtTokenService.getRoles(token);
        final UserDetails userDetails = new User(username, "", roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
        final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
                userDetails.getAuthorities());
        authentication.setDetails(authDetails);
        // After setting the Authentication in the context, we specify
        // that the current user is authenticated. So it passes the
        // Spring Security Configurations successfully.
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private static Optional<String> getJwtFromRequest(HttpServletRequest request) {
        // fungsi ini mengambil token JWT dari header request
        String bearerToken = request.getHeader(AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER)) {
            return Optional.of(bearerToken.substring(7));
        }
        return Optional.empty();
    }

}
