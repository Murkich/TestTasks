package main.java.ru.clevertec.check;

import main.java.ru.clevertec.controller.CheckController;
import main.java.ru.clevertec.exception.InternalServerError;

public class CheckRunner {
    public static void main(String[] args) throws InternalServerError {
        CheckController checkController = new CheckController();
        checkController.createCheck(args);
    }
}