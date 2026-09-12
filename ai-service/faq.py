import re
import unicodedata


FAQS = [
    {
        "question": "Chatbot co the ho tro nhung gi?",
        "keywords": ("tro giup", "ho tro", "lam duoc gi", "chuc nang"),
        "answer": "Tôi co the huong dan su dung cac muc co so nuoi trong, khu vuc, kho vat tu, nhat ky, thu hoach, ban san pham, QR va dashboard.",
        "topic": "Ho tro chung",
    },
    {
        "question": "Lam sao de dang nhap?",
        "keywords": ("dang nhap", "login", "tai khoan mau"),
        "answer": "Mo trang dang nhap, nhap email va mat khau. Tai khoan demo la admin@htx.vn / 123456 hoac dat@htx.vn / 123456.",
        "topic": "Dang nhap",
    },
    {
        "question": "Quen mat khau thi lam sao?",
        "keywords": ("quen mat khau", "doi mat khau", "cap lai mat khau"),
        "answer": "Phien ban hien tai chua co chuc nang tu cap lai mat khau. Hay lien he quan tri vien de duoc ho tro cap nhat tai khoan.",
        "topic": "Dang nhap",
    },
    {
        "question": "Dashboard hien thi gi?",
        "keywords": ("dashboard", "thong ke", "doanh thu", "chi phi", "bieu do"),
        "answer": "Dashboard hien so co so, kho, khu vuc, tong doanh thu ban san pham, tong chi phi mua sam va bieu do cot tong quan.",
        "topic": "Dashboard",
    },
    {
        "question": "Them co so nuoi trong nhu the nao?",
        "keywords": ("them co so", "co so nuoi trong", "dia chi co so"),
        "answer": "Vao Co so nuoi trong, chon Them moi, nhap dien tich, dien tich da su dung va dia chi, sau do nhan Luu. Dia chi trung se bi tu choi khi tao moi.",
        "topic": "Co so nuoi trong",
    },
    {
        "question": "Dien tich da su dung la gi?",
        "keywords": ("dien tich da su dung", "dien tich con lai", "dien tich"),
        "answer": "Dien tich da su dung la phan dien tich cua co so dang duoc khai thac. Hay nhap gia tri khong am va khong lon hon dien tich tong trong quy trinh van hanh.",
        "topic": "Co so nuoi trong",
    },
    {
        "question": "Quan ly khu vuc san xuat o dau?",
        "keywords": ("khu vuc", "them khu vuc", "nguoi quan ly khu vuc", "san pham khu vuc"),
        "answer": "Vao Khu vuc de them, sua hoac xoa khu vuc. Moi khu vuc can gan voi mot co so va mot nguoi quan ly; co the khai bao san pham va duong dan hinh anh.",
        "topic": "Khu vuc",
    },
    {
        "question": "Them kho vat tu nhu the nao?",
        "keywords": ("them kho", "kho vat tu", "ten kho", "ghi chu kho"),
        "answer": "Vao Kho vat tu, chon Them moi, nhap ten kho va ghi chu. He thong khong cho tao moi hai kho trung ten.",
        "topic": "Kho vat tu",
    },
    {
        "question": "Nhat ky mua sam dung de lam gi?",
        "keywords": ("nhat ky mua sam", "mua vat tu", "nhap kho", "phieu mua"),
        "answer": "Nhat ky mua sam ghi nhan ten vat tu, xuat xu, so luong, gia, ngay mua, ngay san xuat, han su dung, trang thai, loai vat tu, kho luu va nguoi mua.",
        "topic": "Nhat ky mua sam",
    },
    {
        "question": "Can theo doi han su dung vat tu nhu the nao?",
        "keywords": ("han su dung", "het han", "ngay san xuat", "bao quan vat tu"),
        "answer": "Khi tao nhat ky mua sam, hay nhap ngay san xuat va han su dung. Phien ban hien tai luu de tra cuu, chua co canh bao tu dong vat tu sap het han.",
        "topic": "Kho vat tu",
    },
    {
        "question": "Nhat ky san xuat ghi nhan thong tin gi?",
        "keywords": ("nhat ky san xuat", "su dung vat tu", "bon phan", "phun thuoc", "canh tac"),
        "answer": "Nhat ky san xuat ghi ten vat tu, so luong su dung, ngay su dung, kho xuat vat tu va khu vuc ap dung. Day la du lieu nen cho truy xuat nguon goc.",
        "topic": "Nhat ky san xuat",
    },
    {
        "question": "Khi nao tao nhat ky thu hoach?",
        "keywords": ("nhat ky thu hoach", "tao thu hoach", "san luong thu hoach", "ngay thu hoach"),
        "answer": "Sau khi co san luong thuc te, vao Nhat ky thu hoach, chon khu vuc va nhap so luong thu hoach, so luong da ban neu co, cung ngay thu hoach.",
        "topic": "Thu hoach",
    },
    {
        "question": "Lam sao ban san pham?",
        "keywords": ("ban san pham", "ban hang", "tao don ban", "gia ban"),
        "answer": "Vao Ban san pham, chon dot thu hoach, nhap so luong ban, gia ban va ngay ban. He thong kiem tra so luong ban so voi so luong con lai dang luu tren dot thu hoach.",
        "topic": "Ban san pham",
    },
    {
        "question": "Tai sao khong luu duoc phieu ban?",
        "keywords": ("khong luu duoc phieu ban", "ban vuot", "so luong ban", "ban qua so luong"),
        "answer": "So luong ban phai khong lon hon so luong thu hoach tru so luong da ban cua dot duoc chon. Hay kiem tra lai dot thu hoach va so luong nhap vao.",
        "topic": "Ban san pham",
    },
    {
        "question": "Doanh thu va chi phi duoc tinh nhu the nao?",
        "keywords": ("tinh doanh thu", "tong doanh thu", "tinh chi phi", "tong chi phi"),
        "answer": "Dashboard tinh doanh thu bang tong so luong nhan gia ban cua cac phieu ban; chi phi mua sam la tong so luong nhan gia cua cac phieu mua.",
        "topic": "Dashboard",
    },
    {
        "question": "Ma QR duoc tao o dau?",
        "keywords": ("ma qr", "qr code", "tao qr", "qr ban san pham"),
        "answer": "Moi nhat ky ban san pham moi se tu sinh chuoi ma dang HTX-BAN-ID-THUHOACH-ID. Danh sach ban san pham hien anh QR de quet va chuoi ma de doi chieu.",
        "topic": "QR truy xuat",
    },
    {
        "question": "Quet QR de lam gi?",
        "keywords": ("quet qr", "truy xuat", "ma truy xuat", "anh qr"),
        "answer": "QR hien tai ma hoa ma dinh danh cua giao dich ban va dot thu hoach lien quan. No dung de doi chieu trong he thong; trang truy xuat cong khai la huong phat trien tiep theo.",
        "topic": "QR truy xuat",
    },
    {
        "question": "Khi phat hien sau benh can lam gi?",
        "keywords": ("sau benh", "sau hai", "trieu chung", "cay bi benh", "vang la"),
        "answer": "Hay ghi nhan trieu chung, khu vuc, thoi diem phat hien va vat tu da xu ly trong nhat ky san xuat. Chatbot chi ho tro nhac quy trinh, khong thay the can bo ky thuat hay chan doan chuyen mon.",
        "topic": "Sau benh",
    },
    {
        "question": "Co the hoi cach dung phan bon hay thuoc khong?",
        "keywords": ("phan bon", "thuoc bao ve", "thuoc bvtv", "lieu dung", "phun thuoc"),
        "answer": "He thong co the ghi nhan vat tu da dung, nhung khong tu dong ke don hay dua lieu dung. Hay tuan thu nhan, quy trinh ky thuat va huong dan cua can bo chuyen mon.",
        "topic": "An toan canh tac",
    },
    {
        "question": "Du lieu co duoc luu lau dai khong?",
        "keywords": ("luu du lieu", "mat du lieu", "h2", "khoi dong lai", "database"),
        "answer": "Phien ban demo dung H2 trong bo nho va schema create-drop, do do du lieu se bi tao lai khi dung ung dung. Can chuyen sang CSDL ben ngoai de luu lau dai.",
        "topic": "Du lieu va H2",
    },
    {
        "question": "H2 Console o dau?",
        "keywords": ("h2 console", "console database", "xem database", "csdl"),
        "answer": "Khi Spring Boot dang chay, H2 Console co dia chi http://localhost:8080/h2-console. Day la cong cu phat trien, khong nen mo cong khai tren moi truong that.",
        "topic": "Du lieu va H2",
    },
    {
        "question": "Co the sua nhat ky da tao khong?",
        "keywords": ("sua nhat ky", "cap nhat nhat ky", "xoa nhat ky", "chinh sua phieu"),
        "answer": "Hien tai cac nhat ky mua sam, san xuat, thu hoach va ban san pham ho tro them, xem danh sach va xoa. Chuc nang sua nhat ky chua duoc trien khai.",
        "topic": "Gioi han he thong",
    },
    {
        "question": "Co phan quyen theo vai tro khong?",
        "keywords": ("phan quyen", "vai tro", "admin", "quan ly htx", "thanh vien"),
        "answer": "He thong co cac vai tro ADMIN, QUAN_LY_HTX va THANH_VIEN trong du lieu mau. Phan quyen chi tiet tren tung man hinh chua duoc ap dung o phien ban hien tai.",
        "topic": "Tai khoan va quyen",
    },
    {
        "question": "Chatbot co dung AI lon khong?",
        "keywords": ("ai", "llm", "chatbot", "fastapi", "rag"),
        "answer": "Chatbot hien la dich vu FastAPI nhe, tra loi theo bo FAQ va tu khoa. Kien truc da tach rieng de co the thay bang RAG hoac LLM trong tuong lai.",
        "topic": "Chatbot",
    },
    {
        "question": "Chatbot khong tra loi thi lam sao?",
        "keywords": ("chatbot khong tra loi", "loi chat", "fastapi chua chay", "khong ket noi ai"),
        "answer": "Kiem tra FastAPI dang chay tai cong 8001 va app.ai.url co dung khong. Neu dich vu Python chua san sang, backend Java van tra loi bang FAQ fallback.",
        "topic": "Chatbot",
    },
    {
        "question": "Co the tai anh san pham len khong?",
        "keywords": ("tai anh", "upload anh", "hinh anh san pham", "anh san pham"),
        "answer": "Form khu vuc hien chi luu duong dan hinh anh san pham dang van ban. Chuc nang upload tep va quan ly anh chua duoc trien khai.",
        "topic": "Gioi han he thong",
    },
    {
        "question": "He thong dung cong nghe gi?",
        "keywords": ("cong nghe", "spring boot", "thymeleaf", "java", "python"),
        "answer": "Ung dung dung Java 21, Spring Boot 4.1.1, Thymeleaf, Spring Data JPA, H2 Database, Chart.js, ZXing QR Code va FastAPI tuy chon cho chatbot.",
        "topic": "Cong nghe",
    },
]

DEFAULT_ANSWER = (
    "Toi chua tim thay cau tra loi phu hop. Ban co the hoi ve dang nhap, co so, khu vuc, kho vat tu, "
    "nhat ky mua sam, san xuat, thu hoach, ban san pham, QR, dashboard hoac chatbot."
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
