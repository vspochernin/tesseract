package ru.spbstu.tesseract.repository;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.spbstu.tesseract.entity.Portfolio;
import ru.spbstu.tesseract.entity.User;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Integer> {

    Slice<Portfolio> findAllByUser(User user, Pageable page);

    Optional<Portfolio> findByIdAndUser(int id, User user);
}
