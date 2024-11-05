package controllers.Yasser_Controllers.Testing;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import javafx.fxml.FXML;

import java.io.IOException;
import java.nio.file.Paths;

public class TestQR {
    @FXML
    public void generateQR() throws WriterException, IOException {
        String data="Youtube.com";
        String path="Qr.jpg";
        BitMatrix matrix = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE,500,500);
        MatrixToImageWriter.writeToPath(matrix,"jpg",Paths.get(path));
        System.out.println("done");

    }
}
