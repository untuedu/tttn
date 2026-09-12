package vn.edu.ptit.htx.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.edu.ptit.htx.model.CoSoNuoiTrong;
import vn.edu.ptit.htx.repository.CoSoNuoiTrongRepository;

@Controller
@RequestMapping("/co-so")
public class CoSoController {
    private final CoSoNuoiTrongRepository repo;

    public CoSoController(CoSoNuoiTrongRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("items", repo.findAll());
        model.addAttribute("title", "Danh sach co so nuoi trong");
        model.addAttribute("active", "co-so");
        return "co-so/list";
    }

    @GetMapping("/new")
    public String create(Model model) {
        model.addAttribute("item", new CoSoNuoiTrong());
        model.addAttribute("title", "Them moi co so nuoi trong");
        model.addAttribute("active", "co-so");
        return "co-so/form";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("item", repo.findById(id).orElseThrow());
        model.addAttribute("title", "Cap nhat co so nuoi trong");
        model.addAttribute("active", "co-so");
        return "co-so/form";
    }

    @PostMapping
    public String save(@ModelAttribute CoSoNuoiTrong item, Model model) {
        boolean duplicated = item.getMaCoSoNuoiTrong() == null && repo.existsByDiaChiIgnoreCase(item.getDiaChi());
        if (duplicated) {
            model.addAttribute("item", item);
            model.addAttribute("error", "Dia chi co so nuoi trong da ton tai");
            model.addAttribute("title", "Them moi co so nuoi trong");
            model.addAttribute("active", "co-so");
            return "co-so/form";
        }
        repo.save(item);
        return "redirect:/co-so";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Integer id) {
        repo.deleteById(id);
        return "redirect:/co-so";
    }
}
