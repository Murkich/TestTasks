package main.java.ru.clevertec.model.cart;

import main.java.ru.clevertec.file.writer.Writer;

/**
 * Класс CartWriter предназначен для записи информации о корзине покупок в файл.
 */
public class CartWriter {
    /**
     * Записывает информацию о корзине покупок в файл.
     *
     * @param objWriter объект Writer, вызывающий метод для записи чека
     * @param cart      объект Cart, содержащий информацию о корзине покупок
     */
    public static void writeResult(Writer objWriter, Cart cart) {
        objWriter.writeResult(cart);
    }
}
