package com.my.project.config;

import lombok.Data;

import java.io.Serializable;

/**
 * @author pi
 */
@Data
public class JwtResponse implements Serializable {
    private final String token;
}
