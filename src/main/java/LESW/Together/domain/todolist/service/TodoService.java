package LESW.Together.domain.todolist.service;

import LESW.Together.domain.todolist.TodoList;
import LESW.Together.domain.todolist.TodolistDto;
import LESW.Together.domain.todolist.repository.TodoListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoListRepository todoListRepository;

    public void save(TodolistDto todolistDto) {
        TodoList saveTodoList = TodoList.builder()
                .content(todolistDto.getContent())
                .isCompletion(todolistDto.isCompletion())
                .createdDate(todolistDto.getCreatedDate())
                .userPk(todolistDto.getUserPk())
                .build();

        todoListRepository.save(saveTodoList);
    }

    public List<TodoList> findAllTodoList(Long userPk, LocalDate createdDate) {
        return todoListRepository.findAllTodoList(userPk, createdDate);
    }

    public void deleteByIdAndDate(Long id) {
        todoListRepository.delete(id);
    }

    public void allDeleteByIdAndDate(Long userPk, LocalDate createdDate) {
        todoListRepository.allDelete(userPk, createdDate);
    }
}
