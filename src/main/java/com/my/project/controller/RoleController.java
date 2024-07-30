package com.my.project.controller;

import com.my.project.dto.RoleDto;
import com.my.project.dto.UserDto;
import com.my.project.model.Role;
import com.my.project.service.RoleService;
import com.my.project.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * @author pi
 */
@RestController
@RequestMapping("/api/role")
public class RoleController {

    @Autowired
    RoleService roleService;

    @GetMapping()
    public ResponseEntity<?> getAll (){
        List<RoleDto> get = roleService.getAll();
        return new ResponseEntity<>(new ApiResponse<>("200", "Success", get), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> create (@RequestBody RoleDto input){
        try {
            RoleDto create = roleService.create(input);
            return new ResponseEntity<>(new ApiResponse<>("200", "Success", create), HttpStatus.OK);
        }catch (Exception e){
         throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
        }
    }
}
