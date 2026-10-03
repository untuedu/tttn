import re
import unicodedata


FAQS = [
    {
        "question": "Chatbot co the ho tro nhung gi?",
        "keywords": ("tro giup", "ho tro", "lam duoc gi", "chuc nang"),
        "answer": "Tôi có thể hướng dẫn sử dụng các mục cơ sở nuôi trồng, khu vực, kho vật tư, nhật ký, thu hoạch, bán sản phẩm, QR và dashboard.",
        "topic": "Ho tro chung",
    },
    {
        "question": "Lam sao de dang nhap?",
        "keywords": ("dang nhap", "login", "tai khoan mau"),
        "answer": "Mở trang đăng nhập, nhập email và mật khẩu. Tài khoản demo là admin@htx.vn / 123456 hoặc dat@htx.vn / 123456.",
        "topic": "Dang nhap",
    },
    {
        "question": "Quen mat khau thi lam sao?",
        "keywords": ("quen mat khau", "doi mat khau", "cap lai mat khau"),
        "answer": "Phiên bản hiện tại chưa có chức năng tự cấp lại mật khẩu. Hãy liên hệ quản trị viên để được hỗ trợ cập nhật tài khoản.",
        "topic": "Dang nhap",
    },
    {
        "question": "Dashboard hien thi gi?",
        "keywords": ("dashboard", "thong ke", "doanh thu", "chi phi", "bieu do"),
        "answer": "Dashboard hiển thị số cơ sở, kho, khu vực, tổng doanh thu bán sản phẩm, tổng chi phí mua sắm và biểu đồ cột tổng quan.",
        "topic": "Dashboard",
    },
    {
        "question": "Them co so nuoi trong nhu the nao?",
        "keywords": ("them co so", "co so nuoi trong", "dia chi co so"),
        "answer": "Vào Cơ sở nuôi trồng, chọn Thêm mới, nhập diện tích, diện tích đã sử dụng và địa chỉ, sau đó nhấn Lưu. Địa chỉ trùng sẽ bị từ chối khi tạo mới.",
        "topic": "Co so nuoi trong",
    },
    {
        "question": "Dien tich da su dung la gi?",
        "keywords": ("dien tich da su dung", "dien tich con lai", "dien tich"),
        "answer": "Diện tích đã sử dụng là phần diện tích của cơ sở đang được khai thác. Hãy nhập giá trị không âm và không lớn hơn diện tích tổng trong quy trình vận hành.",
        "topic": "Co so nuoi trong",
    },
    {
        "question": "Quan ly khu vuc san xuat o dau?",
        "keywords": ("khu vuc", "them khu vuc", "nguoi quan ly khu vuc", "san pham khu vuc"),
        "answer": "Vào Khu vực để thêm, sửa hoặc xóa khu vực. Mỗi khu vực cần gắn với một cơ sở và một người quản lý; có thể khai báo sản phẩm và đường dẫn hình ảnh.",
        "topic": "Khu vuc",
    },
    {
        "question": "Them kho vat tu nhu the nao?",
        "keywords": ("them kho", "kho vat tu", "ten kho", "ghi chu kho"),
        "answer": "Vào Kho vật tư, chọn Thêm mới, nhập tên kho và ghi chú. Hệ thống không cho tạo mới hai kho trùng tên.",
        "topic": "Kho vat tu",
    },
    {
        "question": "Nhat ky mua sam dung de lam gi?",
        "keywords": ("nhat ky mua sam", "mua vat tu", "nhap kho", "phieu mua"),
        "answer": "Nhật ký mua sắm ghi nhận tên vật tư, xuất xứ, số lượng, giá, ngày mua, ngày sản xuất, hạn sử dụng, trạng thái, loại vật tư, kho lưu và người mua.",
        "topic": "Nhat ky mua sam",
    },
    {
        "question": "Can theo doi han su dung vat tu nhu the nao?",
        "keywords": ("han su dung", "het han", "ngay san xuat", "bao quan vat tu"),
        "answer": "Khi tạo nhật ký mua sắm, hãy nhập ngày sản xuất và hạn sử dụng. Phiên bản hiện tại lưu để tra cứu, chưa có cảnh báo tự động vật tư sắp hết hạn.",
        "topic": "Kho vat tu",
    },
    {
        "question": "Nhat ky san xuat ghi nhan thong tin gi?",
        "keywords": ("nhat ky san xuat", "su dung vat tu", "bon phan", "phun thuoc", "canh tac"),
        "answer": "Nhật ký sản xuất ghi tên vật tư, số lượng sử dụng, ngày sử dụng, kho xuất vật tư và khu vực áp dụng. Đây là dữ liệu nền cho truy xuất nguồn gốc.",
        "topic": "Nhat ky san xuat",
    },
    {
        "question": "Khi nao tao nhat ky thu hoach?",
        "keywords": ("nhat ky thu hoach", "tao thu hoach", "san luong thu hoach", "ngay thu hoach"),
        "answer": "Sau khi có sản lượng thực tế, vào Nhật ký thu hoạch, chọn khu vực và nhập số lượng thu hoạch, số lượng đã bán nếu có, cùng ngày thu hoạch.",
        "topic": "Thu hoach",
    },
    {
        "question": "Lam sao ban san pham?",
        "keywords": ("ban san pham", "ban hang", "tao don ban", "gia ban"),
        "answer": "Vào Bán sản phẩm, chọn đợt thu hoạch, nhập số lượng bán, giá bán và ngày bán. Hệ thống kiểm tra số lượng bán so với số lượng còn lại đang lưu trên đợt thu hoạch.",
        "topic": "Ban san pham",
    },
    {
        "question": "Tai sao khong luu duoc phieu ban?",
        "keywords": ("khong luu duoc phieu ban", "ban vuot", "so luong ban", "ban qua so luong"),
        "answer": "Số lượng bán phải không lớn hơn số lượng thu hoạch trừ số lượng đã bán của đợt được chọn. Hãy kiểm tra lại đợt thu hoạch và số lượng nhập vào.",
        "topic": "Ban san pham",
    },
    {
        "question": "Doanh thu va chi phi duoc tinh nhu the nao?",
        "keywords": ("tinh doanh thu", "tong doanh thu", "tinh chi phi", "tong chi phi"),
        "answer": "Dashboard tính doanh thu bằng tổng số lượng nhân giá bán của các phiếu bán; chi phí mua sắm là tổng số lượng nhân giá của các phiếu mua.",
        "topic": "Dashboard",
    },
    {
        "question": "Ma QR duoc tao o dau?",
        "keywords": ("ma qr", "qr code", "tao qr", "qr ban san pham"),
        "answer": "Mỗi nhật ký bán sản phẩm mới sẽ tự sinh chuỗi mã dạng HTX-BAN-ID-THUHOACH-ID. Danh sách bán sản phẩm hiển thị ảnh QR để quét và chuỗi mã để đối chiếu.",
        "topic": "QR truy xuat",
    },
    {
        "question": "Quet QR de lam gi?",
        "keywords": ("quet qr", "truy xuat", "ma truy xuat", "anh qr"),
        "answer": "QR hiện tại mã hóa mã định danh của giao dịch bán và đợt thu hoạch liên quan. Nó dùng để đối chiếu trong hệ thống; trang truy xuất công khai là hướng phát triển tiếp theo.",
        "topic": "QR truy xuat",
    },
    {
        "question": "Khi phat hien sau benh can lam gi?",
        "keywords": ("sau benh", "sau hai", "trieu chung", "cay bi benh", "vang la"),
        "answer": "Hãy ghi nhận triệu chứng, khu vực, thời điểm phát hiện và vật tư đã xử lý trong nhật ký sản xuất. Chatbot chỉ hỗ trợ nhắc quy trình, không thay thế cán bộ kỹ thuật hay chẩn đoán chuyên môn.",
        "topic": "Sau benh",
    },
    {
        "question": "Co the hoi cach dung phan bon hay thuoc khong?",
        "keywords": ("phan bon", "thuoc bao ve", "thuoc bvtv", "lieu dung", "phun thuoc"),
        "answer": "Hệ thống có thể ghi nhận vật tư đã dùng, nhưng không tự động kê đơn hay đưa liều dùng. Hãy tuân thủ nhãn, quy trình kỹ thuật và hướng dẫn của cán bộ chuyên môn.",
        "topic": "An toan canh tac",
    },
    {
        "question": "Du lieu co duoc luu lau dai khong?",
        "keywords": ("luu du lieu", "mat du lieu", "h2", "khoi dong lai", "database"),
        "answer": "Phiên bản demo dùng H2 trong bộ nhớ và schema create-drop, do đó dữ liệu sẽ bị tạo lại khi dừng ứng dụng. Cần chuyển sang CSDL bên ngoài để lưu lâu dài.",
        "topic": "Du lieu va H2",
    },
    {
        "question": "H2 Console o dau?",
        "keywords": ("h2 console", "console database", "xem database", "csdl"),
        "answer": "Khi Spring Boot đang chạy, H2 Console có địa chỉ http://localhost:8080/h2-console. Đây là công cụ phát triển, không nên mở công khai trên môi trường thật.",
        "topic": "Du lieu va H2",
    },
    {
        "question": "Co the sua nhat ky da tao khong?",
        "keywords": ("sua nhat ky", "cap nhat nhat ky", "xoa nhat ky", "chinh sua phieu"),
        "answer": "Hiện tại các nhật ký mua sắm, sản xuất, thu hoạch và bán sản phẩm hỗ trợ thêm, xem danh sách và xóa. Chức năng sửa nhật ký chưa được triển khai.",
        "topic": "Gioi han he thong",
    },
    {
        "question": "Co phan quyen theo vai tro khong?",
        "keywords": ("phan quyen", "vai tro", "admin", "quan ly htx", "thanh vien"),
        "answer": "Hệ thống có các vai trò ADMIN, QUAN_LY_HTX và THANH_VIEN trong dữ liệu mẫu. Phân quyền chi tiết trên từng màn hình chưa được áp dụng ở phiên bản hiện tại.",
        "topic": "Tai khoan va quyen",
    },
    {
        "question": "Chatbot co dung AI lon khong?",
        "keywords": ("ai", "llm", "chatbot", "fastapi", "rag"),
        "answer": "Chatbot hiện là dịch vụ FastAPI nhẹ, trả lời theo bộ FAQ và từ khóa. Kiến trúc đã tách riêng để có thể thay bằng RAG hoặc LLM trong tương lai.",
        "topic": "Chatbot",
    },
    {
        "question": "Chatbot khong tra loi thi lam sao?",
        "keywords": ("chatbot khong tra loi", "loi chat", "fastapi chua chay", "khong ket noi ai"),
        "answer": "Kiểm tra FastAPI đang chạy tại cổng 8001 và app.ai.url có đúng không. Nếu dịch vụ Python chưa sẵn sàng, backend Java vẫn trả lời bằng FAQ dự phòng.",
        "topic": "Chatbot",
    },
    {
        "question": "Co the tai anh san pham len khong?",
        "keywords": ("tai anh", "upload anh", "hinh anh san pham", "anh san pham"),
        "answer": "Form khu vực hiện chỉ lưu đường dẫn hình ảnh sản phẩm dạng văn bản. Chức năng upload tệp và quản lý ảnh chưa được triển khai.",
        "topic": "Gioi han he thong",
    },
    {
        "question": "He thong dung cong nghe gi?",
        "keywords": ("cong nghe", "spring boot", "thymeleaf", "java", "python"),
        "answer": "Ứng dụng dùng Java 21, Spring Boot 4.1.1, Thymeleaf, Spring Data JPA, H2 Database, Chart.js, ZXing QR Code và FastAPI tùy chọn cho chatbot.",
        "topic": "Cong nghe",
    },
]

DEFAULT_ANSWER = (
    "Tôi chưa tìm thấy câu trả lời phù hợp. Bạn có thể hỏi về đăng nhập, cơ sở, khu vực, kho vật tư, "
    "nhật ký mua sắm, sản xuất, thu hoạch, bán sản phẩm, QR, dashboard hoặc chatbot."
)


def normalize(value: str) -> str:
    value = unicodedata.normalize("NFD", value.lower())
    value = "".join(char for char in value if unicodedata.category(char) != "Mn")
    return value.replace("đ", "d")


def find_answer(message: str) -> tuple[str, str]:
    text = normalize(message)
    tokens = set(re.findall(r"[a-z0-9]+", text))
    best_faq = None
    best_score = 0

    for faq in FAQS:
        score = 0
        for keyword in faq["keywords"]:
            normalized_keyword = normalize(keyword)
            keyword_tokens = normalized_keyword.split()
            if normalized_keyword in text:
                score += len(keyword_tokens) + 2
            elif all(token in tokens for token in keyword_tokens):
                score += len(keyword_tokens)
        if score > best_score:
            best_score = score
            best_faq = faq

    if best_faq is None:
        return DEFAULT_ANSWER, "Tra loi mac dinh"
    return best_faq["answer"], best_faq["topic"]
