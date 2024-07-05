package main.java.ru.clevertec.util.currency;

/**
 * Класс USD предназначен для возврата валюты USD (доллара США).
 */
public class USD implements Currency {
    /**
     * Возвращаем валюту USD (доллара США).
     *
     * @return валюту USD (доллара США
     */
    @Override
    public String getSymbol() {
        return "$";
    }
}