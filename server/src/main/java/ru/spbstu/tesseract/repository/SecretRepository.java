package ru.spbstu.tesseract.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.spbstu.tesseract.entity.SecretEntity;

@Repository
public interface SecretRepository extends JpaRepository<SecretEntity, Long> {
}
