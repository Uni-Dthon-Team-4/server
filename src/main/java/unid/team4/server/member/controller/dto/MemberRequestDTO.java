package unid.team4.server.member.controller.dto;

import lombok.Getter;
import lombok.Setter;


public class MemberRequestDTO {

    @Getter
    @Setter
    public static class JoinRequest {
        private String userId;
        private String password;
        private String email;
        private int age;
        private String keyword1;
        private String keyword2;
        private String keyword3;
        private String address;
    }

    @Getter
    @Setter
    public static class LoginRequest {
        private String userId;
        private String password;
    }



}
