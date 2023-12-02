package ru.spbstu.tesseract.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.spbstu.tesseract.entity.Price;

@Repository
public interface PriceRepository extends JpaRepository<Price, Integer> {
}
