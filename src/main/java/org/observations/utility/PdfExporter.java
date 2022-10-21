package org.observations.utility;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PdfExporter {

    private static final String SEP = File.separator;
    private static final String ROOT = System.getProperty("user.home");
    private static final String NAME_APP = "Observations";
    private static final String EXPORT_PATH = ROOT + SEP + NAME_APP + SEP;
    private static final String PDF_NAME = "Observations.pdf";

    private static final PDFont TITLE_TEXT_FONT = PDType1Font.HELVETICA_BOLD;
    private static final float TITLE_TEXT_SIZE = 24;
    private static final PDFont SUB_TITLE_TEXT_FONT = PDType1Font.TIMES_ITALIC;
    private static final float SUB_TITLE_TEXT_SIZE = 14;
    private static final PDFont TEXT_FONT = PDType1Font.TIMES_ROMAN;
    private static final float TEXT_SIZE = 16;

    private static final Integer PAGE_START_OFFSET = 50;
    private static final int TITLE_X_OFFSET = 25;
    private static final int TEXT_X_OFFSET = 60;
    private static final float LEADING = 14;

    private PDDocument document;
    private PDPageContentStream stream;
    private PDPage page;
    private Integer pageWidth;
    private Integer pageHeight;
    private Integer currentLineYValue;


    public PdfExporter() {
        try {
            this.document = new PDDocument();
            this.setNewPage();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean exportPdf(Map<String, Map<String, Map<String, Map<String, Integer>>>> allData) {
        try {
            this.addSingleLineText("Observations", TITLE_X_OFFSET, this.currentLineYValue, PDType1Font.HELVETICA_BOLD, TITLE_TEXT_SIZE);
            this.addSingleLineText("PDF creato il " + " " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                    25, this.currentLineYValue, SUB_TITLE_TEXT_FONT, SUB_TITLE_TEXT_SIZE);

            for (String student : allData.keySet().stream().sorted().collect(Collectors.toList())) {
                this.addSingleLineText(" ", 45, this.currentLineYValue, TITLE_TEXT_FONT, 45);
                this.addSingleLineText(student, 45, this.currentLineYValue, PDType1Font.TIMES_BOLD, 20);

                for (String moment : allData.get(student).keySet().stream().sorted().collect(Collectors.toList())) {
                    this.addSingleLineText(" ", 45, this.currentLineYValue, TITLE_TEXT_FONT, TEXT_SIZE);
                    this.addSingleLineText(moment, TEXT_X_OFFSET, this.currentLineYValue, PDType1Font.TIMES_BOLD, TEXT_SIZE);

                    for (String date : allData.get(student).get(moment).keySet().stream().sorted(new DateComparator()).collect(Collectors.toList())) {
                        this.addSingleLineText(" ", 45, this.currentLineYValue, TITLE_TEXT_FONT, TEXT_SIZE);
                        this.addSingleLineText(date, TEXT_X_OFFSET, this.currentLineYValue, TEXT_FONT, TEXT_SIZE);

                        List<String> observationsList = new ArrayList<>();

                        for (String observation : allData.get(student).get(moment).get(date).keySet().stream().sorted().collect(Collectors.toList())) {
                            observationsList.add(observation + ": " + allData.get(student).get(moment).get(date).get(observation));
                        }
                        this.addMultiLineText(observationsList, LEADING, TEXT_X_OFFSET, this.currentLineYValue, TEXT_FONT, TEXT_SIZE);
                    }
                }
            }

            this.stream.close();
            this.document.save(EXPORT_PATH + PDF_NAME);
            this.document.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    private void addSingleLineText(String text, int xPosition, int yPosition, PDFont font, float fontSize) throws IOException {
        if (this.isEndOfPage(fontSize)) {
            stream.close();
            this.setNewPage();
            yPosition = this.currentLineYValue;
        }
        stream.beginText();
        stream.setFont(font, fontSize);
        stream.newLineAtOffset(xPosition, yPosition);
        stream.showText(text);
        stream.endText();
        this.currentLineYValue -= (int) fontSize;
        stream.moveTo(0, 0);

    }

    private void addMultiLineText(List<String> textList, float leading, int xPosition, int yPosition, PDFont font, float fontSize) throws IOException {
        stream.beginText();
        stream.setFont(font, fontSize);
        stream.setLeading(leading);
        stream.newLineAtOffset(xPosition, yPosition);
        for (String textLine : textList) {
            if (this.isEndOfPage(fontSize)) {
                stream.endText();
                stream.close();
                this.setNewPage();
                stream.beginText();
                stream.setFont(font, fontSize);
                stream.setLeading(leading);
                stream.newLineAtOffset(xPosition, this.currentLineYValue);
            }
            stream.showText(textLine);
            stream.newLine();
            this.currentLineYValue -= (int) leading;
        }
        stream.endText();
        stream.moveTo(0, 0);
    }

    private float getTextWidth(String text, PDFont font, float fontSize) throws IOException {
        return font.getStringWidth(text) / 1000 * fontSize;
    }

    private void setNewPage() throws IOException {
        this.page = new PDPage(PDRectangle.A4);
        this.document.addPage(this.page);
        this.stream = new PDPageContentStream(this.document, this.page);
        this.pageWidth = (int) this.page.getTrimBox().getWidth();
        this.pageHeight = (int) this.page.getTrimBox().getHeight();
        this.currentLineYValue = this.pageHeight - PAGE_START_OFFSET;
    }

    private boolean isEndOfPage(float offset) {
        return currentLineYValue - offset < 0;
    }
}
