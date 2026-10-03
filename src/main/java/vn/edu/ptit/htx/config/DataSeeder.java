package vn.edu.ptit.htx.config;

import java.time.LocalDate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import vn.edu.ptit.htx.model.*;
import vn.edu.ptit.htx.repository.*;

@Component
public class DataSeeder implements CommandLineRunner {
    private final CoSoNuoiTrongRepository coSoRepo;
    private final KhoVatTuRepository khoRepo;
    private final LoaiVatTuRepository loaiRepo;
    private final NguoiDungRepository nguoiDungRepo;
    private final KhuVucRepository khuVucRepo;
    private final NhatKyMuaSamRepository muaSamRepo;
    private final NhatKySanXuatRepository sanXuatRepo;
    private final NhatKyThuHoachRepository thuHoachRepo;
    private final NhatKyBanSanPhamRepository banRepo;

    public DataSeeder(CoSoNuoiTrongRepository coSoRepo, KhoVatTuRepository khoRepo, LoaiVatTuRepository loaiRepo,
                      NguoiDungRepository nguoiDungRepo, KhuVucRepository khuVucRepo,
                      NhatKyMuaSamRepository muaSamRepo, NhatKySanXuatRepository sanXuatRepo,
                      NhatKyThuHoachRepository thuHoachRepo, NhatKyBanSanPhamRepository banRepo) {
        this.coSoRepo = coSoRepo;
        this.khoRepo = khoRepo;
        this.loaiRepo = loaiRepo;
        this.nguoiDungRepo = nguoiDungRepo;
        this.khuVucRepo = khuVucRepo;
        this.muaSamRepo = muaSamRepo;
        this.sanXuatRepo = sanXuatRepo;
        this.thuHoachRepo = thuHoachRepo;
        this.banRepo = banRepo;
    }

    @Override
    public void run(String... args) {
        if (coSoRepo.count() > 0) {
            return;
        }

        CoSoNuoiTrong cs1 = coSo(1800, 900, "Đội 1, xã Vật Lại, Hà Nội");
        CoSoNuoiTrong cs2 = coSo(2600, 1200, "Đội 2, xã Vật Lại, Hà Nội");
        CoSoNuoiTrong cs3 = coSo(3000, 1700, "Đội 3, xã Vật Lại, Hà Nội");
        coSoRepo.save(cs1);
        coSoRepo.save(cs2);
        coSoRepo.save(cs3);

        KhoVatTu khoChinh = khoRepo.save(kho("Kho vật tư chính", "Phân bón, thuốc BVTV"));
        KhoVatTu khoKho = khoRepo.save(kho("Kho khô", "Vật tư không tiêu hao"));

        LoaiVatTu tieuHao = loaiRepo.save(loai("Vật phẩm tiêu hao"));
        LoaiVatTu khongTieuHao = loaiRepo.save(loai("Vật tư không tiêu hao"));

        NguoiDung admin = nguoiDung("Quản trị HTX", "admin@htx.vn", "123456", Role.ADMIN, cs1);
        NguoiDung quanLy = nguoiDung("Nguyễn Tuấn Đạt", "dat@htx.vn", "123456", Role.QUAN_LY_HTX, cs2);
        nguoiDungRepo.save(admin);
        nguoiDungRepo.save(quanLy);

        KhuVuc kv1 = khuVuc("Ao số 1", 600, "Cá rô phi", admin, cs1);
        KhuVuc kv2 = khuVuc("Lô rau A", 450, "Rau cải", quanLy, cs2);
        khuVucRepo.save(kv1);
        khuVucRepo.save(kv2);

        NhatKyMuaSam ms = new NhatKyMuaSam();
        ms.setTenVatTu("Phân hữu cơ");
        ms.setXuatXu("Việt Nam");
        ms.setSoLuong(120);
        ms.setGia(85000);
        ms.setNgayMua(LocalDate.now().minusDays(12));
        ms.setLoaiVatTu(tieuHao);
        ms.setKhoVatTu(khoChinh);
        ms.setNguoiDung(admin);
        muaSamRepo.save(ms);

        NhatKySanXuat sx = new NhatKySanXuat();
        sx.setTenVatTu("Phân hữu cơ");
        sx.setSoLuongSuDung(20);
        sx.setNgaySuDung(LocalDate.now().minusDays(5));
        sx.setKhoVatTu(khoChinh);
        sx.setKhuVuc(kv2);
        sanXuatRepo.save(sx);

        NhatKyThuHoach th = new NhatKyThuHoach();
        th.setKhuVuc(kv2);
        th.setSoLuongThuHoach(700);
        th.setSoLuongDaBan(320);
        th.setNgayThuHoach(LocalDate.now().minusDays(2));
        thuHoachRepo.save(th);

        NhatKyBanSanPham ban = new NhatKyBanSanPham();
        ban.setNhatKyThuHoach(th);
        ban.setSoLuong(320);
        ban.setGiaBan(18000);
        ban.setNgayBan(LocalDate.now().minusDays(1));
        ban.setQrCode("HTX-" + th.getMaNhatKyThuHoach() + "-RAUCAI");
        banRepo.save(ban);
    }

    private CoSoNuoiTrong coSo(double dienTich, double daSuDung, String diaChi) {
        CoSoNuoiTrong item = new CoSoNuoiTrong();
        item.setDienTich(dienTich);
        item.setDienTichDaSuDung(daSuDung);
        item.setDiaChi(diaChi);
        return item;
    }

    private KhoVatTu kho(String ten, String ghiChu) {
        KhoVatTu item = new KhoVatTu();
        item.setTenKho(ten);
        item.setGhiChu(ghiChu);
        return item;
    }

    private LoaiVatTu loai(String ten) {
        LoaiVatTu item = new LoaiVatTu();
        item.setTenLoai(ten);
        return item;
    }

    private NguoiDung nguoiDung(String ten, String email, String matKhau, Role role, CoSoNuoiTrong coSo) {
        NguoiDung user = new NguoiDung();
        user.setTenNguoiDung(ten);
        user.setEmail(email);
        user.setMatKhau(matKhau);
        user.setSdt("086614314");
        user.setNgaySinh(LocalDate.of(1995, 3, 26));
        user.setRole(role);
        user.setCoSoNuoiTrong(coSo);
        return user;
    }

    private KhuVuc khuVuc(String ten, double dienTich, String sanPham, NguoiDung nguoiDung, CoSoNuoiTrong coSo) {
        KhuVuc item = new KhuVuc();
        item.setTenKhuVuc(ten);
        item.setDienTich(dienTich);
        item.setSanPham(sanPham);
        item.setNguoiDung(nguoiDung);
        item.setCoSoNuoiTrong(coSo);
        return item;
    }
}
