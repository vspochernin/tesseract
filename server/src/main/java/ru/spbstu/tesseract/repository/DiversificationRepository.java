package ru.spbstu.tesseract.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.spbstu.tesseract.entity.Diversification;

@Repository
public interface DiversificationRepository extends JpaRepository<Diversification, Integer> {
}
