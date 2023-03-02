package LESW.Together.domain.todolist.controller;

import LESW.Together.domain.todolist.dto.TodoRequestDto;
import LESW.Together.domain.user.dto.SignupUserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class TodoControllerTest {

    @Autowired
    ObjectMapper mapper;
    @Autowired
    MockMvc mvc;


    @Test
    void todoAdd() throws Exception {
//        // given
//        String userId = "test";
//        String password = "1234";
//        String username = "테스터";
//        Integer keyNum = 1;
//        String keyValue = "테스쟁이";
//
//        String registerBody = mapper.writeValueAsString(
//                SignupUserDTO.builder()
//                        .userId(userId)
//                        .password(password)
//                        .username(username)
//                        .keyNum(keyNum)
//                        .keyValue(keyValue)
//                        .build()
//        );
//
//        mvc.perform(post("/api/auth/users")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(registerBody))
//                .andExpect(status().isOk());
//
//
//        String content = "운동";
//        boolean isCompletion = false;
//        LocalDate createdDate = LocalDate.now();
//        Long userPk = 1L;
//
//        // when
//        String body = mapper.writeValueAsString(
//                TodoRequestDto.builder()
//                        .content(content)
//                        .isCompletion(isCompletion)
//                        .createdDate(createdDate)
//                        .userPk(userPk)
//                        .build()
//        );
//
//        // then
//        mvc.perform(post("/api/todo/addition")
//                .content(body)
//                .contentType(MediaType.APPLICATION_JSON))
//
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}