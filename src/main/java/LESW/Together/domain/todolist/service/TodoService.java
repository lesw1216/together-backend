package LESW.Together.domain.todolist.service;

import LESW.Together.domain.todolist.TodoList;
import LESW.Together.domain.todolist.dto.TodoRequestDto;
import LESW.Together.domain.todolist.dto.TodoResponseDto;
import LESW.Together.domain.todolist.repository.TodoListRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoListRepository todoListRepository;

    public TodoResponseDto save(TodoRequestDto todoRequestDto) {
        TodoList requsetTodoList = TodoList.builder()
                .content(todoRequestDto.getContent())
                .isCompletion(todoRequestDto.isCompletion())
                .createdDate(todoRequestDto.getCreatedDate())
                .userPk(todoRequestDto.getUserPk())
                .build();

        Optional<TodoList> optionalList = todoListRepository.save(requsetTodoList);
        TodoList saveList = optionalList.orElse(new TodoList());

        log.info("[SERVICE LAYER][CREATE TODO LIST]{}", saveList);
        return getTodoResponseDto(saveList);
    }



    public List<TodoResponseDto> findAllTodoList(Long userPk, LocalDate createdDate) {
        List<TodoResponseDto> todoResponseDtoList = new ArrayList<>();

        List<TodoList> allTodoList = todoListRepository.findAllTodoList(userPk, createdDate);
        allTodoList.forEach(list -> {
            log.info("[SERVICE LAYER][FIND ALL TODOLIST]{}", list);
            todoResponseDtoList.add(getTodoResponseDto(list));
        });
        return todoResponseDtoList;
    }

    public void deleteTodoListById(Long id) {
        todoListRepository.delete(id);
    }

    public void allDeleteByIdAndDate(Long userPk, LocalDate createdDate) {
        todoListRepository.allDelete(userPk, createdDate);
    }


    private static TodoResponseDto getTodoResponseDto(TodoList todoList) {
        return TodoResponseDto.builder()
                .id(todoList.getId())
                .isCompletion(todoList.isCompletion())
                .content(todoList.getContent())
                .build();
    }
}
