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
public class NhatKySanXuat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maNhatKySanXuat;

    @NotBlank
    private String tenVatTu = "";

    @Min(1)
    private int soLuongSuDung;

    private LocalDate ngaySuDung = LocalDate.now();

    @ManyToOne
    private KhoVatTu khoVatTu;

    @ManyToOne
    private KhuVuc khuVuc;

    public Integer getMaNhatKySanXuat() { return maNhatKySanXuat; }
    public void setMaNhatKySanXuat(Integer maNhatKySanXuat) { this.maNhatKySanXuat = maNhatKySanXuat; }
    public String getTenVatTu() { return tenVatTu; }
    public void setTenVatTu(String tenVatTu) { this.tenVatTu = tenVatTu; }
    public int getSoLuongSuDung() { return soLuongSuDung; }
    public void setSoLuongSuDung(int soLuongSuDung) { this.soLuongSuDung = soLuongSuDung; }
    public LocalDate getNgaySuDung() { return ngaySuDung; }
    public void setNgaySuDung(LocalDate ngaySuDung) { this.ngaySuDung = ngaySuDung; }
    public KhoVatTu getKhoVatTu() { return khoVatTu; }
    public void setKhoVatTu(KhoVatTu khoVatTu) { this.khoVatTu = khoVatTu; }
    public KhuVuc getKhuVuc() { return khuVuc; }
    public void setKhuVuc(KhuVuc khuVuc) { this.khuVuc = khuVuc; }
}
