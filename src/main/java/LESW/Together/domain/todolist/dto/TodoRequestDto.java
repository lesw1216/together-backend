package LESW.Together.domain.todolist.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TodoRequestDto {
    private String content;
    private boolean isCompletion;
    private LocalDate createdDate;
    private Long userPk;
}
