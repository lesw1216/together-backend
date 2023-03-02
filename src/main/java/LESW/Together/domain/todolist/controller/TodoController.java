package LESW.Together.domain.todolist.controller;

import LESW.Together.domain.todolist.dto.TodoDeleteRequestDto;
import LESW.Together.domain.todolist.dto.TodoFindRequestDto;
import LESW.Together.domain.todolist.dto.TodoRequestDto;
import LESW.Together.domain.todolist.dto.TodoResponseDto;
import LESW.Together.domain.todolist.service.TodoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Controller
@RestController
@RequestMapping("/api")
@ResponseBody
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @GetMapping("/todoLists")
    public ResponseEntity<List<TodoResponseDto>> todoFind(@ModelAttribute TodoFindRequestDto todoFindRequestDto) {

        log.info("todoFind invoked");
        log.info("[FindTodoListCreatedDate][{}]", todoFindRequestDto.getCreatedDate());
        log.info("[FindTodoListUserPk][{}]", todoFindRequestDto.getUserPk());
        List<TodoResponseDto> allTodoList = todoService.findAllTodoList(todoFindRequestDto.getUserPk(), todoFindRequestDto.getCreatedDate());
        return ResponseEntity.ok().body(allTodoList);
    }
    @PostMapping("/todoLists")
    public ResponseEntity<List<TodoResponseDto>> todoAdd(@RequestBody TodoRequestDto todoRequestDto) {
        log.info("todoAdd invoked");
        log.info("[CreateTodoListCreatedDate][{}]", todoRequestDto.getCreatedDate());
        log.info("[CreateTodoListUserPk][{}]", todoRequestDto.getUserPk());

        todoService.save(todoRequestDto);
        List<TodoResponseDto> allTodoList = todoService.findAllTodoList(todoRequestDto.getUserPk(), todoRequestDto.getCreatedDate());
        return ResponseEntity.ok().body(allTodoList);
    }

    @DeleteMapping("/todoLists")
    public ResponseEntity<List<TodoResponseDto>> todoDelete(@RequestBody TodoDeleteRequestDto todoDeleteRequestDto) {
        log.info("todoDelete Invoked");
        log.info("[DeleteTodoListCreatedDate][{}]", todoDeleteRequestDto.getCreatedDate());

        if (todoDeleteRequestDto.getId() == null) {
            todoService.allDeleteByIdAndDate(todoDeleteRequestDto.getUserPk(), todoDeleteRequestDto.getCreatedDate());
        } else {
            todoService.deleteTodoListById(todoDeleteRequestDto.getId());
        }

        List<TodoResponseDto> allTodoList = todoService.findAllTodoList(todoDeleteRequestDto.getUserPk(), todoDeleteRequestDto.getCreatedDate());
        return ResponseEntity.ok().body(allTodoList);
    }

}
