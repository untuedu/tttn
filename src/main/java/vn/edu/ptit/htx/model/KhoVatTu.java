package vn.edu.ptit.htx.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

@Entity
public class KhoVatTu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maKhoVatTu;

    @NotBlank(message = "Ten kho khong duoc bo trong")
    private String tenKho = "";

    public Integer getMaKhoVatTu() { return maKhoVatTu; }
    public void setMaKhoVatTu(Integer maKhoVatTu) { this.maKhoVatTu = maKhoVatTu; }
    public String getTenKho() { return tenKho; }
    public void setTenKho(String tenKho) { this.tenKho = tenKho; }
}
