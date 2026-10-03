package vn.edu.ptit.htx.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.edu.ptit.htx.model.KhoVatTu;
import vn.edu.ptit.htx.repository.KhoVatTuRepository;

@Controller
@RequestMapping("/kho-vat-tu")
public class KhoVatTuController {
    private final KhoVatTuRepository repo;

    public KhoVatTuController(KhoVatTuRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("items", repo.findAll());
        model.addAttribute("title", "Danh sách kho vật tư");
        model.addAttribute("active", "kho");
        return "kho/list";
    }

    @GetMapping("/new")
    public String create(Model model) {
        model.addAttribute("item", new KhoVatTu());
        model.addAttribute("title", "Thêm mới kho vật tư");
        model.addAttribute("active", "kho");
        return "kho/form";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("item", repo.findById(id).orElseThrow());
        model.addAttribute("title", "Cập nhật kho vật tư");
        model.addAttribute("active", "kho");
        return "kho/form";
    }

    @PostMapping
    public String save(@ModelAttribute KhoVatTu item, Model model) {
        if (item.getMaKhoVatTu() == null && repo.existsByTenKhoIgnoreCase(item.getTenKho())) {
            model.addAttribute("item", item);
            model.addAttribute("error", "Kho vật tư đã tồn tại");
            model.addAttribute("title", "Thêm mới kho vật tư");
            model.addAttribute("active", "kho");
            return "kho/form";
        }
        repo.save(item);
        return "redirect:/kho-vat-tu";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Integer id) {
        repo.deleteById(id);
        return "redirect:/kho-vat-tu";
    }
}
