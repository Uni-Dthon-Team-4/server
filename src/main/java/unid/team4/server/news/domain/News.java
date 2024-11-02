package unid.team4.server.news.domain;

import jakarta.persistence.*;
import lombok.*;
import unid.team4.server.global.domain.BaseDateTimeEntity;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "news")
public class News extends BaseDateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "news_id")
    private Long newsId;

    private String headline;
    private String url;

    @Column(columnDefinition = "TEXT")
    private String preview;
}
