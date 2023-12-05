package ru.spbstu.tesseract.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.spbstu.tesseract.entity.Diversification;

@Repository
public interface DiversificationRepository extends JpaRepository<Diversification, Integer> {

    Slice<Diversification> findBy(Pageable page);
}
