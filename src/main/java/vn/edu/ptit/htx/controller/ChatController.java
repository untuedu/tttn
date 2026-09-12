package vn.edu.ptit.htx.controller;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.Normalizer;
import java.time.Duration;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
public class ChatController {
    private final String aiUrl;
    private final HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(2)).build();

    public ChatController(@Value("${app.ai.url}") String aiUrl) {
        this.aiUrl = aiUrl;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> chat(@RequestBody Map<String, String> payload) {
        String message = payload.getOrDefault("message", "");
        try {
            String body = "{\"message\":\"" + escape(message) + "\",\"history\":[],\"top_k\":4}";
            HttpRequest request = HttpRequest.newBuilder(URI.create(aiUrl))
                    .timeout(Duration.ofSeconds(4))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();
            String response = client.send(request, HttpResponse.BodyHandlers.ofString()).body();
            return Map.of("answer", extractAnswer(response));
        } catch (Exception ex) {
            return Map.of("answer", fallback(message));
        }
    }

    private String fallback(String message) {
        String text = normalize(message);
        if (containsAny(text, "dang nhap", "login", "tai khoan", "mat khau")) {
            return "Dang nhap bang email va mat khau. Tai khoan demo: admin@htx.vn / 123456 hoac dat@htx.vn / 123456. Phien ban hien tai chua co tu cap lai mat khau.";
        }
        if (containsAny(text, "dashboard", "thong ke", "doanh thu", "chi phi", "bieu do")) {
            return "Dashboard hien so co so, kho, khu vuc, tong doanh thu, tong chi phi mua sam va bieu do cot tong quan.";
        }
        if (containsAny(text, "co so nuoi trong", "them co so", "dia chi co so", "dien tich")) {
            return "Vao Co so nuoi trong de them, sua, xoa co so. Khi them moi, he thong kiem tra dia chi khong bi trung lap.";
        }
        if (containsAny(text, "khu vuc", "nguoi quan ly", "san pham khu vuc")) {
            return "Vao Khu vuc de gan khu vuc voi co so, nguoi quan ly va san pham. Phien ban hien tai chi luu duong dan hinh anh, chua upload tep.";
        }
        if (containsAny(text, "kho vat tu", "them kho", "ten kho", "vat tu", "han su dung")) {
            return "Vao Kho vat tu hoac Nhat ky mua sam de theo doi ten vat tu, xuat xu, so luong, gia, han su dung va trang thai. Ten kho trung se bi tu choi khi tao moi.";
        }
        if (containsAny(text, "nhat ky mua sam", "mua vat tu", "nhap kho", "phieu mua")) {
            return "Nhat ky mua sam ghi nhan vat tu, xuat xu, so luong, gia, ngay mua, ngay san xuat, han su dung, loai vat tu, kho va nguoi mua.";
        }
        if (containsAny(text, "nhat ky san xuat", "su dung vat tu", "bon phan", "phun thuoc", "canh tac")) {
            return "Nhat ky san xuat ghi nhan vat tu da dung, so luong, ngay su dung, kho xuat va khu vuc ap dung de phuc vu truy xuat.";
        }
        if (containsAny(text, "thu hoach", "san luong thu hoach", "ngay thu hoach")) {
            return "Hay tao nhat ky thu hoach theo khu vuc, nhap san luong, so luong da ban neu co va ngay thu hoach truoc khi tao phieu ban san pham.";
        }
        if (containsAny(text, "ban san pham", "ban hang", "gia ban", "phieu ban", "ban vuot")) {
            return "Chon dot thu hoach, nhap so luong ban, gia ban va ngay ban. He thong kiem tra so luong ban so voi so luong con lai dang luu tren dot thu hoach.";
        }
        if (containsAny(text, "ma qr", "qr code", "tao qr", "quet qr", "truy xuat")) {
            return "Moi nhat ky ban moi tu sinh QR dang HTX-BAN-ID-THUHOACH-ID. QR dung de doi chieu giao dich va dot thu hoach; trang truy xuat cong khai chua duoc trien khai.";
        }
        if (containsAny(text, "sau benh", "sau hai", "trieu chung", "vang la", "cay bi benh")) {
            return "Hay ghi nhan trieu chung, khu vuc, thoi diem phat hien va vat tu da xu ly trong nhat ky san xuat. Chatbot khong thay the can bo ky thuat hay chan doan chuyen mon.";
        }
        if (containsAny(text, "phan bon", "thuoc bao ve", "thuoc bvtv", "lieu dung")) {
            return "He thong ho tro ghi nhan vat tu da su dung, khong ke don hay dua lieu dung. Hay tuan thu nhan, quy trinh ky thuat va huong dan chuyen mon.";
        }
        if (containsAny(text, "h2", "database", "csdl", "luu du lieu", "mat du lieu")) {
            return "Phien ban demo dung H2 trong bo nho va create-drop, nen du lieu se duoc tao lai khi dung ung dung. H2 Console nam tai /h2-console khi app dang chay.";
        }
        if (containsAny(text, "phan quyen", "vai tro", "admin", "quan ly htx", "thanh vien")) {
            return "Du lieu co vai tro ADMIN, QUAN_LY_HTX va THANH_VIEN, nhung phan quyen chi tiet tren tung man hinh chua duoc ap dung.";
        }
        if (containsAny(text, "sua nhat ky", "cap nhat nhat ky", "xoa nhat ky")) {
            return "Nhat ky mua sam, san xuat, thu hoach va ban san pham hien ho tro them, xem danh sach va xoa; chuc nang sua chua duoc trien khai.";
        }
        if (containsAny(text, "chatbot", "fastapi", "llm", "rag")) {
            return "Chatbot hien dung FastAPI nhe, FAQ va tu khoa. Neu dich vu Python chua chay, backend Java se tra loi bang FAQ fallback.";
        }
        if (containsAny(text, "cong nghe", "spring boot", "thymeleaf", "java", "python")) {
            return "Ung dung dung Java 21, Spring Boot 4.1.1, Thymeleaf, Spring Data JPA, H2, Chart.js, ZXing va FastAPI tuy chon.";
        }
        return "Toi co the ho tro ve dang nhap, dashboard, co so, khu vuc, kho vat tu, nhat ky mua sam, san xuat, thu hoach, ban san pham, QR va chatbot.";
    }

    private boolean containsAny(String text, String... keywords) {
        for (String keyword : keywords) {
            if (text.contains(keyword)) {
                return true;
            }
        }
        return false;
    }

    private String normalize(String value) {
        return Normalizer.normalize(value.toLowerCase(), Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .replace("\u0111", "d");
    }

    private String escape(String value) {
        return value.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    private String extractAnswer(String json) {
        String marker = "\"answer\":\"";
        int start = json.indexOf(marker);
        if (start < 0) {
            return json;
        }
        start += marker.length();
        int end = json.indexOf('"', start);
        if (end < 0) {
            return json;
        }
        return json.substring(start, end).replace("\\n", "\n").replace("\\\"", "\"");
    }
}
