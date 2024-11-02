package unid.team4.server.news.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import unid.team4.server.news.domain.News;

public interface NewsRepository extends JpaRepository<News, Long> {
}
