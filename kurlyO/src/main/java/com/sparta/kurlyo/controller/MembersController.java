package com.sparta.kurlyo.controller;

import com.sparta.kurlyo.service.MembersService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MembersController {
    private final MembersService membersService;

//    @PostMapping("/signup")
//    public ResponseEntity<Response> signup(@RequestBody @Valid SignupRequestDto signupRequestDto) {
//        return membersService.signup(signupRequestDto);
//    }

//    @PostMapping("/login")
//    public ResponseEntity<Response> signup(@RequestBody LoginRequestDto loginRequestDto) {
//        return membersService.login(loginRequestDto);;
//    }

}
