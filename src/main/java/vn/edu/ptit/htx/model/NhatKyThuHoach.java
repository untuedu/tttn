package vn.edu.ptit.htx.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import java.time.LocalDate;

@Entity
public class NhatKyThuHoach {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maNhatKyThuHoach;

    @Min(1)
    private int soLuongThuHoach;

    private int soLuongDaBan;
    private LocalDate ngayThuHoach = LocalDate.now();

    @ManyToOne
    private KhuVuc khuVuc;

    public Integer getMaNhatKyThuHoach() { return maNhatKyThuHoach; }
    public void setMaNhatKyThuHoach(Integer maNhatKyThuHoach) { this.maNhatKyThuHoach = maNhatKyThuHoach; }
    public int getSoLuongThuHoach() { return soLuongThuHoach; }
    public void setSoLuongThuHoach(int soLuongThuHoach) { this.soLuongThuHoach = soLuongThuHoach; }
    public int getSoLuongDaBan() { return soLuongDaBan; }
    public void setSoLuongDaBan(int soLuongDaBan) { this.soLuongDaBan = soLuongDaBan; }
    public LocalDate getNgayThuHoach() { return ngayThuHoach; }
    public void setNgayThuHoach(LocalDate ngayThuHoach) { this.ngayThuHoach = ngayThuHoach; }
    public KhuVuc getKhuVuc() { return khuVuc; }
    public void setKhuVuc(KhuVuc khuVuc) { this.khuVuc = khuVuc; }
}
