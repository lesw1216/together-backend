package LESW.Together.domain.todolist.controller;

import LESW.Together.domain.todolist.dto.*;
import LESW.Together.domain.todolist.service.TodoService;
import LESW.Together.domain.todolist.service.TodoServiceDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public ResponseEntity<List<TodoResponseDto>> todoFind(@ModelAttribute TodoGetRequestDto todoGetRequestDto) {

        List<TodoResponseDto> allTodoList = todoService.findAllTodoList(todoGetRequestDto.toServiceDto());
        return ResponseEntity.ok().body(allTodoList);
    }

    // 할일 목록 추가 요청메서드
    @PostMapping("/todoLists")
    public ResponseEntity<List<TodoResponseDto>> todoAdd(@RequestBody TodoPostRequestDto todoPostRequestDto) {

        final TodoServiceDto todoPostGetServiceDto = todoPostRequestDto.toServiceDto();
        todoService.save(todoPostGetServiceDto);
        List<TodoResponseDto> allTodoList =
                todoService.findAllTodoList(todoPostGetServiceDto);

        return ResponseEntity.ok().body(allTodoList);
    }

    @DeleteMapping("/todoLists")
    public ResponseEntity<List<TodoResponseDto>> todoDelete(@RequestBody TodoDeleteRequestDto todoDeleteRequestDto) {

        TodoServiceDto todoDeleteGetServiceDTO = todoDeleteRequestDto.toServiceDto();

        // 전체 삭제 또는 하나만 삭제
        if (todoDeleteRequestDto.getId() == null) {
            todoService.allDeleteByIdAndDate(todoDeleteGetServiceDTO);
        } else {
            todoService.deleteTodoListById(todoDeleteGetServiceDTO);
        }

        List<TodoResponseDto> allTodoList =
                todoService.findAllTodoList(todoDeleteGetServiceDTO);
        return ResponseEntity.ok().body(allTodoList);
    }

    @PutMapping("/todoLists")
    public ResponseEntity todoPut(@RequestBody TodoPutRequestDto todoPutRequestDto) {
        todoService.updateTodoList(todoPutRequestDto.toServiceDto());

        return ResponseEntity.ok().body("");
    }
}
