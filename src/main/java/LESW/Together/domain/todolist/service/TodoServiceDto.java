package LESW.Together.domain.todolist.service;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TodoServiceDto {

    private Long todoPk;
    private String content;
    private boolean isCompletion;

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate createdDate;
    private Long userPk;


}
