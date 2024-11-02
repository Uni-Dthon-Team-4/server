package unid.team4.server.news.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NewsResponseDTO {
    private Long newsId;
    private String headline;
    private String url;
    private String preview;
}
