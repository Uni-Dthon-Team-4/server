package unid.team4.server.chatbot.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import unid.team4.server.chatbot.domain.Chatbot;

public interface ChatbotRepository extends JpaRepository<Chatbot, Long> {
}
