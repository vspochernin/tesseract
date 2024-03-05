package ru.spbstu.tesseract.repository;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.spbstu.tesseract.entity.Diversification;
import ru.spbstu.tesseract.entity.User;

@Repository
public interface DiversificationRepository extends JpaRepository<Diversification, Integer> {

    Slice<Diversification> findAllByUser(User user, Pageable page);

    Optional<Diversification> findByIdAndUser(int id, User user);
}
