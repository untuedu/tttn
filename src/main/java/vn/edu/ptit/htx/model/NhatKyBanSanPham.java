package vn.edu.ptit.htx.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import java.time.LocalDate;

@Entity
public class NhatKyBanSanPham {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maNhatKyBanSanPham;

    @Min(1)
    private int soLuong;

    @Min(0)
    private double giaBan;

    private LocalDate ngayBan = LocalDate.now();
    private String qrCode = "";

    @ManyToOne
    private NhatKyThuHoach nhatKyThuHoach;

    public Integer getMaNhatKyBanSanPham() { return maNhatKyBanSanPham; }
    public void setMaNhatKyBanSanPham(Integer maNhatKyBanSanPham) { this.maNhatKyBanSanPham = maNhatKyBanSanPham; }
    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }
    public double getGiaBan() { return giaBan; }
    public void setGiaBan(double giaBan) { this.giaBan = giaBan; }
    public LocalDate getNgayBan() { return ngayBan; }
    public void setNgayBan(LocalDate ngayBan) { this.ngayBan = ngayBan; }
    public String getQrCode() { return qrCode; }
    public void setQrCode(String qrCode) { this.qrCode = qrCode; }
    public NhatKyThuHoach getNhatKyThuHoach() { return nhatKyThuHoach; }
    public void setNhatKyThuHoach(NhatKyThuHoach nhatKyThuHoach) { this.nhatKyThuHoach = nhatKyThuHoach; }
}
