package com.my.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author pi
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRolesDto implements Serializable {
    private Long userId;
    private Long roleId;
}
