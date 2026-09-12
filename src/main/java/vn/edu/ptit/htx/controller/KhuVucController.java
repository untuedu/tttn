package vn.edu.ptit.htx.controller;

import java.time.LocalDateTime;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.edu.ptit.htx.model.KhuVuc;
import vn.edu.ptit.htx.repository.*;

@Controller
@RequestMapping("/khu-vuc")
public class KhuVucController {
    private final KhuVucRepository repo;
    private final NguoiDungRepository nguoiDungRepo;
    private final CoSoNuoiTrongRepository coSoRepo;

    public KhuVucController(KhuVucRepository repo, NguoiDungRepository nguoiDungRepo, CoSoNuoiTrongRepository coSoRepo) {
        this.repo = repo;
        this.nguoiDungRepo = nguoiDungRepo;
        this.coSoRepo = coSoRepo;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("items", repo.findAll());
        model.addAttribute("title", "Danh sach khu vuc nuoi trong");
        model.addAttribute("active", "khu-vuc");
        return "khu-vuc/list";
    }

    @GetMapping("/new")
    public String create(Model model) {
        addFormData(model, new KhuVuc(), "Them moi khu vuc nuoi trong");
        return "khu-vuc/form";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Integer id, Model model) {
        addFormData(model, repo.findById(id).orElseThrow(), "Cap nhat khu vuc nuoi trong");
        return "khu-vuc/form";
    }

    @PostMapping
    public String save(@ModelAttribute KhuVuc item, @RequestParam Integer nguoiDungId, @RequestParam Integer coSoId) {
        if (item.getThoiGianTao() == null) {
            item.setThoiGianTao(LocalDateTime.now());
        }
        item.setNguoiDung(nguoiDungRepo.findById(nguoiDungId).orElse(null));
        item.setCoSoNuoiTrong(coSoRepo.findById(coSoId).orElse(null));
        repo.save(item);
        return "redirect:/khu-vuc";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Integer id) {
        repo.deleteById(id);
        return "redirect:/khu-vuc";
    }

    private void addFormData(Model model, KhuVuc item, String title) {
        model.addAttribute("item", item);
        model.addAttribute("nguoiDungs", nguoiDungRepo.findAll());
        model.addAttribute("coSos", coSoRepo.findAll());
        model.addAttribute("title", title);
        model.addAttribute("active", "khu-vuc");
    }
}
