import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import model.JsonParser;
import model.Person;
import model.PhoneBook;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.asynchttpclient.util.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

public class SelenideTestZipJson {

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

    @Test
    void testParseJson() throws IOException {
        String jsonFilePath = "src/test/resources/people.json";

        PhoneBook phoneBook = JsonParser.parseJson(jsonFilePath);

        assertNotNull(phoneBook, "PhoneBook должен быть не null");

        List<Person> people = phoneBook.getPeople();
        assertNotNull(people, "Список людей не должен быть null");
        assertFalse(people.isEmpty(), "Список людей не должен быть пустым");

        Person firstPerson = people.get(0);
        assertEquals("Arsen Beglaryan", firstPerson.getName(), "Имя первого человека должно быть John Doe");
        assertEquals(26, firstPerson.getAge(), "Возраст первого человека должен быть 30");
        assertTrue(firstPerson.getPhones().contains("918-123-4567"), "Первый человек должен иметь телефон 918-123-4567");

        Person secondPerson = people.get(1);
        assertEquals("Angelina Beglaryan", secondPerson.getName(), "Имя второго человека должно быть Jane Smith");
        assertEquals(20, secondPerson.getAge(), "Возраст второго человека должен быть 25");
        assertTrue(secondPerson.getPhones().contains("918-123-9876"), "Второй человек должен иметь телефон 918-123-9876");
    }
}
