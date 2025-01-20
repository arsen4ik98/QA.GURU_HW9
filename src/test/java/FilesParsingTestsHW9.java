import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import model.PeopleList;
import model.Structure;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.WorkWithFiles;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class FilesParsingTestsHW9 {

    WorkWithFiles workWithFiles = new WorkWithFiles();

    @DisplayName("Проверка что zip архив содержит необходимые файлы")
    @Test
    void zipParsingTest() throws Exception {
        try (ZipInputStream zis = new ZipInputStream(
                getClass().getResourceAsStream(workWithFiles.getZipName())
        )) {
            ZipEntry entry;
            List<String> expectedFiles = List.of("file1.pdf", "file2.xlsx","file3.csv");
            List<String> actualFiles = new ArrayList<>();

            while ((entry = zis.getNextEntry()) != null) {
                actualFiles.add(entry.getName());
            }
            assertEquals(expectedFiles, actualFiles);
        }
    }

    @DisplayName("Проверка содержимого pdf файла")
    @Test
    void pdfParsingTest() throws Exception {
        WorkWithFiles workWithFiles = new WorkWithFiles();
        try (InputStream pdfStream = workWithFiles.getFileFromZip("file1.pdf")) {
            PDF pdf = new PDF(pdfStream);
            assertThat(pdf.text).contains("Expected text in PDF");
        }
    }

    @DisplayName("Проверка содержимого xlsx файла")
    @Test
    void xlsxParsingTest() throws Exception {
        try (InputStream xlsxFile = workWithFiles.getFileFromZip("file2.xlsx")) {
            XLS xlsFile = new XLS(xlsxFile);
            String actualTitle1 = xlsFile.excel.getSheetAt(0).getRow(0).getCell(0).getStringCellValue();
            assertThat(actualTitle1).isEqualTo("Expected value");
        }
    }

    @DisplayName("Проверка содержимого csv файла")
    @Test
    void csvParsingTest() throws Exception {
        try (InputStream csvFile = workWithFiles.getFileFromZip("file3.csv")) {
            CSVReader csvReader = new CSVReader(new InputStreamReader(csvFile));
            List<String[]> data = csvReader.readAll();
            assertThat(data).isNotEmpty();
            String[] firstRow = data.get(0);
            assertThat(firstRow).isNotEmpty();
            String actualValue = firstRow[0];
            assertThat(actualValue).isEqualTo("Expected value in CSV");
        }
    }

    @DisplayName("Проверка содержимого json файла")
    @Test
    void jsonParsingTest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        PeopleList actualStructure = objectMapper.readValue(new File("src/test/resources/people.json"), PeopleList.class);

        // Проверка общего количества людей
        assertThat(actualStructure.getPeople().size()).isEqualTo(2);

        // Проверка данных первого человека
        Structure person1 = actualStructure.getPeople().get(0);
        assertThat(person1.getName()).isEqualTo("Arsen Beglaryan");
        assertThat(person1.getAge()).isEqualTo(26);
        assertThat(person1.getPhones()).containsExactly("918-123-4567", "918-987-6543");

        // Проверка данных второго человека
        Structure person2 = actualStructure.getPeople().get(1);
        assertThat(person2.getName()).isEqualTo("Angelina Beglaryan");
        assertThat(person2.getAge()).isEqualTo(20);
        assertThat(person2.getPhones()).containsExactly("918-123-9876");
    }

}