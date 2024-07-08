package main.java.ru.clevertec.file.writer;

import main.java.ru.clevertec.exception.BadRequest;

/**
 * Абстрактный класс WriterFactory определяет контракт для классов,
 * реализующих фабричный метод для создания объектов Writer.
 */
public abstract class WriterFactory {
    /**
     * Создает и возвращает новый объект Writer.
     *
     * @param filePath путь к файлу сохранения
     * @return новый объект Writer
     */
    public static Writer createWriter(String filePath) {
        if (filePath != null) {
            return filePath.toLowerCase().endsWith(".csv") ? new CSVResultWriter(filePath) :
                    new CSVResultWriter(null);
        }
        return new CSVResultWriter(null);
    }
}
