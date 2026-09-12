package vn.edu.ptit.htx.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Entity
public class CoSoNuoiTrong {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maCoSoNuoiTrong;

    @Min(1)
    private double dienTich;

    @Min(0)
    private double dienTichDaSuDung;

    @NotBlank(message = "Khong duoc bo trong dia chi")
    private String diaChi = "";

    public Integer getMaCoSoNuoiTrong() { return maCoSoNuoiTrong; }
    public void setMaCoSoNuoiTrong(Integer maCoSoNuoiTrong) { this.maCoSoNuoiTrong = maCoSoNuoiTrong; }
    public double getDienTich() { return dienTich; }
    public void setDienTich(double dienTich) { this.dienTich = dienTich; }
    public double getDienTichDaSuDung() { return dienTichDaSuDung; }
    public void setDienTichDaSuDung(double dienTichDaSuDung) { this.dienTichDaSuDung = dienTichDaSuDung; }
    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }
}
