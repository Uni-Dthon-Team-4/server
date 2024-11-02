package unid.team4.server.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unid.team4.server.member.controller.dto.MemberRequestDTO;
import unid.team4.server.member.controller.dto.MemberResponseDTO;
import unid.team4.server.member.domain.Member;
import unid.team4.server.member.service.MemberService;

@RestController
@RequestMapping("/api/member")
public class MemberApi {
    private final MemberService memberService;

    @Autowired
    public MemberApi(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/join")
    public ResponseEntity<MemberResponseDTO.JoinResponse> createUser(@RequestBody MemberRequestDTO.JoinRequest memberRequestDTO) {
        MemberResponseDTO.JoinResponse responseDTO = memberService.createUser(memberRequestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/check-duplicate")
    public ResponseEntity<Boolean> isIdExist(@RequestParam String userId) {
        boolean isDuplicate = memberService.isIdExist(userId);
        if (isDuplicate) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(false);
        } else {
            return ResponseEntity.ok(true);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<MemberResponseDTO.LoginResponse> getUser(@RequestBody MemberRequestDTO.LoginRequest request){
        MemberResponseDTO.LoginResponse responseDTO = memberService.getUser(request);

        return ResponseEntity.ok(responseDTO);
    }




}
