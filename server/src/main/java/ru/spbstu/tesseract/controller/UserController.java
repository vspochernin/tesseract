package ru.spbstu.tesseract.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.spbstu.tesseract.entity.UserEntity;
import ru.spbstu.tesseract.service.UserService;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public UserEntity getFirstUser() {
        return userService.getFirstUser();
    }
}
