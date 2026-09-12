package vn.edu.ptit.htx.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;

@Entity
public class NguoiDung {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maNguoiDung;

    @NotBlank
    private String tenNguoiDung = "";

    @Email
    @NotBlank
    private String email = "";

    private String sdt = "";
    private LocalDate ngaySinh;
    private String matKhau = "";
    private boolean hoatDong = true;

    @Enumerated(EnumType.STRING)
    private Role role = Role.THANH_VIEN;

    @ManyToOne
    private CoSoNuoiTrong coSoNuoiTrong;

    public Integer getMaNguoiDung() { return maNguoiDung; }
    public void setMaNguoiDung(Integer maNguoiDung) { this.maNguoiDung = maNguoiDung; }
    public String getTenNguoiDung() { return tenNguoiDung; }
    public void setTenNguoiDung(String tenNguoiDung) { this.tenNguoiDung = tenNguoiDung; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getSdt() { return sdt; }
    public void setSdt(String sdt) { this.sdt = sdt; }
    public LocalDate getNgaySinh() { return ngaySinh; }
    public void setNgaySinh(LocalDate ngaySinh) { this.ngaySinh = ngaySinh; }
    public String getMatKhau() { return matKhau; }
    public void setMatKhau(String matKhau) { this.matKhau = matKhau; }
    public boolean isHoatDong() { return hoatDong; }
    public void setHoatDong(boolean hoatDong) { this.hoatDong = hoatDong; }
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
    public CoSoNuoiTrong getCoSoNuoiTrong() { return coSoNuoiTrong; }
    public void setCoSoNuoiTrong(CoSoNuoiTrong coSoNuoiTrong) { this.coSoNuoiTrong = coSoNuoiTrong; }
}
