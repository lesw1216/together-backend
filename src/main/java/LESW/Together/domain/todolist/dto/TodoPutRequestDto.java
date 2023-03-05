package LESW.Together.domain.todolist.dto;

import LESW.Together.domain.todolist.service.TodoListServiceDtoConvertor;
import LESW.Together.domain.todolist.service.TodoServiceDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class TodoPutRequestDto implements TodoListServiceDtoConvertor {

    private Long id;
    private String content;
    private boolean isCompletion;

    @Override
    public TodoServiceDto toServiceDto() {
        return TodoServiceDto.builder()
                .todoPk(this.id)
                .content(this.content)
                .isCompletion(this.isCompletion)
                .build();
    }
}
