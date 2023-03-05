package LESW.Together.domain.todolist.dto;

import LESW.Together.domain.todolist.service.TodoListServiceDtoConvertor;
import LESW.Together.domain.todolist.service.TodoServiceDto;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TodoDeleteRequestDto implements TodoListServiceDtoConvertor {
    private Long id;
    private Long userPk;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createdDate;

    @Override
    public TodoServiceDto toServiceDto() {
        return TodoServiceDto.builder()
                .todoPk(this.id)
                .userPk(this.userPk)
                .createdDate(LocalDate.from(this.createdDate))
                .build();
    }


}
