package com.sparta.kurlyo.controller;

import com.sparta.kurlyo.dto.LoginRequestDto;
import com.sparta.kurlyo.dto.SignupRequestDto;
import com.sparta.kurlyo.dto.Response;
import com.sparta.kurlyo.service.MembersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Response> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        return membersService.login(loginRequestDto, response);
    }
    @GetMapping("/signup/accountCheck/{account}")
    public ResponseEntity<Response> accountCheck(@PathVariable String account) {
        return membersService.accountCheck(account);
    }
    @GetMapping("/signup/emailCheck/{email}")
    public ResponseEntity<Response> emailCheck(@PathVariable String email) {
        return membersService.emailCheck(email);
    }

}
