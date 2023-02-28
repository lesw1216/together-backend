package LESW.Together.domain.todolist.repository;

import LESW.Together.domain.todolist.TodoList;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TodoListRepository {

    Optional<TodoList> save(TodoList saveTodoList);

    List<TodoList> findAllTodoList(Long userPk, LocalDate createdDate);

    Optional<TodoList> update(TodoList updateTodoList);

    void delete(Long id);

    void allDelete(Long userPk, LocalDate createDate);
}
