package unid.team4.server.policy.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unid.team4.server.global.response.ApiResponse;
import unid.team4.server.policy.controller.dto.PolicyResponseDTO;
import unid.team4.server.policy.service.PolicyService;

import java.util.List;

@RestController
@RequestMapping("/api/policies")
public class PolicyApi {
    private final PolicyService policyService;

    public PolicyApi(PolicyService policyService) {
        this.policyService = policyService;
    }

    @GetMapping("/by-age")
    @Operation(summary = "연령대별 1인가구 정책 조회 API", description = "유저의 uuid와 category 값을 필수로 받아 연령대별 1인가구 정책을 조회합니다.\n\n" +
            "category: JOB, EDUCATION, LIVING, WELFARE // 일자리, 교육, 주거, 복지]\n\n" +
            "MIDLE_AGED(중장년층)의 LIVING 관련 정책은 없습니다.")
    public ResponseEntity<ApiResponse<List<PolicyResponseDTO>>> getPoliciesByAgeGroup(
            @RequestHeader("uuid") String uuid,
            @RequestParam(value = "category", required = false) String category) {
        ApiResponse<List<PolicyResponseDTO>> response = policyService.getPoliciesByAgeGroupAndCategory(uuid, category);
        return ResponseEntity.ok(response);
    }
}
