package main.java.ru.clevertec.util;

import main.java.ru.clevertec.exception.BadRequest;

import java.io.File;

public class FileValidator {
    public static boolean fileExist(String filePath) throws BadRequest {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new BadRequest("Write the correct path to the file");
        }
        return file.exists();
    }
}
