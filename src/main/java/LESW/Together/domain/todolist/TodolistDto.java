package LESW.Together.domain.todolist;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TodolistDto {
    private String content;
    private boolean isCompletion;
    private LocalDate createdDate;
    private Long userPk;
}
