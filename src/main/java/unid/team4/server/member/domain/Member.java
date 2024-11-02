package unid.team4.server.member.domain;

import jakarta.persistence.*;
import lombok.*;
import unid.team4.server.global.domain.BaseDateTimeEntity;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "member")
public class Member extends BaseDateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String userId;
    private String password;
    private String email;
    private String uuid = UUID.randomUUID().toString();
    private int age;
    private String keyword1;
    private String keyword2;
    private String keyword3;
    private String address;
}
