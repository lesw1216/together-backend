package LESW.Together.domain.todolist.dto;

import LESW.Together.domain.todolist.service.TodoListServiceDtoConvertor;
import LESW.Together.domain.todolist.service.TodoServiceDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TodoPostRequestDto implements TodoListServiceDtoConvertor {
    private String content;
    private boolean isCompletion;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createdDate;
    private Long userPk;

    public TodoServiceDto toServiceDto() {
        return TodoServiceDto.builder()
                .content(this.content)
                .isCompletion(this.isCompletion)
                .createdDate(toLocalDate(this.createdDate))
                .userPk(this.userPk)
                .build();
    }

    protected LocalDate toLocalDate(LocalDateTime createdDate) {
        return LocalDate.from(createdDate);
    }
}
