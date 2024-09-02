package com.my.project.configJwt;

import com.my.project.model.Role;
import com.my.project.model.User;
import com.my.project.repository.RoleRepository;
import com.my.project.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author pi
 */
@Component
public class JwtTokenService implements Serializable {
    // class ini berfungsi untuk menghasilkan, mengambil informasi, dan memvalidasi token JWT.
    // menggunakan pustaka 'io.jsonwebtoken' untuk menangani pembuatan dan parsing token jwt.
    private static final long serialVersionUID = -2550185165626007488L;

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
    public static final String ROLES = "ROLES";
    @Autowired UserRepository userRepository;
    @Autowired RoleRepository roleRepository;

    @Value("${jwt.secret}")
    private String secret;

    //retrieve username from jwt token
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    //retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public List<String> getRoles(String token) {
        return getClaimFromToken(token, claims -> (List) claims.get(ROLES));
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }
    //for retrieving any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    //check if the token has expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    //generate token for user
    // menghasilkan token berdasarkan klaim tertentu, seperti peran pengguna dan informasi lainnya.
    public JwtResponse generateToken(Authentication authentication) {
        final Map<String, Object> claims = new HashMap<>();
        final UserDetails user = (UserDetails) authentication.getPrincipal();

        final List<String> roles = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        User pengguna = userRepository.findByUsername(user.getUsername());
        claims.put(ROLES, roles);
        claims.put("id", pengguna.getId());
        claims.put("username", pengguna.getUsername());
        claims.put("name", pengguna.getName());
        claims.put("roles", pengguna.getRoles().stream().map(Role::getRoleName).collect(Collectors.toList()));

        String token = generateToken(claims, user.getUsername(), user.getPassword());
        claims.put("access_token", token);
        claims.put("token_type", "bearer");

        Map<String, Object> userInfo = getUserInfo(pengguna);
        return new JwtResponse(token, "Bearer", userInfo);
    }

    //while creating the token -
    //1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
    //2. Sign the JWT using the HS512 algorithm and secret key.
    //3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
    //   compaction of the JWT to a URL-safe string
    private String generateToken(Map<String, Object> claims, String subject, String audience) {
        final long now = System.currentTimeMillis();
        return Jwts.builder()
                .setClaims(claims)
                .setAudience(audience)
                .setSubject(subject)
                //                .setPayload()
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    //validate token
    // memvalidasi apakah token valid (tidak kadaluarsa dan memiliki username yang valid)
    public Boolean validateToken(String token) {
        final String username = getUsernameFromToken(token);
        return username != null && !isTokenExpired(token);
    }

    private Map<String, Object> getUserInfo(User user) {
        Role role = roleRepository.findById(user.getRoleId()).orElse(null);
        Map<String, Object> additionalInfo = new HashMap<>();
        additionalInfo.put("user_id", user.getId());
        additionalInfo.put("roles", role.getRoleName());
        additionalInfo.put("role_id", user.getRoleId());
        additionalInfo.put("user_name", user.getUsername());
        additionalInfo.put("userName", user.getUsername());
        additionalInfo.put("email", user.getEmail());
        return additionalInfo;
    }

}
