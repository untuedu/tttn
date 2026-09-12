# HTX Farm Management

Project duoc chuyen tu dac ta Word sang Java Spring Boot 4.1.1, Thymeleaf va H2 Database.

## Chuc nang

- Dang nhap phien lam viec don gian: `admin@htx.vn / 123456`
- Dashboard thong ke bang Chart.js.
- CRUD: co so nuoi trong, khu vuc, kho vat tu, nhat ky mua sam, nhat ky san xuat, thu hoach, ban san pham.
- H2 console: `/h2-console`.
- Chatbot nhe: goi Python FastAPI tai `http://localhost:8001/chat`, neu chua chay thi fallback FAQ noi bo.

## Chay ung dung

```powershell
mvn spring-boot:run
```

Truy cap: `http://localhost:8080`

## Chay chatbot Python tuy chon

```powershell
cd ai-service
pip install -r requirements.txt
uvicorn api:app --reload --host 0.0.0.0 --port 8001
```

