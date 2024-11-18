package ru.flamexander.db.interaction.lesson;

import ru.flamexander.db.interaction.lesson.utils.FileService;

import java.io.File;
import java.sql.SQLException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DbMigrator {

    private DataSource dataSource;

    public DbMigrator(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static void migrate(DataSource dataSource) throws SQLException {
        final String migrationsPath = "src/main/resources/migrations/";

        SchemaHistoryDao schemaHistoryDao = new SchemaHistoryDao(dataSource);
        schemaHistoryDao.init();
        List<String> schemaHistoryFileNames = schemaHistoryDao.getAllSchemaHistoryFileNames();
        System.out.println(schemaHistoryFileNames);

        FileService fileService = new FileService();

        // Получение списка всех имен файлов а каталоге миграции
        List<String> filesInCatalog = fileService.getAllFileNames(migrationsPath);
        System.out.println("Список файлов в каталоге: " + filesInCatalog);

        //находим имена не примененных файлов миграций
        List<String> newMigrationFileNames = new ArrayList<>();
        filesInCatalog.stream().forEach(
                fn -> {
                    if (!schemaHistoryFileNames.contains(fn)) {
                        newMigrationFileNames.add(fn);
                    }
                }
        );

        // Поиск файла по имени, вытаскиваем его содержимое, применяем и сохраняем в БД чтобы больше не применять
        for (String newMigrationFileName : newMigrationFileNames) {
            File file = fileService.findFileByName(migrationsPath, newMigrationFileName);
            if (file != null) {
                System.out.println("Файл найден: " + file.getAbsolutePath());
                String fileContent = null;
                try {
                    fileContent = FileService.getFileContent(file);
                    System.out.println("Содержимое файла: " + fileContent);
                    schemaHistoryDao.executeCommand(fileContent);
                    schemaHistoryDao.save(new SchemaHistory(null, file.getName()));
                } catch (IOException e) {
                    System.out.println("Файл не найден.");
                    throw new RuntimeException(e);
                }

            } else {
                System.out.println("Файл не найден.");
            }
        }
        System.out.println(schemaHistoryDao.getAllSchemaHistory());
    }
}
