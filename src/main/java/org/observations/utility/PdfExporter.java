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

/**
 * Class with a single method to export all locally saved data of students, moments, dates and observations into a legible pdf file.
 */
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

    /**
     * Initialize the class and creates an empty document.
     */
    public PdfExporter() {
        try {
            document = new PDDocument();
            setNewPage();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Export all data saved into a user legible pdf file.
     *
     * @param data map containing all students, a map of their saved moments, each containing a map of saved dates, every date having a map of observations and relative counters.
     */
    public void exportPdf(Map<String, Map<String, Map<String, Map<String, Integer>>>> data) {
        try {
            addSingleLineText("Observations", MAIN_TITLE_X_OFFSET, currentLineYValue, MAIN_TITLE_TEXT_FONT, MAIN_TITLE_TEXT_SIZE);
            addSingleLineText(createSubTitle(), SUB_TITLE_X_OFFSET, currentLineYValue, SUB_TITLE_TEXT_FONT, SUB_TITLE_TEXT_SIZE);

            for (String student : data.keySet().stream().sorted().collect(Collectors.toList())) {
                addSingleLineText(" ", TEXT_X_OFFSET, currentLineYValue, MAIN_TITLE_TEXT_FONT, EMPTY_LINE_SIZE * 2);
                addSingleLineText(student, TITLE_X_OFFSET, currentLineYValue, TITLE_TEXT_FONT, TITLE_TEXT_SIZE);

                for (String moment : data.get(student).keySet().stream().sorted().collect(Collectors.toList())) {
                    addSingleLineText(" ", TEXT_X_OFFSET, currentLineYValue, MAIN_TITLE_TEXT_FONT, EMPTY_LINE_SIZE);
                    addSingleLineText(moment, SUB_TITLE_X_OFFSET, currentLineYValue, TEXT_FONT, TEXT_SIZE);

                    for (String date : data.get(student).get(moment).keySet().stream().sorted(new DateComparator()).collect(Collectors.toList())) {
                        addSingleLineText(" ", TEXT_X_OFFSET, currentLineYValue, MAIN_TITLE_TEXT_FONT, EMPTY_LINE_SIZE);
                        addSingleLineText(date, SUB_TITLE_X_OFFSET, currentLineYValue, TEXT_FONT, TEXT_SIZE);

                        List<String> observationsList = new ArrayList<>();

                        for (String observation : data.get(student).get(moment).get(date).keySet().stream().sorted().collect(Collectors.toList())) {
                            observationsList.add(observation + ": " + data.get(student).get(moment).get(date).get(observation));
                        }
                        addMultiLineText(observationsList, LEADING, TEXT_X_OFFSET, currentLineYValue, TEXT_FONT, TEXT_SIZE);
                    }
                }
            }

            stream.close();
            document.save(EXPORT_PATH + PDF_NAME);
            document.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Create a string containing the date of creation.
     *
     * @return a string.
     */
    private String createSubTitle() {
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        return "PDF creato il " + date + " " + time;
    }

    /**
     * Add a single line of text to the current document and page.
     *
     * @param text      string of text to add.
     * @param xPosition horizontal position on page.
     * @param yPosition vertical position on page.
     * @param font      type of font.
     * @param fontSize  font size.
     * @throws IOException
     */
    private void addSingleLineText(String text, int xPosition, int yPosition, PDFont font, float fontSize) throws IOException {
        if (isEndOfPage(fontSize)) {
            stream.close();
            setNewPage();
            yPosition = currentLineYValue;
        }
        stream.beginText();
        stream.setFont(font, fontSize);
        stream.newLineAtOffset(xPosition, yPosition);
        stream.showText(text);
        currentLineYValue -= (int) fontSize;
        stream.endText();
        stream.moveTo(0, 0);
    }

    /**
     * Add multiple lines of text to the current document and page.
     *
     * @param textList  list of strings to add.
     * @param leading   the leading of the text.
     * @param xPosition horizontal position on page.
     * @param yPosition vertical position on page.
     * @param font      type of font.
     * @param fontSize  font size.
     * @throws IOException
     */
    private void addMultiLineText(List<String> textList, float leading, int xPosition, int yPosition, PDFont font, float fontSize) throws IOException {
        stream.beginText();
        stream.setFont(font, fontSize);
        stream.setLeading(leading);
        stream.newLineAtOffset(xPosition, yPosition);
        for (String textLine : textList) {
            if (isEndOfPage(fontSize)) {
                stream.endText();
                stream.close();
                setNewPage();
                stream.beginText();
                stream.setFont(font, fontSize);
                stream.setLeading(leading);
                stream.newLineAtOffset(xPosition, currentLineYValue);
            }
            stream.showText(textLine);
            stream.newLine();
            currentLineYValue -= (int) leading;
        }
        stream.endText();
        stream.moveTo(0, 0);
    }

    /**
     * Calculate the horizontal space taken by a specific combination of string, font and font size.
     *
     * @param text     string of text.
     * @param font     type of font.
     * @param fontSize size of font.
     * @return a float number the length for the given text, font and font size combination.
     * @throws IOException if font.getStringWidth() throw IOException.
     */
    private float getTextWidth(String text, PDFont font, float fontSize) throws IOException {
        return font.getStringWidth(text) / 1000 * fontSize;
    }

    /**
     * Set a new page for the current document.
     *
     * @throws IOException if document.addPage() or new PDPageContentStream() have thrown an IOException.
     */
    private void setNewPage() throws IOException {
        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);
        stream = new PDPageContentStream(document, page);
        Integer pageHeight = (int) page.getTrimBox().getHeight();
        currentLineYValue = pageHeight - PAGE_START_OFFSET;
    }

    /**
     * Valuates if the current y offset has reached the bottom of the page.
     *
     * @param offset additional offset regarding the height of a text line to evaluate if there still space fo a new line.
     * @return return true if has been reached the bottom of page, false if not.
     */
    private boolean isEndOfPage(float offset) {
        return currentLineYValue - offset < 0;
    }
}
