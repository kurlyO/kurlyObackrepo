package com.sparta.kurlyo.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

@Setter
@Getter
public class SignupRequestDto {

    @Pattern(regexp = "^[a-z0-9]+$", message = "아이디는 소문자와 숫자만 가능합니다.")
    @Size(min=4, max=10, message = "4 ~ 10길이의 소문자, 숫자만 가능합니다.")
    private String membername;

    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,15}", message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 15자의 비밀번호여야 합니다.")
    private String password;

    @NotNull(message = "이름은 공백일 수 없습니다.")
    private String name;

    @NotNull(message = "이메일 입력은 필수입니다.")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식으로 입력해주세요.")
    private String email;


    @NotNull(message = "주소입력은 필수입니다.")
    private String address;

    @NotNull(message = "핸드폰 번호 입력은 필수입니다.")
    private String phone;

    @NotNull(message = "성별을 선택해주세요.")
    private String gender;

    private Date birth;

    private boolean admin = false;
    private String adminToken = "";
}
