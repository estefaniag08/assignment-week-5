package dev.applaudostudios.examples.assignmentweek5.persistence;

import dev.applaudostudios.examples.assignmentweek5.spring.AssignmentWeek5Application;
import dev.applaudostudios.examples.assignmentweek5.persistence.model.User;


import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertThrows;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.Collection;

@SpringBootTest(classes = AssignmentWeek5Application.class)
@DisplayName("When running UserRepository")
public class UserRepositoryTests {

    @Autowired
    UserRepository userRepository;

    User user;

    @BeforeEach
    void loadUser() {
        user = new User("user@email.com", "userName", "userLastName", "+50712345678");
    }

    @TestFactory
    @DisplayName("given invalid user fields to save into repository")
    Collection<DynamicTest> givenInvalidUser_whenSave_thenReturnError(TestReporter testReporter) {
        return Arrays.asList(
                DynamicTest.dynamicTest("(Invalid email field)", () -> {
                    User invalidUser = new User("invalidEmail", user.getFirstName(),
                            user.getLastName(), user.getPhoneNumber());
                    ConstraintViolationException thrown = assertThrows(ConstraintViolationException.class,
                            () -> userRepository.save(invalidUser),
                            "should throw ConstraintViolationException");
                    testReporter.publishEntry("Invalid email test case", String.valueOf(thrown.getConstraintViolations()));
                }),
                DynamicTest.dynamicTest("(Invalid first name field)", () -> {
                    User invalidUser = new User(user.getEmail(), "",
                            user.getLastName(), user.getPhoneNumber());
                    ConstraintViolationException thrown = assertThrows(ConstraintViolationException.class,
                            () -> userRepository.save(invalidUser),
                            "should throw ConstraintViolationException");
                    testReporter.publishEntry("Invalid first name test case", String.valueOf(thrown.getConstraintViolations()));
                }),
                DynamicTest.dynamicTest("(Invalid last name field)", () -> {
                    User invalidUser = new User(user.getEmail(), user.getFirstName(),
                            "", user.getPhoneNumber());
                    ConstraintViolationException thrown = assertThrows(ConstraintViolationException.class,
                            () -> userRepository.save(invalidUser),
                            "should throw ConstraintViolationException");
                    testReporter.publishEntry("Invalid last name test case", String.valueOf(thrown.getConstraintViolations()));
                })
        );
    }

}
