package com.my.project.mapper;

import com.my.project.dto.RoleDto;
import com.my.project.model.Role;
import org.mapstruct.Mapper;

/**
 * @author pi
 */
@Mapper(componentModel = "spring")
public interface RoleMapper{
    Role toEntity (RoleDto dto);
    RoleDto toDto (Role entity);
}
