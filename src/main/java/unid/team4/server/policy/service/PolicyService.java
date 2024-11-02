package unid.team4.server.policy.service;

import org.springframework.stereotype.Service;
import unid.team4.server.global.response.ApiResponse;
import unid.team4.server.global.response.ResponseCode;
import unid.team4.server.member.domain.Member;
import unid.team4.server.member.domain.repository.MemberRepository;
import unid.team4.server.policy.controller.dto.PolicyResponseDTO;
import unid.team4.server.policy.domain.Policy;
import unid.team4.server.policy.domain.repository.PolicyRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PolicyService {
    private final PolicyRepository policyRepository;
    private final MemberRepository memberRepository;

    public PolicyService(PolicyRepository policyRepository, MemberRepository memberRepository) {
        this.policyRepository = policyRepository;
        this.memberRepository = memberRepository;
    }

    public ApiResponse<List<PolicyResponseDTO>> getPoliciesByAgeGroupAndCategory(String uuid, String category) {
        // Member 식별
        Member member = memberRepository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("해당 UUID의 멤버를 찾을 수 없습니다."));

        // 나이 그룹 결정
        Policy.AgeGroup ageGroup;
        if (member.getAge() <= 35) {
            ageGroup = Policy.AgeGroup.YOUTH;
        } else if (member.getAge() <= 59) {
            ageGroup = Policy.AgeGroup.MIDDLE_AGED;
        } else {
            ageGroup = Policy.AgeGroup.SENIOR;
        }

        // 카테고리 조건 설정 및 정책 조회
        List<Policy> policies;
        if (category != null) {
            Policy.Category policyCategory;
            try {
                policyCategory = Policy.Category.valueOf(category.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("유효하지 않은 카테고리입니다.");
            }
            policies = policyRepository.findByAgeAndCategory(ageGroup, policyCategory);
        } else {
            policies = policyRepository.findByAge(ageGroup);
        }

        // 정책 리스트를 DTO로 변환
        List<PolicyResponseDTO> responseDTOs = policies.stream()
                .map(policy -> new PolicyResponseDTO(
                        policy.getPolicyId(),
                        policy.getIsScraped(),
                        policy.getName(),
                        policy.getDescription(),
                        policy.getCategory().name(),
                        policy.getAge().name(),
                        policy.getUrl(),
                        policy.getApplyUrl()))
                .collect(Collectors.toList());

        // 응답 생성
        return ApiResponse.of(ResponseCode.SUCCESS, responseDTOs);
    }
}
