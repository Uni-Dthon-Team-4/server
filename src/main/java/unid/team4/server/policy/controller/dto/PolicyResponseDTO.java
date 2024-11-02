package unid.team4.server.policy.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PolicyResponseDTO {
    private Long policyId;
    private Boolean isScraped;
    private String name;
    private String description;
    private String category;
    private String age;
    private String url;
    private String applyUrl;
}
