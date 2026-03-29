package com.gdghongik.springsecurity.domain.member.dto;

import com.gdghongik.springsecurity.domain.member.entity.Member;
import lombok.Getter;


public record MemberInfoResponse(Long memberId, String username) {
    public MemberInfoResponse(Member member){
        this(member.getId(), member.getUsername());
    }
}
