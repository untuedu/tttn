package vn.edu.ptit.htx.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vn.edu.ptit.htx.repository.NguoiDungRepository;

@Controller
public class AuthController {
    private final NguoiDungRepository nguoiDungRepository;

    public AuthController(NguoiDungRepository nguoiDungRepository) {
        this.nguoiDungRepository = nguoiDungRepository;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam String email, @RequestParam String matKhau, HttpSession session, Model model) {
        return nguoiDungRepository.findByEmailIgnoreCaseAndMatKhauAndHoatDongTrue(email, matKhau)
                .map(user -> {
                    session.setAttribute("user", user);
                    return "redirect:/";
                })
                .orElseGet(() -> {
                    model.addAttribute("error", "Email hoac mat khau khong dung");
                    return "login";
                });
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
