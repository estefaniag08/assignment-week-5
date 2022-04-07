package dev.applaudostudios.examples.assignmentweek5.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PathController {
    @RequestMapping("/")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String home() {
        return "Hello world";
    }

}
