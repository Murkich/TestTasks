package main.java.ru.clevertec.exception;

import main.java.ru.clevertec.file.writer.Writer;

import java.math.BigDecimal;

/**
 * Класс NotEnoughMoney предназначен для проверки достаточности денежных средств и обработки ситуаций,
 * когда денег недостаточно для совершения покупки.
 */
public class NotEnoughMoney {
    private static final String NOT_ENOUGH_MONEY_MESSAGE = "ERROR\nNOT ENOUGH MONEY";
    private static Writer objWriter;

    /**
     * Проверяет, является ли сумма денег отрицательной или нулевой.
     *
     * @param money сумма денег
     * @return исходная сумма денег, если она положительная, иначе -1
     */
    public static BigDecimal validateAmount(BigDecimal money) {
        if (money.compareTo(BigDecimal.ZERO) <= 0) {
            writeNotEnoughMoney(NOT_ENOUGH_MONEY_MESSAGE);
            return BigDecimal.valueOf(-1);
        }
        return money;
    }

    /**
     * Проверяет, достаточно ли денег для совершения покупки.
     *
     * @param availableAmount доступная сумма денег
     * @param totalPrice      общая стоимость покупки
     * @return true, если денег достаточно, иначе false
     */
    public static boolean getNotEnoughMoney(BigDecimal availableAmount, BigDecimal totalPrice) {
        if (availableAmount.compareTo(totalPrice) >= 0) {
            return true;
        }
        writeNotEnoughMoney(NOT_ENOUGH_MONEY_MESSAGE);
        return  false;
    }

    /**
     * Выводит сообщение об ошибке в консоль и записывает его в файл результатов.
     *
     * @param errorMessage сообщение об ошибке
     */
    private static void writeNotEnoughMoney(String errorMessage) {
        System.out.println(errorMessage);
        objWriter.writeError(errorMessage);
    }

    /**
     * Присваивает объект Writer для написания ошибок в определенный файл
     *
     * @param objWriter объект Writer
     */
    public static void setObjWriter(Writer objWriter) {
        NotEnoughMoney.objWriter = objWriter;
    }
}
