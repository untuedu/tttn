package vn.edu.ptit.htx.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

@Entity
public class LoaiVatTu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maLoai;

    @NotBlank
    private String tenLoai = "";

    public Integer getMaLoai() { return maLoai; }
    public void setMaLoai(Integer maLoai) { this.maLoai = maLoai; }
    public String getTenLoai() { return tenLoai; }
    public void setTenLoai(String tenLoai) { this.tenLoai = tenLoai; }
}
