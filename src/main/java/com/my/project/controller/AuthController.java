package com.my.project.controller;

import com.my.project.configJwt.JwtResponse;
import com.my.project.configJwt.JwtTokenService;
import com.my.project.dto.UserDto;
import com.my.project.service.UserService;
import com.my.project.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * @author pi
 */
@RestController
@CrossOrigin
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private JwtTokenService jwtTokenUtil;
    @Autowired private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody UserDto authenticationRequest) throws Exception {
        final Authentication auth = authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        SecurityContextHolder.getContext().setAuthentication(auth);
        JwtResponse jwtResponse = jwtTokenUtil.generateToken(auth);
        return ResponseEntity.ok(jwtResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<?> saveUser(@RequestBody UserDto user) throws Exception {
        return ResponseEntity.ok(userService.create(user));
    }

    private Authentication authenticate(String username, String password) throws Exception {
        try {
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    @GetMapping()
    public ResponseEntity<?> getAll (){
        try {
            List<UserDto> result = userService.getAll();
            return new ResponseEntity<>(new ApiResponse<>("200", "Success", result), HttpStatus.OK);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
        }
    }

}
