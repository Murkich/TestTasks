package main.java.ru.clevertec.file.writer;

import main.java.ru.clevertec.file.writer.factory.Writer;
import main.java.ru.clevertec.file.writer.factory.WriterFactory;

public class CSVResultWriterFactory implements WriterFactory {
    @Override
    public Writer createWriter() {
        return new CSVResultWriter();
    }
}
