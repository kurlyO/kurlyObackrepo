package com.sparta.kurlyo.service;

import com.sparta.kurlyo.dto.LoginRequestDto;
import com.sparta.kurlyo.dto.LoginResponseDto;
import com.sparta.kurlyo.dto.SignupRequestDto;
import com.sparta.kurlyo.entity.Members;
import com.sparta.kurlyo.entity.Response;
import com.sparta.kurlyo.entity.UserRoleEnum;
import com.sparta.kurlyo.jwt.JwtUtil;
import com.sparta.kurlyo.repository.MembersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class MembersService {

    private MembersRepository membersRepository;
    private final JwtUtil jwtUtil;

    private final PasswordEncoder passwordEncoder;

    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    @Transactional
    public ResponseEntity<Response> signup(SignupRequestDto signupRequestDto) {
        String accout = signupRequestDto.getAccount();
        String password = passwordEncoder.encode(signupRequestDto.getPassword());
        String name = signupRequestDto.getName();
        String email = signupRequestDto.getEmail();
        String address = signupRequestDto.getAddress();
        String phone = signupRequestDto.getPhone();
        String gender = signupRequestDto.getGender();
        String birth = signupRequestDto.getBirth();

        //회원 중복 확인하기
        Optional<Members> findMember = membersRepository.findByAccount(accout);
        if(findMember.isPresent()) {
            throw new IllegalArgumentException("중복된 회원이 존재합니다.");
        }
        findMember = membersRepository.findByEmail(email);
        if(findMember.isPresent()) {
            throw new IllegalArgumentException("중복된 이메일이 존재합니다.");
        }

        UserRoleEnum role = UserRoleEnum.USER;
        if(signupRequestDto.isAdmin()) {
            if (!signupRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new IllegalArgumentException("관리자가 아닙니다.");
            }
            role = UserRoleEnum.ADMIN;
        }

        Members member = new Members(accout, password, name, email, address, phone, gender, birth, role);
        membersRepository.save(member);
        return Response.toResponseEntity("회원가입 성공");

    }

    @Transactional(readOnly = true)
    public ResponseEntity<Response> login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        String account = loginRequestDto.getAccount();
        String password = loginRequestDto.getPassword();

        Optional<Members> member = membersRepository.findByAccount(account);
        if(!(member.isPresent() && passwordEncoder.matches(password, member.get().getPassword()))) {
            throw new IllegalArgumentException("회원을 찾을 수 없습니다.");
        }
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(member.get().getName(), member.get().getRole()));

        String token = jwtUtil.createToken(member.get().getName(), member.get().getRole());
        LoginResponseDto loginResponseDto = new LoginResponseDto(membersRepository.findByName(member.get().getName()).get().getName(), token);
        return new Response().toResponseEntity("로그인 성공", loginResponseDto);

    }
}
