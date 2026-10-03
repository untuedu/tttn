package vn.edu.ptit.htx.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.edu.ptit.htx.model.*;
import vn.edu.ptit.htx.repository.*;

@Controller
public class NhatKyController {
    private final NhatKyMuaSamRepository muaSamRepo;
    private final NhatKySanXuatRepository sanXuatRepo;
    private final NhatKyThuHoachRepository thuHoachRepo;
    private final NhatKyBanSanPhamRepository banRepo;
    private final LoaiVatTuRepository loaiRepo;
    private final KhoVatTuRepository khoRepo;
    private final NguoiDungRepository nguoiDungRepo;
    private final KhuVucRepository khuVucRepo;

    public NhatKyController(NhatKyMuaSamRepository muaSamRepo, NhatKySanXuatRepository sanXuatRepo,
                            NhatKyThuHoachRepository thuHoachRepo, NhatKyBanSanPhamRepository banRepo,
                            LoaiVatTuRepository loaiRepo, KhoVatTuRepository khoRepo,
                            NguoiDungRepository nguoiDungRepo, KhuVucRepository khuVucRepo) {
        this.muaSamRepo = muaSamRepo;
        this.sanXuatRepo = sanXuatRepo;
        this.thuHoachRepo = thuHoachRepo;
        this.banRepo = banRepo;
        this.loaiRepo = loaiRepo;
        this.khoRepo = khoRepo;
        this.nguoiDungRepo = nguoiDungRepo;
        this.khuVucRepo = khuVucRepo;
    }

    @GetMapping("/mua-sam")
    public String muaSam(Model model) {
        model.addAttribute("items", muaSamRepo.findAll());
        model.addAttribute("title", "Danh sách nhật ký mua sắm");
        model.addAttribute("active", "mua-sam");
        return "mua-sam/list";
    }

    @GetMapping("/mua-sam/new")
    public String muaSamCreate(Model model) {
        formRefs(model, "Thêm mới nhật ký mua sắm", "mua-sam");
        model.addAttribute("item", new NhatKyMuaSam());
        return "mua-sam/form";
    }

    @PostMapping("/mua-sam")
    public String muaSamSave(@ModelAttribute NhatKyMuaSam item, @RequestParam Integer loaiId,
                             @RequestParam Integer khoId, @RequestParam Integer nguoiDungId) {
        item.setLoaiVatTu(loaiRepo.findById(loaiId).orElse(null));
        item.setKhoVatTu(khoRepo.findById(khoId).orElse(null));
        item.setNguoiDung(nguoiDungRepo.findById(nguoiDungId).orElse(null));
        muaSamRepo.save(item);
        return "redirect:/mua-sam";
    }

    @PostMapping("/mua-sam/{id}/delete")
    public String muaSamDelete(@PathVariable Integer id) {
        muaSamRepo.deleteById(id);
        return "redirect:/mua-sam";
    }

    @GetMapping("/san-xuat")
    public String sanXuat(Model model) {
        model.addAttribute("items", sanXuatRepo.findAll());
        model.addAttribute("title", "Danh sách nhật ký sản xuất");
        model.addAttribute("active", "san-xuat");
        return "san-xuat/list";
    }

    @GetMapping("/san-xuat/new")
    public String sanXuatCreate(Model model) {
        formRefs(model, "Thêm mới nhật ký sản xuất", "san-xuat");
        model.addAttribute("item", new NhatKySanXuat());
        return "san-xuat/form";
    }

    @PostMapping("/san-xuat")
    public String sanXuatSave(@ModelAttribute NhatKySanXuat item, @RequestParam Integer khoId, @RequestParam Integer khuVucId) {
        item.setKhoVatTu(khoRepo.findById(khoId).orElse(null));
        item.setKhuVuc(khuVucRepo.findById(khuVucId).orElse(null));
        sanXuatRepo.save(item);
        return "redirect:/san-xuat";
    }

    @PostMapping("/san-xuat/{id}/delete")
    public String sanXuatDelete(@PathVariable Integer id) {
        sanXuatRepo.deleteById(id);
        return "redirect:/san-xuat";
    }

    @GetMapping("/thu-hoach")
    public String thuHoach(Model model) {
        model.addAttribute("items", thuHoachRepo.findAll());
        model.addAttribute("title", "Danh sách nhật ký thu hoạch");
        model.addAttribute("active", "thu-hoach");
        return "thu-hoach/list";
    }

    @GetMapping("/thu-hoach/new")
    public String thuHoachCreate(Model model) {
        formRefs(model, "Thêm mới nhật ký thu hoạch", "thu-hoach");
        model.addAttribute("item", new NhatKyThuHoach());
        return "thu-hoach/form";
    }

    @PostMapping("/thu-hoach")
    public String thuHoachSave(@ModelAttribute NhatKyThuHoach item, @RequestParam Integer khuVucId) {
        item.setKhuVuc(khuVucRepo.findById(khuVucId).orElse(null));
        thuHoachRepo.save(item);
        return "redirect:/thu-hoach";
    }

    @PostMapping("/thu-hoach/{id}/delete")
    public String thuHoachDelete(@PathVariable Integer id) {
        thuHoachRepo.deleteById(id);
        return "redirect:/thu-hoach";
    }

    @GetMapping("/ban-san-pham")
    public String ban(Model model) {
        model.addAttribute("items", banRepo.findAll());
        model.addAttribute("title", "Danh sách nhật ký bán sản phẩm");
        model.addAttribute("active", "ban");
        return "ban/list";
    }

    @GetMapping("/ban-san-pham/new")
    public String banCreate(Model model) {
        formRefs(model, "Thêm mới nhật ký bán sản phẩm", "ban");
        model.addAttribute("item", new NhatKyBanSanPham());
        return "ban/form";
    }

    @PostMapping("/ban-san-pham")
    public String banSave(@ModelAttribute NhatKyBanSanPham item, @RequestParam Integer thuHoachId, Model model) {
        NhatKyThuHoach thuHoach = thuHoachRepo.findById(thuHoachId).orElse(null);
        if (thuHoach != null && item.getSoLuong() > (thuHoach.getSoLuongThuHoach() - thuHoach.getSoLuongDaBan())) {
            formRefs(model, "Thêm mới nhật ký bán sản phẩm", "ban");
            model.addAttribute("item", item);
            model.addAttribute("error", "Thêm mới thất bại, do số lượng bán lớn hơn số lượng trong kho");
            return "ban/form";
        }
        item.setNhatKyThuHoach(thuHoach);
        NhatKyBanSanPham saved = banRepo.save(item);
        saved.setQrCode("HTX-BAN-" + saved.getMaNhatKyBanSanPham() + "-THUHOACH-" + thuHoachId);
        banRepo.save(saved);
        return "redirect:/ban-san-pham";
    }

    @PostMapping("/ban-san-pham/{id}/delete")
    public String banDelete(@PathVariable Integer id) {
        banRepo.deleteById(id);
        return "redirect:/ban-san-pham";
    }

    private void formRefs(Model model, String title, String active) {
        model.addAttribute("loais", loaiRepo.findAll());
        model.addAttribute("khos", khoRepo.findAll());
        model.addAttribute("nguoiDungs", nguoiDungRepo.findAll());
        model.addAttribute("khuVucs", khuVucRepo.findAll());
        model.addAttribute("thuHoachs", thuHoachRepo.findAll());
        model.addAttribute("title", title);
        model.addAttribute("active", active);
    }
}
