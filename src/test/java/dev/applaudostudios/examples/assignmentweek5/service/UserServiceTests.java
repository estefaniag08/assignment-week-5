package dev.applaudostudios.examples.assignmentweek5.service;

import dev.applaudostudios.examples.assignmentweek5.common.dto.UserDTO;
import dev.applaudostudios.examples.assignmentweek5.common.exception.CrudException;
import dev.applaudostudios.examples.assignmentweek5.common.exception.EmailExistsException;
import dev.applaudostudios.examples.assignmentweek5.persistence.UserRepository;
import dev.applaudostudios.examples.assignmentweek5.persistence.model.User;
import dev.applaudostudios.examples.assignmentweek5.spring.AssignmentWeek5Application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.hamcrest.MatcherAssert;

import static org.hamcrest.Matchers.*;

import org.mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.validation.ConstraintViolationException;
import java.util.Optional;

@SpringBootTest(classes = AssignmentWeek5Application.class)
@DisplayName("When running UserServiceTests")
public class UserServiceTests {
    @MockBean
    UserRepository userRepositoryMock;

    @Autowired
    UserService userService;

    UserDTO userDTO;
    User userDAO;

    @BeforeEach
    void initialize() {
        userDTO = new UserDTO();
        userDTO.setEmail("email@email.com");
        userDTO.setFirstName("firstName");
        userDTO.setLastName("lastName");
        userDTO.setPhoneNumber("+50712345678");
        userDAO = new User("email@email.com", "firstName", "lastName", "+50712345678");
        userDAO.setId(0L);
    }

    @Test
    @DisplayName("given the userDto to create into UserRepository")
    void givenUserDTO_whenSaveIntoRepository_thenReturnsSavedUserDTO() {
        Mockito.when(userRepositoryMock.save(any(User.class))).thenReturn(userDAO);
        UserDTO savedUser = userService.createEntity(userDTO);
        assertAll(
                () -> Mockito.verify(userRepositoryMock, Mockito.description("should call the method save(User) of the repository.")).save(any(User.class)),
                () -> MatcherAssert.assertThat("should have the same id as the one in the repository.",savedUser.getId(),equalTo(userDAO.getId()))
        );
    }

    @Test
    @DisplayName("given the userDto with an existing email to create into UserRepository")
    void givenUserDTOWithExistingEmail_whenSaveIntoRepository_thenThrowsEmailExistsException() {
        Mockito.when(userRepositoryMock.findByEmail(anyString())).thenReturn(userDAO);
        assertThrows(EmailExistsException.class, ()-> userService.createEntity(userDTO), "should throw EmailExistsException.");
    }

    @Test
    @DisplayName("given invalid userDto to create into UserRepository")
    void givenInvalidUserDTO_whenSaveIntoRepository_thenThrowsException() {
        Mockito.when(userRepositoryMock.save(any(User.class))).thenThrow(ConstraintViolationException.class);
        userDTO.setEmail("invalidEmail");
        assertThrows(ConstraintViolationException.class, () -> userService.createEntity(userDTO), "should throw ViolationException.");
    }

    @Test
    @DisplayName("given existing userDto to update into UserRepository")
    void givenExistingUserDTO_whenUpdateUser_thenReturnsUpdatedUser(){
        userDTO.setId(0L);
        Mockito.when(userRepositoryMock.existsById(userDTO.getId())).thenReturn(true);
        Mockito.when(userRepositoryMock.findById(userDTO.getId())).thenReturn(Optional.ofNullable(userDAO));
        Mockito.when(userRepositoryMock.save(any(User.class))).thenReturn(userDAO);

        UserDTO updatedUser = userService.updateEntity(userDTO);
        assertAll(
                ()-> Mockito.verify(userRepositoryMock, Mockito.description("should call the method save(User) from the userRepository")).save(any(User.class)),
                ()-> MatcherAssert.assertThat("should be the same userDTO", updatedUser, equalTo(userDTO))
        );
    }

    @Test
    @DisplayName("given user id to get from the repository")
    void givenUserId_whenGetFromRepository_thenReturnUserDTO(){
        Mockito.when(userRepositoryMock.findById(any(Long.class))).thenReturn(Optional.ofNullable(userDAO));
        Optional<UserDTO> user = userService.getEntity(0L);
        assertAll(
                ()-> Mockito.verify(userRepositoryMock, Mockito.description("should execute findById(id) method of the repository")).findById(any(Long.class)),
                ()-> assertTrue(user.isPresent())
        );
    }

    @Test
    @DisplayName("given non existing user id to get from the repository")
    void givenNonExistingUserId_whenGetFromRepository_thenThrowsCrudException(){
        Mockito.when(userRepositoryMock.findById(any(Long.class))).thenReturn(Optional.empty());
        assertThrows(CrudException.class, ()-> userService.getEntity(0L), "should throw CrudException");
    }

    @Test
    @DisplayName("given existing userDto to update into UserRepository")
    void givenUserDTOWithEmail_whenUpdateUser_thenReturnsUserWithoutEmailUpdated(){
        userDTO.setId(0L);
        userDTO.setEmail("anotherEmail@email.com");
        Mockito.when(userRepositoryMock.existsById(userDTO.getId())).thenReturn(true);
        Mockito.when(userRepositoryMock.findById(userDTO.getId())).thenReturn(Optional.ofNullable(userDAO));
        Mockito.when(userRepositoryMock.save(any(User.class))).thenReturn(userDAO);

        UserDTO updatedUser = userService.updateEntity(userDTO);
        assertAll(
                ()-> Mockito.verify(userRepositoryMock, Mockito.description("should call the method save(User) from the userRepository")).save(any(User.class)),
                ()-> MatcherAssert.assertThat("should be the the email of the userDAO", updatedUser.getEmail(), equalToIgnoringCase(userDTO.getEmail()))
        );
    }

    @Test
    @DisplayName("given non existing userDto to update into UserRepository")
    void givenNotExistingUserDto_whenUpdateUser_thenThrowsCrudException(){
        Mockito.when(userRepositoryMock.existsById(userDTO.getId())).thenReturn(false);
        assertThrows(CrudException.class, ()-> userService.updateEntity(userDTO), "should throw CrudException");
    }

    @Test
    @DisplayName("given user email to get the user from the repository")
    void givenUserEmail_whenGetUserFromRepository_thenReturnsUserDto(){
        Mockito.when(userRepositoryMock.findByEmail(anyString())).thenReturn(userDAO);
        Optional<UserDTO> user = userService.getEntityByEmail("email@email.com");
        assertAll(
                ()-> Mockito.verify(userRepositoryMock, Mockito.description("should execute findById(email) method of the repository")).findByEmail(anyString()),
                ()-> assertTrue(user.isPresent(), "UserDto should be present")
        );
    }

    @Test
    @DisplayName("given non existing user email to get the user from the repository")
    void givenNonExistingUserEmail_whenGetUserFromRepository_thenReturnsEmptyOptionalUserDto(){
        Mockito.when(userRepositoryMock.findById(any(Long.class))).thenReturn(null);
        Optional<UserDTO> user = userService.getEntityByEmail("email@email.com");
        assertAll(
                ()-> Mockito.verify(userRepositoryMock, Mockito.description("should execute findById(email) method of the repository")).findByEmail(anyString()),
                ()-> assertTrue(user.isEmpty(), "UserDto shouldn't be present")
        );
    }


}