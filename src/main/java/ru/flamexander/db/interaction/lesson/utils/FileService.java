package ru.flamexander.db.interaction.lesson.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class FileService {

    public List<String> getAllFileNames(String directoryPath) {
        List<String> fileNames = new ArrayList<>();
        File directory = new File(directoryPath);

        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        fileNames.add(file.getName());
                    }
                }
            }
        } else {
            System.out.println("Указанный путь не является каталогом.");
        }

        return fileNames;
    }

    public File findFileByName(String directoryPath, String fileName) {
        File directory = new File(directoryPath);

        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().equals(fileName)) {
                        return file;
                    }
                }
            }
        } else {
            System.out.println("Указанный путь не является каталогом.");
        }

        return null;
    }

    public static String getFileContent(File file) throws IOException {
        if (file.exists() && file.isFile()) {
            return Files.readString(file.toPath());
        } else {
            throw new IOException("Файл не существует или является каталогом.");
        }
    }
}