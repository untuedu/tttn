package vn.edu.ptit.htx.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.ptit.htx.model.KhoVatTu;

public interface KhoVatTuRepository extends JpaRepository<KhoVatTu, Integer> {
    boolean existsByTenKhoIgnoreCase(String tenKho);
}
