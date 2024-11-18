package ru.flamexander.db.interaction.lesson;

import java.sql.SQLException;

public class MockChatServer {
    public static void main(String[] args) {
        DataSource dataSource = null;
        try {
            System.out.println("Сервер чата запущен");
            dataSource = new DataSource("jdbc:h2:file:./db;MODE=PostgreSQL");
            dataSource.connect();

            UsersDao usersDao = new UsersDao(dataSource);
            usersDao.init();
            System.out.println(usersDao.getAllUsers());
            System.out.println("findById 1 = " + usersDao.findById("select * from users where id = ", 1L));
            //usersDao.save(new User(null, "B", "B", "B"));
            System.out.println("findAll" + usersDao.findAll("select * from users"));

            DbMigrator.migrate(dataSource);


//            AuthenticationService authenticationService = new AuthenticationService(usersDao);
//            UsersStatisticService usersStatisticService = new UsersStatisticService(usersDao);
//            BonusService bonusService = new BonusService(dataSource);
//            bonusService.init();

//            authenticationService.register("A", "A", "A");
            // Основная работа сервера чата
        } catch (SQLException e) {
            System.out.println("Сервер чата завершил свою работу c SQLException " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (dataSource != null) {
                dataSource.close();
            }
            System.out.println("Сервер чата завершил свою работу");
        }
    }
}
