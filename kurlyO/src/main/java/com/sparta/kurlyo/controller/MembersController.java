package com.sparta.kurlyo.controller;

import com.sparta.kurlyo.dto.LoginRequestDto;
import com.sparta.kurlyo.dto.SignupRequestDto;
import com.sparta.kurlyo.dto.Response;
import com.sparta.kurlyo.service.MembersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MembersController {
    private final MembersService membersService;

    @PostMapping("/signup")
    public ResponseEntity<Response> signup(@RequestBody @Valid SignupRequestDto signupRequestDto) {
        return membersService.signup(signupRequestDto);
    }

    @PostMapping("/login")
    public ResponseEntity<Response> signup(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        return membersService.login(loginRequestDto, response);
    }

}
