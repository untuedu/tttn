# Kho cau hoi va tra loi cho tro ly HTX

Day la bo kien thuc FAQ cho chatbot. Chatbot nhan dien y dinh bang tu khoa co dau hoac khong dau; chatbot khong dung de chan doan sau benh hay dua ra lieu dung thuoc.

## Dang nhap va tai khoan

| Cau hoi | Tra loi ngan gon |
| --- | --- |
| Lam sao de dang nhap? | Dung tai khoan demo `admin@htx.vn / 123456` hoac `dat@htx.vn / 123456`. |
| Quen mat khau thi lam sao? | Chuc nang cap lai mat khau chua co; hay lien he quan tri vien. |
| Co phan quyen theo vai tro khong? | Du lieu co ADMIN, QUAN_LY_HTX va THANH_VIEN, nhung chua phan quyen chi tiet theo man hinh. |

## Dashboard, co so va khu vuc

| Cau hoi | Tra loi ngan gon |
| --- | --- |
| Dashboard hien thi gi? | So co so, kho, khu vuc, tong doanh thu, tong chi phi va bieu do cot. |
| Them co so nuoi trong nhu the nao? | Vao Co so nuoi trong, chon Them moi, nhap dien tich va dia chi. Dia chi trung se bi tu choi khi tao moi. |
| Dien tich da su dung la gi? | La phan dien tich cua co so dang duoc khai thac; gia tri khong duoc am. |
| Quan ly khu vuc o dau? | Vao Khu vuc de gan khu vuc voi co so, nguoi quan ly va san pham. |
| Co the tai anh san pham len khong? | Chua; form hien chi luu duong dan hinh anh dang van ban. |

## Kho vat tu va nhat ky san xuat

| Cau hoi | Tra loi ngan gon |
| --- | --- |
| Them kho vat tu nhu the nao? | Vao Kho vat tu, chon Them moi, nhap ten kho va ghi chu. Ten kho trung se bi tu choi. |
| Nhat ky mua sam dung de lam gi? | Luu ten vat tu, xuat xu, so luong, gia, ngay mua, ngay san xuat, han su dung, trang thai, kho va nguoi mua. |
| Theo doi han su dung vat tu nhu the nao? | Nhap ngay san xuat va han su dung khi tao nhat ky mua sam; chua co canh bao tu dong sap het han. |
| Nhat ky san xuat ghi nhan gi? | Ten vat tu, so luong, ngay su dung, kho xuat va khu vuc ap dung. |
| Co the hoi lieu dung phan bon hoac thuoc khong? | Khong. Hay tuan thu nhan, quy trinh ky thuat va huong dan can bo chuyen mon. |

## Thu hoach, ban san pham va QR

| Cau hoi | Tra loi ngan gon |
| --- | --- |
| Khi nao tao nhat ky thu hoach? | Khi co san luong thuc te; chon khu vuc, nhap san luong va ngay thu hoach. |
| Lam sao de ban san pham? | Chon dot thu hoach, nhap so luong ban, gia ban va ngay ban. |
| Tai sao khong luu duoc phieu ban? | So luong ban khong duoc lon hon so luong con lai cua dot thu hoach. |
| Doanh thu tinh nhu the nao? | Tong cua so luong nhan gia ban tren cac nhat ky ban san pham. |
| Chi phi tinh nhu the nao? | Tong cua so luong nhan gia tren cac nhat ky mua sam. |
| Ma QR duoc tao o dau? | Tu dong sinh khi luu nhat ky ban san pham moi va hien thi o danh sach ban. |
| Quet QR de lam gi? | Doi chieu ma giao dich ban va dot thu hoach lien quan trong he thong. |
| Co trang truy xuat QR cong khai chua? | Chua; day la huong phat trien tiep theo. |
| Co the sua nhat ky da tao khong? | Hien tai nhat ky ho tro them, xem va xoa; chua co chuc nang sua. |

## Sau benh, chatbot va du lieu

| Cau hoi | Tra loi ngan gon |
| --- | --- |
| Khi phat hien sau benh can lam gi? | Ghi nhan trieu chung, khu vuc, thoi diem va vat tu da xu ly; can bo ky thuat can kiem tra khi can. |
| Chatbot co chan doan benh cay trong khong? | Khong; chatbot khong thay the chan doan chuyen mon. |
| Du lieu co duoc luu lau dai khong? | Khong o phien ban demo; H2 trong bo nho se tao lai du lieu khi dung ung dung. |
| H2 Console o dau? | `http://localhost:8080/h2-console` khi ung dung dang chay. |
| Chatbot dung AI gi? | FastAPI nhe, bo FAQ va tu khoa; co the nang cap RAG/LLM sau nay. |
| Chatbot khong tra loi thi lam sao? | Kiem tra FastAPI o cong 8001 va `app.ai.url`; Java van co FAQ fallback. |
| He thong dung cong nghe gi? | Java 21, Spring Boot 4.1.1, Thymeleaf, JPA, H2, Chart.js, ZXing va FastAPI. |
