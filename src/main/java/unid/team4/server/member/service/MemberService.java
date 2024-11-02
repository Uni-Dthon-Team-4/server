package unid.team4.server.member.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unid.team4.server.member.controller.dto.MemberRequestDTO;
import unid.team4.server.member.controller.dto.MemberResponseDTO;
import unid.team4.server.member.domain.Member;
import unid.team4.server.member.domain.repository.MemberRepository;
import unid.team4.server.util.PasswordUtil;

import java.util.UUID;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public boolean isIdExist(String userId){
        return memberRepository.findByUserId(userId).isPresent();
    }

    public MemberResponseDTO.JoinResponse createUser(MemberRequestDTO.JoinRequest requestDTO) {
        if (memberRepository.existsByUserId(requestDTO.getUserId())) {
            throw new IllegalArgumentException("아이디가 이미 존재합니다.");
        }

        String password = requestDTO.getPassword(); // password 변수를 정의
        byte[] salt = PasswordUtil.generateSalt();
        String hashedPassword = PasswordUtil.hashPassword(password, salt);


        Member member = Member.builder()
                .userId(requestDTO.getUserId())
                .password(hashedPassword)
                .email(requestDTO.getEmail())
                .uuid(UUID.randomUUID().toString())
                .age(requestDTO.getAge())
                .keyword1(requestDTO.getKeyword1())
                .keyword2(requestDTO.getKeyword2())
                .keyword3(requestDTO.getKeyword3())
                .address(requestDTO.getAddress())
                .salt(salt)
                .build();

        Member savedMember = memberRepository.save(member);

        return MemberResponseDTO.JoinResponse.builder()
                .id(savedMember.getId())
                .userId(savedMember.getUserId())
                .email(savedMember.getEmail())
                .uuid(savedMember.getUuid())
                .age(savedMember.getAge())
                .keyword1(savedMember.getKeyword1())
                .keyword2(savedMember.getKeyword2())
                .keyword3(savedMember.getKeyword3())
                .address(savedMember.getAddress())
                .build();

    }

    public MemberResponseDTO.JoinResponse getUser(MemberRequestDTO.LoginRequest request) {
        Member member = memberRepository.findByUserId(request.getUserId()).orElseThrow(() -> new IllegalArgumentException("User not found"));
        String hashedPassword = PasswordUtil.hashPassword(request.getPassword(), member.getSalt());


        if (!hashedPassword.equals(member.getPassword())) {
            throw new IllegalArgumentException("Invalid password");
        }

        return MemberResponseDTO.JoinResponse.builder()
                .id(member.getId())
               .userId(member.getUserId())
               .email(member.getEmail())
                .uuid(member.getUuid())
                .age(member.getAge())
                .keyword1(member.getKeyword1())
                .keyword2(member.getKeyword2())
                .keyword3(member.getKeyword3())
                .address(member.getAddress())
               .build();
    }



}
