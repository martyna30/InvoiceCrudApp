package com.crud.invoices.controller;

import com.crud.invoices.domain.AppUser;
import com.crud.invoices.domain.AppUserDto;
import com.crud.invoices.mapper.UserMapper;
import com.crud.invoices.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/invoice")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    UserMapper userMapper;

    @Autowired
    AuthenticationManager authenticationManager;
    @GetMapping("getUser")
    public ResponseEntity<AppUserDto> getUser(@RequestParam Long userId) {
        try {
            return ResponseEntity.ok(userMapper.mapToUserDto(userService.getUser(userId).get()));
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("getAppUserByUsername")
    public ResponseEntity<AppUserDto> getAppUserByUsername(@RequestParam String username) {
        try {
            return ResponseEntity.ok(userMapper.mapToUserDto(userService.getAppUserByUsername(username).get()));
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("deleteUser")
    public ResponseEntity<Object> deleteUser(@RequestParam Long userId) {
        try {
            userService.deleteUser(userId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch (EmptyResultDataAccessException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("updateUser")
    public ResponseEntity<AppUserDto> updateUser(@RequestBody AppUserDto appUserDto) {
        try {
            userMapper.mapToUserDto((AppUser) userService.saveUser(userMapper.mapToUser(appUserDto)));
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @PostMapping("createUser")
    public ResponseEntity<AppUser> createUser(@Valid @RequestBody AppUserDto appUserDto) {
        try {
            userService.saveUser(userMapper.mapToUser(appUserDto));
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }


}
