package unid.team4.server.policy.domain;

import jakarta.persistence.*;
import lombok.*;
import unid.team4.server.global.domain.BaseDateTimeEntity;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "policy")
public class Policy extends BaseDateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "policy_id")
    private Long policyId;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String url;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Enumerated(EnumType.STRING)
    private AgeGroup age;

    @Column(columnDefinition = "TEXT")
    private String applyUrl;

    private Boolean isScraped;

    public enum Category {
        JOB, EDUCATION, LIVING, WELFARE // 일자리, 교육, 주거, 복지
    }

    public enum AgeGroup {
        YOUTH, MIDDLE_AGED, SENIOR // 청년층, 중장년층, 노년층
    }
}
