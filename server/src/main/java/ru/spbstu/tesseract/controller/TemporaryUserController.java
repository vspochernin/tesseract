package ru.spbstu.tesseract.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.spbstu.tesseract.service.TemporaryUserService;
import ru.spbstu.tesseract.user.User;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TemporaryUserController {

    private final TemporaryUserService temporaryUserService;

    @GetMapping("/users")
    public List<User> getUsers() {
        return temporaryUserService.getUsers();
    }
}
