package ru.spbstu.tesseract.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.spbstu.tesseract.entity.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {
}
