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
            return "Đăng nhập bằng email và mật khẩu. Tài khoản demo: admin@htx.vn / 123456 hoặc dat@htx.vn / 123456. Phiên bản hiện tại chưa có chức năng tự cấp lại mật khẩu.";
        }
        if (containsAny(text, "dashboard", "thong ke", "doanh thu", "chi phi", "bieu do")) {
            return "Dashboard hiển thị số cơ sở, kho, khu vực, tổng doanh thu, tổng chi phí mua sắm và biểu đồ cột tổng quan.";
        }
        if (containsAny(text, "co so nuoi trong", "them co so", "dia chi co so", "dien tich")) {
            return "Vào Cơ sở nuôi trồng để thêm, sửa, xóa cơ sở. Khi thêm mới, hệ thống kiểm tra địa chỉ không bị trùng lặp.";
        }
        if (containsAny(text, "khu vuc", "nguoi quan ly", "san pham khu vuc")) {
            return "Vào Khu vực để gán khu vực với cơ sở, người quản lý và sản phẩm. Phiên bản hiện tại chỉ lưu đường dẫn hình ảnh, chưa upload tệp.";
        }
        if (containsAny(text, "kho vat tu", "them kho", "ten kho", "vat tu", "han su dung")) {
            return "Vào Kho vật tư hoặc Nhật ký mua sắm để theo dõi tên vật tư, xuất xứ, số lượng, giá, hạn sử dụng và trạng thái. Tên kho trùng sẽ bị từ chối khi tạo mới.";
        }
        if (containsAny(text, "nhat ky mua sam", "mua vat tu", "nhap kho", "phieu mua")) {
            return "Nhật ký mua sắm ghi nhận vật tư, xuất xứ, số lượng, giá, ngày mua, ngày sản xuất, hạn sử dụng, loại vật tư, kho và người mua.";
        }
        if (containsAny(text, "nhat ky san xuat", "su dung vat tu", "bon phan", "phun thuoc", "canh tac")) {
            return "Nhật ký sản xuất ghi nhận vật tư đã dùng, số lượng, ngày sử dụng, kho xuất và khu vực áp dụng để phục vụ truy xuất.";
        }
        if (containsAny(text, "thu hoach", "san luong thu hoach", "ngay thu hoach")) {
            return "Hãy tạo nhật ký thu hoạch theo khu vực, nhập sản lượng, số lượng đã bán nếu có và ngày thu hoạch trước khi tạo phiếu bán sản phẩm.";
        }
        if (containsAny(text, "ban san pham", "ban hang", "gia ban", "phieu ban", "ban vuot")) {
            return "Chọn đợt thu hoạch, nhập số lượng bán, giá bán và ngày bán. Hệ thống kiểm tra số lượng bán so với số lượng còn lại đang lưu trên đợt thu hoạch.";
        }
        if (containsAny(text, "ma qr", "qr code", "tao qr", "quet qr", "truy xuat")) {
            return "Mỗi nhật ký bán mới tự sinh QR dạng HTX-BAN-ID-THUHOACH-ID. QR dùng để đối chiếu giao dịch và đợt thu hoạch; trang truy xuất công khai chưa được triển khai.";
        }
        if (containsAny(text, "sau benh", "sau hai", "trieu chung", "vang la", "cay bi benh")) {
            return "Hãy ghi nhận triệu chứng, khu vực, thời điểm phát hiện và vật tư đã xử lý trong nhật ký sản xuất. Chatbot không thay thế cán bộ kỹ thuật hay chẩn đoán chuyên môn.";
        }
        if (containsAny(text, "phan bon", "thuoc bao ve", "thuoc bvtv", "lieu dung")) {
            return "Hệ thống hỗ trợ ghi nhận vật tư đã sử dụng, không kê đơn hay đưa liều dùng. Hãy tuân thủ nhãn, quy trình kỹ thuật và hướng dẫn chuyên môn.";
        }
        if (containsAny(text, "h2", "database", "csdl", "luu du lieu", "mat du lieu")) {
            return "Phiên bản demo dùng H2 trong bộ nhớ và create-drop, nên dữ liệu sẽ được tạo lại khi dừng ứng dụng. H2 Console nằm tại /h2-console khi ứng dụng đang chạy.";
        }
        if (containsAny(text, "phan quyen", "vai tro", "admin", "quan ly htx", "thanh vien")) {
            return "Dữ liệu có vai trò ADMIN, QUAN_LY_HTX và THANH_VIEN, nhưng phân quyền chi tiết trên từng màn hình chưa được áp dụng.";
        }
        if (containsAny(text, "sua nhat ky", "cap nhat nhat ky", "xoa nhat ky")) {
            return "Nhật ký mua sắm, sản xuất, thu hoạch và bán sản phẩm hiện hỗ trợ thêm, xem danh sách và xóa; chức năng sửa chưa được triển khai.";
        }
        if (containsAny(text, "chatbot", "fastapi", "llm", "rag")) {
            return "Chatbot hiện dùng FastAPI nhẹ, FAQ và từ khóa. Nếu dịch vụ Python chưa chạy, backend Java sẽ trả lời bằng FAQ dự phòng.";
        }
        if (containsAny(text, "cong nghe", "spring boot", "thymeleaf", "java", "python")) {
            return "Ứng dụng dùng Java 21, Spring Boot 4.1.1, Thymeleaf, Spring Data JPA, H2, Chart.js, ZXing và FastAPI tùy chọn.";
        }
        return "Tôi có thể hỗ trợ về đăng nhập, dashboard, cơ sở, khu vực, kho vật tư, nhật ký mua sắm, sản xuất, thu hoạch, bán sản phẩm, QR và chatbot.";
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
