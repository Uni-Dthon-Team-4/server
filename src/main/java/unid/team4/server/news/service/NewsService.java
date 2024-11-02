package unid.team4.server.news.service;

import org.springframework.stereotype.Service;
import unid.team4.server.news.controller.dto.NewsResponseDTO;
import unid.team4.server.news.domain.News;
import unid.team4.server.news.domain.repository.NewsRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NewsService {
    private final NewsRepository newsRepository;

    public NewsService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    public List<NewsResponseDTO> getAllNews() {
        List<News> newsList = newsRepository.findAll();
        return newsList.stream()
                .map(news -> new NewsResponseDTO(
                        news.getNewsId(),
                        news.getHeadline(),
                        news.getUrl(),
                        news.getPreview()))
                .collect(Collectors.toList());
    }
}
