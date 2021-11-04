package ru.folder.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.folder.forms.UserForm;


import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private List<Car> carList;

    public static User from(UserForm userForm){
        return User.builder()
                .firstName(userForm.getFirstName())
                .lastName(userForm.getLastName())
                .build();
    }
}
