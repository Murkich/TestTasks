package main.java.ru.clevertec.file.reader;

import main.java.ru.clevertec.exception.InternalServerError;
import main.java.ru.clevertec.model.discountcard.DiscountCard;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Класс CSVDiscountCardReader предназначен для чтения данных о дисконтных картах из CSV-файла.
 */
public class CSVDiscountCardReader {
    private static final String DISCOUNT_CARDS_FILE = "./src/main/resources/discountCards.csv";
    private static final BigDecimal DEFAULT_DISCOUNT = BigDecimal.valueOf(2).setScale(2, RoundingMode.FLOOR);

    /**
     * Читает данные о дисконтной карте по ее номеру из CSV-файла.
     *
     * @param number номер дисконтной карты
     * @return объект DiscountCard, содержащий данные о дисконтной карте, или null, если карта не найдена
     */
    public static DiscountCard readDiscountCardByNumber(String number) {
        try (BufferedReader br = new BufferedReader(new FileReader(DISCOUNT_CARDS_FILE))) {
            // Пропускаем заголовок файла
            br.readLine();

            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(";");
                if (data[1].equals(number)) {
                    return new DiscountCard.DiscountCardBuilder()
                            .setId(Integer.parseInt(data[0]))
                            .setNumber(data[1])
                            .setDiscount(new BigDecimal(data[2]))
                            .build();
                }
            }
        } catch (FileNotFoundException e) {
            InternalServerError.writeOtherError("discountCards.csv file does not found");
            throw new RuntimeException(e);
        } catch (IOException e) {
            InternalServerError.writeOtherError("CSVDiscountCard has an error");
            throw new RuntimeException(e);
        }

        // Если дисконтная карта не найдена, возвращаем карту с указанной скидкой
        return new DiscountCard.DiscountCardBuilder()
                .setId(0)
                .setNumber(number)
                .setDiscount(DEFAULT_DISCOUNT)
                .build();
    }
}
