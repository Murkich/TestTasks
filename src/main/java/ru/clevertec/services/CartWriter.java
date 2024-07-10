package main.java.ru.clevertec.services;

import main.java.ru.clevertec.file.writer.factory.Writer;
import main.java.ru.clevertec.file.writer.factory.WriterFactory;
import main.java.ru.clevertec.model.cart.Cart;

public class CartWriter {
    public static void writeResult(Writer writer, Cart cart) {
        writer.writeResult(cart);
    }
}
