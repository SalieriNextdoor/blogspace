package com.blogspace.BlogSpace;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("api/hello")
    public String hello() {
        return "Hello, World from Spring Boot!";
    }
}
