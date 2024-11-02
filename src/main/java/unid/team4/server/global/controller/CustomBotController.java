package unid.team4.server.global.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import unid.team4.server.global.controller.dto.ChatGPTRequest;
import unid.team4.server.global.controller.dto.ChatGPTResponse;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/bot")
public class CustomBotController {
    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiURL;

    @Autowired
    private RestTemplate template;

    public String modifyPrompt(String prompt) {
        return prompt + "\n\n" +
                "위 질문에 대한 답변을 JSON 형식으로 제공해주세요. 형식은 다음과 같습니다:\n" +
                "{ \"answer\": \"<답변 내용>\", \"keywords\": [\"키워드1\", \"키워드2\"] }\n" +
                "반드시 JSON 형식으로만 답변을 주세요.";
    }

    @GetMapping("/chat2")
    public Map<String, Object> chat2(@RequestParam(name = "prompt") String prompt) {
        String modifiedPrompt = modifyPrompt(prompt);
        ChatGPTRequest request = new ChatGPTRequest(model, modifiedPrompt);

        ChatGPTResponse chatGPTResponse = template.postForObject(apiURL, request, ChatGPTResponse.class);
        String responseContent = chatGPTResponse.getChoices().get(0).getMessage().getContent();

        // JSON 형식 확인 및 처리
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> responseMap;
        try {
            responseMap = objectMapper.readValue(responseContent, new TypeReference<Map<String, Object>>() {});
        } catch (JsonProcessingException e) {
            // JSON 파싱에 실패한 경우 기본 응답을 제공
            responseMap = new HashMap<>();
            responseMap.put("answer", responseContent.trim());
            responseMap.put("keywords", Arrays.asList("키워드1", "키워드2"));  // 기본 키워드 설정
        }

        return responseMap;
    }

    @GetMapping("/chat")
    @Operation(summary = "GPT 챗봇 질의응답 API", description = "Best Case: 서울시에서 청년과 관련된 1인 가구 정책 추천해줘.")
    public String chat(@RequestParam(name = "prompt")String prompt){
        ChatGPTRequest request = new ChatGPTRequest(model, prompt);
        ChatGPTResponse chatGPTResponse =  template.postForObject(apiURL, request, ChatGPTResponse.class);
        return chatGPTResponse.getChoices().get(0).getMessage().getContent();
    }
}

