package ru.spbstu.tesseract.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.spbstu.tesseract.entity.Asset;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Integer> {
}
