package ru.spbstu.tesseract.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.spbstu.tesseract.entity.SecretEntity;

@Repository
public interface SecretRepository extends CrudRepository<SecretEntity, Long> {
}
