package vn.edu.ptit.htx.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;

@Entity
public class NhatKyMuaSam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maNhatKyMuaSam;

    @NotBlank
    private String tenVatTu = "";

    private String xuatXu = "";

    @Min(1)
    private int soLuong;

    @Min(0)
    private double gia;

    private LocalDate ngayMua = LocalDate.now();
    private LocalDate ngaySanXuat = LocalDate.now();
    private LocalDate hanSuDung = LocalDate.now().plusMonths(6);
    private int soLuongDaSuDung;
    private String trangThai = "Con hang";

    @ManyToOne
    private LoaiVatTu loaiVatTu;

    @ManyToOne
    private KhoVatTu khoVatTu;

    @ManyToOne
    private NguoiDung nguoiDung;

    public Integer getMaNhatKyMuaSam() { return maNhatKyMuaSam; }
    public void setMaNhatKyMuaSam(Integer maNhatKyMuaSam) { this.maNhatKyMuaSam = maNhatKyMuaSam; }
    public String getTenVatTu() { return tenVatTu; }
    public void setTenVatTu(String tenVatTu) { this.tenVatTu = tenVatTu; }
    public String getXuatXu() { return xuatXu; }
    public void setXuatXu(String xuatXu) { this.xuatXu = xuatXu; }
    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }
    public double getGia() { return gia; }
    public void setGia(double gia) { this.gia = gia; }
    public LocalDate getNgayMua() { return ngayMua; }
    public void setNgayMua(LocalDate ngayMua) { this.ngayMua = ngayMua; }
    public LocalDate getNgaySanXuat() { return ngaySanXuat; }
    public void setNgaySanXuat(LocalDate ngaySanXuat) { this.ngaySanXuat = ngaySanXuat; }
    public LocalDate getHanSuDung() { return hanSuDung; }
    public void setHanSuDung(LocalDate hanSuDung) { this.hanSuDung = hanSuDung; }
    public int getSoLuongDaSuDung() { return soLuongDaSuDung; }
    public void setSoLuongDaSuDung(int soLuongDaSuDung) { this.soLuongDaSuDung = soLuongDaSuDung; }
    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
    public LoaiVatTu getLoaiVatTu() { return loaiVatTu; }
    public void setLoaiVatTu(LoaiVatTu loaiVatTu) { this.loaiVatTu = loaiVatTu; }
    public KhoVatTu getKhoVatTu() { return khoVatTu; }
    public void setKhoVatTu(KhoVatTu khoVatTu) { this.khoVatTu = khoVatTu; }
    public NguoiDung getNguoiDung() { return nguoiDung; }
    public void setNguoiDung(NguoiDung nguoiDung) { this.nguoiDung = nguoiDung; }
}
