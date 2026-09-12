package vn.edu.ptit.htx.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
public class KhuVuc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maKhuVuc;

    @NotBlank
    private String tenKhuVuc = "";

    @Min(1)
    private double dienTich;

    private String sanPham = "";
    private String hinhAnhSanPham = "";
    private LocalDateTime thoiGianTao = LocalDateTime.now();

    @ManyToOne
    private NguoiDung nguoiDung;

    @ManyToOne
    private CoSoNuoiTrong coSoNuoiTrong;

    public Integer getMaKhuVuc() { return maKhuVuc; }
    public void setMaKhuVuc(Integer maKhuVuc) { this.maKhuVuc = maKhuVuc; }
    public String getTenKhuVuc() { return tenKhuVuc; }
    public void setTenKhuVuc(String tenKhuVuc) { this.tenKhuVuc = tenKhuVuc; }
    public double getDienTich() { return dienTich; }
    public void setDienTich(double dienTich) { this.dienTich = dienTich; }
    public String getSanPham() { return sanPham; }
    public void setSanPham(String sanPham) { this.sanPham = sanPham; }
    public String getHinhAnhSanPham() { return hinhAnhSanPham; }
    public void setHinhAnhSanPham(String hinhAnhSanPham) { this.hinhAnhSanPham = hinhAnhSanPham; }
    public LocalDateTime getThoiGianTao() { return thoiGianTao; }
    public void setThoiGianTao(LocalDateTime thoiGianTao) { this.thoiGianTao = thoiGianTao; }
    public NguoiDung getNguoiDung() { return nguoiDung; }
    public void setNguoiDung(NguoiDung nguoiDung) { this.nguoiDung = nguoiDung; }
    public CoSoNuoiTrong getCoSoNuoiTrong() { return coSoNuoiTrong; }
    public void setCoSoNuoiTrong(CoSoNuoiTrong coSoNuoiTrong) { this.coSoNuoiTrong = coSoNuoiTrong; }
}
