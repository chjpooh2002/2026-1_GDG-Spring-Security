package com.gdghongik.springsecurity.domain.member.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


public record MemberCreateRequest(String username, String password) {

}
