package LESW.Together.domain.todolist.repository;

import LESW.Together.domain.todolist.TodoList;
import LESW.Together.domain.user.Role;
import LESW.Together.domain.user.User;
import LESW.Together.domain.user.repository.jdbcTemplate.UserJdbcTemplateRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@Transactional
class TodolistJdbcRepositoryTest {

    @Autowired
    private TodolistJdbcRepository repository;

    @Autowired
    private UserJdbcTemplateRepository userJdbcTemplateRepository;


    private User saveUser;
    @BeforeEach
    void userCreate() {
        User user = new User("test", "1234", "테스터", "user", Role.USER);
        saveUser = userJdbcTemplateRepository.createUser(user);
    }

    @Test
    void save() {
        TodoList todolist = new TodoList("운동하기", false, LocalDate.now(), saveUser.getId());
        Optional<TodoList> OptionalList = repository.save(todolist);
        TodoList saveList = OptionalList.orElseThrow();

        Assertions.assertThat(saveList).isEqualTo(todolist);
    }

    @Test
    void findAllTodoList() {
        TodoList todolist = new TodoList("운동하기", false, LocalDate.now(), saveUser.getId());
        Optional<TodoList> OptionalList = repository.save(todolist);
        TodoList saveList = OptionalList.orElseThrow();

        List<TodoList> allTodoList = repository.findAllTodoList(saveUser.getId(), LocalDate.now());

        for (TodoList todoList : allTodoList) {
            Assertions.assertThat(todoList.getId()).isEqualTo(saveList.getId());
        }
    }

    @Test
    void update() {
        LocalDate now = LocalDate.now();
        TodoList todolist = new TodoList("운동하기", false, now, saveUser.getId());
        Optional<TodoList> OptionalList = repository.save(todolist);
        TodoList saveList = OptionalList.orElseThrow();

        // 업데이트
        TodoList inputUpdateList = new TodoList(saveList.getId(), "밥먹기", false, now, saveUser.getId());
        TodoList updateList = repository.update(inputUpdateList).orElse(new TodoList());

        Assertions.assertThat(updateList.getContent()).isEqualTo(inputUpdateList.getContent());
        Assertions.assertThat(updateList.isCompletion()).isEqualTo(inputUpdateList.isCompletion());
    }

    @Test
    void delete() {
        TodoList todolist = new TodoList("운동하기", false, LocalDate.now(), saveUser.getId());
        Optional<TodoList> OptionalList = repository.save(todolist);
        TodoList saveList = OptionalList.orElseThrow();

        repository.delete(saveList.getId());

        List<TodoList> allTodoList = repository.findAllTodoList(saveUser.getId(), LocalDate.now());
        for (TodoList todoList : allTodoList) {
            Assertions.assertThat(todoList.getId()).isNotEqualTo(saveList.getId());
        }
    }
}