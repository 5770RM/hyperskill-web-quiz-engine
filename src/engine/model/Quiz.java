package engine.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "quiz")
public class Quiz {
    @Column(name = "quizId")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quiz_seq")
    private int id;
    @Column
    @NotBlank(message = "Title is required")
    private String title;
    @Column
    @NotBlank(message = "Text is required")
    private String text;
    @Size(min = 2)
    @NotNull
    @Column
    @ElementCollection(targetClass = String.class)
    private List<String> options;
    @Column
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private int[] answer;

    public Quiz() {
    }

    public Quiz(String title, String text, List<String> options, int[] answer) {
        this.title = title;
        this.text = text;
        this.options = options;
        this.answer = answer;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int[] getAnswer() { return answer == null? new int[]{} : answer; }

    public void setAnswer(int[] answer) { this.answer = answer; }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quiz quiz = (Quiz) o;
        return id == quiz.id &&
                Objects.equals(title, quiz.title) &&
                Objects.equals(text, quiz.text) &&
                Objects.equals(options, quiz.options) &&
                Arrays.equals(answer, quiz.answer);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(title, text, options, id);
        result = 31 * result + Arrays.hashCode(answer);
        return result;
    }
}
