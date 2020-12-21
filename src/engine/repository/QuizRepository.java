package engine.repository;

import engine.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
    Quiz findQuizById(int id);
    List<Quiz> findAll();
}
