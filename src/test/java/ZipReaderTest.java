import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ZipReaderTest {

    private static final String ZIP_FILE = "src/test/resources/files/sample.zip";
    private static final String OUTPUT_DIR = "src/test/resources/temp/";

    @BeforeAll
    static void setup() throws Exception {

        ZipReader.unzip(ZIP_FILE, OUTPUT_DIR);
    }

    @AfterAll
    static void cleanup() {

        try {
            FileUtils.deleteDirectory(new File(OUTPUT_DIR));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testPdfContent() throws Exception {
        File pdfFile = new File(OUTPUT_DIR + "file1.pdf");
        assertTrue(pdfFile.exists(), "PDF файл не извлечён!");

        PDF pdf = new PDF(pdfFile);
        assertEquals("Expected text in PDF", pdf.text.strip(), "Содержимое PDF файла не совпадает!");
    }

    @Test
    void testXlsxContent() throws Exception {
        File xlsxFile = new File(OUTPUT_DIR + "file2.xlsx");
        assertTrue(xlsxFile.exists(), "XLSX файл не извлечён!");

        XLS xls = new XLS(xlsxFile);
        assertEquals("Expected value", xls.excel.getSheetAt(0).getRow(0).getCell(0).getStringCellValue(),
                "Значение в первой ячейке XLSX не совпадает!");
    }

    @Test
    void testCsvContent() throws Exception {
        File csvFile = new File(OUTPUT_DIR + "file3.csv");
        assertTrue(csvFile.exists(), "CSV файл не извлечён!");

        String content = FileUtils.readFileToString(csvFile, "UTF-8");
        assertTrue(content.contains("Expected value in CSV"), "Содержимое CSV файла не совпадает!");
    }
}
