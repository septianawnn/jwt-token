package com.my.project.model;

import com.my.project.dto.UserRolesDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author pi
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@IdClass(UserRolesDto.class)
@Table(name = "user_roles")
public class UserRoles {
    @Id
    @Column(name = "user_id")
    private Long userId;

    @Id
    @Column(name = "role_id")
    private Long roleId;
}
