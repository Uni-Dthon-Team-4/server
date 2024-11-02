package unid.team4.server.chatbot.domain;

import jakarta.persistence.*;
import lombok.*;
import unid.team4.server.global.domain.BaseDateTimeEntity;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "chatbot")
public class Chatbot extends BaseDateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatbot_id")
    private Long chatbotId;

    private String question;

    @Column(columnDefinition = "TEXT")
    private String answer;

    private String keyword1;
    private String keyword2;
}
