package LESW.Together.domain.todolist.repository;

import LESW.Together.domain.todolist.TodoList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class TodolistJdbcRepository implements TodoListRepository{

    private final NamedParameterJdbcTemplate template;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public TodolistJdbcRepository(DataSource dataSource) {
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("todolists")
                .usingGeneratedKeyColumns("id");
        template = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Optional<TodoList> save(TodoList saveTodoList) {
        MapSqlParameterSource param = new MapSqlParameterSource()
                .addValue("content", saveTodoList.getContent())
                .addValue("is_completion", saveTodoList.isCompletion())
                .addValue("created_date", saveTodoList.getCreatedDate())
                .addValue("user_pk", saveTodoList.getUserPk());


        Number key = simpleJdbcInsert.executeAndReturnKey(param);
        saveTodoList.setId(key.longValue());

        return Optional.of(saveTodoList);
    }

    @Override
    public List<TodoList> findAllTodoList(Long userPk, LocalDate createdDate) {
        String sql = "select * from todolists where user_pk=:userPk and created_date=:createDate";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("userPk", userPk)
                .addValue("createDate", createdDate);

        return template.query(sql, params, (rs, rowNum) -> {
            TodoList todolist = new TodoList();
            todolist.setId(rs.getLong("id"));
            todolist.setContent(rs.getString("content"));
            todolist.setCompletion(rs.getBoolean("is_completion"));
            todolist.setCreatedDate(rs.getDate("created_date").toLocalDate());
            todolist.setUserPk(rs.getLong("user_pk"));
            return todolist;
        });
    }

    @Override
    public Optional<TodoList> update(TodoList updateTodoList) {
        String sql = "update todolists set content=:content, is_completion=:isCompletion " +
                "where id=:id";

        MapSqlParameterSource param = new MapSqlParameterSource()
                .addValue("content", updateTodoList.getContent())
                .addValue("isCompletion", updateTodoList.isCompletion())
                .addValue("id", updateTodoList.getId());

        template.update(sql, param);
        return Optional.of(updateTodoList);
    }
    @Override
    public void delete(Long id) {
        String sql = "delete from todolists where id=:id";

        template.update(sql, new MapSqlParameterSource("id", id));
    }

    @Override
    public void allDelete(Long userPk, LocalDate createdDate) {
        String sql = "delete from todolists where user_pk=:userPk and created_date=:createdDate";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("userPk", userPk)
                .addValue("createdDate", createdDate);

        template.update(sql, params);
    }
}
