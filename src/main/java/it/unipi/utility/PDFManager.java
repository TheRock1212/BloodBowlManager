package it.unipi.utility;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import it.unipi.dataset.Model.Team;

import java.io.FileOutputStream;
import java.io.IOException;

public class PDFManager {
    private static final String[] headerRoster = {"#", "PLAYER NAME", "TYPE", "QTY", "MA", "ST", "AG", "PA", "AV", "SKILLS", "TotalSPP", "UnspentSPP", "COST"};
    private static final int nrColumn = 13;
    private static final String TAHOMA = "src/main/resources/fonts/Tahoma.ttf";
    private static final String TAHOMA_BOLD = "src/main/resources/fonts/Tahoma Bold.ttf";

    public PDFManager() {
    }

    public static void generatePDF(Team t) throws IOException, DocumentException {
        Document document = new Document(PageSize.A4.rotate());
        PdfWriter.getInstance(document, new FileOutputStream(t.coach + ".pdf"));
        document.open();
        BaseFont baseFont = BaseFont.createFont(TAHOMA, BaseFont.WINANSI, BaseFont.EMBEDDED);
        Font bodoni = new Font(baseFont, 8);
        Font headT = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.WHITE);

        PdfPTable table = new PdfPTable(13);
        for(String head : headerRoster) {
            PdfPCell header = new PdfPCell();
            header.setBackgroundColor(BaseColor.GRAY);
            header.setBorderWidth(2);
            header.setPhrase(new Phrase(head, bodoni));
            table.addCell(header);
        }

        document.add(table);
        document.close();
    }
}
