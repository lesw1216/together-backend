package LESW.Together.domain.todolist.controller;

import LESW.Together.domain.auth.controller.AuthenticationController;
import LESW.Together.domain.user.dto.SignupUserDTO;
import LESW.Together.domain.user.dto.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class TodoControllerTest {

    @Autowired
    ObjectMapper mapper;

    @Autowired
    TodoController todoController;

    @Autowired
    AuthenticationController authenticationController;

    MockMvc mockMvc;

    String token;
    Long userPk;

    @BeforeEach
    void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.standaloneSetup(authenticationController, todoController ).build();

        SignupUserDTO signupUserDTO = SignupUserDTO.builder()
                .userId("test")
                .password("1234")
                .username("테스터")
                .keyNum(1)
                .keyValue("없음").build();

        String signupUserJSON = mapper.writeValueAsString(signupUserDTO);

        ResultActions perform = mockMvc.perform(post("/api/auth/users")
                .content(signupUserJSON)
                .contentType(MediaType.APPLICATION_JSON));

        perform.andExpect(status().isOk());
    }

    void authenticate() throws Exception {
        // Login
        UserDto userDto = UserDto.builder()
                .userId("test")
                .password("1234")
                .build();

        String userDtoJson = mapper.writeValueAsString(userDto);

        // when
        ResultActions resultActions = mockMvc.perform(post("/api/auth/authentication")
                .content(userDtoJson)
                .contentType(MediaType.APPLICATION_JSON));

        // then
        this.token = (String) new JacksonJsonParser().parseMap(resultActions.andReturn().getResponse().getContentAsString()).get("token");

        Integer userPkInteger = (Integer) new JacksonJsonParser().parseMap(resultActions.andReturn().getResponse().getContentAsString()).get("userPk");
        this.userPk = Long.valueOf(userPkInteger);
    }

    @Test
    void todoFind() throws Exception {
        // given
        authenticate();
        String uri = "/api/todoLists?userPk=" + userPk + "&createdDate=2021-06-20T06:09:09.043Z";

        // when
        ResultActions resultActions = mockMvc.perform(get(uri)
                .header("Authorization", token));


        // then
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
    }


    @Test
    void todoAdd() throws Exception {

        // given
        authenticate();

        HashMap<String, Object> requestBodyObjectMap = new HashMap<>();
        requestBodyObjectMap.put("userPk", userPk);
        requestBodyObjectMap.put("content", "운동하기");
        requestBodyObjectMap.put("isCompletion", false);
        requestBodyObjectMap.put("createdDate", "2023-03-03T17:44:25.906Z");

        String addTodoJson = mapper.writeValueAsString(requestBodyObjectMap);

        // when
        ResultActions resultActions = mockMvc.perform(post("/api/todoLists")
                .content(addTodoJson)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", this.token));

        // then
        resultActions
                .andExpect(status().isOk())
                .andDo(print());
    }
}