package unid.team4.server.news.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import unid.team4.server.global.response.ApiResponse;
import unid.team4.server.global.response.ResponseCode;
import unid.team4.server.news.controller.dto.NewsResponseDTO;
import unid.team4.server.news.service.NewsService;

import java.util.List;

@RestController
@RequestMapping("/api/news")
public class NewsApi {
    private final NewsService newsService;

    public NewsApi(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/all")
    @Operation(summary = "1인가구 관련 최신 뉴스 조회 API", description = "1인 가구와 관련된 최신 뉴스를 조회합니다.")
    public ResponseEntity<ApiResponse<List<NewsResponseDTO>>> getLatestNews() {
        List<NewsResponseDTO> newsList = newsService.getAllNews();

        if (newsList.isEmpty()) {
            // 데이터가 없으면 204 No Content 대신 커스텀 응답을 사용하여 명확한 응답 제공
            return ResponseEntity.ok(ApiResponse.of(ResponseCode.CONFIRM, null));
        }

        // 데이터가 있으면 200 OK와 함께 ApiResponse 형식으로 데이터 반환
        return ResponseEntity.ok(ApiResponse.of(newsList));
    }
}
