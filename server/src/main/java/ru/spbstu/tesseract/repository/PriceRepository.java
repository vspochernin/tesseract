package ru.spbstu.tesseract.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.spbstu.tesseract.entity.Price;

public interface PriceRepository extends JpaRepository<Price, Integer> {
}
