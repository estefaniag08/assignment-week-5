package dev.applaudostudios.examples.assignmentweek5.web.controller;


import com.fasterxml.jackson.databind.ObjectMapper;

import dev.applaudostudios.examples.assignmentweek5.common.dto.UserDTO;
import dev.applaudostudios.examples.assignmentweek5.common.exception.EmailExistsException;
import dev.applaudostudios.examples.assignmentweek5.common.exception.ResponseException;
import dev.applaudostudios.examples.assignmentweek5.common.exception.ResponseExceptionHandler;
import dev.applaudostudios.examples.assignmentweek5.service.UserService;
import dev.applaudostudios.examples.assignmentweek5.spring.AssignmentWeek5Application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = AssignmentWeek5Application.class)
@DisplayName("When running UserController")
public class UserControllerTests {

    @Mock
    UserService userService;

    UserDTO userDTO;

    @Autowired
    @InjectMocks
    UserController userController = new UserController();

    MockMvc mockMvc = MockMvcBuilders.standaloneSetup(userController)
            .setControllerAdvice(new ResponseExceptionHandler())
            .build();

    @BeforeEach
    void initialize() {
        userDTO = new UserDTO();
        userDTO.setEmail("email@email.com");
        userDTO.setFirstName("firstName");
        userDTO.setLastName("lastName");
        userDTO.setPhoneNumber("+50712345678");
    }
    @Nested
    @DisplayName("When running post requests")
    class postRequestTestCases{
        @Test
        void givenUserDto_whenCreatingUser_thenResponseCreated() throws Exception {
            Mockito.when(userService.createEntity(any(UserDTO.class))).thenReturn(userDTO);
            mockMvc.perform(post("/user")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(new ObjectMapper().writeValueAsString(userDTO))
                    )
                    .andDo(print())
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("message").value("User created successfully."));
        }

        @Test
        void givenExistingEmailOnUserDto_whenCreatingUser_thenThrowsEmailExistsExceptionAndBadRequest() throws Exception {
            Mockito.when(userService.createEntity(any(UserDTO.class))).thenThrow(
                    new EmailExistsException("The user already exists with the given email account."));
            mockMvc.perform(post("/user")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(new ObjectMapper().writeValueAsString(userDTO))
                    )
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect( result -> assertTrue(result.getResolvedException() instanceof ResponseException))
                    .andExpect(jsonPath("message")
                            .value("The user already exists with the given email account."));
        }

        @Test
        void givenInvalidUserDto_whenCreatingUser_thenThrowsResponseExceptionAndBadRequest() throws Exception{
            userDTO.setLastName("");
            Mockito.when(userService.createEntity(any(UserDTO.class))).thenReturn(userDTO);
            mockMvc.perform(post("/user")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(new ObjectMapper().writeValueAsString(userDTO))
                    )
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect( result -> assertNotNull(result.getResolvedException()));
        }
    }

    @Nested
    @DisplayName("When running patch requests")
    class pathRequestTestCases{
        @Test
        void givenUserDto_whenUpdatingUser_thenResponseOk() throws Exception{
            userDTO.setEmail(null);
            Mockito.when(userService.updateEntity(any(UserDTO.class))).thenReturn(userDTO);
            mockMvc.perform(patch("/user/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(new ObjectMapper().writeValueAsString(userDTO))
                    )
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("message").value("User successfully updated."));
        }

        @Test
        void givenUserDtoWithEmail_whenUpdatingUser_thenResponseAccepted() throws Exception{
            Mockito.when(userService.updateEntity(any(UserDTO.class))).thenReturn(userDTO);
            mockMvc.perform(patch("/user/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(new ObjectMapper().writeValueAsString(userDTO))
                    )
                    .andDo(print())
                    .andExpect(status().isAccepted())
                    .andExpect(jsonPath("message")
                            .value("User updated but not the email. It's not allowed."));
        }

        @Test
        void givenInvalidUserDto_whenUpdatingUser_thenThrowsResponseExceptionAndBadRequest() throws Exception{
            userDTO.setLastName("");
            userDTO.setEmail("invalidEmail");
            //Mockito.when(userService.updateEntity(any(UserDTO.class))).thenReturn(userDTO);
            mockMvc.perform(patch("/user/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(new ObjectMapper().writeValueAsString(userDTO))
                    )
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect( result -> assertNotNull(result.getResolvedException()));
            //.andExpect(jsonPath("message").value("Error in some fields."));
        }

    }

    @Nested
    @DisplayName("When running get requests")
    class getRequestTestCases{
        @Test
        void givenIdUser_whenGettingUser_thenResponseOkAndUserDto() throws Exception{
            Mockito.when(userService.getEntity(any(Long.class))).thenReturn(Optional.ofNullable(userDTO));
            mockMvc.perform(get("/user/1")
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("email").value(userDTO.getEmail()));
        }

        @Test
        void givenUserEmail_whenGettingUser_thenResponseOkAndUserDto() throws Exception{
            Mockito.when(userService.getEntityByEmail(anyString())).thenReturn(Optional.ofNullable(userDTO));
            mockMvc.perform(get("/user/").param("email", "email@email.com")
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("email").value(userDTO.getEmail()));
        }

        @Test
        void givenNonExistingUserEmail_whenGettingUser_thenResponseNotFound() throws Exception{
            Mockito.when(userService.getEntityByEmail(anyString())).thenReturn(Optional.empty());
            mockMvc.perform(get("/user/").param("email", "email@email.com")
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andDo(print())
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("message")
                            .value("We could not find a user with the given email."));
        }
    }
}
