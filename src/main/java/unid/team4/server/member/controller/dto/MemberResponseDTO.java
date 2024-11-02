package unid.team4.server.member.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


public class MemberResponseDTO {

    @Getter
    @Setter
    @Builder
    public static class JoinResponse{
        private Long id;
        private String userId;
        private String email;
        private String uuid;
        private int age;
        private String keyword1;
        private String keyword2;
        private String keyword3;
        private String address;
    }

    @Getter
    @Setter
    @Builder
    public static class LoginResponse{
        private String userId;
        private String email;
        private String uuid;
    }



}
