package com.hms.user.controller;

import com.hms.user.dto.UserLoginRequest;
import com.hms.user.dto.UserRequestDto;
import com.hms.user.dto.UserResposeDto;
import com.hms.user.dto.UserTokenResponse;
import com.hms.user.repositry.UserRepo;
import com.hms.user.service.UserService;
import com.hms.user.service.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Validated
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<UserTokenResponse> create(@RequestBody @Valid UserRequestDto userRequestDto){
        UserTokenResponse user = userService.registerUser(userRequestDto);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<UserTokenResponse> create(@RequestBody @Valid UserLoginRequest userLoginRequest){
        UserTokenResponse user = userService.login(userLoginRequest);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/get")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String>  getsome(){
        return ResponseEntity.ok("Test");
    }


    @GetMapping("/get/{id}")
    public ResponseEntity<UserResposeDto> get(@PathVariable("id") Long id){
        UserResposeDto user = userService.getUser(id);
        return ResponseEntity.ok(user);
    }

}
