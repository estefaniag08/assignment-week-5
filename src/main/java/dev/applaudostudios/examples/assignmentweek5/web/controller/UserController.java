package dev.applaudostudios.examples.assignmentweek5.web.controller;

import dev.applaudostudios.examples.assignmentweek5.common.dto.IEntityDTO;
import dev.applaudostudios.examples.assignmentweek5.common.dto.SuccessDTO;
import dev.applaudostudios.examples.assignmentweek5.common.dto.UserDTO;
import dev.applaudostudios.examples.assignmentweek5.service.UserService;
import dev.applaudostudios.examples.assignmentweek5.common.exception.EmailExistsException;

import dev.applaudostudios.examples.assignmentweek5.common.exception.ResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.persistence.NoResultException;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ResponseEntity<IEntityDTO> createUser(@RequestBody @Valid final UserDTO user, final BindingResult result) {
        System.out.println(user.toString());
        if (result.hasErrors()) {
            List<?> errors = result.getFieldErrors().stream().map(
                    objectError -> new ObjectError("User", "Error in " +
                            objectError.getField() + ". " + objectError.getDefaultMessage())).toList();
            throw new ResponseException("Error in some fields.", errors, HttpStatus.BAD_REQUEST);
        }
        try {
            userService.createEntity(user);
            SuccessDTO success = new SuccessDTO("User created successfully.");
            return new ResponseEntity<>(success, HttpStatus.CREATED);
        } catch (EmailExistsException e) {
            throw new ResponseException(e.getMessage(), null, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = {"{id}"}, method = RequestMethod.PATCH)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<IEntityDTO> updateUser(@RequestBody @Valid final UserDTO user, @PathVariable("id") final Long id, final BindingResult result) {
        if (result.hasErrors()) {
            List<?> errors = result.getFieldErrors().stream().map(
                    objectError -> new ObjectError("User", "Error in " +
                            objectError.getField() + ". " + objectError.getDefaultMessage())).toList();
            throw new ResponseException("Error in some fields.", errors, HttpStatus.BAD_REQUEST);
        } else {
            user.setId(id);
            userService.updateEntity(user);
            if(user.getEmail() == null){
                SuccessDTO success = new SuccessDTO("User successfully updated.");
                return new ResponseEntity<>(success, HttpStatus.OK);
            } else {
                SuccessDTO success = new SuccessDTO("User updated but not the email. It's not allowed.");
                return new ResponseEntity<>(success, HttpStatus.ACCEPTED);
            }
        }
    }

    @RequestMapping(value = {"{id}"}, method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public UserDTO getUser(@PathVariable("id") final Long id) {
        Optional<UserDTO> user = userService.getEntity(id);
        return user.get();
    }

    @RequestMapping(method = RequestMethod.GET, params = {"email"})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public UserDTO getUserByEmail(@RequestParam("email") final String email) {
        Optional<UserDTO> user = userService.getEntityByEmail(email);
        if(user.isPresent()){
            return user.get();
        } else {
            throw new ResponseException("We could not find a user with the given email.", null, HttpStatus.NOT_FOUND);
        }
    }
}
