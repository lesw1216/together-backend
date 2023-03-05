package LESW.Together.domain.todolist.service;

import LESW.Together.domain.todolist.dto.*;
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
import java.time.LocalDateTime;
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
        TodoServiceDto todoAddServiceDto = TodoPostRequestDto.builder()
                .content("운동하기")
                .isCompletion(false)
                .createdDate(LocalDateTime.now())
                .userPk(saveUser.getId())
                .build().toServiceDto();
        TodoResponseDto todoResponseDto = service.save(todoAddServiceDto);

        Assertions.assertThat(todoResponseDto.getContent()).isEqualTo(todoAddServiceDto.getContent());
    }


    @Test
    void findAllTodoList() {
        final LocalDateTime localDateTimeNow = LocalDateTime.now();
        TodoPostRequestDto todoPostRequestDto1 = TodoPostRequestDto.builder()
                .content("운동하기")
                .isCompletion(false)
                .createdDate(localDateTimeNow)
                .userPk(saveUser.getId())
                .build();

        TodoPostRequestDto todoPostRequestDto2 = TodoPostRequestDto.builder()
                .content("밥먹기")
                .isCompletion(false)
                .createdDate(localDateTimeNow)
                .userPk(saveUser.getId())
                .build();

        TodoServiceDto todoServiceDto1 = todoPostRequestDto1.toServiceDto();
        TodoServiceDto todoServiceDto2 = todoPostRequestDto2.toServiceDto();

        TodoResponseDto todoResponseDto1 = service.save(todoServiceDto1);
        TodoResponseDto todoResponseDto2 = service.save(todoServiceDto2);

        TodoGetRequestDto todoGetRequestDto = TodoGetRequestDto.builder()
                .userPk(saveUser.getId())
                .createdDate(localDateTimeNow)
                .build();

        TodoServiceDto todoFindServiceDto = todoGetRequestDto.toServiceDto();

        List<TodoResponseDto> allTodoList = service.findAllTodoList(todoFindServiceDto);

        Assertions.assertThat(allTodoList).contains(todoResponseDto1, todoResponseDto2);

    }

    @Test
    void deleteByIdAndDate() {
        // given

        // 임시 데이터 1개를 저장한다.
        final LocalDateTime localDateTimeNow = LocalDateTime.now();

        TodoServiceDto todoAddServiceDto = TodoPostRequestDto.builder()
                .content("운동하기")
                .isCompletion(false)
                .createdDate(localDateTimeNow)
                .userPk(saveUser.getId())
                .build().toServiceDto();
        TodoResponseDto todoResponseDto = service.save(todoAddServiceDto);

        // when

        // 저장한 임시데이터를 삭제한다.
        TodoServiceDto todoDeleteServiceDto = TodoDeleteRequestDto.builder()
                .id(todoResponseDto.getId())
                .createdDate(localDateTimeNow)
                .userPk(saveUser.getId())
                .build().toServiceDto();
        service.deleteTodoListById(todoDeleteServiceDto);


        // then

        // 해당 유저의 모든 할일 목록을 가져온다.
        TodoServiceDto todoFindAllServiceDto = TodoGetRequestDto.builder()
                .userPk(saveUser.getId())
                .createdDate(localDateTimeNow)
                .build().toServiceDto();
        List<TodoResponseDto> allTodoList = service.findAllTodoList(todoFindAllServiceDto);

        // 검증
        Assertions.assertThat(allTodoList).isNotIn(todoResponseDto);
    }

    @Test
    void allDeleteByIdAndDate() {
        // given

        // 임시 데이터 2개를 저장한다.
        final LocalDateTime localDateTimeNow = LocalDateTime.now();
        TodoServiceDto todoAddServiceDto1 = TodoPostRequestDto.builder()
                .content("운동하기")
                .isCompletion(false)
                .createdDate(localDateTimeNow)
                .userPk(saveUser.getId())
                .build().toServiceDto();

        TodoServiceDto todoAddServiceDto2 = TodoPostRequestDto.builder()
                .content("밥먹기")
                .isCompletion(false)
                .createdDate(localDateTimeNow)
                .userPk(saveUser.getId())
                .build().toServiceDto();


        TodoResponseDto saveTodoRequestDto1 = service.save(todoAddServiceDto1);
        TodoResponseDto saveTodoRequestDto2 = service.save(todoAddServiceDto2);

        // when

        // 임시데이터 두개를 모두 삭제한다.
        TodoServiceDto todoDeleteAllServiceDto = TodoDeleteRequestDto.builder()
                .userPk(saveUser.getId())
                .createdDate(localDateTimeNow)
                .build().toServiceDto();
        service.allDeleteByIdAndDate(todoDeleteAllServiceDto);

        // then

        // 해당 유저의 특정날짜 todolist 가져오기
        TodoServiceDto todoFindAllServiceDto = TodoGetRequestDto.builder()
                .userPk(saveUser.getId())
                .createdDate(localDateTimeNow)
                .build().toServiceDto();
        List<TodoResponseDto> allTodoList = service.findAllTodoList(todoFindAllServiceDto);

        // 꺼내온 todolists에 위에서 생성한 임시데이터가 존재하지 않음을 검증.
        Assertions.assertThat(allTodoList).isNotIn(saveTodoRequestDto1, saveTodoRequestDto2);
    }

    @Test
    void updateContent() {
        // given

        // 임시 데이터 1개를 저장한다.
        final LocalDateTime localDateTimeNow = LocalDateTime.now();
        TodoServiceDto todoAddServiceDto1 = TodoPostRequestDto.builder()
                .content("운동하기")
                .isCompletion(false)
                .createdDate(localDateTimeNow)
                .userPk(saveUser.getId())
                .build().toServiceDto();
        TodoResponseDto saveTodoRequestDto1 = service.save(todoAddServiceDto1);


        // when
        TodoPutRequestDto todoPutRequestDto = TodoPutRequestDto.builder()
                .id(saveTodoRequestDto1.getId())
                .content("벤치프레스 50키로 5세트 10회 반복하기")
                .isCompletion(false)
                .build();
        TodoServiceDto updateTodoServiceDto = service.updateTodoList(todoPutRequestDto.toServiceDto());


        // then
        Assertions.assertThat(updateTodoServiceDto.getContent()).isEqualTo(todoPutRequestDto.getContent());
    }

    @Test
    void updateIsCompletion() {
        // given

        // 임시 데이터 1개를 저장한다.
        final LocalDateTime localDateTimeNow = LocalDateTime.now();
        TodoServiceDto todoAddServiceDto1 = TodoPostRequestDto.builder()
                .content("운동하기")
                .isCompletion(false)
                .createdDate(localDateTimeNow)
                .userPk(saveUser.getId())
                .build().toServiceDto();
        TodoResponseDto saveTodoRequestDto1 = service.save(todoAddServiceDto1);


        // when
        TodoPutRequestDto todoPutRequestDto = TodoPutRequestDto.builder()
                .id(saveTodoRequestDto1.getId())
                .content("벤치프레스 50키로 5세트 10회 반복하기")
                .isCompletion(true)
                .build();
        TodoServiceDto updateTodoServiceDto = service.updateTodoList(todoPutRequestDto.toServiceDto());


        // then
        Assertions.assertThat(updateTodoServiceDto.isCompletion()).isEqualTo(todoPutRequestDto.isCompletion());
    }
}