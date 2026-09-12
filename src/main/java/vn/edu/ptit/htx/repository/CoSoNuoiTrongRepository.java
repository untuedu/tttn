package vn.edu.ptit.htx.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.ptit.htx.model.CoSoNuoiTrong;

public interface CoSoNuoiTrongRepository extends JpaRepository<CoSoNuoiTrong, Integer> {
    boolean existsByDiaChiIgnoreCase(String diaChi);
}
