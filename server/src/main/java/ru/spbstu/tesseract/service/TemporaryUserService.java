package ru.spbstu.tesseract.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.spbstu.tesseract.auth.user.User;
import ru.spbstu.tesseract.auth.user.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TemporaryUserService {

    private final UserRepository userRepository;

    public List<User> getUsers() {
        return userRepository.findAll();
    }
}