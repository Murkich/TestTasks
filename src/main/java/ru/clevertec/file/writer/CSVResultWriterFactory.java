package main.java.ru.clevertec.file.writer;

/**
 * Класс CSVResultWriterFactory реализует интерфейс WriterFactory
 * и предназначен для создания объектов файла csv CSVResultWriter.
 */
public class CSVResultWriterFactory implements WriterFactory {
    /**
     * Создает и возвращает новый объект файла csv CSVResultWriter.
     *
     * @return новый объект файла csv CSVResultWriter
     */
    @Override
    public Writer createWriter() {
        return new CSVResultWriter();
    }
}
