package com.my.project.mapper;

import com.my.project.dto.UserDto;
import com.my.project.model.User;
import org.mapstruct.Mapper;

/**
 * @author pi
 */
@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity (UserDto dto);
    UserDto toDto (User entity);
}
