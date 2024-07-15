package main.java.ru.clevertec.util;

import main.java.ru.clevertec.exception.BadRequest;

public class QuantityValidator {

    public static void quantityNotEnoughInStock(String description,
                                                int quantityInStock,
                                                int requestedQuantity) throws BadRequest {
        if (requestedQuantity <= quantityInStock)
            return;

        throw new BadRequest("Quantity of " + description + " not enough in stock");
    }
}
