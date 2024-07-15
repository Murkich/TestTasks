package main.java.ru.clevertec.file.reader;

import main.java.ru.clevertec.exception.InternalServerError;
import main.java.ru.clevertec.model.DiscountCard;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;

import static main.java.ru.clevertec.constants.Constants.*;

public class CSVDiscountCardReader {
    public static DiscountCard readDiscountCardByNumber(String number) throws InternalServerError {
        try (BufferedReader br = new BufferedReader(new FileReader(DISCOUNT_CARDS_FILE))) {
            br.readLine();

            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(DELIMITER);
                if (data[1].equals(number)) {
                    return new DiscountCard.DiscountCardBuilder()
                            .setId(Integer.parseInt(data[0]))
                            .setNumber(data[1])
                            .setDiscount(new BigDecimal(data[2]))
                            .build();
                }
            }
        } catch (FileNotFoundException e) {
            throw new InternalServerError("discountCards.csv file does not found");

        } catch (IOException e) {
            throw new InternalServerError("CSVDiscountCard has an error");
        }

        return new DiscountCard.DiscountCardBuilder()
                .setId(0)
                .setNumber(number)
                .setDiscount(DEFAULT_DISCOUNT)
                .build();
    }
}
