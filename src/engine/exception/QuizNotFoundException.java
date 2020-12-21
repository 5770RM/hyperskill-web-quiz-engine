package engine.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND,reason = "Quiz is not found")
public class QuizNotFoundException extends RuntimeException {
    public QuizNotFoundException() {
    }
}
