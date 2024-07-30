package com.my.project.service;

import com.my.project.dto.RoleDto;
import com.my.project.mapper.RoleMapper;
import com.my.project.model.Role;
import com.my.project.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author pi
 */
@Service
public class RoleService {
    @Autowired
    RoleRepository repository;
    @Autowired
    RoleMapper mapper;

    public List<RoleDto> getAll (){
        return repository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    public RoleDto create (RoleDto input){
        Role data = mapper.toEntity(input);
        repository.save(data);
        return mapper.toDto(data);
    }

}
