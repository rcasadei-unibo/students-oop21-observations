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
import java.time.LocalTime;
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

    private static final float EMPTY_LINE_SIZE = 24;
    private static final PDFont MAIN_TITLE_TEXT_FONT = PDType1Font.HELVETICA_BOLD;
    private static final float MAIN_TITLE_TEXT_SIZE = 24;
    private static final PDFont SUB_TITLE_TEXT_FONT = PDType1Font.TIMES_ITALIC;
    private static final float SUB_TITLE_TEXT_SIZE = 14;

    private static final PDFont TITLE_TEXT_FONT = PDType1Font.TIMES_BOLD;
    private static final float TITLE_TEXT_SIZE = 20;
    private static final PDFont TEXT_FONT = PDType1Font.TIMES_ROMAN;
    private static final float TEXT_SIZE = 16;

    private static final Integer PAGE_START_OFFSET = 50;
    private static final int MAIN_TITLE_X_OFFSET = 25;
    private static final int SUB_TITLE_X_OFFSET = 45;
    private static final int TITLE_X_OFFSET = 45;
    private static final int TEXT_X_OFFSET = 60;
    private static final float LEADING = 14;

    private final PDDocument document;
    private PDPageContentStream stream;
    private Integer currentLineYValue;

    public PdfExporter() {
        try {
            this.document = new PDDocument();
            this.setNewPage();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Export all data saved into a user legible pdf file.
     *
     * @param data map containing all data.
     */
    public void exportPdf(Map<String, Map<String, Map<String, Map<String, Integer>>>> data) {
        try {
            this.addSingleLineText("Observations", MAIN_TITLE_X_OFFSET, this.currentLineYValue, MAIN_TITLE_TEXT_FONT, MAIN_TITLE_TEXT_SIZE);
            this.addSingleLineText(this.createSubTitle(), SUB_TITLE_X_OFFSET, this.currentLineYValue, SUB_TITLE_TEXT_FONT, SUB_TITLE_TEXT_SIZE);

            for (String student : data.keySet().stream().sorted().collect(Collectors.toList())) {
                this.addSingleLineText(" ", TEXT_X_OFFSET, this.currentLineYValue, MAIN_TITLE_TEXT_FONT, EMPTY_LINE_SIZE * 2);
                this.addSingleLineText(student, TITLE_X_OFFSET, this.currentLineYValue, TITLE_TEXT_FONT, TITLE_TEXT_SIZE);

                for (String moment : data.get(student).keySet().stream().sorted().collect(Collectors.toList())) {
                    this.addSingleLineText(" ", TEXT_X_OFFSET, this.currentLineYValue, MAIN_TITLE_TEXT_FONT, EMPTY_LINE_SIZE);
                    this.addSingleLineText(moment, SUB_TITLE_X_OFFSET, this.currentLineYValue, TEXT_FONT, TEXT_SIZE);

                    for (String date : data.get(student).get(moment).keySet().stream().sorted(new DateComparator()).collect(Collectors.toList())) {
                        this.addSingleLineText(" ", TEXT_X_OFFSET, this.currentLineYValue, MAIN_TITLE_TEXT_FONT, EMPTY_LINE_SIZE);
                        this.addSingleLineText(date, SUB_TITLE_X_OFFSET, this.currentLineYValue, TEXT_FONT, TEXT_SIZE);

                        List<String> observationsList = new ArrayList<>();

                        for (String observation : data.get(student).get(moment).get(date).keySet().stream().sorted().collect(Collectors.toList())) {
                            observationsList.add(observation + ": " + data.get(student).get(moment).get(date).get(observation));
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
    }

    private String createSubTitle() {
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        return "PDF creato il " + date + " " + time;
    }

    private void addSingleLineText(String text, int xPosition, int yPosition, PDFont font, float fontSize) throws IOException {
        if (this.isEndOfPage(fontSize)) {
            this.stream.close();
            this.setNewPage();
            yPosition = this.currentLineYValue;
        }
        this.stream.beginText();
        this.stream.setFont(font, fontSize);
        this.stream.newLineAtOffset(xPosition, yPosition);
        this.stream.showText(text);
        this.currentLineYValue -= (int) fontSize;
        this.stream.endText();
        this.stream.moveTo(0, 0);
    }

    private void addMultiLineText(List<String> textList, float leading, int xPosition, int yPosition, PDFont font, float fontSize) throws IOException {
        this.stream.beginText();
        this.stream.setFont(font, fontSize);
        this.stream.setLeading(leading);
        this.stream.newLineAtOffset(xPosition, yPosition);
        for (String textLine : textList) {
            if (this.isEndOfPage(fontSize)) {
                this.stream.endText();
                this.stream.close();
                this.setNewPage();
                this.stream.beginText();
                this.stream.setFont(font, fontSize);
                this.stream.setLeading(leading);
                this.stream.newLineAtOffset(xPosition, this.currentLineYValue);
            }
            this.stream.showText(textLine);
            this.stream.newLine();
            this.currentLineYValue -= (int) leading;
        }
        this.stream.endText();
        this.stream.moveTo(0, 0);
    }

    private float getTextWidth(String text, PDFont font, float fontSize) throws IOException {
        return font.getStringWidth(text) / 1000 * fontSize;
    }

    private void setNewPage() throws IOException {
        PDPage page = new PDPage(PDRectangle.A4);
        this.document.addPage(page);
        this.stream = new PDPageContentStream(this.document, page);
        Integer pageHeight = (int) page.getTrimBox().getHeight();
        this.currentLineYValue = pageHeight - PAGE_START_OFFSET;
    }

    private boolean isEndOfPage(float offset) {
        return currentLineYValue - offset < 0;
    }
}
