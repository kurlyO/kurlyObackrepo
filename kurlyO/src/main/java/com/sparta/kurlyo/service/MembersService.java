package com.sparta.kurlyo.service;

import com.sparta.kurlyo.jwt.JwtUtil;
import com.sparta.kurlyo.repository.MembersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class MembersService {

    private MembersRepository membersRepository;
    private final JwtUtil jwtUtil;




//    public ResponseEntity<Response> signup(SignupRequestDto signupRequestDto) {
//    }
}
