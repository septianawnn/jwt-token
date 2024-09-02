package com.my.project.configJwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author pi
 */
@Component
@Slf4j

public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    // class ini berfungsi untuk mengatur response yang diberikan ketika permintaan tidak terauntetikasi
    // atau tidak memiliki akses yang sah. Ini digunakan untuk mengembalikan response "Unauthorized (HTTP 401)"
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        // Mengirimkan respons kesalahan "Unauthorized" jika permintaan tidak terotentikasi.
        log.warn("Responding with unauthorized error. Message - {}", authException.getMessage());
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Sorry, You're not authorized to access this resource.");
    }
}
