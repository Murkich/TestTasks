package main.java.ru.clevertec.file.writer;

/**
 * Интерфейс WriterFactory определяет контракт для классов,
 * реализующих фабричный метод для создания объектов Writer.
 */
public interface WriterFactory {
    /**
     * Создает и возвращает новый объект Writer.
     *
     * @return новый объект Writer
     */
    Writer createWriter();
}
