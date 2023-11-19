package ru.spbstu.tesseract.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.spbstu.tesseract.entity.UserEntity;
import ru.spbstu.tesseract.repository.UserRepository;

@Service
public class UserService {

    private static final Long FIRST_USER_ID = 1L;

    @Autowired
    private UserRepository userRepository;

    public UserEntity getFirstUser() {
        return userRepository.findById(FIRST_USER_ID).orElseThrow();
    }
}
