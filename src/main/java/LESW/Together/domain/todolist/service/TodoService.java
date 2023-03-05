package LESW.Together.domain.todolist.service;

import LESW.Together.domain.todolist.TodoList;
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

    public TodoResponseDto save(TodoServiceDto todoServiceDto) {
        TodoList requsetTodoList = getTodoList(todoServiceDto);

        Optional<TodoList> optionalList = todoListRepository.save(requsetTodoList);
        TodoList saveList = optionalList.orElse(new TodoList());

        log.info("[SERVICE LAYER][CREATE TODO LIST]{}", saveList);
        return getTodoResponseDto(saveList);
    }

    public List<TodoResponseDto> findAllTodoList(TodoServiceDto todoServiceDto) {
        final Long userPk = todoServiceDto.getUserPk();
        final LocalDate createdDate = todoServiceDto.getCreatedDate();

        List<TodoResponseDto> todoResponseDtoList = new ArrayList<>();

        List<TodoList> allTodoList = todoListRepository.findAllTodoList(userPk, createdDate);
        allTodoList.forEach(list -> {
            log.info("[SERVICE LAYER][FIND ALL TODOLIST]{}", list);
            todoResponseDtoList.add(getTodoResponseDto(list));
        });
        return todoResponseDtoList;
    }

    public void deleteTodoListById(TodoServiceDto todoServiceDto) {
        final Long id = todoServiceDto.getTodoPk();
        todoListRepository.delete(id);
    }

    public void allDeleteByIdAndDate(TodoServiceDto todoServiceDto) {
        final Long userPk = todoServiceDto.getUserPk();
        final LocalDate createdDate = todoServiceDto.getCreatedDate();
        todoListRepository.allDelete(userPk, createdDate);
    }

    public TodoServiceDto updateTodoList(TodoServiceDto todoServiceDto) {
        TodoList convertTodoList = TodoList.builder()
                .id(todoServiceDto.getTodoPk())
                .content(todoServiceDto.getContent())
                .isCompletion(todoServiceDto.isCompletion())
                .build();

        Optional<TodoList> optional = todoListRepository.update(convertTodoList);

        TodoList updateTodoList = optional.orElse(new TodoList());

        return TodoServiceDto.builder()
                .todoPk(updateTodoList.getId())
                .content(updateTodoList.getContent())
                .isCompletion(updateTodoList.isCompletion())
                .build();
    }

    private static TodoResponseDto getTodoResponseDto(TodoList todoList) {
        return TodoResponseDto.builder()
                .id(todoList.getId())
                .isCompletion(todoList.isCompletion())
                .content(todoList.getContent())
                .build();
    }

    private static TodoList getTodoList(TodoServiceDto todoServiceDto) {
        return TodoList.builder()
                .content(todoServiceDto.getContent())
                .isCompletion(todoServiceDto.isCompletion())
                .createdDate(LocalDate.from(todoServiceDto.getCreatedDate()))
                .userPk(todoServiceDto.getUserPk())
                .build();
    }
}
