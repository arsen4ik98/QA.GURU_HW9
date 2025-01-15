package utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class WorkWithFiles {

    private static final String ZIP_PATH = "/files/sample.zip";

    public InputStream getFileFromZip(String fileToBeExtracted) throws Exception {
        InputStream zipStream = getClass().getResourceAsStream(ZIP_PATH);
        if (zipStream == null) {
            throw new IllegalArgumentException("ZIP file not found: " + ZIP_PATH);
        }

        ZipInputStream zis = new ZipInputStream(zipStream);
        ZipEntry entry;
        while ((entry = zis.getNextEntry()) != null) {
            if (entry.getName().equals(fileToBeExtracted)) {
                return zis;  // Возвращаем поток напрямую
            }
        }

        zis.close();  // Закрываем поток только если файл не найден
        return InputStream.nullInputStream();
    }
    public String getZipName() {
        return ZIP_PATH;
    }
}