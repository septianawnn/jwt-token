package com.my.project.model;

import com.my.project.util.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author pi
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "login_attempt", columnDefinition = "int4 default 0")
    private Integer loginAttempt = 0;

    @Column(name = "lock_until")
    private LocalDateTime lockUntil;

    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(name = "user_roles",
            uniqueConstraints = {
                    @UniqueConstraint(name = "uk_user_roles", columnNames = {"user_id", "role_id"})
            },
            joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles = new ArrayList<>();
}
