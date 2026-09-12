package vn.edu.ptit.htx.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.ptit.htx.model.NguoiDung;

public interface NguoiDungRepository extends JpaRepository<NguoiDung, Integer> {
    Optional<NguoiDung> findByEmailIgnoreCaseAndMatKhauAndHoatDongTrue(String email, String matKhau);
}
