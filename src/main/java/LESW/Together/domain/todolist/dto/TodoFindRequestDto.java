package LESW.Together.domain.todolist.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TodoFindRequestDto {
    private Long userPk;

    @DateTimeFormat(pattern = "yyyy-mm-dd HH:mm:ss")
    private LocalDate createdDate;
}
