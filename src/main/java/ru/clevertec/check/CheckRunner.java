package main.java.ru.clevertec.check;

import main.java.ru.clevertec.controller.CheckController;
import main.java.ru.clevertec.exception.BadRequest;
import main.java.ru.clevertec.util.InputValidator;

public class CheckRunner {
    public static void main(String[] args) {
        try {
            InputValidator.validateInput(args);
        } catch (BadRequest e) {
            // Исключение неверного ввода выведено на экран и записано в файл, выходим из программы
            return;
        }

        CheckController checkController = new CheckController();
        checkController.createCheck(args);
    }
}