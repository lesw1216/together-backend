package LESW.Together.domain.todolist.service;

import LESW.Together.domain.todolist.dto.TodoRequestDto;
import LESW.Together.domain.todolist.dto.TodoResponseDto;
import LESW.Together.domain.user.Role;
import LESW.Together.domain.user.User;
import LESW.Together.domain.user.repository.jdbcTemplate.UserJdbcTemplateRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@Transactional
class TodoServiceTest {

    @Autowired
    private TodoService service;

    @Autowired
    private UserJdbcTemplateRepository jdbcTemplateRepository;

    private User saveUser;

    @BeforeEach
    void userCreate() {
        User user = new User("test", "1234", "테스터", "user", Role.USER);
        saveUser = jdbcTemplateRepository.createUser(user);
    }

    @Test
    void save() {
        LocalDate today = LocalDate.now();
        TodoRequestDto todoRequestDto = new TodoRequestDto("운동하기", false, today, saveUser.getId());
        TodoResponseDto todoResponseDto = service.save(todoRequestDto);

        Assertions.assertThat(todoResponseDto.getContent()).isEqualTo(todoRequestDto.getContent());
    }

    @Test
    void findAllTodoList() {
        LocalDate today = LocalDate.now();
        TodoRequestDto todoRequestDto1 = new TodoRequestDto("운동하기", false, today, saveUser.getId());
        TodoRequestDto todoRequestDto2 = new TodoRequestDto("밥먹기", false, today, saveUser.getId());
        TodoResponseDto todoResponseDto1 = service.save(todoRequestDto1);
        TodoResponseDto todoResponseDto2 = service.save(todoRequestDto2);


        List<TodoResponseDto> allTodoList = service.findAllTodoList(saveUser.getId(), LocalDate.now());

        Assertions.assertThat(allTodoList).contains(todoResponseDto1, todoResponseDto2);

    }

    @Test
    void deleteByIdAndDate() {
        TodoRequestDto todoRequestDto = new TodoRequestDto("운동하기", false, LocalDate.now(), saveUser.getId());
        TodoResponseDto todoResponseDto = service.save(todoRequestDto);

        service.deleteTodoListById(todoResponseDto.getId());

        List<TodoResponseDto> allTodoList = service.findAllTodoList(saveUser.getId(), LocalDate.now());
        Assertions.assertThat(allTodoList).isNotIn(todoResponseDto);
    }

    @Test
    void allDeleteByIdAndDate() {
        TodoRequestDto todoRequestDto1 = new TodoRequestDto("운동하기", false, LocalDate.now(), saveUser.getId());
        TodoRequestDto todoRequestDto2 = new TodoRequestDto("밥먹기", false, LocalDate.now(), saveUser.getId());

        TodoResponseDto saveTodoRequestDto1 = service.save(todoRequestDto1);
        TodoResponseDto saveTodoRequestDto2 = service.save(todoRequestDto2);

        service.allDeleteByIdAndDate(saveUser.getId(), LocalDate.now());

        List<TodoResponseDto> allTodoList = service.findAllTodoList(saveUser.getId(), LocalDate.now());
        Assertions.assertThat(allTodoList).isNotIn(saveTodoRequestDto1, saveTodoRequestDto2);
    }
}