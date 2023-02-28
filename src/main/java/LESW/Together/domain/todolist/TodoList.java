package LESW.Together.domain.todolist;

import lombok.*;

import java.time.LocalDate;

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

    @Override
    public String toString() {
        return "[id=" + id + "][content=" + content + "][isCompletion=" + isCompletion +
                "][createdDate=" + createdDate + "][userPk=" + userPk + "]";
    }
}
