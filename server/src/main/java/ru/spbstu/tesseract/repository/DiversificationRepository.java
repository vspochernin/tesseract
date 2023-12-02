package ru.spbstu.tesseract.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.spbstu.tesseract.entity.Diversification;

public interface DiversificationRepository extends JpaRepository<Diversification, Integer> {
}
