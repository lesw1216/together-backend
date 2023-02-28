package LESW.Together.domain.todolist.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TodoResponseDto {
    private Long id;
    private String content;
    private boolean isCompletion;
}
