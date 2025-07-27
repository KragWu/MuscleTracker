package fr.kragwu.muscletracker.userapi.adapters;

import fr.kragwu.muscletracker.userapi.services.bo.UserBO;
import fr.kragwu.muscletracker.userapi.repositories.entities.User;

public class UserAdapter {

    public static User transformBOtoDAO(UserBO userBO) {
        return new User(userBO.getId(), userBO.getLogin(), userBO.getPassword(), userBO.getRegistrationDate());
    }

    public static UserBO transformDAOtoBO(User user) {
        return UserBO.builder()
                .id(user.getId())
                .login(user.getLogin())
                .password(user.getPassword())
                .registrationDate(user.getRegistrationDate())
                .build();
    }
}
