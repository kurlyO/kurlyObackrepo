package com.sparta.kurlyo.repository;

import com.sparta.kurlyo.entity.Members;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.reflect.Member;
import java.util.Optional;

public interface MembersRepository extends JpaRepository<Members, Long> {
    Optional<Members> findByAccount(String account);
    Optional<Members> findByEmail(String email);

    Optional<Members> findByName(String name);

}
