package controllers.Yasser_Controllers.Testing;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.Exception;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.BaseColor;

public class GeneratePDF {



    @FXML
    private Button burr;

    @FXML
    void GeneratePDF(ActionEvent event) {
        Generate();
    }


    public void Generate()
    {
        Document document = new Document();
        document.setPageSize(PageSize.A4);
        try {
            PdfWriter.getInstance(document, new FileOutputStream("simple.pdf"));
            document.open();

//            Paragraph paragraph = new Paragraph();
//            paragraph.add("Hello, World! This is my first PDF using iText.");
//            document.add(paragraph);

            Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 21, Font.BOLD, BaseColor.BLACK);
            Font textFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.BLACK);

            Paragraph title = new Paragraph("Title", titleFont);
            title.setAlignment(Paragraph.ALIGN_LEFT);
            document.add(title);

            Paragraph user = new Paragraph("User", titleFont);
            user.setAlignment(Paragraph.ALIGN_LEFT);
            document.add(user);

            Paragraph timestamp = new Paragraph("TimeStamp", textFont);
            timestamp.setAlignment(Paragraph.ALIGN_RIGHT);
            document.add(timestamp);

            Paragraph postText = new Paragraph("This is a Test of a Comment that i am writing to create my PostTemplate it will help me determine if this template works and works Great", textFont);
            postText.setAlignment(Paragraph.ALIGN_LEFT);
            document.add(postText);

            Paragraph likeNum = new Paragraph("0", textFont);
            likeNum.setAlignment(Paragraph.ALIGN_RIGHT);
            document.add(likeNum);

            document.close();
            System.out.println("PDF created successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
