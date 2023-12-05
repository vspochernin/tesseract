package ru.spbstu.tesseract.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.spbstu.tesseract.entity.User;
import ru.spbstu.tesseract.repository.UserRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
