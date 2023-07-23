package com.david.project03.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.david.project03.model.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
