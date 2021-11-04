package ru.folder.app;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.folder.dao.UsersDao;
import ru.folder.dao.UsersDaoJdbcTemplateImplements;
import ru.folder.models.User;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUsername("postgres");
        dataSource.setPassword("******");
        dataSource.setUrl("jdbc:postgresql://localhost:5433/Users");

        UsersDao usersDao = new UsersDaoJdbcTemplateImplements(dataSource);

        List<User> users = usersDao.findAll();
        System.out.println(users);
    }
}
