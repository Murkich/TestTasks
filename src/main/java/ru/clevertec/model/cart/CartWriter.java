package main.java.ru.clevertec.model.cart;

import main.java.ru.clevertec.file.writer.CSVResultWriterFactory;
import main.java.ru.clevertec.file.writer.Writer;
import main.java.ru.clevertec.file.writer.WriterFactory;

/**
 * Класс CartWriter предназначен для записи информации о корзине покупок в файл.
 */
public class CartWriter {
    /**
     * Записывает информацию о корзине покупок в файл.
     *
     * @param cart объект Cart, содержащий информацию о корзине покупок
     */
    public static void writeResult(Cart cart) {
        Writer csvResultWriter = new CSVResultWriterFactory().createWriter();

        csvResultWriter.writeResult(cart);
    }
}
