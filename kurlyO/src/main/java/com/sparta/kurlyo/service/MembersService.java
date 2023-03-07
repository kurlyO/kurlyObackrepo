package com.sparta.kurlyo.service;

import com.sparta.kurlyo.dto.*;
import com.sparta.kurlyo.entity.Members;
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

import static com.sparta.kurlyo.dto.SuccessMessage.*;
import static com.sparta.kurlyo.dto.ExceptionMessage.*;

@Service
@RequiredArgsConstructor
public class MembersService {
    private final MembersRepository membersRepository;
    private final JwtUtil jwtUtil;

    private final PasswordEncoder passwordEncoder;

    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    @Transactional
    public ResponseEntity<Response> signup(SignupRequestDto signupRequestDto) {
        String accout = signupRequestDto.getAccount();
//        String password = passwordEncoder.encode(signupRequestDto.getPassword());
        String password = signupRequestDto.getPassword();
        String name = signupRequestDto.getName();
        String email = signupRequestDto.getEmail();
        String address = signupRequestDto.getAddress();
        String phone = signupRequestDto.getPhone();
        String gender = signupRequestDto.getGender();
        String birth = signupRequestDto.getBirth();

        //회원 중복 확인하기
        Optional<Members> findMember = membersRepository.findByAccount(accout);
        if (findMember.isPresent()) {
            throw new CustomException(DUPLICATE_USER);
        }
        findMember = membersRepository.findByEmail(email);
        if (findMember.isPresent()) {
            throw new CustomException(DUPLICATE_EMAIL);
        }

        UserRoleEnum role = UserRoleEnum.USER;
        if (signupRequestDto.isAdmin()) {
            if (!signupRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new CustomException(UNAUTHORIZED_ADMIN);
            }
            role = UserRoleEnum.ADMIN;
        }

        Members member = new Members(accout, password, name, email, address, phone, gender, birth, role);
        membersRepository.save(member);
        return Response.toResponseEntity(SIGN_UP_SUCCESS);

    }

    //    @Transactional(readOnly = true)
//    public ResponseEntity<Response> login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
//        String account = loginRequestDto.getAccount();
//        String password = loginRequestDto.getPassword();
//
//        Optional<Members> member = membersRepository.findByAccount(account);
//        if(!(member.isPresent() && passwordEncoder.matches(password, member.get().getPassword()))) {
//            throw new CustomException(MEMBER_NOT_FOUND);
//        }
//        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(member.get().getName(), member.get().getRole()));
//
//        String token = jwtUtil.createToken(member.get().getName(), member.get().getRole());
//        LoginResponseDto loginResponseDto = new LoginResponseDto(membersRepository.findByName(member.get().getName()).get().getName(), token);
//        return new Response().toResponseEntity(LOGIN_SUCCESS, loginResponseDto);
//
//    }
//
    @Transactional(readOnly = true)
    public ResponseEntity<Response> login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        String account = loginRequestDto.getAccount();
        String password = loginRequestDto.getPassword();

        Optional<Members> member = membersRepository.findByAccount(account);
        if (!(member.isPresent() && password.equals(member.get().getPassword()))) {
            throw new CustomException(MEMBER_NOT_FOUND);
        }
        String token = jwtUtil.createToken(member.get().getAccount(), member.get().getRole());
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, token);

        LoginResponseDto loginResponseDto = LoginResponseDto.of(member.get().getMemberName());
        return Response.toResponseEntity(LOGIN_SUCCESS, loginResponseDto);

    }

    public ResponseEntity<Response>accountCheck(String account) {
        if (membersRepository.findByAccount(account).isPresent()) {
            return Response.toAllExceptionResponseEntity(DUPLICATE_USER, account);
        }
        return Response.toResponseEntity(SuccessMessage.ACOUNT_CHECK_SUCCESS);
    }

    public ResponseEntity<Response> emailCheck(String email) {
        if (membersRepository.findByEmail(email).isPresent()) {
            return Response.toAllExceptionResponseEntity(DUPLICATE_EMAIL, email);
        }
        return Response.toResponseEntity(SuccessMessage.EMAIL_CHECK_SUCCESS);
    }
}
