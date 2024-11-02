package unid.team4.server.policy.service;

import org.springframework.stereotype.Service;
import unid.team4.server.global.response.ApiResponse;
import unid.team4.server.global.response.ResponseCode;
import unid.team4.server.member.domain.Member;
import unid.team4.server.member.domain.repository.MemberRepository;
import unid.team4.server.policy.controller.dto.PolicyResponseDTO;
import unid.team4.server.policy.controller.dto.PolicySearchDTO;
import unid.team4.server.policy.domain.Policy;
import unid.team4.server.policy.domain.repository.PolicyRepository;

import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.tuple.Pair.of;

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

    public ApiResponse<List<PolicySearchDTO>> getPoliciesByKeyword(String keyword) {
        List<Policy> policies = policyRepository.findByKeyword(keyword);

        // 정책 리스트를 PolicySearchDTO로 변환
        List<PolicySearchDTO> responseDTOs = policies.stream()
                .map(policy -> new PolicySearchDTO(
                        policy.getPolicyId(),
                        policy.getName(),
                        policy.getDescription(),
                        policy.getCategory().name(),
                        policy.getAge().name(),
                        policy.getUrl()))
                .collect(Collectors.toList());

        // 응답 생성
        return ApiResponse.of(ResponseCode.SUCCESS, responseDTOs);
    }

    public ApiResponse<PolicyResponseDTO> updatePolicyScraped(Long policyId) {
        Policy policy = policyRepository.findById(policyId)
                .orElseThrow(() -> new IllegalArgumentException("해당 정책을 찾을 수 없습니다."));

        if (Boolean.TRUE.equals(policy.getIsScraped())) {
            throw new IllegalArgumentException("해당 정책은 이미 스크랩 되었습니다.");
        } else {
            policy.setIsScraped(true);
        }
        if (policy.getIsScraped() == null) {
            policy.setIsScraped(true);
        }

        policyRepository.save(policy);

        //      생성
        return ApiResponse.of(ResponseCode.SUCCESS, new PolicyResponseDTO(
                policy.getPolicyId(),
                policy.getIsScraped(),
                policy.getName(),
                policy.getDescription(),
                policy.getCategory().name(),
                policy.getAge().name(),
                policy.getUrl(),
                policy.getApplyUrl()));
    }
    public ApiResponse<PolicyResponseDTO> updatePolicyUnscraped(Long policyId){
        Policy policy =policyRepository.findById(policyId)
                .orElseThrow(()->new IllegalStateException("해당 정책을 찾을 수 없습니다."));

        if(Boolean.FALSE.equals(policy.getIsScraped())){
            throw new IllegalArgumentException("해당 정책은 이미 스크랩 해지되었습니다.");
        }
        else{
            policy.setIsScraped(false);
        }
        if(policy.getIsScraped()==null)
        {
            policy.setIsScraped(false);
        }
        policyRepository.save(policy);

        return ApiResponse.of(ResponseCode.SUCCESS, new PolicyResponseDTO(
                policy.getPolicyId(),
                policy.getIsScraped(),
                policy.getName(),
                policy.getDescription(),
                policy.getCategory().name(),
                policy.getAge().name(),
                policy.getUrl(),
                policy.getApplyUrl()));

    }

    public ApiResponse<List<PolicyResponseDTO>> getScrapedPolicies(){
        List<Policy> policies=policyRepository.findByIsScraped(true);

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

        return ApiResponse.of(ResponseCode.SUCCESS, responseDTOs);
    }



}
