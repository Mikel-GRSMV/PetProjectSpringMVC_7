package ru.folder.dao;


import org.springframework.stereotype.Component;
import ru.folder.models.User;

import java.util.List;

public interface UsersDao extends CrudDao<User> {
    List<User> findAllByFirstName(String firstName);
}