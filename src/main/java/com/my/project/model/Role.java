package com.my.project.model;

import com.my.project.util.AuditEntity;
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
@Table(name = "m_roles")
public class Role extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role_name", nullable = false)
    private String roleName;
}
