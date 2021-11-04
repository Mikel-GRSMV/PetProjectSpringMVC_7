package ru.folder.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import ru.folder.models.Car;
import ru.folder.models.User;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.util.*;

@Component
public class UsersDaoJdbcTemplateImplements implements UsersDao {
    private JdbcTemplate template;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    //language=SQL
    private final String SQL_SELECT_USERS_WITH_CARS =
            "SELECT users.*, users_car.id as car_id, users_car.model FROM users LEFT JOIN users_car ON users.id = users_car.owner_id";
    //language=SQL
    private final String SQL_SELECT_ALL_BY_FIRST_NAME =
            "SELECT * FROM users WHERE first_name = ?";
    //language=SQL
    private final String SQL_SELECT_BY_ID =
            "SELECT * FROM users WHERE id = :id";

    //language=SQL
    private final String SQL_INSERT_USER =
            "INSERT INTO users(first_name, last_name) VALUES (:firstName, :lastName)";

    @Autowired
    public UsersDaoJdbcTemplateImplements(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    private Map<Long, User> usersMap = new HashMap<>();
    private RowMapper<User> userRowMapperWithoutCars = (resultSet, i) -> User.builder()
            .id(resultSet.getLong("id"))
            .firstName(resultSet.getString("first_name"))
            .lastName(resultSet.getString("last_name"))
            .build();

    private RowMapper<User> userRowMapper = (ResultSet resultSet, int i) -> {
        Long id = resultSet.getLong("id");
        if (!usersMap.containsKey(id)) {
            String firstName = resultSet.getString("first_name");
            String lastName = resultSet.getString("last_name");
            User user = new User(id, firstName, lastName, new ArrayList<>());
            usersMap.put(id, user);
        }
        Car car = new Car(resultSet.getLong("car_id"),
                resultSet.getString("model"), usersMap.get(id));
        usersMap.get(id).getCarList().add(car);
        return usersMap.get(id);
    };

    @Override
    public List<User> findAllByFirstName(String firstName) {
        return template.query(SQL_SELECT_ALL_BY_FIRST_NAME, userRowMapperWithoutCars, firstName);
    }

    @Override
    public Optional<User> find(Long id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        List<User> result = namedParameterJdbcTemplate.query(SQL_SELECT_BY_ID, params, userRowMapperWithoutCars);
        if (result.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(result.get(0));
    }

    @Override
    public void save(User model) {
        Map<String, Object> params = new HashMap<>();
        params.put("firstName", model.getFirstName());
        params.put("lastName", model.getLastName());
        namedParameterJdbcTemplate.update(SQL_INSERT_USER, params);
    }

    @Override
    public void update(User model) {
    }

    @Override
    public void delete(Long id) {
    }

    @Override
    public List<User> findAll() {
        List<User> result = template.query(SQL_SELECT_USERS_WITH_CARS, userRowMapper);
        //usersMap.clear();
        return result;
    }
}