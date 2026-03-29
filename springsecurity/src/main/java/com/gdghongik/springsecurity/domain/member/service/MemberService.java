package com.gdghongik.springsecurity.domain.member.service;

import com.gdghongik.springsecurity.domain.member.dto.MemberCreateRequest;
import com.gdghongik.springsecurity.domain.member.dto.MemberInfoResponse;
import com.gdghongik.springsecurity.domain.member.dto.MemberUpdateRequest;
import com.gdghongik.springsecurity.domain.member.entity.Member;
import com.gdghongik.springsecurity.domain.member.repository.MemberRepository;
import com.gdghongik.springsecurity.global.exception.CustomException;
import com.gdghongik.springsecurity.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.gdghongik.springsecurity.global.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public void createMember(MemberCreateRequest request) {

        boolean isDuplicated = memberRepository.existsByUsername(request.getUsername()); // username이 DB에 있는 username과 겹치는 안 겹치는지 확인
        // 중복되는 유저네임이 있으면 에러
        if (isDuplicated) {
            throw new CustomException(MEMBER_USERNAME_DUPLICATE);
        }

        Member member = new Member(request.getUsername(), request.getPassword());

        // 멤버를 저장한다
        memberRepository.save(member);
    }

    @Transactional(readOnly = true)
    public List<MemberInfoResponse> getMembers() {
        // 모든 멤버를 가져온다
        List<MemberInfoResponse> list = new ArrayList<>();

        List<Member> Members = memberRepository.findAll();  // findAll을 직접 구현하지 않아도 조회 로직이 완성됨.(SpringJpa가 구현해줘서 memberRepository가 상속 받았으니 사용 가능)
        // 위 코드는 DB의 member 테이블에 있는 모든 데이터를 긁어온다.

        for (Member member : Members) {  // DTO 변환 과정
            MemberInfoResponse response = new MemberInfoResponse(member);
            list.add(response);
        }

        return list;
    }

    @Transactional(readOnly = true)
    public MemberInfoResponse getMemberByUsername(String username) {
        // 해당하는 멤버를 가져온다. 없으면 에러
        Member member = memberRepository.findByUsername(username);

        if(member == null){
            throw new CustomException(MEMBER_NOT_FOUND);
        }
        return new MemberInfoResponse(member);
    }

    @Transactional
    public void updateMember(Long memberId, MemberUpdateRequest request) {
        // 해당하는 멤버를 가져온다. 없으면 에러
       Member member= memberRepository.findById(memberId) // 그냥 member로 받으면 안되고 Optional로 받아야 됨, but 람다 표현식을 사용하면 가능(?)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));  // orElseThrow는 memberId로 조회했을 때 member가 null이면 예외를 발생시킴.
        // 해당하는 멤버 정보를 갱신한다
        member.updateUsername(request.getUsername());

    }

    @Transactional
    public void deleteMember(Long memberId) {
        // 해당하는 멤버를 가져온다. 없으면 에러, updateMember랑 동일한 로직
        Member member= memberRepository.findById(memberId) 
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));
        // 해당 멤버를 삭제한다
        memberRepository.delete(member);
    }
}
