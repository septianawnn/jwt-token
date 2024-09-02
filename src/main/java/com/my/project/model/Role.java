package com.my.project.model;

import com.my.project.util.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author pi
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "m_roles")
public class Role extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role_name", nullable = false)
    private String roleName;

    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(name = "user_roles",
            uniqueConstraints = {
                    @UniqueConstraint(name = "uk_user_roles", columnNames = {"user_id", "role_id"})
            },
            joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users = new ArrayList<>();
}
