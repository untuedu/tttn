package vn.edu.ptit.htx.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import vn.edu.ptit.htx.repository.*;

@Controller
public class DashboardController {
    private final CoSoNuoiTrongRepository coSoRepo;
    private final KhoVatTuRepository khoRepo;
    private final KhuVucRepository khuVucRepo;
    private final NhatKyMuaSamRepository muaSamRepo;
    private final NhatKyBanSanPhamRepository banRepo;

    public DashboardController(CoSoNuoiTrongRepository coSoRepo, KhoVatTuRepository khoRepo, KhuVucRepository khuVucRepo,
                               NhatKyMuaSamRepository muaSamRepo, NhatKyBanSanPhamRepository banRepo) {
        this.coSoRepo = coSoRepo;
        this.khoRepo = khoRepo;
        this.khuVucRepo = khuVucRepo;
        this.muaSamRepo = muaSamRepo;
        this.banRepo = banRepo;
    }

    @GetMapping("/")
    public String index(Model model) {
        double doanhThu = banRepo.findAll().stream().mapToDouble(i -> i.getSoLuong() * i.getGiaBan()).sum();
        double chiPhi = muaSamRepo.findAll().stream().mapToDouble(i -> i.getSoLuong() * i.getGia()).sum();
        model.addAttribute("coSoCount", coSoRepo.count());
        model.addAttribute("khoCount", khoRepo.count());
        model.addAttribute("khuVucCount", khuVucRepo.count());
        model.addAttribute("doanhThu", doanhThu);
        model.addAttribute("chiPhi", chiPhi);
        model.addAttribute("title", "Trang thong ke");
        model.addAttribute("active", "dashboard");
        return "dashboard";
    }
}
