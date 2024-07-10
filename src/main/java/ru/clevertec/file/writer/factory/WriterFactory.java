package main.java.ru.clevertec.file.writer.factory;

import main.java.ru.clevertec.exception.InternalServerError;
import main.java.ru.clevertec.file.writer.CSVResultWriter;

import static main.java.ru.clevertec.constants.Constants.CSV_FILE;

public interface WriterFactory {
    static Writer createWriter(String filePath) throws InternalServerError {
        if (filePath != null) {
            return filePath.toLowerCase().endsWith(CSV_FILE) ? new CSVResultWriter(filePath) :
                    new CSVResultWriter(null);
        }
        return new CSVResultWriter(null);
    }
}
