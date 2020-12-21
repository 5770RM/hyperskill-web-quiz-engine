package engine.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "completion")
public class CompletionEntity {
    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "completion_seq")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long completionId;

    @Column
    private long id;
    @Column
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String email; //user's email
    @Column
    private Date completedAt;

    public CompletionEntity() {
    }

    public CompletionEntity(long id, String email, Date completedAt) {
        this.id = id;
        this.email = email;
        this.completedAt = completedAt;
    }

    public Long getCompletionId() {
        return completionId;
    }

    public void setCompletionId(Long completionId) {
        this.completionId = completionId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(Date completedAt) {
        this.completedAt = completedAt;
    }
}
