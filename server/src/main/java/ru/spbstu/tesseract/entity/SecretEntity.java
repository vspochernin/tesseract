package ru.spbstu.tesseract.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "secrets")
@Getter
public class SecretEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String secret;
}
