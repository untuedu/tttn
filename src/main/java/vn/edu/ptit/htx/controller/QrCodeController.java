package vn.edu.ptit.htx.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.ptit.htx.model.NhatKyBanSanPham;
import vn.edu.ptit.htx.repository.NhatKyBanSanPhamRepository;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@RestController
public class QrCodeController {
    private final NhatKyBanSanPhamRepository banRepo;

    public QrCodeController(NhatKyBanSanPhamRepository banRepo) {
        this.banRepo = banRepo;
    }

    @GetMapping(value = "/ban-san-pham/{id}/qr.png", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> qrCode(@PathVariable Integer id) throws Exception {
        NhatKyBanSanPham item = banRepo.findById(id).orElse(null);
        if (item == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        String content = item.getQrCode();
        if (content == null || content.isBlank()) {
            content = "HTX-BAN-" + item.getMaNhatKyBanSanPham();
        }
        BitMatrix matrix = new MultiFormatWriter().encode(
                content,
                BarcodeFormat.QR_CODE,
                180,
                180,
                Map.of(EncodeHintType.CHARACTER_SET, StandardCharsets.UTF_8.name(), EncodeHintType.MARGIN, 1));
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(matrix, "PNG", output);

        return ResponseEntity.ok()
                .cacheControl(CacheControl.noCache())
                .contentType(MediaType.IMAGE_PNG)
                .body(output.toByteArray());
    }
}
