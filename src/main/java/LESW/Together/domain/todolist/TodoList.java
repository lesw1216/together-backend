package LESW.Together.domain.todolist;

import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TodoList {
    private Long id;
    private String content;
    private boolean isCompletion;
    private LocalDate createdDate;
    private Long userPk;

    public TodoList(String content, boolean isCompletion, LocalDate createdDate, Long userPk) {
        this.content = content;
        this.isCompletion = isCompletion;
        this.createdDate = createdDate;
        this.userPk = userPk;
    }
}
