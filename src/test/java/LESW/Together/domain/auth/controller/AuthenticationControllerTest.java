package LESW.Together.domain.auth.controller;

import LESW.Together.domain.auth.service.AuthenticationService;
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
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AuthenticationControllerTest {

    @Autowired
    ObjectMapper mapper;

    @Autowired
    AuthenticationController authenticationController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(authenticationController).build();
    }

    @Test
    void register() throws Exception {
        // given
        SignupUserDTO signupUserDTO = SignupUserDTO.builder()
                .userId("test")
                .password("1234")
                .username("테스터")
                .keyNum(1)
                .keyValue("없음")
                .build();

        String signupJson = mapper.writeValueAsString(signupUserDTO);

        // when
        ResultActions resultActions = mockMvc.perform(post("/api/auth/users")
                .content(signupJson)
                .contentType(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(log())
                .andDo(print())
                .andReturn();
    }

    @Test
    void authenticate() throws Exception {
        // given
        SignupUserDTO signupUserDTO = SignupUserDTO.builder()
                .userId("test")
                .password("1234")
                .username("테스터")
                .keyNum(1)
                .keyValue("없음")
                .build();

        String signupJson = mapper.writeValueAsString(signupUserDTO);

        mockMvc.perform(post("/api/auth/users")
                .content(signupJson)
                .contentType(MediaType.APPLICATION_JSON));

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
        Object token = new JacksonJsonParser().parseMap(resultActions.andReturn().getResponse().getContentAsString()).get("token");
        log.info("{}", token.toString());
    }
}